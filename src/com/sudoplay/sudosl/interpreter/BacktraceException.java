package com.sudoplay.sudosl.interpreter;

import java.util.Stack;

public class BacktraceException extends RuntimeException {

  private static final long serialVersionUID = -8079519707694091461L;
  private final Stack<Object> backtraceStack;

  public BacktraceException(final Stack<Object> newBacktraceStack, final String newMessage, final Throwable newCause) {
    super(newMessage, newCause);
    backtraceStack = newBacktraceStack;
  }

  public BacktraceException(final Stack<Object> newBacktraceStack, final Throwable newCause) {
    super(newCause);
    backtraceStack = newBacktraceStack;
  }

  @Override
  public void printStackTrace() {
    if (!backtraceStack.isEmpty()) {
      System.err.println("Backtrace:");
      while (backtraceStack.size() > 0) {
        System.err.println("   " + backtraceStack.pop());
      }
    }
    Throwable cause = getCause();
    while (cause != null) {
      if (!(cause instanceof BacktraceException)) {
        System.err.println("Caused by: " + cause.getClass().getSimpleName() + ": " + cause.getMessage());
      }
      cause = cause.getCause();
    }
  }

}
