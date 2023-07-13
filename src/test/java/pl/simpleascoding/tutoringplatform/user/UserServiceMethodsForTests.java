package pl.simpleascoding.tutoringplatform.user;

import pl.simpleascoding.tutoringplatform.security.jwt.Token;
import pl.simpleascoding.tutoringplatform.security.jwt.TokenType;

 interface UserServiceMethodsForTests {

    long ID_1L = 1L;

    String USERNAME = "TestUser";

    String PASSWORD = "Password1!";

    String NAME = "TestName";

    String SURNAME = "TestSurname";

    String EMAIL = "test@mail.com";

    default User createUserEntity() {
        User user = new User();
        user.setId(ID_1L);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setEmail(EMAIL);
        return user;
    }

    default Token createTokenEntity() {
        Token token = new Token();
        token.setType(TokenType.REGISTER_CONFIRMATION);
        token.setUser(createUserEntity());
        return token;
    }

}
