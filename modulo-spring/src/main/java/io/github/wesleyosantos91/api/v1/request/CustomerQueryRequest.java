package io.github.wesleyosantos91.api.v1.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.wesleyosantos91.core.validation.Groups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CustomerQueryRequest(
        @NotBlank(groups = Groups.Create.class)
        @Size(min = 2, max = 120, groups = Groups.Create.class)
        String name,
        @NotBlank(groups = Groups.Create.class)
        @Email(groups = Groups.Create.class)
        String email
) {
}
