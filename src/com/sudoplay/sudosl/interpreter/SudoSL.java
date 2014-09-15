package com.sudoplay.sudosl.interpreter;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudoplay.sudosl.builtin.ConsProcedure;
import com.sudoplay.sudosl.builtin.EvalProcedure;
import com.sudoplay.sudosl.builtin.ExceptionProcedure;
import com.sudoplay.sudosl.builtin.LoadProcedure;
import com.sudoplay.sudosl.builtin.MapProcedure;
import com.sudoplay.sudosl.builtin.PrintProcedure;
import com.sudoplay.sudosl.builtin.booleans.BooleanQProcedure;
import com.sudoplay.sudosl.builtin.booleans.NotProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterAlphaQProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterLowercaseProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterLowercaseQProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterNumericQProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterQProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterToIntegerProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterUppercaseProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterUppercaseQProcedure;
import com.sudoplay.sudosl.builtin.characters.CharacterWhitespaceQProcedure;
import com.sudoplay.sudosl.builtin.characters.IntegerToCharacterProcedure;
import com.sudoplay.sudosl.builtin.control.ApplyProcedure;
import com.sudoplay.sudosl.builtin.equivalence.EQQProcedure;
import com.sudoplay.sudosl.builtin.equivalence.EQVQProcedure;
import com.sudoplay.sudosl.builtin.equivalence.EqualQProcedure;
import com.sudoplay.sudosl.builtin.lists.AppendProcedure;
import com.sudoplay.sudosl.builtin.lists.FirstProcedure;
import com.sudoplay.sudosl.builtin.lists.LengthProcedure;
import com.sudoplay.sudosl.builtin.lists.ListProcedure;
import com.sudoplay.sudosl.builtin.lists.ListQProcedure;
import com.sudoplay.sudosl.builtin.lists.ListRefProcedure;
import com.sudoplay.sudosl.builtin.lists.ListTailProcedure;
import com.sudoplay.sudosl.builtin.lists.NullQProcedure;
import com.sudoplay.sudosl.builtin.lists.PairQProcedure;
import com.sudoplay.sudosl.builtin.lists.RestProcedure;
import com.sudoplay.sudosl.builtin.lists.ReverseProcedure;
import com.sudoplay.sudosl.builtin.lists.SecondProcedure;
import com.sudoplay.sudosl.builtin.lists.SetCarProcedure;
import com.sudoplay.sudosl.builtin.lists.SetCdrProcedure;
import com.sudoplay.sudosl.builtin.lists.ThirdProcedure;
import com.sudoplay.sudosl.builtin.numbers.AbsProcedure;
import com.sudoplay.sudosl.builtin.numbers.AcosProcedure;
import com.sudoplay.sudosl.builtin.numbers.AddAssignmentProcedure;
import com.sudoplay.sudosl.builtin.numbers.AddProcedure;
import com.sudoplay.sudosl.builtin.numbers.AsinProcedure;
import com.sudoplay.sudosl.builtin.numbers.AtanProcedure;
import com.sudoplay.sudosl.builtin.numbers.CeilProcedure;
import com.sudoplay.sudosl.builtin.numbers.CompoundAssignmentProcedure;
import com.sudoplay.sudosl.builtin.numbers.CosProcedure;
import com.sudoplay.sudosl.builtin.numbers.DecrementProcedure;
import com.sudoplay.sudosl.builtin.numbers.DivAssignmentProcedure;
import com.sudoplay.sudosl.builtin.numbers.DivProcedure;
import com.sudoplay.sudosl.builtin.numbers.EQProcedure;
import com.sudoplay.sudosl.builtin.numbers.EvenQProcedure;
import com.sudoplay.sudosl.builtin.numbers.ExpProcedure;
import com.sudoplay.sudosl.builtin.numbers.ExptProcedure;
import com.sudoplay.sudosl.builtin.numbers.FloorProcedure;
import com.sudoplay.sudosl.builtin.numbers.GEProcedure;
import com.sudoplay.sudosl.builtin.numbers.GTProcedure;
import com.sudoplay.sudosl.builtin.numbers.InExactQProcedure;
import com.sudoplay.sudosl.builtin.numbers.IncrementProcedure;
import com.sudoplay.sudosl.builtin.numbers.IntegerQProcedure;
import com.sudoplay.sudosl.builtin.numbers.LEProcedure;
import com.sudoplay.sudosl.builtin.numbers.LTProcedure;
import com.sudoplay.sudosl.builtin.numbers.LogProcedure;
import com.sudoplay.sudosl.builtin.numbers.MaxProcedure;
import com.sudoplay.sudosl.builtin.numbers.MinProcedure;
import com.sudoplay.sudosl.builtin.numbers.ModuloAssignmentProcedure;
import com.sudoplay.sudosl.builtin.numbers.ModuloProcedure;
import com.sudoplay.sudosl.builtin.numbers.MultAssignmentProcedure;
import com.sudoplay.sudosl.builtin.numbers.MultProcedure;
import com.sudoplay.sudosl.builtin.numbers.NegativeQProcedure;
import com.sudoplay.sudosl.builtin.numbers.NumberQProcedure;
import com.sudoplay.sudosl.builtin.numbers.NumberToStringProcedure;
import com.sudoplay.sudosl.builtin.numbers.OddQProcedure;
import com.sudoplay.sudosl.builtin.numbers.PositiveQProcedure;
import com.sudoplay.sudosl.builtin.numbers.RemainderAssignmentProcedure;
import com.sudoplay.sudosl.builtin.numbers.RemainderProcedure;
import com.sudoplay.sudosl.builtin.numbers.RoundProcedure;
import com.sudoplay.sudosl.builtin.numbers.SinProcedure;
import com.sudoplay.sudosl.builtin.numbers.SqrtProcedure;
import com.sudoplay.sudosl.builtin.numbers.StringToNumberProcedure;
import com.sudoplay.sudosl.builtin.numbers.SubAssignmentProcedure;
import com.sudoplay.sudosl.builtin.numbers.SubProcedure;
import com.sudoplay.sudosl.builtin.numbers.TanProcedure;
import com.sudoplay.sudosl.builtin.numbers.TruncateProcedure;
import com.sudoplay.sudosl.builtin.numbers.ZeroQProcedure;
import com.sudoplay.sudosl.builtin.strings.StringAppendProcedure;
import com.sudoplay.sudosl.builtin.symbols.StringToSymbolProcedure;
import com.sudoplay.sudosl.builtin.symbols.SymbolQProcedure;
import com.sudoplay.sudosl.builtin.symbols.SymbolToStringProcedure;

public class SudoSL {

  private static final Logger LOG = LoggerFactory.getLogger(SudoSL.class);
  private static final PrintWriter DEFAULT_WRITER = new PrintWriter(System.out);

  private final Environment globalEnvironment;
  private PrintWriter printWriter = DEFAULT_WRITER;

  private Stack<Object> backtraceStack = new Stack<Object>();

  public SudoSL() {
    this(new Environment());
  }

  public SudoSL(final Environment newGlobalEnvironment) {
    globalEnvironment = newGlobalEnvironment;

    // SECTION 6.1 BOOLEANS
    globalEnvironment.register("not", new NotProcedure());
    globalEnvironment.register("bool?", new BooleanQProcedure());

    // SECTION 6.2 EQUIVALENCE PREDICATES
    globalEnvironment.register("eqv?", new EQVQProcedure());
    globalEnvironment.register("eq?", new EQQProcedure());
    globalEnvironment.register("equal?", new EqualQProcedure());

    // SECTION 6.3 LISTS AND PAIRS
    globalEnvironment.register("pair?", new PairQProcedure());
    globalEnvironment.register("list?", new ListQProcedure());
    globalEnvironment.register(new String[] { "cons", "pair" }, new ConsProcedure());
    globalEnvironment.register(new String[] { "car", "first" }, new FirstProcedure());
    globalEnvironment.register(new String[] { "cdr", "rest" }, new RestProcedure());
    globalEnvironment.register(new String[] { "set-car", "set-first" }, new SetCarProcedure());
    globalEnvironment.register(new String[] { "set-cdr", "set-rest" }, new SetCdrProcedure());
    globalEnvironment.register("second", new SecondProcedure());
    globalEnvironment.register("third", new ThirdProcedure());
    globalEnvironment.register("null?", new NullQProcedure());
    globalEnvironment.register("list", new ListProcedure());
    globalEnvironment.register(new String[] { "length", "len" }, new LengthProcedure());
    globalEnvironment.register("append", new AppendProcedure());
    globalEnvironment.register(new String[] { "list-ref", "get" }, new ListRefProcedure());
    globalEnvironment.register("list-tail", new ListTailProcedure());
    globalEnvironment.register(new String[] { "reverse", "rev" }, new ReverseProcedure());

    // SECTION 6.4 SYMBOLS
    globalEnvironment.register(new String[] { "symbol?", "sym?" }, new SymbolQProcedure());
    globalEnvironment.register(new String[] { "symbol->string", "sym->str" }, new SymbolToStringProcedure());
    globalEnvironment.register(new String[] { "string->symbol", "str->sym" }, new StringToSymbolProcedure());

    // SECTION 6.5 NUMBERS
    globalEnvironment.register(new String[] { "number?", "num?" }, new NumberQProcedure());
    globalEnvironment.register("odd?", new OddQProcedure());
    globalEnvironment.register("even?", new EvenQProcedure());
    globalEnvironment.register(new String[] { "zero?", "0?" }, new ZeroQProcedure());
    globalEnvironment.register(new String[] { "positive?", "pos?" }, new PositiveQProcedure());
    globalEnvironment.register(new String[] { "negative?", "neg?" }, new NegativeQProcedure());
    globalEnvironment.register(new String[] { "integer?", "int?" }, new IntegerQProcedure());
    globalEnvironment.register("inexact?", new InExactQProcedure());
    globalEnvironment.register("min", new MinProcedure());
    globalEnvironment.register("max", new MaxProcedure());
    globalEnvironment.register("+", new AddProcedure());
    globalEnvironment.register("-", new SubProcedure());
    globalEnvironment.register("*", new MultProcedure());
    globalEnvironment.register("/", new DivProcedure());
    globalEnvironment.register(new String[] { "remainder", "%" }, new RemainderProcedure());
    globalEnvironment.register(new String[] { "modulo", "mod" }, new ModuloProcedure());
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
    globalEnvironment.register("%=", new RemainderAssignmentProcedure());
    globalEnvironment.register(new String[] { "modulo=", "mod=" }, new ModuloAssignmentProcedure());
    globalEnvironment.register("abs", new AbsProcedure());
    globalEnvironment.register("floor", new FloorProcedure());
    globalEnvironment.register("ceil", new CeilProcedure());
    globalEnvironment.register(new String[] { "truncate", "trunc" }, new TruncateProcedure());
    globalEnvironment.register("round", new RoundProcedure());
    globalEnvironment.register("exp", new ExpProcedure());
    globalEnvironment.register("log", new LogProcedure());
    globalEnvironment.register("sin", new SinProcedure());
    globalEnvironment.register("cos", new CosProcedure());
    globalEnvironment.register("tan", new TanProcedure());
    globalEnvironment.register("asin", new AsinProcedure());
    globalEnvironment.register("acos", new AcosProcedure());
    globalEnvironment.register("atan", new AtanProcedure());
    globalEnvironment.register("sqrt", new SqrtProcedure());
    globalEnvironment.register(new String[] { "expt", "^" }, new ExptProcedure());
    globalEnvironment.register(new String[] { "number->string", "num->str" }, new NumberToStringProcedure());
    globalEnvironment.register(new String[] { "string->number", "str->num" }, new StringToNumberProcedure());

    // SECTION 6.6 CHARACTERS
    globalEnvironment.register(new String[] { "character?", "char?" }, new CharacterQProcedure());
    globalEnvironment.register(new String[] { "character-alphabetic?", "char-alpha?" }, new CharacterAlphaQProcedure());
    globalEnvironment.register(new String[] { "character-numeric?", "char-num?" }, new CharacterNumericQProcedure());
    globalEnvironment.register(new String[] { "character-whitespace?", "char-white?" }, new CharacterWhitespaceQProcedure());
    globalEnvironment.register(new String[] { "character-uppercase?", "char-upper?" }, new CharacterUppercaseQProcedure());
    globalEnvironment.register(new String[] { "character-lowercase?", "char-lower?" }, new CharacterLowercaseQProcedure());
    globalEnvironment.register(new String[] { "character->integer", "char->int" }, new CharacterToIntegerProcedure());
    globalEnvironment.register(new String[] { "integer->character", "int->char" }, new IntegerToCharacterProcedure());
    globalEnvironment.register(new String[] { "character-uppercase", "char-upper" }, new CharacterUppercaseProcedure());
    globalEnvironment.register(new String[] { "character-lowercase", "char-lower" }, new CharacterLowercaseProcedure());

    // SECTION 6.7 STRINGS
    globalEnvironment.register(new String[] { "string-append", "str-append" }, new StringAppendProcedure());

    // SECTION 6.9 CONTROL FEATURES
    globalEnvironment.register("apply", new ApplyProcedure());

    globalEnvironment.register("load", new LoadProcedure());
    globalEnvironment.register("print", new PrintProcedure());
    globalEnvironment.register("throw", new ExceptionProcedure());

    globalEnvironment.register("map", new MapProcedure());
    globalEnvironment.register("eval", new EvalProcedure());

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

    backtraceStack.push(x);

    try {

      LOG.debug("------------------------------------------------------------------------------");
      LOG.debug("Entering eval(x=[{}], env=[{}])", x, env);
      while (true) {
        if (x instanceof String) {
          // VARIABLE
          LOG.trace("Instance of String: [{}]", x);
          // Look up a variable or a procedure (built in function).
          Object result = null;
          if (!("null".equals((String) x))) {
            result = env.lookup((String) x);
          }
          LOG.debug("Leaving eval(): [{}]", result);
          backtraceStack.pop();
          return result;

        } else if (!(x instanceof Pair)) {

          LOG.trace("Instance of Constant => [{}]", x);
          Object result = x;
          LOG.debug("Leaving eval(): [{}]", result);
          backtraceStack.pop();
          return result;

        } else {

          Object fn = Util.first(x);
          Object args = Util.rest(x);
          LOG.trace("Instance of Procedure: fn=[{}], args=[{}]", fn, args);

          if (Keyword.QUOTE.equals(fn)) {

            Object result = Util.first(args);
            LOG.debug("Leaving eval(): [{}]", result);
            backtraceStack.pop();
            return result;

          } else if (Keyword.BEGIN.equals(fn)) {

            for (; Util.rest(args) != null; args = Util.rest(args)) {
              eval(Util.first(args), env);
            }
            x = Util.first(args);

          } else if (Keyword.BLOCK.equals(fn) || Keyword.THEN.equals(fn) || Keyword.ELSE.equals(fn)) {

            x = new Closure(null, Util.rest(args), env);

          } else if (Keyword.FUNCTION.equals(fn) || Keyword.LOCAL.equals(fn)) {

            if (Util.first(args) instanceof Pair) {

              LOG.trace("Defining function");
              Object result = env.define( //
                  Util.first(Util.first(args)), //
                  eval(Util.cons("lambda", Util.cons(Util.rest(Util.first(args)), Util.rest(args))), env));
              LOG.debug("Leaving eval(): [{}]", result);
              backtraceStack.pop();
              return result;

            } else {

              LOG.trace("Defining variable");
              Object result = env.define(Util.first(args), eval(Util.second(args), env));
              LOG.debug("Leaving eval(): [{}]", result);
              backtraceStack.pop();
              return result;

            }

          } else if (Keyword.SET.equals(fn)) {

            Object result = env.set(Util.first(args), eval(Util.second(args), env));
            LOG.debug("Leaving eval(): [{}]", result);
            backtraceStack.pop();
            return result;

          } else if (Keyword.DO.equals(fn)) {

            Object predicate = Util.first(args);
            Closure closure = new Closure(null, Util.rest(args), env);
            do {
              closure.apply(this, null);
            } while (Util.truth(eval(predicate, env)));
            x = null;

          } else if (Keyword.WHILE.equals(fn)) {

            Object predicate = Util.first(args);
            Closure closure = new Closure(null, Util.rest(args), env);
            while (Util.truth(eval(predicate, env))) {
              closure.apply(this, null);
            }
            x = null;

          } else if (Keyword.FOR.equals(fn)) {

            env = new Environment(env);

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
            x = null;

          } else if (Keyword.IF.equals(fn)) {

            x = (Util.truth(eval(Util.first(args), env))) ? Util.second(args) : Util.third(args);

          } else if (Keyword.COND.equals(fn)) {

            x = reduceCond(args, env);

          } else if (Keyword.SWITCH.equals(fn)) {

            x = reduceSwitch(args, env);

          } else if (Keyword.LAMBDA.equals(fn)) {

            Object result = new Closure(Util.first(args), Util.rest(args), env);
            LOG.debug("Leaving eval(): [{}]", result);
            backtraceStack.pop();
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
              backtraceStack.pop();
              return result;

            } else {

              LOG.trace("fn instance of Procedure");
              Object result = Util.proc(fn).apply(this, evalList(args, env));
              LOG.debug("Leaving eval(): [{}]", result);
              backtraceStack.pop();
              return result;

            }
          }
        }
      }

    } catch (Exception e) {
      throw new BacktraceException(backtraceStack, e);
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
      if (Keyword.ELSE.equals(Util.first(clause)) || Util.truth(result = eval(Util.first(clause), env))) {
        if (Util.rest(clause) == null) {
          return Util.list(Keyword.QUOTE, result);
        } else if ("=>".equals(Util.second(clause))) {
          return Util.list(Util.third(clause), Util.list(Keyword.QUOTE, result));
        } else {
          return Util.cons(Keyword.BEGIN, Util.rest(clause));
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
      if (Keyword.DEFAULT.equals(Util.first(clause)) || Util.truth(result = eval(Util.second(clause), env))) {
        if (Util.rest(clause) == null) {
          return Util.list(Keyword.QUOTE, result);
        } else if ("=>".equals(Util.second(clause))) {
          return Util.list(Util.third(clause), Util.list(Keyword.QUOTE, result));
        } else {
          return Util.cons(Keyword.BEGIN, Util.rest(clause));
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