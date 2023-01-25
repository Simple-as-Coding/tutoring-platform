package pl.simpleascoding.tutoringplatform.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtService {

    DecodedJWT verifyAndReturnDecodedToken(String token);

    String createAccessToken(String username, String issuer);

    String createRefreshToken(String username, String issuer);

}
