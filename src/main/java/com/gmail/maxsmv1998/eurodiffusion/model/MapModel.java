package com.gmail.maxsmv1998.eurodiffusion.model;

import com.gmail.maxsmv1998.eurodiffusion.util.validator.impl.NumberValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MIN_COORDINATE;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COORDINATE;

@Value
class MapModel {
    private static final NumberValidator initialCoordinateValidator = new NumberValidator(MIN_COORDINATE, MAX_COORDINATE);
    private static final String HOLE = "(empty)";

    @Getter(AccessLevel.NONE)
    NumberValidator actualXCoordinateValidator;
    @Getter(AccessLevel.NONE)
    NumberValidator actualYCoordinateValidator;

    @Getter(AccessLevel.NONE)
    CityModel[][] countriesMap;
    int lengthX;
    int lengthY;

    public MapModel(int lengthX, int lengthY) {
        this.lengthX = requireWithinRestrictions(lengthX);
        this.lengthY = requireWithinRestrictions(lengthY);
        this.countriesMap = new CityModel[lengthY][lengthX];

        this.actualXCoordinateValidator = new NumberValidator(MIN_COORDINATE, lengthX);
        this.actualYCoordinateValidator = new NumberValidator(MIN_COORDINATE, lengthY);
    }

    public CityModel set(int x, int y, CityModel city) {
        checkCoordinates(x, y);
        int convertedX = convertCoordinate(x);
        int convertedY = convertCoordinate(y);
        CityModel valueToReturn = countriesMap[convertedY][convertedX];
        countriesMap[convertedY][convertedX] = city;
        return valueToReturn;
    }

    public CityModel get(int x, int y) {
        checkCoordinates(x, y);
        int convertedX = convertCoordinate(x);
        int convertedY = convertCoordinate(y);
        return countriesMap[convertedY][convertedX];
    }

    public List<CityModel> getNeighbours(int x, int y) {
        checkCoordinates(x, y);
        List<CityModel> neighbours = new ArrayList<>();
        addNeighbourIfExist(neighbours, x - 1, y);
        addNeighbourIfExist(neighbours, x + 1, y);
        addNeighbourIfExist(neighbours, x, y - 1);
        addNeighbourIfExist(neighbours, x, y + 1);
        return neighbours;
    }

    private void addNeighbourIfExist(List<CityModel> neighbours, int x, int y) {
        if (actualXCoordinateValidator.isInvalid(x)
                || actualYCoordinateValidator.isInvalid(y)) {
            return;
        }
        int convertedX = convertCoordinate(x);
        int convertedY = convertCoordinate(y);
        CityModel city = countriesMap[convertedY][convertedX];
        if (Objects.nonNull(city)) {
            neighbours.add(city);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = lengthY - 1; y >= 0; y--) {
            for (int x = 0; x < lengthX; x++) {
                String name = Objects.nonNull(countriesMap[y][x])
                        ? countriesMap[y][x].getCountry().getName()
                        : HOLE;
                sb.append(String.format("%-15s", name));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private int convertCoordinate(int coordinate) {
        return --coordinate;
    }

    private int requireWithinRestrictions(int coordinate) {
        if (initialCoordinateValidator.isInvalid(coordinate)) {
            throw new IllegalArgumentException("Invalid coordinate: " + coordinate);
        }
        return coordinate;
    }

    private void checkCoordinates(int x, int y) {
        if (actualXCoordinateValidator.isInvalid(x)) {
            throw new IllegalArgumentException("Invalid X coordinate: " + x);
        }
        if (actualYCoordinateValidator.isInvalid(y)) {
            throw new IllegalArgumentException("Invalid Y coordinate: " + y);
        }
    }

}
