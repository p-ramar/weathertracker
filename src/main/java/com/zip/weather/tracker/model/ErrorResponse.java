package com.zip.weather.tracker.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Defines the JSON output format of error responses
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"errorCode", "errorMessage", "errorDescription"})
public class ErrorResponse {

  private String errorMessage;
  private String errorCode;
  private String errorDescription;

  public String toString() {
    return "{\"errorCode\":\""
        + errorCode
        + "\",\"errorMessage\":\""
        + errorMessage
        + "\",\"errorDescription\":\""
        + errorDescription
        + "\"}";
  }
}
