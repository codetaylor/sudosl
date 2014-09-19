package com.sudoplay.sudosl.builtin.control;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class ExceptionProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 0;
  private static final int MAX_ARGS = 1;

  public ExceptionProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    if (args == null) {
      throw new RuntimeException();
    } else {
      throw new RuntimeException(Util.stringify(first));
    }
  }

}
