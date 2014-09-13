package com.sudoplay.sudosl.interpreter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreParser {

  private static final Logger LOG = LoggerFactory.getLogger(PreParser.class);

  private final BufferedReader reader;
  private final List<String> lines = new ArrayList<String>();
  private final StringBuilder sb = new StringBuilder();
  private final StringBuilder lb = new StringBuilder();
  private String parsedText;
  private int index;

  public PreParser(final InputStream in) {
    this(new InputStreamReader(in, StandardCharsets.UTF_8));
  }

  public PreParser(final Reader newReader) {
    reader = new BufferedReader(newReader);
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      reader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        reader.close();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    Iterator<String> it = lines.iterator();
    index = 0;
    while (it.hasNext()) {
      lb.setLength(0);
      String line = it.next();
      char[] ca = line.toCharArray();
      int scope = getScope(ca);
      LOG.trace("{}: {}", scope, line);
      boolean firstToken = true;
      boolean isLineEmpty = true;
      char c = (char) -1;
      for (int i = 0; i < ca.length; i++) {
        c = ca[i];
        if (c != ';') {
          if (firstToken && !InputReader.isWhitespace(c)) {
            firstToken = false;
            lb.append("(");
            lb.append(c);
          } else {
            if (InputReader.isTabOrSpace(c)) {
              lb.append(" ");
            } else {
              if (!InputReader.isWhitespace(c)) {
                isLineEmpty = false;
              }
              lb.append(c);
            }
          }
        } else {
          break;
        }
      }
      if (!isLineEmpty && lb.length() > 0) {
        int nextScope = getScopeOfLine(index + 1);
        if (nextScope <= scope) {
          LOG.trace("Injecting ')'");
          lb.append(")");
          int scopeDiff = scope - nextScope;
          LOG.trace("Scope diff: [{}]", scopeDiff);
          for (int i = 0; i < scopeDiff; i++) {
            LOG.trace("Injecting ')'");
            lb.append(")");
          }
        }
        String newline = lb.toString();
        sb.append(newline);
      }
      index++;
    }

    parsedText = sb.toString();
    parsedText = parsedText.replaceAll("\\s+", " ");
  }

  public String getParsedText() {
    return parsedText;
  }

  public InputStream getParsedTextAsInputStream() {
    return new ByteArrayInputStream(parsedText.getBytes(StandardCharsets.UTF_8));
  }

  private int getScopeOfLine(int index) {
    if (index == 0) {
      return 0;
    } else if (index == lines.size()) {
      return 0;
    } else {
      return getScope(lines.get(index).toCharArray());
    }
  }

  private int getScope(char[] ca) {
    int count = 0;
    for (int i = 0; i < ca.length; i++) {
      if (InputReader.isWhitespace(ca[i])) {
        count++;
        continue;
      } else {
        return count;
      }
    }
    return 0;
  }

}
