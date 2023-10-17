package com.linktic.model.commons;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class PageDTO<T> {

  private List<T> content;
  private PageMetadata pageMetadata;

}
