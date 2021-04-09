package lambda.school.javacountries.controllers;

import lambda.school.javacountries.models.Country;
import lambda.school.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    // list all countries
    // http://localhost:2021/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> findAllCountries() {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(c -> countries.add(c));

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    // return countries alphabetically at a certain letter
    // http://localhost:2021/names/start/{letter}
    @GetMapping(value = "names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> findByFirstLetter(@PathVariable char letter) {
        List <Country> countryByLetter = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(c -> countryByLetter.add(c));

        List<Country> filteredCountries = filterCountries(countryByLetter,
                (c) -> c.getName().charAt(0) == letter
        );

        return new ResponseEntity<>(filteredCountries, HttpStatus.OK);
    }

    // return the total population of all countries
    // http://localhost:2021/population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> findTotalPopulation() {
        List<Country> countryTotal = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(c -> countryTotal.add(c));

        long total = 0;
        for (Country c : countryTotal) {
            total += c.getPopulation();
        }

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    // return country with the smallest population
    // http://localhost:2021/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> findSmallestPopulation() {
        List<Country> findSmallestCountry = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(c -> findSmallestCountry.add(c));

        Country smallestPopulation = findSmallestCountry.get(0);
        for (Country c : findSmallestCountry) {
            if (c.getPopulation() < smallestPopulation.getPopulation()) {
                smallestPopulation = c;
            }
        }

        return new ResponseEntity<>(smallestPopulation, HttpStatus.OK);
    }

    // return country with the largest population
    // http://localhost:2021/population/max

    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> findLargestPopulation() {
        List<Country> findLargestCountry = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(c -> findLargestCountry.add(c));

        Country largestPopulation = findLargestCountry.get(0);
        for (Country c : findLargestCountry) {
            if (c.getPopulation() > largestPopulation.getPopulation()) {
                largestPopulation = c;
            }
        }

        return new ResponseEntity<>(largestPopulation, HttpStatus.OK);
    }

    private List<Country> filterCountries(List<Country> countryByLetter, CheckCountry tester) {
        List<Country> returnList = new ArrayList<>();

        for(Country c : countryByLetter) {
            if (tester.test(c)) {
                returnList.add(c);
            }
        }

        return returnList;
    }
};
