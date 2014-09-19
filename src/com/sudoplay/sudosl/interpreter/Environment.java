package com.sudoplay.sudosl.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Environment {

  private static final Logger LOG = LoggerFactory.getLogger(Environment.class);

  private Environment parentEnvironment;
  private Map<Object, Object> mapDataVars = new HashMap<Object, Object>();

  public Environment() {
    this(null);
  }

  public Environment(Object vars, Object vals, Environment newParentEnvironment) {
    Object varList = vars, valList = vals;
    if (varList instanceof Pair) {
      while (varList != null) {
        mapDataVars.put(Util.first(varList), Util.first(valList));
        varList = Util.rest(varList);
        valList = Util.rest(valList);
      }
    } else {
      mapDataVars.put(varList, valList);
    }
    parentEnvironment = newParentEnvironment;
  }

  public Environment(Environment newParentEnvironment) {
    parentEnvironment = newParentEnvironment;
  }

  /**
   * Find the value of a symbol, in this environment or a parent.
   */
  public Object lookup(String symbol) {
    Object o = this.mapDataVars.get(symbol);
    if (o != null) {
      return o;
    } else {
      if (parentEnvironment != null) {
        return parentEnvironment.lookup(symbol);
      } else {
        LOG.error("Unbound symbol: [{}]", symbol);
        throw new RuntimeException("Unbound symbol: " + symbol);
      }
    }
  }

  /** Add a new variable,value pair to this environment. */
  public Object define(Object var, Object val) {
    LOG.debug("Entering define(var=[{}], val=[{}])", var, val);
    if (mapDataVars.containsKey(var)) {
      LOG.error("Symbol already bound: [{}]", var);
      throw new RuntimeException("Symbol already bound: " + var);
    }
    this.mapDataVars.put(var, val);
    if (val instanceof Procedure && ((Procedure) val).getName().equals(Procedure.DEFAULT_NAME)) {
      ((Procedure) val).setName(var.toString());
    }
    return var;
  }

  /**
   * Set the value of an existing variable *
   */
  public Object set(Object var, Object val) {
    if (!(var instanceof String)) {
      LOG.error("Attempt to set a non-symbol: [{}]", Util.stringify(var));
    }

    String symbol = (String) var;
    Object o = this.mapDataVars.get(symbol);
    if (o != null) {
      mapDataVars.put(symbol, val);
      return val;
    } else {
      if (parentEnvironment != null) {
        return parentEnvironment.set(symbol, val);
      } else {
        LOG.error("Unbound variable: [{}]", symbol);
        throw new RuntimeException("Unbound variable: " + symbol);
      }
    }
  }

  public Environment register(String name, BuiltInProcedure procedure) {
    define(name, procedure);
    return this;
  }

  public Environment register(String name, String shortName, BuiltInProcedure procedure) {
    define(name, procedure);
    define(shortName, procedure);
    return this;
  }

  @Override
  public String toString() {
    return "Environment [parentEnvironment=" + parentEnvironment + ", mapDataVars=" + mapDataVars + "]";
  }

}