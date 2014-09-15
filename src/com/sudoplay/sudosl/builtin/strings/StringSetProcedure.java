package com.sudoplay.sudosl.builtin.strings;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class StringSetProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 3;

  public StringSetProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    Object z = Util.third(args);
    Util.str(first)[(int) Util.num(second)] = Util.chr(z);
    return z;
  }

}
