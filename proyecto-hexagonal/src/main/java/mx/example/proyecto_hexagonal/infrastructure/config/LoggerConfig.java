package mx.example.proyecto_hexagonal.infrastructure.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Component
@WebFilter(urlPatterns = "/*")
public class LoggerConfig implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConfig.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("=====> API Logger Config Initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 🔹 Log Headers
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(": ").append(httpRequest.getHeader(headerName)).append(" | ");
        }

        // 🔹 Log de la Request
        LOGGER.info("\n➡️ [REQUEST] - [{} {}] \n\tURI: {} \n\tHeaders: [{}]",
                httpRequest.getMethod(),
                httpRequest.getProtocol(),
                httpRequest.getRequestURI(),
                headers.toString()
        );

        // 🔹 Continuar con el filtro
        chain.doFilter(request, response);

        // 🔹 Log de la Response
        LOGGER.info("\n⬅️ [RESPONSE] - [{}] \n\tStatus: {} \n\tHeaders: [{}]",
                httpRequest.getMethod(),
                httpResponse.getStatus(),
                httpResponse.getHeaderNames().toString()
        );
    }

    @Override
    public void destroy() {
        LOGGER.info("=====> API Logger Config Destroyed");
    }
}
