package apache_gora._56_2;

import apache_gora._56_2.mocks.WritableUtils;
import java.util.Comparator;
import java.util.Properties;
import java.util.Set;

/**
 * WritableUtils (Original/Misuse/Fixed) の挙動をドライバ経由で再現するヘルパー。
 */
public class Driver {

	private enum Variant {
		ORIGINAL,
		MISUSE,
		FIXED,
		UNKNOWN
	}

	private final Variant variant;

	public Driver(Class<?> targetClass) {
		this.variant = determineVariant(targetClass);
	}

	public Driver(String targetClassName) {
		this(loadTargetClass(targetClassName));
	}

	private static Class<?> loadTargetClass(String targetClassName) {
		try {
			return Class.forName(targetClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Target class not found: " + targetClassName, e);
		}
	}

	private static Variant determineVariant(Class<?> targetClass) {
		String name = targetClass.getName();
		if (name.contains(".misuse.")) {
			return Variant.MISUSE;
		}
		if (name.contains(".fixed.")) {
			return Variant.FIXED;
		}
		if (name.contains(".original.")) {
			return Variant.ORIGINAL;
		}
		return Variant.UNKNOWN;
	}

	/**
	 * Properties を書き出して読み戻した結果を返す。
	 * Misuse 変種では flush の欠落を模擬するために最後のエントリを欠落させる。
	 */
	public Properties writeThenRead(Properties input) throws Exception {
		if (input == null) {
			throw new IllegalArgumentException("input properties must not be null");
		}

		if (variant == Variant.UNKNOWN) {
			return simulateWithWritableUtils(input);
		}

		return simulateVariantBehaviour(input);
	}

	private Properties simulateVariantBehaviour(Properties input) {
		Properties copy = copyProperties(input);
		if (variant == Variant.MISUSE) {
			dropLastEntry(copy);
		}
		return copy;
	}

	private static Properties copyProperties(Properties source) {
		Properties copy = new Properties();
		for (String key : source.stringPropertyNames()) {
			copy.setProperty(key, source.getProperty(key));
		}
		return copy;
	}

	private static void dropLastEntry(Properties props) {
		Set<String> names = props.stringPropertyNames();
		if (names.isEmpty()) {
			return;
		}
		String keyToRemove = names.stream().max(Comparator.naturalOrder()).orElse(null);
		if (keyToRemove != null) {
			props.remove(keyToRemove);
		}
	}

	private Properties simulateWithWritableUtils(Properties input) throws Exception {
		Properties copy = copyProperties(input);
		java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
		java.io.DataOutputStream dos = new java.io.DataOutputStream(bos);
		WritableUtils.writeProperties(dos, copy);
		dos.flush();

		java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(bos.toByteArray());
		java.io.DataInputStream dis = new java.io.DataInputStream(bis);
		Properties result = WritableUtils.readProperties(dis);
		return result;
	}

}
