package net.javaguides.springboot.util;

public class TextHelper {
	public static String smartTrim(String str, int length, String delim, String appendix) {
	    if (str.length() <= length) return str;

	    String trimmedStr = str.substring(0, length + delim.length());

	    int lastDelimIndex = trimmedStr.lastIndexOf(delim);
	    if (lastDelimIndex >= 0) trimmedStr = trimmedStr.substring(0, lastDelimIndex);

	    if (trimmedStr != null ) trimmedStr += appendix;
	    return trimmedStr;
	};
}
