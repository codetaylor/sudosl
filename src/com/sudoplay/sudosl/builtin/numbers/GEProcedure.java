package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.Pair;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class GEProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;
  private static final int MAX_ARGS = Integer.MAX_VALUE;

  public GEProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    while (Util.rest(args) instanceof Pair) {
      double x = Util.num(Util.first(args));
      args = Util.rest(args);
      double y = Util.num(Util.first(args));
      if (!(x >= y)) {
        return Util.FALSE;
      }
    }
    return Util.TRUE;
  }

}
