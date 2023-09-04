package com.example.post.config.filter;

import lombok.Getter;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] cachedInputStream;

    @Getter
    private Collection<Part> multipart;

    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        try {
            this.multipart = request.getParts();
        } catch (IOException | ServletException e) {
            this.multipart = List.of();
        }
        try (InputStream requestInputStream = request.getInputStream()) {
            this.cachedInputStream = StreamUtils.copyToByteArray(requestInputStream);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            private final InputStream cachedBodyInputStream = new ByteArrayInputStream(cachedInputStream);

            @Override
            public boolean isFinished() {
                try {
                    return cachedBodyInputStream.available() == 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }



            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() throws IOException {
                return cachedBodyInputStream.read();
            }
        };
    }

}

