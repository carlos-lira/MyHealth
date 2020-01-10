package utils;

/**
 * String utils.
 * 
 * @author adlo
 */
public final class StringUtils {

	/**
	 * Hide constructor
	 */
	private StringUtils() {
		// nop
	}

	/**
	 * Convert a camel case String into a readable String. Examples: - MyClass will
	 * be converted to My Class - FamilyDoctor will be converted to Family Doctor. -
	 * SpecialistDoctor will be converted to Specialist Doctor.
	 * 
	 * @param s the string to convert
	 * @return a camelCase String split.
	 */
	public static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}

	/**
	 * Check if 2 string are equals
	 * 
	 * @param s1 the first string to check
	 * @param s2 the second string to check
	 * @return true if the two strings are equal, false otherwise.
	 */
	public static boolean isSameString(String s1, String s2) {
		return s1 != null && s1.equals(s2);
	}
}
