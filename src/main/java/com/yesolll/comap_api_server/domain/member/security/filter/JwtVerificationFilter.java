package com.yesolll.comap_api_server.domain.member.security.filter;

import com.yesolll.comap_api_server.domain.member.security.util.JWTTokenizer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JWTTokenizer jwtTokenizer;
//    private final CustomAuthorityUtils authorityUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> claims = verifyJws(request, response);
        if (claims != null) { // 토큰 검증, claims 파싱이 잘 되면
            setAuthenticationToContext(claims);
            filterChain.doFilter(request, response);
        }
    }

    //  "Header - Authorization 없거나  'Bearer' 로 시작하지 않을 때"
    //  JWT 자격증명이 필요하지 않은 리소스에 대한 요청이라고 판단, 다음 필터로 처리 넘어가도록
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer");
    }

    // Jws 가져와서 getClaims() 처리
    private Map<String, Object> verifyJws(HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Jws<Claims> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey, response);
        return claims != null ? claims.getBody() : null;
    }

    // SecurityContextHolder 에 인증정보 저장
    private void setAuthenticationToContext(Map<String, Object> claims) {
//        String username = (String) claims.get("username");
//        List<String> roles = TypeCastingUtils.objToList(claims.get("roles"), String.class);
//        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(roles);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}