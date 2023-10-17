package com.linktic.api.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ErrorDescription {

  String reason;
  String domain;
  String code;
  String message;
}
