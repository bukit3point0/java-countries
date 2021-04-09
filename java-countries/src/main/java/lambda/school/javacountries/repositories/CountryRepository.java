package lambda.school.javacountries.repositories;

import lambda.school.javacountries.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
