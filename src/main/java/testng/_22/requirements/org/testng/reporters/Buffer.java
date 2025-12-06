package testng._22.requirements.org.testng.reporters;

public class Buffer {
  public static IBuffer create() {
    return new FileStringBuffer();
  }
}
