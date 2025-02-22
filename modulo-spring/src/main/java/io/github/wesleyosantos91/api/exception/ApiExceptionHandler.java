package io.github.wesleyosantos91.api.exception;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import static java.util.List.of;

import io.github.wesleyosantos91.api.v1.response.CustomProblemDetail;
import io.github.wesleyosantos91.api.v1.response.ErrorResponse;
import io.github.wesleyosantos91.domain.exception.CustomerHasOrdersException;
import io.github.wesleyosantos91.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.ServerHttpObservationFilter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

        final var rootCauseMessage = getRootCauseMessage(ex);

        Pattern pattern = Pattern.compile("Key \\(.*\\)=\\(.*\\) already exists\\.");

        Matcher matcher = pattern.matcher(rootCauseMessage);

        if (matcher.find()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomProblemDetail(HttpStatus.CONFLICT, "Data integrity violation",
                            "The following errors occurred:", of(new ErrorResponse("error", matcher.group()))));
        }

        final var problemDetail = new CustomProblemDetail(HttpStatus.CONFLICT, "Data integrity violation",
                "The following errors occurred:", of(new ErrorResponse("error", rootCauseMessage)));
        ServerHttpObservationFilter
                .findObservationContext(request).ifPresent(context -> context.setError(ex));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

}