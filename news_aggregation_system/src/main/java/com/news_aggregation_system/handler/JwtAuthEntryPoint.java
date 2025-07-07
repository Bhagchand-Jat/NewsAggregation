package com.news_aggregation_system.handler;

import com.news_aggregation_system.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.news_aggregation_system.service.common.Constant.*;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType(TYPE_JSON);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.getWriter().write(MAPPER.writeValueAsString(ApiResponse.error(UN_AUTHORIZED_ACCESS)));
    }
}

