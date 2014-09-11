package com.sudoplay.script.builtin;

import com.sudoplay.script.interpreter.BuiltInProcedure;
import com.sudoplay.script.interpreter.Scheme;
import com.sudoplay.script.interpreter.Util;

public class PrintProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public PrintProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(Scheme scheme, Object args, Object first, Object second) {
    System.out.println(">> " + Util.stringify(first));
    return Util.TRUE;
  }

}
