package io.github.wesleyosantos91.core.validation;

import jakarta.validation.groups.Default;

public interface Groups {

    interface Create extends Default {}

    interface Update extends Default {}
}