package com.zip.weather.tracker.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private ErrorCodes errorCodes;

  public ApplicationException(final ErrorCodes errorCodes) {
    super(errorCodes.getErrorDescription());
    this.errorCodes = errorCodes;
  }
}
