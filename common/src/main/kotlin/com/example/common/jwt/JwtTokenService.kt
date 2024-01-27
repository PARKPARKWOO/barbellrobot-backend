package com.example.common.jwt

import com.example.common.exception.ParseJwtFailedException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtTokenService(
    @Value("\${jwt.secret-key}")
    private val secretKeyString: String,
    @Value("\${jwt.expire-millis}")
    private val expireMillis: Long,
) {
    private val secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString))

    fun build(claims: Map<String, Any>): String {
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .setHeader(Jwts.header().setType(Header.JWT_TYPE))
            .setClaims(claims)
            .setExpiration(Date((now + expireMillis)))
            .setIssuedAt(Date(now))
            .signWith(
                secretKey,
                SignatureAlgorithm.ES512,
            )
            .compact()
    }

    fun parse(token: String): Map<String, Any> {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw com.example.common.exception.ExpiredJwtException(e.message.toString())
        } catch (e: JwtException) {
            throw ParseJwtFailedException("failed to parse jwt: $token ")
        }
    }

    companion object {
        fun minKeyStringLength(algorithm: SignatureAlgorithm) = algorithm.minKeyLength.let { (it + 5) / 6 }
    }
}
