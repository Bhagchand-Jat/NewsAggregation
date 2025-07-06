package com.news_aggregation_system.handler;

import com.news_aggregation_system.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.news_aggregation_system.service.common.Constant.*;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(TYPE_JSON);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response.getWriter().write(MAPPER.writeValueAsString(ApiResponse.error(ACCESS_DENIED)));
    }
}

