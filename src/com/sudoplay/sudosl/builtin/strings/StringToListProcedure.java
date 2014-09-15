package com.sudoplay.sudosl.builtin.strings;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.Pair;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class StringToListProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public StringToListProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    Pair result = null;
    char[] str2 = Util.str(first);
    for (int i = str2.length - 1; i >= 0; i--) {
      result = Util.cons(Util.chr(str2[i]), result);
    }
    return result;
  }

}
