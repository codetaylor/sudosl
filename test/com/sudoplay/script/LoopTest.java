package com.sudoplay.script;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.script.interpreter.InputReader;
import com.sudoplay.script.interpreter.Scheme;

public class LoopTest {

  @Test
  public void test() {

    try {
      Scheme scheme = new Scheme();
      scheme.load(this.getClass().getResourceAsStream("unit-test.script"));
      scheme.load(this.getClass().getResourceAsStream("loop-test.script"));
    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

}
