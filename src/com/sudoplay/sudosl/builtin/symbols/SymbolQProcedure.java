package com.sudoplay.sudosl.builtin.symbols;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class SymbolQProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public SymbolQProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.truth(first instanceof String);
  }

}
