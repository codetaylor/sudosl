package com.sudoplay.script.builtin.numbers;

import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class DecrementProcedure extends CompoundAssignmentProcedure {

  private static final int MIN_ARGS = 1;

  public DecrementProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    return Util.num(Util.num(first) - 1);
  }

}
