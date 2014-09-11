package com.sudoplay.script.builtin.eq;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class EQVQProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;

  public EQVQProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    return Util.truth( //
        first == second //
            || (first instanceof Double && first.equals(second)) //
            || (first instanceof Character && first.equals(second)));
  }

}
