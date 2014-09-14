package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.Pair;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class MinProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;
  private static final int MAX_ARGS = Integer.MAX_VALUE;

  public MinProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    double result = Util.num(first);
    if (args == null) {
      return result;
    } else {
      while (args instanceof Pair) {
        Object o = Util.first(args);
        if (o instanceof Double) {
          double x = Util.num(o);
          args = Util.rest(args);
          if (result > x) {
            result = x;
          }
        }
      }
      return Util.num(result);
    }
  }

}
