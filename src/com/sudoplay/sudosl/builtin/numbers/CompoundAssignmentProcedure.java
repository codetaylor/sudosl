package com.sudoplay.sudosl.builtin.numbers;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;

public abstract class CompoundAssignmentProcedure extends BuiltInProcedure {

  protected CompoundAssignmentProcedure(int minArgs) {
    super(minArgs);
  }

  protected CompoundAssignmentProcedure(int minArgs, int maxArgs) {
    super(minArgs, maxArgs);
  }

}
