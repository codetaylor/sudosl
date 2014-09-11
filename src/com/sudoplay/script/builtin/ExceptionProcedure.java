package com.sudoplay.script.builtin;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class ExceptionProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 0;
  private static final int MAX_ARGS = 1;

  public ExceptionProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    if (args == null) {
      throw new RuntimeException();
    } else {
      throw new RuntimeException(Util.stringify(first));
    }
  }

}
