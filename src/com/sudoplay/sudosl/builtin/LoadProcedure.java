package com.sudoplay.sudosl.builtin;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;

public class LoadProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public LoadProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return scheme.load(first);
  }

}
