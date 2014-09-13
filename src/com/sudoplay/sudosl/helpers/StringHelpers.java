package com.sudoplay.sudosl.helpers;

import java.util.ArrayList;
import java.util.List;

public class StringHelpers {

  public static String join(List<?> s, String glue) {
    String newString = "";
    for (int i = 0; i < s.size() - 1; i++) {
      newString = newString + s.get(i).toString() + glue;
    }
    return newString + s.get(s.size() - 1).toString();
  }

  public static List<String> vectorize(String s) {
    List<String> v = new ArrayList<String>();
    int i;
    for (i = 0; i < s.length() - 1; i++) {
      v.add(s.substring(i, i + 1));
    }
    if (i > 0) {
      v.add(s.substring(i));
    }
    return v;
  }

}
