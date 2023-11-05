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

  PHONE_IS_REGISTERED(
      new ServerException(HttpStatus.CONFLICT, "PHONE_IS_REGISTERED", "PHONE_IS_REGISTERED")),

  INVALID_FIO(new ServerException(HttpStatus.BAD_REQUEST, "INVALID_FIO", "INVALID_FIO")),

  INVALID_PHONE(new ServerException(HttpStatus.BAD_REQUEST, "INVALID_PHONE", "INVALID_PHONE")),

  INVALID_PASSWORD(
      new ServerException(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "INVALID_PASSWORD"));
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
