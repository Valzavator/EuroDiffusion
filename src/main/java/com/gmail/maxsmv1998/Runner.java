package com.gmail.maxsmv1998;

import com.gmail.maxsmv1998.eurodiffusion.data.TestCaseData;
import com.gmail.maxsmv1998.eurodiffusion.model.EuroDiffusionModel;
import com.gmail.maxsmv1998.eurodiffusion.model.ResultModel;
import com.gmail.maxsmv1998.eurodiffusion.util.InputReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Runner {
    private static final Logger LOG = LogManager.getLogger(Runner.class.getSimpleName());
    private static final String FILE_NAME = "testCases.txt";

    public static void main(String[] args) {
        List<TestCaseData> testCases = InputReader.getInputData(FILE_NAME);
        for (TestCaseData testCase : testCases) {
            try {
                LOG.info(testCase);
                ResultModel resultModel = new EuroDiffusionModel(testCase.getCountries())
                        .simulateEuroDiffusionProcess();
                LOG.info(resultModel);
            } catch (Exception ex) {
                LOG.error(ex);
            }
        }
    }

}
