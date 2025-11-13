package com.example.antrojiprogramavimopraktika.Files;

public final class ValidationResult {

    private final boolean isValid;
    private final String errorMessage;

    public ValidationResult(boolean isValid, String errorMessage) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }

    public boolean isInvalid() {
        return !isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
