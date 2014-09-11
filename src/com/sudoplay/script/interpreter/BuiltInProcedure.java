package com.sudoplay.script.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BuiltInProcedure extends Procedure implements FunctionConstants {

  private static final Logger LOG = LoggerFactory.getLogger(BuiltInProcedure.class);

  private int minArgs;
  private int maxArgs;

  public static Double ZERO = new Double(0.0);
  public static Double ONE = new Double(1.0);

  protected BuiltInProcedure(int minArgs) {
    this(minArgs, minArgs);
  }

  protected BuiltInProcedure(int minArgs, int maxArgs) {
    this.minArgs = minArgs;
    this.maxArgs = maxArgs;
  }

  @Override
  public Object apply(Scheme interp, Object args) {

    int nArgs = Util.length(args);
    if (nArgs < minArgs) {
      LOG.error("Too few args, [{}] for [{}]: [{}]", nArgs, this.getName(), args);
      throw new RuntimeException("Too few args, " + nArgs + ", for " + this.getName() + ": " + args);
    } else if (nArgs > maxArgs) {
      LOG.error("Too many args, [{}] for [{}]: [{}]", nArgs, this.getName(), args);
      throw new RuntimeException("Too many args, " + nArgs + ", for " + this.getName() + ": " + args);
    }

    Object x = Util.first(args);
    Object y = Util.second(args);

    return apply(interp, args, x, y);

  }

  public abstract Object apply(Scheme scheme, Object args, Object first, Object second);

}