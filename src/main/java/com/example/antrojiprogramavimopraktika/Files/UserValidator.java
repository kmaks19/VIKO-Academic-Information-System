package com.example.antrojiprogramavimopraktika.Files;

import java.time.LocalDate;

public final class UserValidator {

    public static String validate(String firstName, String lastName, String email, LocalDate birthDate) {

        if (firstName == null || firstName.trim().isBlank()) {
            return "Vardas negali būti tuščias";
        }

        if (lastName == null || lastName.trim().isBlank()) {
            return "Pavardė negali būti tuščia";
        }

        if (email == null || email.trim().isBlank()) {
            return "El. paštas negali būti tuščias";
        }

        if (birthDate == null) {
            return "Privalote pasirinkti gimimo datą";
        }

        if (birthDate.isAfter(LocalDate.now())) {
            return "Gimimo data negali būti vėlesnė nei šiandien";
        }

        return null; // Valid
    }
}