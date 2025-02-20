package io.github.wesleyosantos91.api.v1.request;

import java.time.Instant;

public record CustomerRequest(
        String name,
        String email,
        Instant createdAt
) {
}
