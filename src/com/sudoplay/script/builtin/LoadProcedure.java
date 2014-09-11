package com.sudoplay.script.builtin;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Scheme;

public class LoadProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public LoadProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    return scheme.load(first);
  }

}
