package org.example.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public enum TypicalServerExceptions {
  USER_NOT_FOUND(new ServerException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "USER_NOT_FOUND")),
  TYPE_NOT_FOUND(new ServerException(HttpStatus.NOT_FOUND, "TYPE_NOT_FOUND", "TYPE_NOT_FOUND")),
  SPECIALITY_NOT_FOUND(
      new ServerException(HttpStatus.NOT_FOUND, "SPECIALITY_NOT_FOUND", "SPECIALITY_NOT_FOUND")),
  SEARCH_NOT_FOUND(
      new ServerException(HttpStatus.NOT_FOUND, "SEARCH_NOT_FOUND", "SEARCH_NOT_FOUND")),
  ITEM_NOT_FOUND(new ServerException(HttpStatus.NOT_FOUND, "ITEM_NOT_FOUND", "ITEM_NOT_FOUND")),
  NOT_FOUND(new ServerException(HttpStatus.NOT_FOUND, "NOT_FOUND", "NOT_FOUND")),
  WRONG_INPUT_DATA(
      new ServerException(HttpStatus.BAD_REQUEST, "WRONG_INPUT_DATA", "WRONG_INPUT_DATA")),
  WRONG_LOGIN_PASSWORD(
      new ServerException(HttpStatus.BAD_REQUEST, "WRONG_LOGIN_PASSWORD", "WRONG_LOGIN_PASSWORD")),
  USER_NOT_DOC(new ServerException(HttpStatus.BAD_REQUEST, "USER_NOT_DOC", "USER_NOT_DOC")),
  PHARMACY_NOT_FOUND(
      new ServerException(HttpStatus.NOT_FOUND, "PHARMACY_NOT_FOUND", "PHARMACY_NOT_FOUND"));

  private final ServerException serverException;

  TypicalServerExceptions(HttpStatus httpStatus, String message, String moreInfo) {
    serverException = new ServerException(httpStatus, message, moreInfo);
  }

  TypicalServerExceptions(ServerException serverException) {
    this.serverException = serverException;
  }

  public TypicalServerExceptions message(String message) {
    this.serverException.setCode(message);
    return this;
  }

  public TypicalServerExceptions moreInfo(String moreInfo) {
    this.serverException.setMessage(moreInfo);
    return this;
  }

  public void throwException() {
    throw serverException;
  }

  public ServerException getServerException() {
    return serverException;
  }

  public Map<String, Object> getAnswer() {
    return serverException.getAnswer();
  }

  public int status() {
    return serverException.status.value();
  }
}
