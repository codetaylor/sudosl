package com.sudoplay.sudosl.interpreter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputReader {

  private static final Logger LOG = LoggerFactory.getLogger(InputReader.class);

  public static final String EOF = "#!EOF";

  public static final int TOK_LEFT_PAREN_VALUE = (int) '(';
  public static final int TOK_RIGHT_PAREN_VALUE = (int) ')';

  public static final char TOK_LEFT_PAREN = '(';
  public static final char TOK_RIGHT_PAREN = ')';
  public static final char TOK_SINGLE_QUOT = '\'';
  public static final char TOK_SEMICOLON = ';';
  public static final char TOK_DOUBLE_QUOT = '"';
  public static final char TOK_COMMA = ',';
  public static final char TOK_BACK_QUOT = '`';

  private Reader inputReader;

  private StringBuffer buff = new StringBuffer();

  private Stack<Integer> charStack = new Stack<Integer>();
  private Stack<Object> tokenStack = new Stack<Object>();

  public InputReader(InputStream in) {
    this.inputReader = new InputStreamReader(new BufferedInputStream(in));
  }

  /**
   * Determines if the specified character is white space according to Java.
   * 
   * @param ch
   *          the character to be tested.
   * @return <code>true</code> if the character is a Java whitespace character;
   *         <code>false</code> otherwise.
   * @see java.lang.Character#isSpaceChar(char)
   */
  public static boolean isWhitespace(int ci) {
    switch (ci) {
    case 9: // Horizontal Tab
    case 10: // Newline
    case 11: // Vertical Tab
    case 12: // New Page
    case 13: // Carriage Return
    case 28: // File separator
    case 29: // Group separator
    case 30: // Record separator
    case 31: // Unit separator
    case 32: // Space
      return true;
    default:
      return false;
    }
  }

  public static boolean isNewLine(int ci) {
    switch (ci) {
    case 10: // Newline
      return true;
    default:
      return false;
    }
  }

  public static boolean isTabOrSpace(int ci) {
    switch (ci) {
    case 9: // Horizontal Tab
    case 32: // Space
      return true;
    default:
      return false;
    }
  }

  /**
   * Read and return a Scheme expression, or EOF.
   */
  public Object read() {
    try {
      Object token = nextToken();
      if ("(".equals(token)) {
        return readTail();
      } else if (")".equals(token)) {
        LOG.warn("Extra ')' ignored");
        return read();
      } else {
        return token;
      }
    } catch (IOException e) {
      LOG.error("", e);
      return EOF;
    }
  }

  private Object readTail() throws IOException {
    Object token = nextToken();
    LOG.debug("readTail(): [{}]", token);
    if (EOF.equals(token)) {
      LOG.error("EOF during read");
      throw (new RuntimeException("EOF during read"));
    } else if (")".equals(token)) {
      return null;
    } else if (".".equals(token)) {
      Object result = read();
      token = nextToken();
      if (!")".equals(token)) {
        LOG.warn("Missing ')'? Received [{}] after .", token);
      }
      return result;
    } else {
      tokenStack.push(token);
      return Util.cons(read(), readTail());
    }
  }

  /**
   * Collect the set of characters from the input stream until whitespace or one
   * of the language tokens is found.
   * 
   * @param o_ch
   * @throws IOException
   */
  private void buildGenericToken(final int o_ch) throws IOException {
    int ch = o_ch;
    do {
      // Build alpha numeric, atom/symbol characters/tokens into the buffer
      buff.append((char) ch);
      ch = inputReader.read();
    } while (!Character.isWhitespace((char) ch) && //
        (ch != -1) && //
        (ch != TOK_LEFT_PAREN) && //
        (ch != TOK_RIGHT_PAREN) && //
        (ch != TOK_SINGLE_QUOT) && //
        (ch != TOK_SEMICOLON) && //
        (ch != TOK_DOUBLE_QUOT) && //
        (ch != TOK_COMMA) && //
        (ch != TOK_BACK_QUOT));

    // Push a language token onto the character stack
    charStack.push(ch);
  }

  private Object nextToken() throws IOException {

    int ch;

    // See if we should re-use a pushed char or token
    // Task 1: Pop the token and character stacks
    if (!this.tokenStack.empty() && (this.tokenStack.peek() != null)) {
      return this.tokenStack.pop();
    } else if (!this.charStack.empty() && (this.charStack.peek() != null)) {
      ch = this.charStack.pop();
    } else {
      ch = inputReader.read();
    }

    // Ignore whitespace
    // Task 2: Check for and tally whitespace
    while (isWhitespace(ch)) {
      ch = inputReader.read();
    }
    LOG.debug("nextToken() -> [{}] $[{}]", (char) ch, ch);

    // See what kind of non-white character we got
    // Task 3: Check if the character is of various token types.
    switch (ch) {
    case -1:
      return EOF;
    case TOK_LEFT_PAREN:
      return "(";
    case TOK_RIGHT_PAREN:
      return ")";
    case TOK_SINGLE_QUOT:
      return "'";
    case TOK_BACK_QUOT:
      return "`";
    case TOK_SEMICOLON:
      // Comment: skip to end of line and then read next token
      while (ch != -1 && ch != '\n' && ch != '\r') {
        ch = inputReader.read();
      }
      return nextToken();
    case TOK_DOUBLE_QUOT:
      // Strings are represented as char[]
      buff.setLength(0);
      while ((ch = inputReader.read()) != '"' && ch != -1) {
        buff.append((char) ((ch == '\\') ? inputReader.read() : ch));
      }
      if (ch == -1) {
        LOG.warn("EOF inside of a string.");
      }
      return buff.toString().toCharArray();
    case '#':
      // Begin new switch statement, next set of characters
      switch (ch = inputReader.read()) {
      case 't':
      case 'T':
        return Boolean.TRUE;
      case 'f':
      case 'F':
        return Boolean.FALSE;
      default:
        LOG.warn("#{} not recognized, ignored", ((char) ch));
        return nextToken();
      }
    default:
      buff.setLength(0);
      int c = ch;
      buildGenericToken(ch);
      // Try potential numbers, but catch any format errors.
      if (c == '.' || c == '+' || c == '-' || (c >= '0' && c <= '9')) {
        try {
          // Number type is currently in the buffer queue
          return new Double(buff.toString());
        } catch (NumberFormatException e) {
          ;
        }
      }
      String token = buff.toString();
      if ("true".equals(token.toLowerCase())) {
        return Boolean.TRUE;
      } else if ("false".equals(token.toLowerCase())) {
        return Boolean.FALSE;
      }
      return token;
    }
  }

  /**
   * Is the argument the EOF object? *
   */
  public static boolean isEOF(Object x) {
    return x == EOF;
  }

}
