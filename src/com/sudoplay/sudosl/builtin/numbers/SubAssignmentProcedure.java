package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.Pair;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class SubAssignmentProcedure extends CompoundAssignmentProcedure {

  private static final int MIN_ARGS = 2;
  private static final int MAX_ARGS = Integer.MAX_VALUE;

  public SubAssignmentProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    double result = Util.num(first);
    args = Util.rest(args);
    if (args == null) {
      return result;
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
