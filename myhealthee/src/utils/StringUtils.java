package utils;

/**
 * String utils.
 * 
 * @author adlo
 */
public abstract class StringUtils {

	/**
	 * Convert a camel case String into a readable String.
	 * Examples:
	 * 	- MyClass will be converted to My Class
	 * 	- FamilyDoctor will be converted to Family Doctor.
	 * 	- SpecialistDoctor will be converted to Specialist Doctor.
	 * 
	 * @param s the string to convert
	 * @return a camelCase String split.
	 */
	public static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}
}
