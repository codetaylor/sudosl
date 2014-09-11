package com.sudoplay.script.interpreter;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.script.builtin.ConsProcedure;
import com.sudoplay.script.builtin.EvalProcedure;
import com.sudoplay.script.builtin.ExceptionProcedure;
import com.sudoplay.script.builtin.FirstProcedure;
import com.sudoplay.script.builtin.ListProcedure;
import com.sudoplay.script.builtin.LoadProcedure;
import com.sudoplay.script.builtin.MapProcedure;
import com.sudoplay.script.builtin.NullQProcedure;
import com.sudoplay.script.builtin.PrintProcedure;
import com.sudoplay.script.builtin.RestProcedure;
import com.sudoplay.script.builtin.eq.EQQProcedure;
import com.sudoplay.script.builtin.eq.EQVQProcedure;
import com.sudoplay.script.builtin.eq.EqualQProcedure;
import com.sudoplay.script.builtin.numbers.AddProcedure;
import com.sudoplay.script.builtin.numbers.AddAssignmentProcedure;
import com.sudoplay.script.builtin.numbers.CompoundAssignmentProcedure;
import com.sudoplay.script.builtin.numbers.DecrementProcedure;
import com.sudoplay.script.builtin.numbers.DivAssignmentProcedure;
import com.sudoplay.script.builtin.numbers.DivProcedure;
import com.sudoplay.script.builtin.numbers.EQProcedure;
import com.sudoplay.script.builtin.numbers.GEProcedure;
import com.sudoplay.script.builtin.numbers.GTProcedure;
import com.sudoplay.script.builtin.numbers.IncrementProcedure;
import com.sudoplay.script.builtin.numbers.LEProcedure;
import com.sudoplay.script.builtin.numbers.LTProcedure;
import com.sudoplay.script.builtin.numbers.ModAssignmentProcedure;
import com.sudoplay.script.builtin.numbers.ModProcedure;
import com.sudoplay.script.builtin.numbers.MultAssignmentProcedure;
import com.sudoplay.script.builtin.numbers.MultProcedure;
import com.sudoplay.script.builtin.numbers.SubAssignmentProcedure;
import com.sudoplay.script.builtin.numbers.SubProcedure;

public class Scheme {

  private static final Logger LOG = LoggerFactory.getLogger(Scheme.class);
  private static final PrintWriter DEFAULT_WRITER = new PrintWriter(System.out);

  private final Environment globalEnvironment;
  private PrintWriter printWriter = DEFAULT_WRITER;

  public Scheme() {
    this(new Environment());
  }

  public Scheme(final Environment newGlobalEnvironment) {
    globalEnvironment = newGlobalEnvironment;

    globalEnvironment.register("load", new LoadProcedure());
    globalEnvironment.register("print", new PrintProcedure());
    globalEnvironment.register("throw", new ExceptionProcedure());

    globalEnvironment.register("cons", new ConsProcedure());
    globalEnvironment.register("car", new FirstProcedure());
    globalEnvironment.register("first", new FirstProcedure());
    globalEnvironment.register("cdr", new RestProcedure());
    globalEnvironment.register("rest", new RestProcedure());
    globalEnvironment.register("list", new ListProcedure());
    globalEnvironment.register("map", new MapProcedure());
    globalEnvironment.register("eval", new EvalProcedure());

    globalEnvironment.register("eqv?", new EQVQProcedure());
    globalEnvironment.register("eq?", new EQQProcedure());
    globalEnvironment.register("equal?", new EqualQProcedure());
    
    globalEnvironment.register("null?", new NullQProcedure());

    globalEnvironment.register("+", new AddProcedure());
    globalEnvironment.register("-", new SubProcedure());
    globalEnvironment.register("*", new MultProcedure());
    globalEnvironment.register("/", new DivProcedure());
    globalEnvironment.register("%", new ModProcedure());
    globalEnvironment.register("<", new LTProcedure());
    globalEnvironment.register(">", new GTProcedure());
    globalEnvironment.register("<=", new LEProcedure());
    globalEnvironment.register(">=", new GEProcedure());
    globalEnvironment.register("==", new EQProcedure());
    globalEnvironment.register("++", new IncrementProcedure());
    globalEnvironment.register("--", new DecrementProcedure());
    globalEnvironment.register("+=", new AddAssignmentProcedure());
    globalEnvironment.register("-=", new SubAssignmentProcedure());
    globalEnvironment.register("*=", new MultAssignmentProcedure());
    globalEnvironment.register("/=", new DivAssignmentProcedure());
    globalEnvironment.register("%=", new ModAssignmentProcedure());
  }

  public Environment getGlobalEnvironment() {
    return globalEnvironment;
  }

  public void setPrintWriter(PrintWriter printWriter) {
    this.printWriter = printWriter;
  }

  public Object load(Object filename) {
    return load(filename, globalEnvironment);
  }

  public Object load(Object filename, Environment env) {
    String name = Util.stringify(filename);
    try {
      return load(new InputReader(new PreParser(new FileReader(name)).getParsedTextAsInputStream()), env);
    } catch (IOException e) {
      LOG.error("Can't load [{}]", name);
      throw new RuntimeException("Can't load " + name, e);
    }
  }

  public Object load(InputStream in) {
    return load(in, globalEnvironment);
  }

  public Object load(InputStream in, Environment env) {
    return load(new InputReader(new PreParser(in).getParsedTextAsInputStream()), env);
  }

  protected Object load(InputReader in) {
    return load(in, globalEnvironment);
  }

  protected Object load(InputReader in, Environment env) {
    Object x = null;
    for (;;) {
      if (InputReader.isEOF(x = in.read())) {
        return Util.TRUE;
      }
      LOG.trace("Read: {}", Util.stringify(x));
      eval(x, env);
    }
  }

  public void debugLoad(InputReader in, Environment env) {
    Object x = null;
    for (;;) {
      if (InputReader.isEOF(x = in.read())) {
        return;
      }
      LOG.info("Read: {}", Util.stringify(x));
    }
  }

  public Object eval(Object x) {
    return eval(x, globalEnvironment);
  }

  public Object eval(Object x, Environment env) {
    LOG.debug("------------------------------------------------------------------------------");
    LOG.debug("Entering eval(x=[{}], env=[{}])", x, env);
    while (true) {
      if (x instanceof String) {
        // VARIABLE
        LOG.trace("Instance of String: [{}]", x);
        // Look up a variable or a procedure (built in function).
        Object result = env.lookup((String) x);
        LOG.debug("Leaving eval(): [{}]", result);
        return result;

      } else if (!(x instanceof Pair)) {

        LOG.trace("Instance of Constant => [{}]", x);
        Object result = x;
        LOG.debug("Leaving eval(): [{}]", result);
        return result;

      } else {

        Object fn = Util.first(x);
        Object args = Util.rest(x);
        LOG.trace("Instance of Procedure: fn=[{}], args=[{}]", fn, args);

        if ("quote".equals(fn)) {

          Object result = Util.first(args);
          LOG.debug("Leaving eval(): [{}]", result);
          return result;

        } else if ("begin".equals(fn)) {

          for (; Util.rest(args) != null; args = Util.rest(args)) {
            eval(Util.first(args), env);
          }
          x = Util.first(args);

        } else if ("function".equals(fn) || "local".equals(fn)) {

          if (Util.first(args) instanceof Pair) {

            LOG.trace("Defining function");
            Object result = env.define( //
                Util.first(Util.first(args)), //
                eval(Util.cons("lambda", Util.cons(Util.rest(Util.first(args)), Util.rest(args))), env));
            LOG.debug("Leaving eval(): [{}]", result);
            return result;

          } else {

            LOG.trace("Defining variable");
            Object result = env.define(Util.first(args), eval(Util.second(args), env));
            LOG.debug("Leaving eval(): [{}]", result);
            return result;

          }

        } else if ("=".equals(fn)) {

          Object result = env.set(Util.first(args), eval(Util.second(args), env));
          LOG.debug("Leaving eval(): [{}]", result);
          return result;

        } else if ("for".equals(fn)) {

          env = new Environment(env);
          Object body = null;

          Object var = Util.first(args);
          args = Util.rest(args);

          Object val = Util.first(args);
          env.define(var, eval(val, env));
          args = Util.rest(args);

          Object predicate = Util.first(args);
          args = Util.rest(args);

          Object operation = Util.first(args);
          Closure closure = new Closure(var, Util.rest(args), env);
          while (Util.truth(eval(predicate, env))) {
            closure.apply(this, val);
            val = eval(operation, env);
          }
          x = Util.first(body);

        } else if ("if".equals(fn)) {

          x = (Util.truth(eval(Util.first(args), env))) ? Util.second(args) : Util.third(args);

        } else if ("cond".equals(fn)) {

          x = reduceCond(args, env);

        } else if ("switch".equals(fn)) {

          x = reduceSwitch(args, env);

        } else if ("lambda".equals(fn)) {

          Object result = new Closure(Util.first(args), Util.rest(args), env);
          LOG.debug("Leaving eval(): [{}]", result);
          return result;

        } else {
          fn = eval(fn, env);
          if (fn instanceof Closure) {

            LOG.trace("fn instance of Closure");
            Closure f = (Closure) fn;
            x = f.body;
            env = new Environment(f.parms, evalList(args, env), f.env);

          } else if (fn instanceof CompoundAssignmentProcedure) {

            Object result = Util.proc(fn).apply(this, evalList(args, env));
            if (Util.first(args) instanceof String) {
              env.set(Util.first(args), result);
            }
            return result;

          } else {

            LOG.trace("fn instance of Procedure");
            Object result = Util.proc(fn).apply(this, evalList(args, env));
            LOG.debug("Leaving eval(): [{}]", result);
            return result;

          }
        }
      }
    }
  }

  public Pair evalList(Object list) {
    return evalList(list, globalEnvironment);
  }

  public Pair evalList(Object list, Environment env) {
    if (list == null)
      return null;
    else if (!(list instanceof Pair)) {
      final String msg = "Illegal arg list: " + list;
      System.err.println(msg);
      throw new RuntimeException(msg);
    } else {
      final Object first = eval(Util.first(list), env);
      final Object rest = evalList(Util.rest(list), env);
      return Util.cons(first, rest);
    }
  }

  /**
   * Reduce a cond expression to some code which, when evaluated, gives the
   * value of the cond expression. We do it that way to maintain tail recursion.
   * *
   */
  private Object reduceCond(Object clauses, Environment env) {
    Object result = null;
    for (;;) {
      if (clauses == null) {
        return Util.FALSE;
      }
      Object clause = Util.first(clauses);
      clauses = Util.rest(clauses);
      if ("else".equals(Util.first(clause)) || Util.truth(result = eval(Util.first(clause), env))) {
        if (Util.rest(clause) == null) {
          return Util.list("quote", result);
        } else if ("=>".equals(Util.second(clause))) {
          return Util.list(Util.third(clause), Util.list("quote", result));
        } else {
          return Util.cons("begin", Util.rest(clause));
        }
      }
    }
  }

  private Object reduceSwitch(Object clauses, Environment env) {
    Object result = null;
    for (;;) {
      if (clauses == null) {
        return Util.FALSE;
      }
      Object clause = Util.first(clauses);
      clauses = Util.rest(clauses);
      if ("default".equals(Util.first(clause)) || Util.truth(result = eval(Util.second(clause), env))) {
        if (Util.rest(clause) == null) {
          return Util.list("quote", result);
        } else if ("=>".equals(Util.second(clause))) {
          return Util.list(Util.third(clause), Util.list("quote", result));
        } else {
          return Util.cons("begin", Util.rest(clause));
        }
      }
    }
  }

  public Object write(Object x) {
    return write(x, printWriter);
  }

  public Object write(Object x, PrintWriter port) {
    port.print(Util.stringify(x));
    port.flush();
    return x;
  }

}