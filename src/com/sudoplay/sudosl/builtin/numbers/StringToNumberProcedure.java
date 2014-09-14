package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class StringToNumberProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;
  private static final int MAX_ARGS = 2;

  public StringToNumberProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.stringToNumber(first, second);
  }

}
