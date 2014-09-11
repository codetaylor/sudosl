package com.sudoplay.script.builtin.numbers;

import com.sudoplay.script.interpreter.BuiltInProcedure;

public abstract class CompoundAssignmentProcedure extends BuiltInProcedure {

  protected CompoundAssignmentProcedure(int minArgs) {
    super(minArgs);
  }

  protected CompoundAssignmentProcedure(int minArgs, int maxArgs) {
    super(minArgs, maxArgs);
  }

}
