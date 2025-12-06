package testng._21.requirements.org.testng.annotations;

import java.util.List;
import testng._21.requirements.org.testng.internal.annotations.IDataProvidable;

/** Encapsulate the @Factory / @testng.factory annotation */
public interface IFactoryAnnotation extends IParameterizable, IDataProvidable {

  List<Integer> getIndices();

  void setIndices(List<Integer> indices);
}
