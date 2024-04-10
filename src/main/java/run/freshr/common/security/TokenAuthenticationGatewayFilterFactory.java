package run.freshr.common.security;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasLength;

import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import run.freshr.common.data.ExceptionsData;
import run.freshr.common.security.TokenAuthenticationGatewayFilterFactory.Config;
import run.freshr.common.utils.JwtUtil;

@Component
public class TokenAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

  private final TokenProvider provider;
  private final ExceptionsData exceptionsData;

  public TokenAuthenticationGatewayFilterFactory(TokenProvider provider,
      ExceptionsData exceptionsData) {
    super(Config.class);

    this.provider = provider;
    this.exceptionsData = exceptionsData;
  }

  @Setter
  public static class Config {
    private Boolean isRefresh;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      boolean isAccess = !(!isNull(config.isRefresh) && config.isRefresh);
      String token = provider.extractToken(exchange.getRequest());

      if (!hasLength(token) && isAccess) {
        return chain.filter(exchange);
      }

      boolean flag = isAccess
          ? provider.validateAccessToken(token)
          : provider.validateRefreshToken(token);

      if (!flag) {
        return provider.error(exchange, exceptionsData.getUnAuthenticated());
      }

      flag = JwtUtil.checkExpiration(token);

      if (!flag) {
        return provider.error(exchange, exceptionsData.getExpiredJwt());
      }

      return chain.filter(exchange);
    };
  }

}
