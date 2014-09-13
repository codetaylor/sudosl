package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.Pair;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class AddProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 0;
  private static final int MAX_ARGS = Integer.MAX_VALUE;
  private static final double DEFAULT_RESULT = 0.0;

  public AddProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    double result = DEFAULT_RESULT;
    if (args == null) {
      return Util.num(result);
    } else {
      while (args instanceof Pair) {
        Object o = Util.first(args);
        if (o instanceof Double) {
          double x = Util.num(o);
          args = Util.rest(args);
          result += x;
        }
      }
      return Util.num(result);
    }
  }

}
