package com.sudoplay.sudosl.builtin.control;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class MapProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;
  private static final int MAX_ARGS = Integer.MAX_VALUE;

  public MapProcedure() {
    super(MIN_ARGS, MAX_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.map(Util.proc(first), Util.rest(args), scheme, Util.list(null));
  }

}
