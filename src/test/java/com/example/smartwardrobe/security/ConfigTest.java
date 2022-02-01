package com.example.smartwardrobe.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.Config;
import com.example.smartwardrobe.authentication.BasicAuthEntryPoint;
import com.example.smartwardrobe.service.impl.UserDetailsServiceImpl;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {Config.class, AuthenticationConfiguration.class})
@ExtendWith(SpringExtension.class)
class ConfigTest {
    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private AuthenticationTrustResolver authenticationTrustResolver;

    @MockBean
    private BasicAuthEntryPoint basicAuthEntryPoint;

    @Autowired
    private Config config;

    @MockBean
    private ContentNegotiationStrategy contentNegotiationStrategy;

    @Autowired
    private ObjectPostProcessor<Object> objectPostProcessor;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    void testConfigureGlobal() throws Exception {
        when(this.passwordEncoder.encode(any())).thenReturn("secret");
        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(
                this.objectPostProcessor);
        this.config.configureGlobal(authenticationManagerBuilder);
        verify(this.passwordEncoder).encode(any());
        assertTrue(authenticationManagerBuilder
                .getDefaultUserDetailsService() instanceof org.springframework.security.provisioning.InMemoryUserDetailsManager);
        assertTrue(((DaoAuthenticationProvider) ((ProviderManager) authenticationManagerBuilder.getOrBuild()).getProviders()
                .get(0)).getUserCache() instanceof org.springframework.security.core.userdetails.cache.NullUserCache);
        assertTrue(((DaoAuthenticationProvider) ((ProviderManager) authenticationManagerBuilder.getOrBuild()).getProviders()
                .get(0)).isHideUserNotFoundExceptions());
        assertFalse(
                ((DaoAuthenticationProvider) ((ProviderManager) authenticationManagerBuilder.getOrBuild()).getProviders()
                        .get(0)).isForcePrincipalAsString());
    }

    @Test
    void testConfigure() throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(
                this.objectPostProcessor);
        this.config.configure(authenticationManagerBuilder);
        assertTrue(authenticationManagerBuilder.isConfigured());
    }

    @Test
    void testConfigure2() throws Exception {
        AuthenticationManagerBuilder authenticationBuilder = new AuthenticationManagerBuilder(this.objectPostProcessor);
        HttpSecurity httpSecurity = new HttpSecurity(this.objectPostProcessor, authenticationBuilder, new HashMap<>());

        this.config.configure(httpSecurity);
        assertSame(httpSecurity, httpSecurity.logout().and());
    }

    @Test
    void testDaoAuthenticationProvider() {
        BasicAuthEntryPoint authenticationEntryPoint = new BasicAuthEntryPoint();
        DaoAuthenticationProvider actualDaoAuthenticationProviderResult = (new Config(authenticationEntryPoint,
                new Argon2PasswordEncoder())).daoAuthenticationProvider();
        assertTrue(actualDaoAuthenticationProviderResult
                .getUserCache() instanceof org.springframework.security.core.userdetails.cache.NullUserCache);
        assertTrue(actualDaoAuthenticationProviderResult.isHideUserNotFoundExceptions());
        assertFalse(actualDaoAuthenticationProviderResult.isForcePrincipalAsString());
    }

    @Test
    void testAddCorsMappings() {
        CorsRegistry corsRegistry = mock(CorsRegistry.class);
        when(corsRegistry.addMapping(any())).thenReturn(new CorsRegistration("Path Pattern"));
        this.config.addCorsMappings(corsRegistry);
        verify(corsRegistry).addMapping(any());
    }
}

