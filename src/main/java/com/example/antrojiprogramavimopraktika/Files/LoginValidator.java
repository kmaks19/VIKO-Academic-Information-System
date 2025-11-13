package com.example.antrojiprogramavimopraktika.Files;

public final class LoginValidator {

    public ValidationResult validateUserID(String userID) {
        if (userID == null || userID.trim().isEmpty()) {
            return new ValidationResult(
                false,
                "Privalote įvesti naudotojo vardą"
            );
        }
        return new ValidationResult(true, null);
    }

    public ValidationResult validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return new ValidationResult(false, "Privalote įvesti slaptažodį");
        }
        return new ValidationResult(true, null);
    }
}
