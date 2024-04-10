package run.freshr.common.security;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasLength;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import run.freshr.common.data.ExceptionData;
import run.freshr.common.data.ResponseData;
import run.freshr.domain.auth.unit.redis.AccessRedisUnit;
import run.freshr.domain.auth.unit.redis.RefreshRedisUnit;

/**
 * Token 관리 기능 정의
 *
 * @author FreshR
 * @apiNote Token 관리 기능 정의
 * @since 2024. 4. 1. 오후 3:04:31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

  private final AccessRedisUnit accessRedisUnit;
  private final RefreshRedisUnit refreshRedisUnit;

  private final ObjectMapper objectMapper;

  public static final String BEARER_PREFIX = "Bearer ";

  /**
   * 토큰 조회
   *
   * @param request request
   * @return string
   * @apiNote 요청 헤더에서 토큰 정보 조회
   * @author FreshR
   * @since 2024. 4. 1. 오후 1:43:15
   */
  public String extractToken(ServerHttpRequest request) {
    List<String> authorizationList = request.getHeaders().get(AUTHORIZATION);

    if (Objects.isNull(authorizationList)) {
      return null;
    }

    if (authorizationList.isEmpty()) {
      return null;
    }

    String header = authorizationList.get(0);

    return hasLength(header) ? header.replace(BEARER_PREFIX, "") : null;
  }

  /**
   * 접근 토큰 유효성 검증
   *
   * @param token token
   * @apiNote 접근 토큰 유효성 검증
   * @author FreshR
   * @since 2024. 3. 29. 오전 10:03:07
   */
  public boolean validateAccessToken(final String token) {
    return accessRedisUnit.exists(token);
  }

  /**
   * 갱신 토큰 유효성 검증
   *
   * @param token token
   * @apiNote 갱신 토큰 유효성 검증
   * @author FreshR
   * @since 2024. 3. 29. 오전 10:03:07
   */
  public boolean validateRefreshToken(final String token) {
    return refreshRedisUnit.exists(token);
  }

  /**
   * 오류 데이터 구성
   *
   * @param exchange      exchange
   * @param exceptionData exception data
   * @return mono
   * @apiNote 오류 데이터 구성
   * @author FreshR
   * @since 2024. 4. 1. 오후 3:03:36
   */
  public Mono<Void> error(ServerWebExchange exchange, ExceptionData exceptionData) {
    ServerHttpResponse response = exchange.getResponse();
    String message;

    try {
      message = objectMapper.writeValueAsString(ResponseData
          .builder()
          .name(exceptionData.getHttpStatus().getReasonPhrase())
          .code(exceptionData.getCode())
          .message(exceptionData.getMessage())
          .build());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      message = exceptionData.getMessage();
    }

    response.setStatusCode(exceptionData.getHttpStatus());

    return response.writeWith(
        Mono.just(
            response.bufferFactory().wrap(message.getBytes(UTF_8))));
  }

}
