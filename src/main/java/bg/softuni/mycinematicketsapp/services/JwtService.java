package bg.softuni.mycinematicketsapp.services;

import java.util.Map;
import java.util.Objects;

public interface JwtService {
    String generateToken(long userId, Map<String, Object> claims);
}
