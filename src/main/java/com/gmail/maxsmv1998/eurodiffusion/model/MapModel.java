package com.gmail.maxsmv1998.eurodiffusion.model;

import com.gmail.maxsmv1998.eurodiffusion.util.validator.impl.NumberValidator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MIN_COORDINATE;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COORDINATE;

public class MapModel implements Serializable {
    private static final long serialVersionUID = -6080451792203424960L;

    private static final NumberValidator initialCoordinateValidator = new NumberValidator(MIN_COORDINATE, MAX_COORDINATE);

    private final NumberValidator actualXCoordinateValidator;
    private final NumberValidator actualYCoordinateValidator;

    private final String[][] countriesMap;
    private final int lengthX;
    private final int lengthY;

    public MapModel(int lengthX, int lengthY) {
        this.lengthX = requireWithinRestrictions(lengthX);
        this.lengthY = requireWithinRestrictions(lengthY);
        this.countriesMap = new String[lengthY][lengthX];

        this.actualXCoordinateValidator = new NumberValidator(MIN_COORDINATE, lengthX);
        this.actualYCoordinateValidator = new NumberValidator(MIN_COORDINATE, lengthY);
    }

    public String set(int x, int y, String value) {
        checkCoordinates(x, y);

        int convertX = convertCoordinate(x);
        int convertY = convertCoordinate(y);
        String valueToReturn = countriesMap[convertY][convertX];
        countriesMap[convertY][convertX] = value;
        return valueToReturn;
    }

    public int getLengthX() {
        return lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = lengthY - 1; y >= 0; y--) {
            for (int x = 0; x < lengthX; x++) {
                sb.append(String.format("%-15s", countriesMap[y][x]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapModel mapModel = (MapModel) o;
        return lengthX == mapModel.lengthX &&
                lengthY == mapModel.lengthY &&
                Arrays.equals(countriesMap, mapModel.countriesMap);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(lengthX, lengthY);
        result = 31 * result + Arrays.hashCode(countriesMap);
        return result;
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
