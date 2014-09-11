package com.sudoplay.script.interpreter;

public class Closure extends Procedure {

  Object parms;
  Object body;
  Environment env;

  /**
   * Make a closure from a parameter list, body, and environment. *
   */
  public Closure(Object parms, Object body, Environment env) {
    this.parms = parms;
    this.env = env;
    this.body = (body instanceof Pair && Util.rest(body) == null) ? Util.first(body) : Util.cons("begin", body);
  }

  /**
   * Apply a closure to a list of arguments. *
   */
  @Override
  public Object apply(Scheme interpreter, Object args) {
    return interpreter.eval(body, new Environment(parms, args, env));
  }
}