package com.linktic.model.commons;

import com.linktic.model.commons.message.TechnicalErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TechnicalException extends Exception {

  private final TechnicalErrorMessage technicalErrorMessage;

}
