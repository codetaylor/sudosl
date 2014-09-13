package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class IncrementProcedure extends CompoundAssignmentProcedure {

  private static final int MIN_ARGS = 1;

  public IncrementProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.num(Util.num(first) + 1);
  }

}
