package lambda.school.javacountries.controllers;

import lambda.school.javacountries.models.Country;

@FunctionalInterface
public interface CheckCountry {
    boolean test(Country country);
}
