package com.fitow2512.basketinfo.utils;

import java.util.List;
import java.util.stream.Collectors;

public class StringsUtils {
	
	public static String joinList(List<String> parts, String delimiter, String lastDelimiter) {
	    return parts.stream()
	            .limit(parts.size() - 1)
	            .collect(Collectors.joining(delimiter + " ", "", " " + lastDelimiter + " "))
	            .concat(parts.get(parts.size() - 1));
	}
}
