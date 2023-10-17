package com.linktic.model.commons;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PageMetadata {

  private Long totalElements;
  private int totalPages;
  private int number;
  private int size;

}
