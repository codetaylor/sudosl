package com.sudoplay.sudosl.builtin.list;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class ListTailProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 2;

  public ListTailProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    for (int k = (int) Util.num(second); k > 0; k--) {
      first = Util.rest(first);
    }
    return first;
  }

}
