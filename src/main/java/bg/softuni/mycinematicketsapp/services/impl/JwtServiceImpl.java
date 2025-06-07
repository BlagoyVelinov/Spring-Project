package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.services.JwtService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateToken(long userId, Map<String, Object> claims) {
        return null;
    }
}
