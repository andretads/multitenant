package br.com.damsete.multitenant.filter;

import br.com.damsete.multitenant.TenantContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static br.com.damsete.multitenant.TenantContext.DEFAULT_TENANT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

class TenantFilterTest {

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private FilterChain chain;

    private TenantFilter tenantFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.tenantFilter = new TenantFilter();
    }

    @AfterEach
    void tearDown() {
        this.tenantFilter = null;
    }

    @Test
    void shouldDoFilterWithAccess() throws Exception {
        when(this.request.getHeader("X-TenantID")).thenReturn(DEFAULT_TENANT);

        this.tenantFilter.doFilterInternal(this.request, this.response, this.chain);

        assertNotNull(TenantContext.getCurrentTenant());
        assertEquals(DEFAULT_TENANT, TenantContext.getCurrentTenant());
    }
}
