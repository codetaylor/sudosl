package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class TruncateProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public TruncateProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    double d = Util.num(first);
    return Util.num((d < 0.0) ? Math.ceil(d) : Math.floor(d));
  }

}
