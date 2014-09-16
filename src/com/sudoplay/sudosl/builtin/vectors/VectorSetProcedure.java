package com.sudoplay.sudosl.builtin.vectors;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class VectorSetProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 3;

  public VectorSetProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.vec(first)[(int) Util.num(second)] = Util.third(args);
  }

}
