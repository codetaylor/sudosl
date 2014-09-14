package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class RemainderProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;

  public RemainderProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.num((long) Util.num(first) % (long) Util.num(second));
  }

}
