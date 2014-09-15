package com.sudoplay.sudosl.builtin.strings;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class StringQProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public StringQProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.truth(first instanceof char[]);
  }

}
