package org.example.resources;

public class Patterns {
  public static final String FIORegex =
      "^[А-ЯЁ][а-яё]{2,}([-][А-ЯЁ][а-яё]{2,})?\\s[А-ЯЁ][а-яё]{2,}(\\s[А-ЯЁ][а-яё]{2,})?$";
  public static final String phoneNumberRegex = "^\\+7[0-9]{10}$";
  public static final String passwordRegex = "^(([A-z0-9]){6,16})$";

  public static final String nameRegex = "^[\\wа-яА-Я%-]+$";
}
