package com.sudoplay.sudosl.builtin.lists;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class SetCdrProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;

  public SetCdrProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.setRest(first, second);
  }

}
