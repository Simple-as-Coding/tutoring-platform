package pl.simpleascoding.tutoringplatform.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;

import static pl.simpleascoding.tutoringplatform.security.SecurityFinals.*;

@Service
@Primary
class JwtServiceImpl implements JwtService {
    private final Algorithm algorithm;

    @Value("${jwt.accessLifetime}")
    private long accessLifetime;

    @Value("${jwt.refreshLifetime}")
    private long refreshLifetime;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        algorithm = Algorithm.HMAC256(secret);
    }

    @Override
    public DecodedJWT verifyAndReturnDecodedToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }

    @Override
    public String createAccessToken(String username, String issuer) {
        return JWT.create().withSubject(username).withIssuer(issuer).withClaim(TYPE, ACCESS).withExpiresAt(new Date(System.currentTimeMillis() + accessLifetime)).sign(algorithm);
    }

    @Override
    public String createRefreshToken(String username, String issuer) {
        return JWT.create().withSubject(username).withIssuer(issuer).withClaim(TYPE, REFRESH).withExpiresAt(new Date(System.currentTimeMillis() + refreshLifetime)).sign(algorithm);
    }
}
