package io.github.wesleyosantos91.api.v1.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.Instant;
import java.util.List;

public class CustomProblemDetail extends ProblemDetail {

    private static final String TIMESTAMP = "timestamp";

    public CustomProblemDetail(HttpStatus status, String title, String detail, List<ErrorResponse> errors) {
        this.setStatus(status.value());
        this.setTitle(title);
        this.setDetail(detail);
        this.setProperty(TIMESTAMP, Instant.now());
        this.setProperty("errors", errors);
    }
}