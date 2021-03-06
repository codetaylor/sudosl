package com.sudoplay.sudosl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sudoplay.sudosl.interpreter.InputReader;
import com.sudoplay.sudosl.interpreter.SudoSL;

public class CharacterTest {

  @Test
  public void test() {

    try {
      SudoSL scheme = new SudoSL();
      scheme.load(this.getClass().getResourceAsStream("unit-test.script"));
      scheme.load(this.getClass().getResourceAsStream("character-test.script"));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }

  }

}
