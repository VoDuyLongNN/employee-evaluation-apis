package com.example.employeeevaluation.util;

import com.example.employeeevaluation.entity.enums.EInputPermission;
import com.example.employeeevaluation.entity.enums.EInputType;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class InputValidator {
  public static final String emailRegexPattern =
      "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
          + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
  public static final DateTimeFormatter dateFormatter =
      DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

  public static boolean patternMatches(String input, String regexPattern) {
    return Pattern.compile(regexPattern).matcher(input).matches();
  }

  public static boolean containsSpecialCharacter(String str) {
    return str.matches(".*[!@#$%^&*()_+{}|\"<>?].*");
  }

  public static boolean validateInputType(String inputType) {
    return Arrays.stream(EInputType.values())
        .anyMatch(type -> type.name().equals(inputType));
  }

  public static boolean validateInputPermission(String inputPermission) {
    return Arrays.stream((EInputPermission.values()))
        .anyMatch(permission -> permission.name().equals(inputPermission));
  }

  public static boolean isLong(String s) {
    if (s == null) {
      return false;
    }
    try {
      Long.parseLong(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
