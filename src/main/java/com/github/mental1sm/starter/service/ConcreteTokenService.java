package com.github.mental1sm.starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.mental1sm.starter.domain.TokenPack;
import com.github.mental1sm.starter.domain.TokenPackWrapper;
import com.github.mental1sm.starter.dto.TokenAuthDTO;
import com.github.mental1sm.starter.dto.TokenRegistrationDTO;
import com.github.mental1sm.starter.infrastructure.requests.*;
import com.github.mental1sm.starter.util.exceptions.InvalidCredentialsException;
import com.github.mental1sm.starter.util.exceptions.UserIsAlreadyExistsException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Класс, реализующий TokenService
 * Отвечает за все манипуляции, связанные с JWT токенами
 */
@Service
@RequiredArgsConstructor
public class ConcreteTokenService implements TokenService {

    private final AuthTokenRequest authTokenRequest;
    private final IntrospectTokenRequest introspectTokenRequest;
    private final RefreshTokenRequest refreshTokenRequest;
    private final AdminTokenRequest adminTokenRequest;
    private final RegUserInRealmRequest regUserInRealmRequest;

    /**
     * {@inheritDoc}
    */
    @Override
    public int registerNewUser(TokenRegistrationDTO tokenRegistrationDTO) throws JsonProcessingException, UserIsAlreadyExistsException {
        TokenPack admin = adminTokenRequest.getAdminToken();
        return regUserInRealmRequest.regUserInRealm(admin, tokenRegistrationDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenPackWrapper authUser(TokenAuthDTO tokenAuthDTO) throws JsonProcessingException, InvalidCredentialsException {
        return authTokenRequest.getAuthTokens(tokenAuthDTO);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean introspectToken(String accessToken) throws JsonProcessingException, InvalidCredentialsException {
        return introspectTokenRequest.introspectAccessToken(accessToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TokenPackWrapper refreshToken(String refreshToken) throws JsonProcessingException, InvalidCredentialsException {
        return refreshTokenRequest.refreshToken(refreshToken);
    }
}
