package com.faxcoin.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.faxcoin.server.node.exceptions.InvalidSignatureException;
import org.springframework.http.HttpStatus;

public enum HttpErrorCustomResponse {

  WRONG_JSON_FORMAT(
          JsonProcessingException.class,
          "An invalid JSON structure has been provided.",
          HttpStatus.BAD_REQUEST
  ),
  INVALID_MESSAGE_SIGNATURE(
          InvalidSignatureException.class,
          "The message sent has been improperly signed.",
          HttpStatus.BAD_REQUEST
  ),
  UNKNOWN_RESPONSE(null, "An Unknown Error has occured.", HttpStatus.INTERNAL_SERVER_ERROR);

  HttpErrorCustomResponse(Class<?> exceptionClass, String httpErrorTitle, HttpStatus httpErrorStatusCode) {
    this.exceptionClass = exceptionClass;
    this.httpErrorTitle = httpErrorTitle;
    this.httpErrorStatusCode = httpErrorStatusCode;
  }

  private final Class<?> exceptionClass;

  private final String httpErrorTitle;
  private final HttpStatus httpErrorStatusCode;

  public static HttpErrorCustomResponse getErrorResponse(Class<?> exceptionClass) {
    for (HttpErrorCustomResponse response : HttpErrorCustomResponse.values()) {
      if (response.getExceptionClass().equals(exceptionClass)) {
        return response;
      }
    }
    return UNKNOWN_RESPONSE;
  }

  public Class<?> getExceptionClass() {
    return exceptionClass;
  }

  public HttpStatus getHttpErrorStatusCode() {
    return httpErrorStatusCode;
  }

  public String getHttpErrorTitle() {
    return httpErrorTitle;
  }
}
