package com.example.smartwardrobe.authentication;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.FilterChain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class HeaderStringFilterTest {
    @Test
    void testDoFilterInternal() {
        HeaderStringFilter headerStringFilter = new HeaderStringFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThrows(NullPointerException.class,
                () -> headerStringFilter.doFilterInternal(request, null, mock(FilterChain.class)));
    }

    @Test
    void testDoFilterInternal2() {
        HeaderStringFilter headerStringFilter = new HeaderStringFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        assertThrows(NullPointerException.class,
                () -> headerStringFilter.doFilterInternal(request, response, null));
    }
}

