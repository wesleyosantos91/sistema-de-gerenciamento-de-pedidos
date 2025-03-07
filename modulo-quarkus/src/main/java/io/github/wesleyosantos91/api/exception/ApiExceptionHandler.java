package io.github.wesleyosantos91.api.exception;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

import io.github.wesleyosantos91.api.exception.builder.ProblemBuilder;
import io.github.wesleyosantos91.api.v1.response.ErrorResponse;
import io.github.wesleyosantos91.domain.exception.CustomerHasOrdersException;
import io.github.wesleyosantos91.domain.exception.ResourceNotFoundException;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.exception.ConstraintViolationException;

@Provider
@ApplicationScoped
@Priority(1)
public class ApiExceptionHandler {


    @Provider
    public static class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

        @Context
        private UriInfo uriInfo;

        @Override
        public Response toResponse(ResourceNotFoundException exception) {

            final var problemDetail = ProblemBuilder.createProblemDetail(
                    exception,
                    Response.Status.NOT_FOUND,
                    "Resource not found",
                    exception.getMessage(),
                    URI.create(uriInfo.getPath()),
                    List.of(new ErrorResponse("error", exception.getMessage()))
            );

            return Response.status(Response.Status.NOT_FOUND).entity(problemDetail).build();
        }
    }

    @Provider
    public static class CustomerHasOrdersExceptionMapper implements ExceptionMapper<CustomerHasOrdersException> {

        @Context
        private UriInfo uriInfo;

        @Override
        public Response toResponse(CustomerHasOrdersException exception) {
            final var problemDetail = ProblemBuilder.createProblemDetail(
                    exception,
                    Response.Status.CONFLICT,
                    "Conflict",
                    exception.getMessage(),
                    URI.create(uriInfo.getPath()),
                    List.of(new ErrorResponse("error", exception.getMessage()))
            );

            return Response.status(Response.Status.CONFLICT).entity(problemDetail).build();
        }
    }

    @Provider
    public static class DataIntegrityViolationExceptionMapper implements ExceptionMapper<PersistenceException> {

        @Context
        private UriInfo uriInfo;

        @Override
        public Response toResponse(PersistenceException exception) {
            if (exception instanceof ConstraintViolationException) {

                final var rootCauseMessage = getRootCauseMessage(exception);

                Pattern pattern = Pattern.compile("Key \\(.*\\)=\\(.*\\) already exists\\.");

                Matcher matcher = pattern.matcher(rootCauseMessage);

                if (matcher.find()) {

                    final var problemDetail = ProblemBuilder.createProblemDetail(
                            exception,
                            Response.Status.CONFLICT,
                            "Data Integrity Violation",
                            "The following errors occurred:",
                            URI.create(uriInfo.getPath()),
                            List.of(new ErrorResponse("error", matcher.group()))
                    );

                   return Response.status(Response.Status.CONFLICT).entity(problemDetail).build();
                }

                final var problemDetail = ProblemBuilder.createProblemDetail(
                        exception,
                        Response.Status.CONFLICT,
                        "Data Integrity Violation",
                        "The following errors occurred:",
                        URI.create(uriInfo.getPath()),
                        List.of(new ErrorResponse("error", rootCauseMessage))
                );

                return Response.status(Response.Status.CONFLICT).entity(problemDetail).build();
            }

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unexpected persistence error")
                    .build();
        }
    }
}
