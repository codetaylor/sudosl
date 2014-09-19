package com.sudoplay.sudosl.interpreter;

public class Continuation extends Procedure {

  RuntimeException cc = null;
  public Object value = null;

  public Continuation(RuntimeException cc) {
    this.cc = cc;
  }

  public Object apply(SudoSL interpreter, Object args) {
    value = Util.first(args);
    throw cc;
  }
}