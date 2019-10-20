package com.collab.translation.models;

import java.util.Arrays;
import java.util.Objects;

public class Validation {
    private final String value;
    private final String[] validations;

    private Validation(String value, String[] validations) {
        this.value = value;
        this.validations = validations;
    }

    public static Validation of(String value, String ...causes) {
        return new Validation(value, causes);
    }

    public String getValue() {
        return value;
    }

    public String[] getValidations() {
        return validations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Validation that = (Validation) o;
        return Objects.equals(value, that.value) &&
                Arrays.equals(validations, that.validations);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(value);
        result = 31 * result + Arrays.hashCode(validations);
        return result;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "value='" + value + '\'' +
                ", validations=" + Arrays.toString(validations) +
                '}';
    }
}
