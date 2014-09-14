package com.sudoplay.sudosl.builtin.characters;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class CharacterQProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public CharacterQProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    return Util.truth(first instanceof Character);
  }

}
