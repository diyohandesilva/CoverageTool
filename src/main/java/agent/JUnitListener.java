package agent;

import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class JUnitListener extends RunListener {

    public void testRunStarted(Description description) throws Exception {

        if (null == CollectCoverage.testCases) {
            CollectCoverage.testCases = new HashMap<String,HashMap<String, IntLinkedOpenHashSet>>();
        }

        // System.out.println("\n\nStart!");
    }

    public void testStarted(Description description) {
        System.out.println("\n\n\nStarting .... " + description.getMethodName());
        CollectCoverage.testName = "" + description.getClassName() + ":" + description.getMethodName();
        CollectCoverage.linesCovered = new HashMap<String, IntLinkedOpenHashSet>();
    }
    public void testFinished(Description description) {
        System.out.println("Finished .... "+ description.getMethodName());
        CollectCoverage.testCases.put(CollectCoverage.testName, CollectCoverage.linesCovered);
    }
    public void testRunFinished(Result result) throws IOException {
        System.out.println("Done\n\n");
        // Write to stmt-cov.txt
        File fout = new File("stmt-cov.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        StringBuilder builder = new StringBuilder();
        for (String testCaseName : CollectCoverage.testCases.keySet()) {
            builder.append(testCaseName + "\n");

            HashMap<String, IntLinkedOpenHashSet> caseCoverage =
                    CollectCoverage.testCases.get(testCaseName);

            for (String className : caseCoverage.keySet()) {
                int[] lines = caseCoverage.get(className).toIntArray();

                Arrays.sort(lines);
                for (int i = 0; i < lines.length; i++) {
                    builder.append(className + ":" + lines[i] + "\n");
                }
            }
        }

        bw.write(builder.toString());
        bw.close();
    }

}