package com.sudoplay.sudosl.builtin.strings;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class SubstringProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 3;

  public SubstringProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    int start = (int) Util.num(second), end = (int) Util.num(Util.third(args));
    return new String(Util.str(first), start, end - start).toCharArray();
  }

}
