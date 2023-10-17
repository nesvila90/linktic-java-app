package com.linktic.api.util;

import static com.linktic.api.util.RequestUtil.FiltersParameters.BRAND;
import static com.linktic.api.util.RequestUtil.FiltersParameters.FINAL_RANGE;
import static com.linktic.api.util.RequestUtil.FiltersParameters.INITIAL_RANGE;
import static com.linktic.api.util.RequestUtil.FiltersParameters.NAME;
import static com.linktic.api.util.RequestUtil.FiltersParameters.PAGE_NUMBER;
import static com.linktic.api.util.RequestUtil.FiltersParameters.PAGE_SIZE;

import com.linktic.api.request.FiltersDTO;
import com.linktic.model.commons.BusinessException;
import com.linktic.model.commons.message.BusinessErrorMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@UtilityClass
public class RequestUtil {

  public static Mono<FiltersDTO> extractFilters(ServerRequest serverRequest) {
    return Mono.fromCallable(() -> processFilters(serverRequest));
  }

  private static FiltersDTO processFilters(ServerRequest serverRequest) throws BusinessException {
    return FiltersDTO.builder()
        .name(getQueryParameter(serverRequest, NAME.getName()))
        .brand(getQueryParameter(serverRequest, BRAND.getName()))
        .initialPriceRange(getQueryParameter(serverRequest, INITIAL_RANGE.getName()))
        .finalPriceRange(getQueryParameter(serverRequest, FINAL_RANGE.getName()))
        .pageNumber(getRequiredQueryParameter(serverRequest, PAGE_NUMBER.getName()))
        .pageSize(getRequiredQueryParameter(serverRequest, PAGE_SIZE.getName()))
        .build();
  }

  public static String getRequiredQueryParameter(ServerRequest serverRequest, String paramName)
      throws BusinessException {
    return serverRequest.queryParam(paramName)
        .orElseThrow(() -> new BusinessException(BusinessErrorMessage.MISSING_REQUIRED_FIELD));
  }

  public static String getQueryParameter(ServerRequest serverRequest, String paramName) {
    return serverRequest.queryParam(paramName)
        .orElse(null);
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  enum FiltersParameters {

    NAME("name"),
    BRAND("brand"),
    INITIAL_RANGE("initial_range"),
    FINAL_RANGE("final_range"),
    PAGE_NUMBER("page_number"),
    PAGE_SIZE("page_size");

    private final String name;

  }

}

