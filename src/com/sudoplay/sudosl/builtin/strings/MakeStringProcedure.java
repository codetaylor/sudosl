package com.sudoplay.sudosl.builtin.strings;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class MakeStringProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;
  private static final int MAX_ARGS = 2;

  public MakeStringProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    char[] str = new char[(int) Util.num(first)];
    if (second != null) {
      char c = Util.chr(second);
      for (int i = str.length - 1; i >= 0; i--) {
        str[i] = c;
      }
    }
    return str;
  }

}
