package testng._21.original;

import testng._21.requirements.org.testng.IResultMap;
import testng._21.requirements.org.testng.ISuite;
import testng._21.requirements.org.testng.ISuiteResult;
import testng._21.requirements.org.testng.ITestContext;
import testng._21.requirements.org.testng.ITestResult;
import testng._21.requirements.org.testng.collections.ListMultiMap;
import testng._21.requirements.org.testng.collections.Lists;
import testng._21.requirements.org.testng.collections.Maps;
import testng._21.requirements.org.testng.collections.SetMultiMap;
import testng._21.requirements.org.testng.internal.Utils;
import testng._21.requirements.org.testng.reporters.jq.ResultsByClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Model {
  private ListMultiMap<ISuite, ITestResult> m_model = Maps.newListMultiMap();
  private List<ISuite> m_suites = null;
  private Map<String, String> m_testTags = Maps.newHashMap();
  private Map<ITestResult, String> m_testResultMap = Maps.newHashMap();
  private Map<ISuite, ResultsByClass> m_failedResultsByClass = Maps.newHashMap();
  private Map<ISuite, ResultsByClass> m_skippedResultsByClass = Maps.newHashMap();
  private Map<ISuite, ResultsByClass> m_passedResultsByClass = Maps.newHashMap();
  private List<ITestResult> m_allFailedResults = Lists.newArrayList();
  private Map<String, String> m_statusBySuiteName = Maps.newHashMap();
  private SetMultiMap<String, String> m_groupsBySuiteName = Maps.newSetMultiMap();
  private SetMultiMap<String, String> m_methodsByGroup = Maps.newSetMultiMap();

  public Model(List<ISuite> suites) {
    m_suites = suites;
    init();
  }

  public List<ISuite> getSuites() {
    return m_suites;
  }

  // FIXED (Original): Synchronized block around iteration of synchronized map
  private void init() {
    int testCounter = 0;
    for (ISuite suite : m_suites) {
      List<ITestResult> passed = Lists.newArrayList();
      List<ITestResult> failed = Lists.newArrayList();
      List<ITestResult> skipped = Lists.newArrayList();
      Map<String, ISuiteResult> results = suite.getResults();
      synchronized (results) {
        for (ISuiteResult sr : results.values()) {
          ITestContext context = sr.getTestContext();
          m_testTags.put(context.getName(), "test-" + testCounter++);
          failed.addAll(context.getFailedTests().getAllResults());
          skipped.addAll(context.getSkippedTests().getAllResults());
          passed.addAll(context.getPassedTests().getAllResults());
          IResultMap[] map = new IResultMap[] {
              context.getFailedTests(),
              context.getSkippedTests(),
              context.getPassedTests()
          };
          for (IResultMap m : map) {
            for (ITestResult tr : m.getAllResults()) {
              m_testResultMap.put(tr, getTestResultName(tr));
            }
          }
        }
      }

      processResults(suite, passed, failed, skipped);
    }
  }

  private void processResults(ISuite suite, List<ITestResult> passed, 
                              List<ITestResult> failed, List<ITestResult> skipped) {
    // Passed
    {
      ResultsByClass rbc = new ResultsByClass();
      for (ITestResult tr : passed) {
        rbc.addResult(tr.getTestClass().getRealClass(), tr);
        updateGroups(suite, tr);
      }
      m_passedResultsByClass.put(suite, rbc);
    }

    // Skipped
    {
      ResultsByClass rbc = new ResultsByClass();
      for (ITestResult tr : skipped) {
        m_statusBySuiteName.put(suite.getName(), "skipped");
        rbc.addResult(tr.getTestClass().getRealClass(), tr);
        updateGroups(suite, tr);
      }
      m_skippedResultsByClass.put(suite, rbc);
    }

    // Failed
    {
      ResultsByClass rbc = new ResultsByClass();
      for (ITestResult tr : failed) {
        m_statusBySuiteName.put(suite.getName(), "failed");
        rbc.addResult(tr.getTestClass().getRealClass(), tr);
        m_allFailedResults.add(tr);
        updateGroups(suite, tr);
      }
      m_failedResultsByClass.put(suite, rbc);
    }

    m_model.putAll(suite, failed);
    m_model.putAll(suite, skipped);
    m_model.putAll(suite, passed);
  }

  private void updateGroups(ISuite suite, ITestResult tr) {
    String[] groups = tr.getMethod().getGroups();
    m_groupsBySuiteName.putAll(suite.getName(), Arrays.asList(groups));
    for (String group : groups) {
      m_methodsByGroup.put(group, tr.getMethod().getMethodName());
    }
  }

  public static String getTestResultName(ITestResult tr) {
    StringBuilder result = new StringBuilder(tr.getMethod().getMethodName());
    Object[] parameters = tr.getParameters();
    if (parameters.length > 0) {
      result.append("(");
      StringBuilder p = new StringBuilder();
      for (int i = 0; i < parameters.length; i++) {
        if (i > 0) p.append(", ");
        p.append(Utils.toString(parameters[i]));
      }
      if (p.length() > 100) {
        result.append(p.toString().substring(0, 100)).append("...");
      } else {
        result.append(p.toString());
      }
      result.append(")");
    }
    return result.toString();
  }

  public List<ITestResult> getAllFailedResults() {
    return m_allFailedResults;
  }

  public String getStatusForSuite(String suiteName) {
    String result = m_statusBySuiteName.get(suiteName);
    return result != null ? result : "passed";
  }

  public <T> Set<T> nonnullSet(Set<T> l) {
    return l != null ? l : Collections.<T>emptySet();
  }

  public <T> List<T> nonnullList(List<T> l) {
    return l != null ? l : Collections.<T>emptyList();
  }
}
