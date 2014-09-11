package com.sudoplay.script.builtin.eq;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class EqualQProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;

  public EqualQProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    return Util.truth(Util.equal(first, second));
  }

}
