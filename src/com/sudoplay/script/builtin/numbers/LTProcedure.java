package com.sudoplay.script.builtin.numbers;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Pair;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class LTProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;
  private static final int MAX_ARGS = Integer.MAX_VALUE;

  public LTProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    while (Util.rest(args) instanceof Pair) {
      double x = Util.num(Util.first(args));
      args = Util.rest(args);
      double y = Util.num(Util.first(args));
      if (!(x < y)) {
        return Util.FALSE;
      }
    }
    return Util.TRUE;
  }

}
