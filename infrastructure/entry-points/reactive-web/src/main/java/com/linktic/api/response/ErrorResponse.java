package com.linktic.api.response;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ErrorResponse {

  List<ErrorDescription> errors;

}
