package com.sudoplay.sudosl.builtin.strings;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class StringRefProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;

  public StringRefProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.chr(Util.str(first)[(int) Util.num(second)]);
  }

}
