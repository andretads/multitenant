package br.com.damsete.multitenant;

import org.junit.Test;

import static org.junit.Assert.*;

public class TenantContextTest {

    @Test
    public void should_set_tenant() {
        TenantContext.setCurrentTenant("tenant");

        assertNotNull(TenantContext.getCurrentTenant());
        assertEquals(TenantContext.getCurrentTenant(), "tenant");
    }

    @Test
    public void should_clear_tenant() {
        TenantContext.clear();

        assertNotNull(TenantContext.getCurrentTenant());
        assertEquals(TenantContext.getCurrentTenant(), "public");
    }
}