package io.github.wesleyosantos91.api.exception;

import io.github.wesleyosantos91.domain.exception.CustomerHasOrdersException;
import io.github.wesleyosantos91.domain.exception.ResourceNotFoundException;
import io.quarkiverse.resteasy.problem.HttpProblem;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@ApplicationScoped
@Priority(1)
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @Provider
    public static class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
        @Override
        public Response toResponse(ResourceNotFoundException exception) {
            final var problemDetail = HttpProblem
                    .builder()
                    .withStatus(Response.Status.NOT_FOUND)
                    .withTitle("Resource not found")
                    .withDetail(exception.getMessage())
                    .build();
            return Response.status(Response.Status.NOT_FOUND).entity(problemDetail).build();
        }
    }

    @Provider
    public static class CustomerHasOrdersExceptionMapper implements ExceptionMapper<CustomerHasOrdersException> {
        @Override
        public Response toResponse(CustomerHasOrdersException exception) {
            final var problemDetail = HttpProblem
                    .builder()
                    .withStatus(Response.Status.CONFLICT)
                    .withTitle("Conflict")
                    .withDetail(exception.getMessage())
                    .build();
            return Response.status(Response.Status.CONFLICT).entity(problemDetail).build();
        }
    }

    @Provider
    public static class DataIntegrityViolationExceptionMapper implements ExceptionMapper<PersistenceException> {
        @Override
        public Response toResponse(PersistenceException exception) {
            if (exception instanceof ConstraintViolationException) {
                final var problemDetail = HttpProblem
                        .builder()
                        .withStatus(Response.Status.CONFLICT)
                        .withTitle("Data Integrity Violation")
                        .withDetail(exception.getMessage())
                        .build();

                return Response.status(Response.Status.CONFLICT).entity(problemDetail).build();
            }

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unexpected persistence error")
                    .build();
        }
    }
}
