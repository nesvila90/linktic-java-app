package com.linktic.api.handleexception;


import com.linktic.api.response.ErrorDescription;
import com.linktic.api.response.ErrorResponse;
import com.linktic.model.commons.BusinessException;
import com.linktic.model.commons.TechnicalException;
import com.linktic.model.commons.message.TechnicalErrorMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {


  private final String appName;

  public GlobalErrorWebExceptionHandler(DefaultErrorAttributes errorAttributes, ApplicationContext applicationContext,
      ServerCodecConfigurer serverCodecConfigurer,
      @Value("${spring.application.name}") String appName) {
    super(errorAttributes, new WebProperties.Resources(), applicationContext);
    super.setMessageWriters(serverCodecConfigurer.getWriters());
    super.setMessageReaders(serverCodecConfigurer.getReaders());
    this.appName = appName;
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
    return Mono.just(request)
        .map(this::getError)
        .flatMap(Mono::error)
        .log()
        .onErrorResume(TechnicalException.class, this::buildErrorResponse)
        .onErrorResume(BusinessException.class, this::buildErrorResponse)
        .onErrorResume(this::buildErrorResponse)
        .cast(ErrorDescription.class)
        .map(errorResponse -> errorResponse.toBuilder().domain(request.path()).build())
        .flatMap(errorResponse -> buildResponse(errorResponse, request));
  }

  private Mono<ErrorDescription> buildErrorResponse(TechnicalException technicalException) {
    return Mono.just(ErrorDescription.builder()
        .reason(technicalException.getTechnicalErrorMessage().getMessage())
        .code(technicalException.getTechnicalErrorMessage().getCode())
        .message(technicalException.getTechnicalErrorMessage().getMessage())
        .build());
  }

  private Mono<ErrorDescription> buildErrorResponse(BusinessException businessException) {
    return Mono.just(ErrorDescription.builder()
        .reason(businessException.getBusinessErrorMessage().getMessage())
        .code(businessException.getBusinessErrorMessage().getCode())
        .message(businessException.getBusinessErrorMessage().getMessage())
        .build());
  }

  private Mono<ErrorDescription> buildErrorResponse(Throwable throwable) {
    throwable.printStackTrace();
    return Mono.just(ErrorDescription.builder()
        .reason(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getMessage())
        .code(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getCode())
        .message(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.getMessage())
        .build());
  }

  private Mono<ServerResponse> buildResponse(ErrorDescription errorDto, final ServerRequest request) {
    var errorResponse = ErrorResponse.builder()
        .errors(List.of(errorDto))
        .build();

    return ServerResponse.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(errorResponse);
  }
}
