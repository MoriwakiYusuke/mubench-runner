package testng._17.requirements.org.testng.xml;

import static testng._17.requirements.org.testng.collections.CollectionUtils.hasElements;

import java.util.List;
import testng._17.requirements.org.testng.collections.Lists;
import testng._17.requirements.org.testng.reporters.XMLStringBuffer;

public class XmlMethodSelectors {

  private List<XmlMethodSelector> m_methodSelectors = Lists.newArrayList();

  public XmlMethodSelectors() {}

  public List<XmlMethodSelector> getMethodSelectors() {
    return m_methodSelectors;
  }

  public void setMethodSelector(XmlMethodSelector xms) {
    m_methodSelectors.add(xms);
  }

  public String toXml(String indent) {
    XMLStringBuffer xsb = new XMLStringBuffer(indent);
    if (hasElements(m_methodSelectors)) {
      xsb.push("method-selectors");
      for (XmlMethodSelector selector : m_methodSelectors) {
        xsb.getStringBuffer().append(selector.toXml(indent + "  "));
      }

      xsb.pop("method-selectors");
    }
    return xsb.toXML();
  }
}
