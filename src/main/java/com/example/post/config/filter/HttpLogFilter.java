package com.example.post.config.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE) // 가장 높은 우선순위로 설정
@Component
public class HttpLogFilter extends OncePerRequestFilter {

    private AtomicLong id = new AtomicLong(0L);

    private static final long KILOBYTE = 1024L;
    private static final long MEGABYTE = KILOBYTE * 1024L;

    private final ObjectMapper objectMapper;
    private final AntPathMatcher antPathMatcher;
    private final DecimalFormat decimalFormat;




    public HttpLogFilter() {
        this.objectMapper = new ObjectMapper();
        this.antPathMatcher = new AntPathMatcher();
        this.decimalFormat = new DecimalFormat("#,##0.00");

        // 출력 형식 설정 (옵션)
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isAsyncDispatch(request) || isSwagger(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            try {
                MDC.put("traceId", generateTraceId());
                doFilterWrapped(request, response, filterChain);
            }
            finally {
                MDC.clear();
            }
        }

    }

    private void doFilterWrapped(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        long startTime = 0;
        HttpResponseWrapper responseWrapper = new HttpResponseWrapper(httpServletResponse);

        try {
            HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpServletRequest);
            logRequest(requestWrapper);
            startTime = System.currentTimeMillis();
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logResponse(responseWrapper, startTime);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(HttpRequestWrapper requestWrapper) {
        StringBuilder sb = new StringBuilder();

        start("REQUEST", sb);

        url(requestWrapper, sb);
        requestContents(requestWrapper, sb);

        headers(requestWrapper, sb);

        end("REQUEST", sb);

        log.info(sb.toString());
    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper, long startTime) {
        StringBuilder sb = new StringBuilder();
        final long executedTime = System.currentTimeMillis() - startTime;

        start("RESPONSE", sb);

        responseContents(responseWrapper, sb);

        result(executedTime, responseWrapper.getStatus(), sb);

        end("RESPONSE", sb);

        log.info(sb.toString());
    }

    private void result(long executedTime, int statusCode, StringBuilder sb) {
        sb.append(String.format("\nStatus: %s, Response Time: %s ms\n", statusCode, executedTime));
    }


    private void start(String prefix, StringBuilder sb) {
        sb.append(String.format("\n╭───────────────────────────────── [%s] HTTP %s MESSAGE START ─────────────────────────────────╮\n", MDC.get("traceId"), prefix));
    }

    private void end(String prefix, StringBuilder sb) {
        sb.append(String.format("\n╰───────────────────────────────── [%s] HTTP %s MESSAGE END   ─────────────────────────────────╯\n", MDC.get("traceId"), prefix));
    }


    private void url(HttpRequestWrapper request, StringBuilder sb) {
        sb.append(String.format("[%s] %s", request.getMethod(), request.getRequestURL()));
        String queryString = request.getQueryString();
        if (hasText(queryString)) {
            sb.append(String.format("?%s", queryString));
        }
        sb.append("\n");
    }

    private void headers(HttpRequestWrapper request, StringBuilder sb) {
        sb.append("\nⓢ Header\n");

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            if (allowedHeaders().contains(headerName.toLowerCase())) {
                sb.append(String.format("%s : %s\n", rightPad(headerName), request.getHeader(headerName)));
            }
        }
    }


    private void requestContents(HttpRequestWrapper requestWrapper, StringBuilder sb) {
        if (isMultipartRequest(requestWrapper)) {
            multipart(requestWrapper, sb);
        } else {
            try (ServletInputStream inputStream = requestWrapper.getInputStream()) {
                json(inputStream, sb);
            } catch (IOException ignored) {
            }
        }
    }

    private void responseContents(ContentCachingResponseWrapper response, StringBuilder sb) {
        try (InputStream inputStream = response.getContentInputStream()) {
            json(inputStream, sb);
        } catch (IOException ignored) {
        }
    }

    private void multipart(HttpRequestWrapper request, StringBuilder sb) {
        Collection<Part> parts = request.getMultipart();
        if (isEmpty(parts)) {
            return;
        }

        sb.append("\nⓢ Multipart\n");

        int i = 1;
        for (Part part : parts) {
            sb.append(String.format("%s : \n", i));
            sb.append(String.format("%s: %s\n", rightPad("FileName"), part.getSubmittedFileName()));
            sb.append(String.format("%s: %s\n", rightPad("ContentType"), part.getContentType()));
            sb.append(String.format("%s: %s\n", rightPad("Size"), formatBytes(part.getSize())));
            sb.append(String.format("%s: %s\n", rightPad("FieldName"), part.getName()));
            i++;
        }

    }

    private void json(InputStream inputStream, StringBuilder sb) {
        try {
            byte[] content = StreamUtils.copyToByteArray(inputStream);

            if (content.length > 0) {
                sb.append("\nⓢ Content\n");

                // JSON 문자열을 JsonNode로 변환
                JsonNode jsonNode = objectMapper.readTree(new String(content));

                // 특정 키 제외
                if (jsonNode.isObject()) {
                    ObjectNode objectNode = (ObjectNode) jsonNode;
                    if (objectNode.has("password")) {
                        objectNode.replace("password", objectMapper.valueToTree("xxx"));
                    }
                }

                // 너무 길 경우 한줄로
                if (content.length > 2000) {
                    sb.append(String.format("%s\n", jsonNode));
                    return;
                }

                sb.append(String.format("%s\n", objectMapper.writeValueAsString(jsonNode)));
            }
        } catch (IOException ignored) {
            sb.append("content parsing error\n");
        }
    }

    private static Set<String> allowedHeaders() {
        Set<String> headers = Set.of(
                HttpHeaders.USER_AGENT,
                HttpHeaders.REFERER,
                HttpHeaders.COOKIE,
                HttpHeaders.CONTENT_LANGUAGE,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.HOST,
                HttpHeaders.ACCEPT_LANGUAGE,
                HttpHeaders.AUTHORIZATION
        );

        return headers.stream()
                .map(String::toLowerCase)
                .collect(toUnmodifiableSet());
    }

    private boolean isMultipartRequest(HttpRequestWrapper requestWrapper) {
        String contentType = requestWrapper.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/form-data");
    }

    private String rightPad(String value) {
        return org.apache.commons.lang3.StringUtils.rightPad(value, 24);
    }

    private boolean isSwagger(String requestURI) {
        return antPathMatcher.match("/swagger**/**", requestURI) || antPathMatcher.match("/v3/api-docs**/**", requestURI);
    }

    private String generateTraceId() {
        LocalDate today = LocalDate.now();
        long currentTimeMillis = System.currentTimeMillis();
        long randomValue = this.id.incrementAndGet() % 1000;
        return String.format("%02d%02d%02d%d", today.getYear(), today.getMonthValue(), today.getDayOfMonth(), currentTimeMillis % 1000000 * 1000 + randomValue);
    }


    private String formatBytes(long bytes) {
        if (bytes < KILOBYTE) {
            return bytes + " BYTE";
        } else if (bytes < MEGABYTE) {
            double kilobytes = (double) bytes / KILOBYTE;
            return decimalFormat.format(kilobytes) + " KB";
        } else {
            double megabytes = (double) bytes / MEGABYTE;
            return decimalFormat.format(megabytes) + " MB";
        }
    }


}
