package io.github.wesleyosantos91.api.exception;

import io.github.wesleyosantos91.api.v1.response.CustomProblemDetail;
import io.github.wesleyosantos91.api.v1.response.ErrorResponse;
import io.github.wesleyosantos91.domain.exception.CustomerHasOrdersException;
import io.github.wesleyosantos91.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.ServerHttpObservationFilter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Optional;

import static java.util.List.of;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);
    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        final List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse(fieldError.getField(), messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())))
                .toList();

        final CustomProblemDetail problemDetail =
                new CustomProblemDetail(HttpStatus.BAD_REQUEST,"Validation failed", "The following errors occurred:", errors);
        final HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        ServerHttpObservationFilter.findObservationContext(httpServletRequest).ifPresent(context -> context.setError(ex));

        LOGGER.error("Validation failed: {}", errors);

        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<ProblemDetail> handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {

        final CustomProblemDetail problemDetail =
                new CustomProblemDetail(NOT_FOUND, "Resource not found",
                        "The following errors occurred:", of(new ErrorResponse("error", getRootCauseMessage(ex))));
        problemDetail.setTitle(NOT_FOUND.getReasonPhrase());
        ServerHttpObservationFilter.findObservationContext(request).ifPresent(context -> context.setError(ex));;

        return ResponseEntity.status(NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(CustomerHasOrdersException.class)
    public ResponseEntity<ProblemDetail> handleCustomerHasOrdersException(HttpServletRequest request, CustomerHasOrdersException ex) {

        final var problemDetail = new CustomProblemDetail(HttpStatus.CONFLICT, "Customer has orders",
                "The following errors occurred:", of(new ErrorResponse("error", getRootCauseMessage(ex))));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex) {

        String userMessage = "Conflito ao processar a requisição. Já existe um registro com um valor único.";
        String violatedConstraint = extractConstraintName(ex);
        String field = mapConstraintToField(violatedConstraint);

        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, userMessage);
        problemDetail.setTitle(HttpStatus.CONFLICT.getReasonPhrase());

        if (field != null) {
            problemDetail.setProperty("field", field);
            LOGGER.warn("Violação de constraint conhecida (constraint: {}, campo: {}): {}", violatedConstraint, field, ex.getMessage());
        } else {
            LOGGER.error("Violação de constraint desconhecida: {}", ex.getMessage(), ex);
        }

        ServerHttpObservationFilter.findObservationContext(request).ifPresent(context -> context.setError(ex));;

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    private String extractConstraintName(DataIntegrityViolationException ex) {
        return Optional.ofNullable(ex.getMostSpecificCause())
                .filter(PSQLException.class::isInstance)
                .map(PSQLException.class::cast)
                .map(PSQLException::getServerErrorMessage)
                .map(ServerErrorMessage::getConstraint)
                .orElse(null);
    }

    private String mapConstraintToField(String constraint) {
        return switch (constraint) {
            case "tb_customers_email_key" -> "email";
            default -> null;
        };
    }
}