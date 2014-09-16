package com.sudoplay.sudosl.builtin.vectors;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class MakeVectorProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;
  private static final int MAX_ARGS = 2;

  public MakeVectorProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    Object[] vec = new Object[(int) Util.num(first)];
    if (second != null) {
      for (int i = 0; i < vec.length; i++) {
        vec[i] = second;
      }
    }
    return vec;
  }

}
