package com.zip.weather.tracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
  SYSTEM_EXCEPTION("WT001", 500, "Unexpected system exception occurred"),
  MEDIA_TYPE_NOT_SUPPORTED("WT002", 415, "HTTP media type not supported"),
  REQUESTED_METHOD_NOT_ALLOWED("WT003", 405, "Requested http method not allowed"),
  INVALID_USER_ID("WT004", 400, "Invalid user id"),
  USER_NOT_FOUND("WT005", 404, "User Not Found"),
  PROFILE_NOT_FOUND("WT006", 404, "No weather profile found"),
  CITY_NOT_FOUND("WT007", 404, "City not found"),
  CITY_AlREADY_ADDED("WT008", 500, "City already added in profile"),
  OPERATION_NOT_FOUND("WT009", 404, "Operation not supported"),
  BAD_REQUEST("WT010", 400, "Bad Request"),
  JSON_PARSING_EXCEPTION("WT011", 500, "Failed to parse the json message");

  private final String errorCode;
  private final int httpStatus;
  private final String errorDescription;
}
