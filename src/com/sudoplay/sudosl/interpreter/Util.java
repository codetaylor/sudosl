package com.sudoplay.sudosl.interpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Collection static scheme interpreter utilities.
 */
public abstract class Util {

  private static final Logger LOG = LoggerFactory.getLogger(Util.class);

  /**
   * Same as Boolean.TRUE. *
   */
  public static final Boolean TRUE = Boolean.TRUE;
  /**
   * Same as Boolean.FALSE. *
   */
  public static final Boolean FALSE = Boolean.FALSE;

  public static Double ZERO = new Double(0.0);
  public static Double ONE = new Double(1.0);

  /**
   * Convert boolean to Boolean. *
   */
  public static Boolean truth(boolean x) {
    return x ? TRUE : FALSE;
  }

  /**
   * Convert Scheme object to boolean. Only #f is false, others are true. *
   */
  public static boolean truth(Object x) {
    return x != FALSE;
  }

  /**
   * Like Common Lisp first; car of a Pair, or null for anything else.
   */
  public static Object first(Object x) {
    return (x instanceof Pair) ? ((Pair) x).getFirst() : null;
  }

  public static Object third(Object x) {
    return first(rest(rest(x)));
  }

  public static Object fourth(Object x) {
    return first(rest(rest(rest(x))));
  }

  public static Object fifth(Object x) {
    return first(rest(rest(rest(rest(x)))));
  }

  /**
   * Like Common Lisp rest; car of a Pair, or null for anything else. "A cons
   * cell is composed of two pointers; the car operation extracts the first
   * pointer, and the cdr operation extracts the second."
   * 
   * "Thus, the expression (car (cons x y)) evaluates to x, and (cdr (cons x y))
   * evaluates to y."
   */
  public static Object rest(Object x) {
    return (x instanceof Pair) ? ((Pair) x).getRest() : null;
  }

  /**
   * Like Common Lisp second.
   */
  public static Object second(Object x) {
    return first(rest(x));
  }

  /**
   * The length of a list, or zero for a non-list.
   */
  public static int length(Object x) {
    int len = 0;
    while (x instanceof Pair) {
      len++;
      x = ((Pair) x).getRest();
    }
    return len;
  }

  /**
   * Creates a two element list.
   */
  public static Pair list(Object a, Object b) {
    return new Pair(a, new Pair(b, null));
  }

  /**
   * Creates a one element list.
   */
  public static Pair list(Object a) {
    return new Pair(a, null);
  }

  /**
   * cons(x, y) is the same as new Pair(x, y).
   * 
   * Cons presents and interesting function that is fundamental to lisp. Here
   * are some examples of cons usage (tested in common lisp). <code>
   * (cons 1 2):
   * Pair pair = SchemeUtil.cons("1", "2");
   * assertEquals("" + pair, "(1 . 2)");
   * 
   * (cons 1 nil):
   * Pair pair = SchemeUtil.cons("1", null);
   * assertEquals("" + pair, "(1)");
   * 
   * (cons 1 (cons 2 nil)):
   * 
   * Pair pair = SchemeUtil.cons("1", SchemeUtil.cons("2", null));
   * assertEquals("" + pair, "(1 2)");
   *  
   * </code>
   */
  public static Pair cons(Object a, Object b) {
    return new Pair(a, b);
  }

  /**
   * Convert a Scheme object to its printed representation, as a java String
   * (not a Scheme string). If quoted is true, use "str" and #\c, otherwise use
   * str and c. You need to pass in a StringBuffer that is used to accumulate
   * the results. (If the interface didn't work that way, the system would use
   * lots of little internal StringBuffers. But note that you can still call
   * <tt>stringify(x)</tt> and a new StringBuffer will be created for you. *
   */
  public static void stringify(Object x, boolean quoted, StringBuffer buf) {
    if (x == null)
      buf.append("()");
    else if (x instanceof Double) {
      double d = ((Double) x).doubleValue();
      if (Math.round(d) == d)
        buf.append((long) d);
      else
        buf.append(d);
    } else if (x instanceof Character) {
      if (quoted)
        buf.append("#\\");
      buf.append(x);
    } else if (x instanceof Pair) {
      ((Pair) x).stringifyPair(quoted, buf);
    } else if (x instanceof char[]) {
      char[] chars = (char[]) x;
      if (quoted)
        buf.append('"');
      for (int i = 0; i < chars.length; i++) {
        if (quoted && chars[i] == '"')
          buf.append('\\');
        buf.append(chars[i]);
      }
      if (quoted)
        buf.append('"');
    } else if (x instanceof Object[]) {
      Object[] v = (Object[]) x;
      buf.append("#(");
      for (int i = 0; i < v.length; i++) {
        stringify(v[i], quoted, buf);
        if (i != v.length - 1)
          buf.append(' ');
      }
      buf.append(')');
    } else if (x == TRUE) {
      buf.append("#t");
    } else if (x == FALSE) {
      buf.append("#f");
    } else {
      buf.append(x);
    }
  }

  /**
   * Convert x to a Java String giving its external representation. Strings and
   * characters are quoted. *
   */
  public static String stringify(Object x) {
    return stringify(x, true);
  }

  /**
   * Convert x to a Java String giving its external representation. Strings and
   * characters are quoted iff <tt>quoted</tt> is true.. *
   */
  public static String stringify(Object x, boolean quoted) {
    StringBuffer buf = new StringBuffer();
    stringify(x, quoted, buf);
    return buf.toString();
  }

  /**
   * Check if two objects are equal. *
   */
  public static boolean equal(Object x, Object y) {
    if (x == null || y == null) {
      return x == y;
    } else if (x instanceof char[]) {
      if (!(y instanceof char[]))
        return false;
      char[] xc = (char[]) x, yc = (char[]) y;
      if (xc.length != yc.length)
        return false;
      for (int i = xc.length - 1; i >= 0; i--) {
        if (xc[i] != yc[i])
          return false;
      }
      return true;
    } else if (x instanceof Object[]) {
      if (!(y instanceof Object[]))
        return false;
      Object[] xo = (Object[]) x, yo = (Object[]) y;
      if (xo.length != yo.length)
        return false;
      for (int i = xo.length - 1; i >= 0; i--) {
        if (!equal(xo[i], yo[i]))
          return false;
      }
      return true;
    } else {
      return x.equals(y);
    }
  }

  public static Double num(double x) {
    return (x == 0.0) ? ZERO : (x == 1.0) ? ONE : new Double(x);
  }

  public static double num(Object x) {
    if (x instanceof Number) {
      return ((Number) x).doubleValue();
    } else {
      LOG.error("Expected a number, got: [{}]", x);
      throw new RuntimeException("Expected a number, got: " + x);
    }
  }

  /**
   * Coerces a Scheme object to a procedure.
   */
  public static Procedure proc(Object x) {
    if (x instanceof Procedure) {
      return (Procedure) x;
    } else {
      LOG.error("Not a procedure: [{}]", Util.stringify(x));
      throw new RuntimeException("Not a procedure: " + Util.stringify(x));
    }
  }

  /**
   * Map proc over a list of lists of args, in the given interpreter. If result
   * is non-null, accumulate the results of each call there and return that at
   * the end. Otherwise, just return null. *
   */
  public static Pair map(Procedure proc, Object args, SudoSL interp, Pair result) {
    Pair accum = result;
    if (rest(args) == null) {
      args = first(args);
      while (args instanceof Pair) {
        Object x = proc.apply(interp, list(first(args)));
        if (accum != null)
          accum = (Pair) (accum.setRest(list(x)));
        args = rest(args);
      }
    } else {
      Procedure car = proc(interp.eval("car")), cdr = proc(interp.eval("cdr"));
      while (first(args) instanceof Pair) {
        Object x = proc.apply(interp, map(car, list(args), interp, list(null)));
        if (accum != null)
          accum = (Pair) (accum.setRest(list(x)));
        args = map(cdr, list(args), interp, list(null));
      }
    }
    return (Pair) rest(result);
  }

}