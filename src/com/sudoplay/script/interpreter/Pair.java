package com.sudoplay.script.interpreter;

/**
 * A Pair has two fields, first and rest (or car and cdr). This forms the basis
 * of a simple, singly-linked list structure whose contents can be manipulated
 * with cons, car, and cdr.
 * 
 * "One mathematical consideration that influenced LISP was to express programs
 * as applicative expressions built up from variables and constants using
 * functions" -- John McCarthy
 * http://www-formal.stanford.edu/jmc/history/lisp/node3.html
 */
public class Pair {

  /**
   * The first element of the pair.
   */
  private Object first;

  /**
   * The other element of the pair.
   */
  private Object rest;

  /** Build a pair from two components. * */
  public Pair(Object first, Object rest) {
    this.first = first;
    this.rest = rest;
  }

  public String toString() {
    return Util.stringify(this, true);
  }

  /**
   * Build up a String representation of the Pair in a StringBuffer. *
   */
  void stringifyPair(boolean quoted, StringBuffer buf) {
    String special = null;
    if ((rest instanceof Pair) && Util.rest(rest) == null)
      special = (first == "quote") ? "'" : (first == "quasiquote") ? "`" : (first == "unquote") ? "," : (first == "unquote-splicing") ? ",@" : null;

    if (special != null) {
      buf.append(special);
      Util.stringify(Util.second(this), quoted, buf);
    } else {
      buf.append('(');
      Util.stringify(first, quoted, buf);
      Object tail = rest;
      while (tail instanceof Pair) {
        buf.append(' ');
        Util.stringify(((Pair) tail).first, quoted, buf);
        tail = ((Pair) tail).rest;
      }
      if (tail != null) {
        buf.append(" . ");
        Util.stringify(tail, quoted, buf);
      }
      buf.append(')');
    }
  }

  /**
   * @return the first
   */
  public Object getFirst() {
    return first;
  }

  public Object setFirst(Object first) {
    return this.first = first;
  }

  /**
   * @return the rest
   */
  public Object getRest() {
    return rest;
  }

  public Object setRest(Object rest) {
    return this.rest = rest;
  }
}