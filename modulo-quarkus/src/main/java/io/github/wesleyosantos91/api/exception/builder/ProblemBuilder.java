package io.github.wesleyosantos91.api.exception.builder;

import io.github.wesleyosantos91.api.v1.response.ErrorResponse;
import io.quarkiverse.resteasy.problem.HttpProblem;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProblemBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProblemBuilder.class);
    private static final URI BLANK_TYPE = URI.create("about:blank");
    private static final String TIMESTAMP = "timestamp";

    public static HttpProblem createProblemDetail(
            Exception ex,
            Response.Status status,
            String title,
            String detail,
            URI uriInfo,
            List<ErrorResponse> errors
    ) {
        final var problemDetail = HttpProblem
                .builder()
                .withStatus(status)
                .withTitle(title)
                .withDetail(detail)
                .withType(BLANK_TYPE)
                .withInstance(Objects.nonNull(uriInfo) ? uriInfo : null)
                .with(TIMESTAMP, Instant.now())
                .with("errors", errors)
                .build();

        LOGGER.error("Error occurred: {} - {}", title, detail, ex);
        return problemDetail;
    }
}
