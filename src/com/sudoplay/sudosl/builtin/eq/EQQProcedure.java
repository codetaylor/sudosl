package com.sudoplay.sudosl.builtin.eq;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class EQQProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;

  public EQQProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.truth(first == second);
  }

}
