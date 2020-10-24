package com.gmail.maxsmv1998;

import com.gmail.maxsmv1998.eurodiffusion.data.TestCaseData;
import com.gmail.maxsmv1998.eurodiffusion.model.EuroDiffusionModel;
import com.gmail.maxsmv1998.eurodiffusion.data.ResultData;
import com.gmail.maxsmv1998.eurodiffusion.util.InputReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static com.gmail.maxsmv1998.eurodiffusion.constant.Paths.RESOURCES_DIR;

public class Runner {
    private static final Logger LOG = LogManager.getLogger(Runner.class.getSimpleName());
    private static final String INPUT_FILE_NAME = "testCases.txt";
    private static final String OUTPUT_FILE_NAME = "results.txt";

    public static void main(String[] args) {
        try {
            execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error(ex.getMessage());
        }
    }

    private static void execute() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(RESOURCES_DIR, OUTPUT_FILE_NAME));
        List<TestCaseData> testCases = InputReader.getInputData(INPUT_FILE_NAME);
        for (TestCaseData testCase : testCases) {
            pw.println("Case Number " + testCase.getNumber());
            try {
                ResultData result = new EuroDiffusionModel(testCase)
                        .simulateEuroDiffusionProcess();
                pw.println(result);
            } catch (IllegalArgumentException ex) {
                LOG.error(ex.getMessage());
                pw.println(ex.getMessage());
            }
        }
        pw.close();
    }

}
