package br.com.damsete.multitenant.filter;

import br.com.damsete.multitenant.TenantContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class TenantFilterTest {

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private FilterChain chain;

    private TenantFilter tenantFilter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.tenantFilter = new TenantFilter();
    }

    @Before
    public void setdown() {
        this.tenantFilter = null;
    }

    @Test
    public void should_do_filter_with_access() throws Exception {
        when(this.request.getHeader("X-TenantID")).thenReturn("public");

        this.tenantFilter.doFilterInternal(this.request, this.response, this.chain);

        assertNotNull(TenantContext.getCurrentTenant());
        assertEquals(TenantContext.getCurrentTenant(), "public");
    }
}