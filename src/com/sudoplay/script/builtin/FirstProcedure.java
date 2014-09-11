package com.sudoplay.script.builtin;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class FirstProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public FirstProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    return Util.first(first);
  }

}
