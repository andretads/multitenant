package br.com.damsete.multitenant.hibernate;

import br.com.damsete.multitenant.TenantContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentTenantIdentifierResolverImplTest {

    private CurrentTenantIdentifierResolverImpl currentTenantIdentifierResolver;

    @Before
    public void setup() {
        this.currentTenantIdentifierResolver = new CurrentTenantIdentifierResolverImpl();
    }

    @Test
    public void should_resolve_current_tenant_identifier() {
        TenantContext.setCurrentTenant("tenant");

        String tenant = this.currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();

        assertNotNull(tenant);
        assertEquals(tenant, "tenant");
    }

    @Test
    public void should_resolve_current_tenant_identifier_when_clear_tenant() {
        TenantContext.clear();

        String tenant = this.currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();

        assertNotNull(tenant);
        assertEquals(tenant, "public");
    }

    @Test
    public void should_validate_existing_current_sessions() {
        boolean valid = this.currentTenantIdentifierResolver.validateExistingCurrentSessions();

        assertTrue(valid);
    }
}