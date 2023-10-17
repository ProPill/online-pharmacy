package org.example.resources;

import java.text.SimpleDateFormat;

public class DateTimeFormatter {
  private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

  public SimpleDateFormat getFormatter() {
    return formatter;
  }
}
