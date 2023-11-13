package com.ment09.starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ment09.starter.domain.TokenPack;
import com.ment09.starter.domain.TokenPackWrapper;
import com.ment09.starter.dto.TokenAuthDTO;
import com.ment09.starter.dto.TokenRegistrationDTO;
import com.ment09.starter.infrastructure.requests.*;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ConcreteTokenService implements TokenService {

    private final AuthTokenRequest authTokenRequest;
    private final IntrospectTokenRequest introspectTokenRequest;
    private final RefreshTokenRequest refreshTokenRequest;
    private final AdminTokenRequest adminTokenRequest;
    private final RegUserInRealmRequest regUserInRealmRequest;

    @Override
    public void registerNewUser(TokenRegistrationDTO tokenRegistrationDTO) throws JsonProcessingException {
        TokenPack admin = adminTokenRequest.getAdminToken();
        regUserInRealmRequest.regUserInRealm(admin, tokenRegistrationDTO);
    }

    @Override
    public TokenPackWrapper authUser(TokenAuthDTO tokenAuthDTO) throws JsonProcessingException {
        return authTokenRequest.getAuthTokens(tokenAuthDTO);
    }

    @Override
    public boolean validateToken(String accessToken) {
        try {
            JWT token = JWTParser.parse(accessToken);
            Instant exp = token.getJWTClaimsSet().getExpirationTime().toInstant();
            Instant current = Instant.now();
            return (exp != null && current.isBefore(exp));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean introspectToken(String accessToken) throws JsonProcessingException {
        return introspectTokenRequest.introspectAccessToken(accessToken);
    }

    @Override
    public TokenPackWrapper refreshToken(String refreshToken) throws JsonProcessingException {
        return refreshTokenRequest.refreshToken(refreshToken);
    }
}
