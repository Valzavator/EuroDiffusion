package com.gmail.maxsmv1998.eurodiffusion.model;

import com.gmail.maxsmv1998.Runner;
import com.gmail.maxsmv1998.eurodiffusion.data.InputCountryData;
import com.gmail.maxsmv1998.eurodiffusion.data.OutputCountryData;
import com.gmail.maxsmv1998.eurodiffusion.data.ResultData;
import com.gmail.maxsmv1998.eurodiffusion.data.TestCaseData;
import com.gmail.maxsmv1998.eurodiffusion.util.comparator.OutputCountryDataComparator;
import com.gmail.maxsmv1998.eurodiffusion.util.validator.CountryValidatorManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COORDINATE;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COUNTRIES;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MIN_COUNTRIES;

public class EuroDiffusionModel {
    private static final Logger LOG = LogManager.getLogger(Runner.class.getSimpleName());

    private final List<CountryModel> countries;
    private MapModel countriesMap;

    public EuroDiffusionModel(TestCaseData testCase) {
        LOG.debug(testCase);
        validateCountries(testCase.getCountries());
        this.countries = convert(testCase.getCountries());
        buildCountriesMap();
    }

    public ResultData simulateEuroDiffusionProcess() {
        List<CityModel> allCities = countries.stream()
                .flatMap(country -> country.getCities().stream())
                .collect(Collectors.toList());
        while (!startNewDay(countries)) {
            prepareTransactionsForCities(allCities);
            executeTransactionsForCities(allCities);
        }
        return getResult(countries);
    }

    private boolean startNewDay(List<CountryModel> countries) {
        countries.forEach(CountryModel::incAmountOfDays);
        return countries.stream()
                .allMatch(CountryModel::isComplete);
    }

    private void prepareTransactionsForCities(List<CityModel> cities) {
        cities.forEach(city -> countriesMap.getNeighbours(city.getX(), city.getY())
                .forEach(neighbour -> city.addTransaction(neighbour.getTransactionForNeighbour()))
        );
    }

    private void executeTransactionsForCities(List<CityModel> cities) {
        cities.forEach(CityModel::executeTransactions);
    }

    private ResultData getResult(List<CountryModel> countries) {
        List<OutputCountryData> result = countries.stream()
                .map(country -> new OutputCountryData(country.getName(), country.getAmountOfDays()))
                .sorted(new OutputCountryDataComparator())
                .collect(Collectors.toList());
        return new ResultData(result);
    }

    private void validateCountries(List<InputCountryData> countries) {
        if (countries.size() < MIN_COUNTRIES || countries.size() > MAX_COUNTRIES) {
            throw new IllegalArgumentException("Invalid number of countries: " + countries.size() +
                    ". It should be in the range " + MIN_COUNTRIES + " to " + MAX_COUNTRIES);
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
                        throw new IllegalArgumentException("Invalid country coordinates. " + country.getUniqueName() +
                                " intersects with " + returnedValue.getCountry().getUniqueName() +
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
                        "Invalid country coordinates. A country must connect with another one: " + currentCountry);
            }
        });
    }

    private boolean cityFromAnotherCountryExist(CountryModel currentCountry, List<CityModel> neighbours) {
        for (CityModel neighbour : neighbours) {
            String cityCountryName = neighbour.getCountry().getUniqueName();
            if (!cityCountryName.equals(currentCountry.getUniqueName())) {
                return true;
            }
        }
        return false;
    }

    private CityModel createCity(CountryModel country, int x, int y) {
        List<String> motifs = countries.stream()
                .map(CountryModel::getUniqueName)
                .collect(Collectors.toList());
        BankModel bank = new BankModel(motifs, country.getUniqueName());
        return CityModel.builder()
                .country(country)
                .bankModel(bank)
                .x(x)
                .y(y)
                .build();
    }

    private List<CountryModel> convert(List<InputCountryData> countries) {
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
