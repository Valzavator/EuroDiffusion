package com.gmail.maxsmv1998.eurodiffusion.model;

import com.gmail.maxsmv1998.Runner;
import com.gmail.maxsmv1998.eurodiffusion.data.CountryData;
import com.gmail.maxsmv1998.eurodiffusion.util.validator.CountryValidatorManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COORDINATE;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COUNTRIES;

public class EuroDiffusionModel {
    private static final Logger LOG = LogManager.getLogger(Runner.class.getSimpleName());

    private final List<CountryModel> countries;
    private MapModel countriesMap;

    public EuroDiffusionModel(List<CountryData> countries) {
        validateCountries(countries);
        this.countries = convert(countries);
        buildCountriesMap();
        simulateEuroDiffusionProcess();
    }

    public ResultModel simulateEuroDiffusionProcess() {
        List<CityModel> allCities = countries.stream()
                .flatMap(country -> country.getCities().stream())
                .collect(Collectors.toList());
        while (!checkComplete(countries)) {
            prepareTransactionsForCities(allCities);
            executeTransactionsForCities(allCities);
            countries.forEach(CountryModel::incAmountOfDays);
        }
        return getResult(countries);
    }

    private boolean checkComplete(List<CountryModel> countries) {
        for (CountryModel country : countries) {
            if (!country.isComplete()) {
                return false;
            }
        }
        return true;
    }

    private void prepareTransactionsForCities(List<CityModel> cities) {
        cities.forEach(city -> countriesMap.getNeighbours(city.getX(), city.getY())
                .forEach(neighbour -> city.addTransaction(neighbour.getTransactionForNeighbour()))
        );
    }

    private void executeTransactionsForCities(List<CityModel> cities) {
        cities.forEach(CityModel::executeTransactions);
    }

    private ResultModel getResult(List<CountryModel> countries) {
        countries.forEach(c -> LOG.info("{0} - {1}", c.getName(), c.getAmountOfDays()));
        // TODO
        return null;
    }

    private void validateCountries(List<CountryData> countries) {
        if (countries.size() > MAX_COUNTRIES) {
            throw new IllegalArgumentException("The maximum number of countries allowed is " +
                    MAX_COUNTRIES + ". But given " + countries.size() + "instead.");
        }
        countries.forEach(country -> {
            List<String> errors = CountryValidatorManager.validate(country);
            if (!errors.isEmpty()) {
                throw new IllegalArgumentException("Invalid country [" + country + "]. Errors: " + errors);
            }
        });
    }

    private void buildCountriesMap() {
        int lengthX = countries.stream().mapToInt(CountryModel::getXh).max().orElse(MAX_COORDINATE);
        int lengthY = countries.stream().mapToInt(CountryModel::getYh).max().orElse(MAX_COORDINATE);
        countriesMap = new MapModel(lengthX, lengthY);
        countries.forEach(country -> {
            for (int x = country.getXl(); x <= country.getXh(); x++) {
                for (int y = country.getYl(); y <= country.getYh(); y++) {
                    CityModel city = createCity(country, x, y);
                    country.addCity(city);
                    CityModel returnedValue = countriesMap.set(x, y, city);
                    if (Objects.nonNull(returnedValue)) {
                        throw new IllegalArgumentException("Invalid countries coordinates. " +
                                country.getName() + " intersects with " + returnedValue.getCountry().getName() +
                                " at [" + x + "," + y + "]");
                    }
                }
            }
        });
        LOG.debug(countriesMap);
        checkConnectionsBetweenCountries();
    }

    private void checkConnectionsBetweenCountries() {
        if (countries.size() == 1) {
            return;
        }
        countries.forEach(currentCountry -> {
            boolean cityFromAnotherCountryExist = false;
            for (CityModel currentCity : currentCountry.getCities()) {
                List<CityModel> neighbours = countriesMap.getNeighbours(currentCity.getX(), currentCity.getY());
                if (cityFromAnotherCountryExist(currentCountry, neighbours)) {
                    cityFromAnotherCountryExist = true;
                    break;
                }
            }
            if (!cityFromAnotherCountryExist) {
                throw new IllegalArgumentException(
                        "Invalid countries coordinates. A country must connect with another one: " + currentCountry);
            }
        });
    }

    private boolean cityFromAnotherCountryExist(CountryModel currentCountry, List<CityModel> neighbours) {
        for (CityModel neighbour : neighbours) {
            String cityCountryName = neighbour.getCountry().getName();
            if (!cityCountryName.equals(currentCountry.getName())) {
                return true;
            }
        }
        return false;
    }

    private CityModel createCity(CountryModel country, int x, int y) {
        List<String> motifs = countries.stream()
                .map(CountryModel::getName)
                .collect(Collectors.toList());
        BankModel bank = new BankModel(motifs, country.getName());
        return CityModel.builder()
                .country(country)
                .bankModel(bank)
                .x(x)
                .y(y)
                .build();
    }

    private List<CountryModel> convert(List<CountryData> countries) {
        return countries.stream()
                .map(countryData ->
                        CountryModel.builder()
                                .name(countryData.getName())
                                .xl(countryData.getXl())
                                .yl(countryData.getYl())
                                .xh(countryData.getXh())
                                .yh(countryData.getYh())
                                .build())
                .collect(Collectors.toUnmodifiableList());
    }

}
