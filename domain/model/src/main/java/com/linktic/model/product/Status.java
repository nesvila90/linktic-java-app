package com.linktic.model.product;

import java.util.Arrays;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Status {

  NEW("Nuevo"),
  USED("Usado");

  private final String descriptionName;

  @SneakyThrows
  public static Optional<Status> setStatusFromString(String value) {
    return Arrays.stream(Status.values())
        .filter(status -> status.getDescriptionName().equalsIgnoreCase(value))
        .findAny();
  }
}
