package com.sudoplay.script.builtin.numbers;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Pair;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class MultProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 0;
  private static final int MAX_ARGS = Integer.MAX_VALUE;
  private static final double DEFAULT_RESULT = 1.0;

  public MultProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    double result = DEFAULT_RESULT;
    if (args == null) {
      return Util.num(result);
    } else {
      while (args instanceof Pair) {
        double x = Util.num(Util.first(args));
        args = Util.rest(args);
        result *= x;
      }
      return Util.num(result);
    }
  }

}
