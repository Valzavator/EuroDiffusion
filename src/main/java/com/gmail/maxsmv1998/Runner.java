package com.gmail.maxsmv1998;

import com.gmail.maxsmv1998.eurodiffusion.data.TestCaseData;
import com.gmail.maxsmv1998.eurodiffusion.model.EuroDiffusionModel;
import com.gmail.maxsmv1998.eurodiffusion.util.InputReader;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        List<TestCaseData> testCases = InputReader.getInputData("case1.txt");
        for (TestCaseData testCase : testCases) {
            System.out.println(testCase);
            new EuroDiffusionModel(testCase.getCountries());
        }
    }

}
