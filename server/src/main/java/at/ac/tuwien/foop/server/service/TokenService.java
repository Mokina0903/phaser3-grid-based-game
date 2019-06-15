package at.ac.tuwien.foop.server.service;


import at.ac.tuwien.foop.server.exception.InvalidTokenException;
import at.ac.tuwien.foop.server.exception.PlayerNotFoundException;
import at.ac.tuwien.foop.server.game.player.Player;
import at.ac.tuwien.foop.server.repository.PlayerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private static final Long DAY = 24 * 60 * 60 * 1000L;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private final PlayerRepository playerRepository;

    @Value("${security.jwt.secret}")
    private String secret;

    public TokenService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String createNewToken(Long id) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + DAY);
        return Jwts.builder()
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(expiration)
                .setSubject(id.toString())
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    public Player getPlayerId(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch(Exception e) {
            throw new InvalidTokenException();
        }
        Long id = Long.parseLong(claims.getSubject());
        return playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
    }
}
