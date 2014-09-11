package com.sudoplay.script.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Procedure {

  private static final Logger LOG = LoggerFactory.getLogger(Procedure.class);

  public static String DEFAULT_NAME = "Anonymous Procedure";

  private String name = DEFAULT_NAME;

  @Override
  public String toString() {
    return "{" + name + "}";
  }

  public abstract Object apply(Scheme interpreter, Object args);

  public String getName() {
    return name;
  }

  public void setName(final String n) {
    this.name = n;
  }

}