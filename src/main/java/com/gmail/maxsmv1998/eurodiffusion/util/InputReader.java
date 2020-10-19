package com.gmail.maxsmv1998.eurodiffusion.util;

import com.gmail.maxsmv1998.eurodiffusion.model.CountryModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.gmail.maxsmv1998.eurodiffusion.constant.Paths.RESOURCES_DIR;

public class InputReader {
    private static final String END_OF_FILE = "0";

    private InputReader() {
    }

    public static List<CountryModel> getInputData(String filepath) {
        List<CountryModel> countries = new ArrayList<>();
        try (Scanner sc = getScanner(filepath)) {
            int countriesNum = sc.nextInt();
            for (int i = 0; i < countriesNum; i++) {
                countries.add(new CountryModel(
                        sc.next(),
                        sc.nextInt(),
                        sc.nextInt(),
                        sc.nextInt(),
                        sc.nextInt()
                ));
            }
            if (!END_OF_FILE.equals(sc.next()) || sc.hasNext()) {
                throw new IllegalArgumentException("Invalid end of file!");
            }
        } catch (NoSuchElementException ex) {
            throw new IllegalArgumentException("Invalid input data!", ex);
        }
        return countries;
    }

    private static Scanner getScanner(String filepath) {
        try {
            return new Scanner(new File(RESOURCES_DIR, filepath));
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("File '" + filepath + "' does not exist!", ex);
        }
    }
}
