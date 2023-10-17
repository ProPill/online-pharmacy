package org.example.exception;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ServerException extends RuntimeException {
  @NotNull HttpStatus status;
  String code;
  String message;

  public ServerException(@NotNull HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public ServerException(HttpStatus status, String code) {
    this(status, code, code);
  }

  public ServerException(HttpStatus status) {
    this(status, status.name(), status.name());
  }

  public static void throwException(
      @NotNull HttpStatus httpStatus, String message, String moreInfo) {
    throw new ServerException(httpStatus, message, moreInfo);
  }

  public static void throwException(@NotNull HttpStatus httpStatus, String message) {
    throw new ServerException(httpStatus, message, message);
  }

  public static void throwException(@NotNull HttpStatus httpStatus) {
    throw new ServerException(httpStatus, httpStatus.name(), "");
  }

  public Map<String, Object> getAnswer() {
    return Map.of("code", code, "message", message, "status", status.value());
  }
}
