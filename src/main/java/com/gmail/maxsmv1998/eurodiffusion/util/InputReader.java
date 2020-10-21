package com.gmail.maxsmv1998.eurodiffusion.util;

import com.gmail.maxsmv1998.eurodiffusion.data.CountryData;
import com.gmail.maxsmv1998.eurodiffusion.data.TestCaseData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.gmail.maxsmv1998.eurodiffusion.constant.Paths.RESOURCES_DIR;

public class InputReader {

    private InputReader() {
    }

    public static List<TestCaseData> getInputData(String filepath) {
        List<TestCaseData> testCases = new ArrayList<>();
        int countriesNum;
        try (Scanner sc = getScanner(filepath)) {
            while ((countriesNum = sc.nextInt()) != 0) {
                testCases.add(getTestCase(sc, countriesNum));
            }
            checkEndOfFile(sc);
            return testCases;
        } catch (NoSuchElementException ex) {
            throw new IllegalArgumentException("Invalid input data!", ex);
        }
    }

    private static TestCaseData getTestCase(Scanner sc, int countriesNum) {
        List<CountryData> countries = new ArrayList<>();
        for (int i = 0; i < countriesNum; i++) {
            countries.add(CountryData.builder()
                    .name(sc.next())
                    .xl(sc.nextInt())
                    .yl(sc.nextInt())
                    .xh(sc.nextInt())
                    .yh(sc.nextInt())
                    .build());
        }
        return new TestCaseData(countries);
    }

    private static Scanner getScanner(String filepath) {
        try {
            return new Scanner(new File(RESOURCES_DIR, filepath));
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("File '" + filepath + "' does not exist!", ex);
        }
    }

    private static void checkEndOfFile(Scanner sc) {
        if (sc.hasNext()) {
            throw new IllegalArgumentException("Invalid end of file!");
        }
    }
}
