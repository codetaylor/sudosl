package com.sudoplay.script;

import java.io.FileReader;

import com.sudoplay.script.interpreter.PreParser;

public class Main {

  public static void main(String[] args) throws Exception {

    PreParser p = new PreParser(new FileReader("nested-function-test.script"));
    System.out.println(p.getParsedText());

  }

}
