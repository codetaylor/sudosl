package com.sudoplay.script.builtin;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class MapProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public MapProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    return Util.map(Util.proc(first), Util.rest(args), scheme, Util.list(null));
  }

}
