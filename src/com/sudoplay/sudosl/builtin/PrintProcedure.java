package com.sudoplay.sudosl.builtin;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class PrintProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public PrintProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    System.out.println(">> " + Util.stringify(first));
    return Util.TRUE;
  }

}
