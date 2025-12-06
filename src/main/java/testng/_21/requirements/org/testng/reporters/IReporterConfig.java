package testng._21.requirements.org.testng.reporters;

import java.util.List;
import testng._21.requirements.org.testng.internal.PropertyUtils;
import testng._21.requirements.org.testng.internal.ReporterConfig;

public interface IReporterConfig {

  default void setProperties(List<ReporterConfig.Property> properties) {
    for (ReporterConfig.Property property : properties) {
      setProperty(property.getName(), property.getValue());
    }
  }

  default void setProperty(String name, String value) {
    PropertyUtils.setProperty(this, name, value);
  }
}
