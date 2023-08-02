package com.example.post.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import lombok.Getter;
import org.springframework.util.StreamUtils;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] cachedInputStream;

    @Getter
    private Collection<Part> multipart;
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
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
