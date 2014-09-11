package com.sudoplay.script.builtin.numbers;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Pair;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class SubProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;
  private static final int MAX_ARGS = Integer.MAX_VALUE;

  public SubProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    double result = Util.num(first);
    args = Util.rest(args);
    if (args == null) {
      return Util.num(0 - result);
    } else {
      while (args instanceof Pair) {
        double x = Util.num(Util.first(args));
        args = Util.rest(args);
        result -= x;
      }
      return Util.num(result);
    }
  }

}
