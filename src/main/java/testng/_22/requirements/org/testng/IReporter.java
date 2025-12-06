package testng._22.requirements.org.testng;

import testng._22.requirements.org.testng.xml.XmlSuite;
import java.util.List;

/**
 * This interface allows you to modify the final test reports.
 */
public interface IReporter extends ITestNGListener {
    /**
     * Generate a report for the given suites into the specified output directory.
     */
    void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory);
}
