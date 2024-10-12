package com.spring.tour.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.io.IOException;


@Order(1)
@Slf4j
public class RequestResponseFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.info(
                "Logging Request  {} : {}", req.getMethod(),
                req.getRequestURI());
        chain.doFilter(request, response);
        log.info(
                "Logging Response :{}",
                res.getContentType());
    }

}

