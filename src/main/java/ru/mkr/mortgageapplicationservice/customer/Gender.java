package ru.mkr.mortgageapplicationservice.customer;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum Gender {
    MALE,
    FEMALE
}
