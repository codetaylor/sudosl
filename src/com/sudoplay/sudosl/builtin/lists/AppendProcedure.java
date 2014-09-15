package com.sudoplay.sudosl.builtin.lists;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class AppendProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 0;
  private static final int MAX_ARGS = Integer.MAX_VALUE;

  public AppendProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return args == null ? null : Util.append(args);
  }

}
