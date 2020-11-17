package me.sangoh.training.kotlin_spring_boot.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

// JWT 토큰을 생성 및 검증 모듈
@Component
class JwtTokenProvider(
        private val userDetailsService: UserDetailsService
) {

    @Value("spring.jwt.secret")
    private var secretKey: String = ""

    private val tokenValidMilisecond = 1000L * 60 * 60 // 1시간만 토큰 유효


    @PostConstruct
    fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    /**
     * Jwt 토큰 생성
     */
    fun createToken(userPk: String, roles: List<String>): String {
        val claims = Jwts.claims().setSubject(userPk)
        claims["roles"] = roles
        val now = Date()

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    /**
     * Jwt 토큰으로 인증 정보를 조회
     */
    fun  getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    /**
     * Jwt 토큰에서 회원 구별 정보 추출
     */
    fun getUserPk(token: String?): String? {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    /**
     * Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt 토큰"
     */
    fun resolveToken(req: HttpServletRequest): String? {
        return req.getHeader("X-AUTH-TOKEN")
    }

    /**
     *  Jwt 토큰의 유효성 + 만료일자 확인
     */
    fun validateToken(jwtToken: String?): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}