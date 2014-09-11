package com.sudoplay.script;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.script.interpreter.InputReader;
import com.sudoplay.script.interpreter.Scheme;

public class ConditionalTest {

  @Test
  public void test() {

    try {
      Scheme scheme = new Scheme();
      scheme.load(this.getClass().getResourceAsStream("unit-test.script"));
      scheme.load(this.getClass().getResourceAsStream("conditional-test.script"));
    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

}
