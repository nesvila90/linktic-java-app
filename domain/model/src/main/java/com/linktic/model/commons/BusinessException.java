package com.linktic.model.commons;

import com.linktic.model.commons.message.BusinessErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends Exception {

  private final BusinessErrorMessage businessErrorMessage;

}
