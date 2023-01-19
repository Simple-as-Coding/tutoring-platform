package pl.simpleascoding.tutoringplatform.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.security.jwt.exception.InvalidTokenException;
import pl.simpleascoding.tutoringplatform.security.jwt.exception.MissingRefreshTokenException;
import pl.simpleascoding.tutoringplatform.user.dto.CredentialsDTO;
import pl.simpleascoding.tutoringplatform.security.jwt.JwtService;
import pl.simpleascoding.tutoringplatform.user.UserService;

import java.util.HashMap;
import java.util.Map;

import static pl.simpleascoding.tutoringplatform.security.SecurityFinals.*;

@Service
@Primary
@RequiredArgsConstructor
class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public Map<String, String> login(CredentialsDTO dto, String issuer) {
        if (dto.username() == null || dto.password() == null) throw new MissingParametersException();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN, jwtService.createAccessToken(dto.username(), issuer));
        tokens.put(REFRESH_TOKEN, jwtService.createRefreshToken(dto.username(), issuer));

        return tokens;
    }

    public Map<String, String> refresh(String auth, String issuer) {
        if (auth == null || !auth.startsWith(TOKEN_PREFIX)) throw new MissingRefreshTokenException();

        DecodedJWT jwt = jwtService.verifyAndReturnDecodedToken(auth.substring(TOKEN_PREFIX.length()));

        if (!REFRESH.equals(jwt.getClaim(TYPE).asString())) throw new InvalidTokenException();

        String username = jwt.getSubject();

        //This line isn't required in the "login()" method, since authenticationManager automatically checks if the user exists
        //Here, however, we need to check if the refresh token, even if valid, corresponds to a registered user
        userService.loadUserByUsername(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN, jwtService.createAccessToken(username, issuer));
        tokens.put(REFRESH_TOKEN, auth.replace(TOKEN_PREFIX, ""));

        return tokens;
    }

}
