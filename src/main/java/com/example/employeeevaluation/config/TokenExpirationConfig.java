package com.example.employeeevaluation.config;

import com.example.employeeevaluation.entity.Token;
import com.example.employeeevaluation.entity.User;
import com.example.employeeevaluation.repository.TokenRepository;
import com.example.employeeevaluation.service.impl.JwtServiceImpl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenExpirationConfig {

  private final TokenRepository tokenRepository;

  private final JwtServiceImpl jwtService;

  @Autowired
  public TokenExpirationConfig(TokenRepository tokenRepository, JwtServiceImpl jwtService) {
    this.tokenRepository = tokenRepository;
    this.jwtService = jwtService;
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void checkTokenExpirationAndRefresh() {
    List<Token> expiredTokens = tokenRepository.findByExpireBefore(new Date());
    for (Token token : expiredTokens) {
      User user = token.getUser();
      String newJwtToken = jwtService.generateToken(user);
      String newJwtRefreshToken = jwtService.generateRefreshToken(user);
      Date newExpire =
          jwtService.getExpirationTimeFromToken(newJwtToken, jwtService.getSecretKey());

      token.setTokenString(newJwtToken);
      token.setExpire(newExpire);

      tokenRepository.save(token);
    }
  }
}
