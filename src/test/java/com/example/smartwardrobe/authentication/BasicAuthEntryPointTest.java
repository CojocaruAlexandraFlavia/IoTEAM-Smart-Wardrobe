package com.example.smartwardrobe.authentication;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.apache.catalina.connector.Response;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AccountExpiredException;

class BasicAuthEntryPointTest {

    @Test
    void testCommence() throws IOException {
        BasicAuthEntryPoint basicAuthEntryPoint = new BasicAuthEntryPoint();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        Response response = mock(Response.class);
        doNothing().when(response).sendError(anyInt(), anyString());
        basicAuthEntryPoint.commence(httpServletRequest, response, new AccountExpiredException("Msg"));
        verify(response).sendError(anyInt(), any());
    }
}

