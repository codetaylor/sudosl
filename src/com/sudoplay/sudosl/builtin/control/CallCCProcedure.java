package com.sudoplay.sudosl.builtin.control;

import com.sudoplay.sudosl.interpreter.BuiltInProcedure;
import com.sudoplay.sudosl.interpreter.Continuation;
import com.sudoplay.sudosl.interpreter.SudoSL;
import com.sudoplay.sudosl.interpreter.Util;

public class CallCCProcedure extends BuiltInProcedure {

  private static final int MIN_ARGS = 1;

  public CallCCProcedure() {
    super(MIN_ARGS);
  }

  @Override
  public Object apply(SudoSL scheme, Object args, Object first, Object second) {
    RuntimeException cc = new RuntimeException();
    Continuation proc = new Continuation(cc);
    try {
      return Util.proc(first).apply(scheme, Util.list(proc));
    } catch (RuntimeException e) {
      if (e == cc) {
        return proc.value;
      } else {
        throw e;
      }
    }
  }

}
