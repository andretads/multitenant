package br.com.damsete.multitenant.hibernate;

import br.com.damsete.multitenant.TenantContext;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.damsete.multitenant.TenantContext.DEFAULT_TENANT;
import static org.junit.Assert.*;

class CurrentTenantIdentifierResolverImplTest {

    private final Faker faker = new Faker();

    private CurrentTenantIdentifierResolverImpl currentTenantIdentifierResolver;

    @BeforeEach
    void setUp() {
        this.currentTenantIdentifierResolver = new CurrentTenantIdentifierResolverImpl();
    }

    @Test
    void shouldResolveCurrentTenantIdentifier() {
        var tenant = this.faker.name().name();

        TenantContext.setCurrentTenant(tenant);

        var currentTenant = this.currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();

        assertNotNull(currentTenant);
        assertEquals(tenant, currentTenant);
    }

    @Test
    void shouldResolveCurrentTenantIdentifierWhenClearTenant() {
        TenantContext.clear();

        var currentTenant = this.currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();

        assertNotNull(currentTenant);
        assertEquals(DEFAULT_TENANT, currentTenant);
    }

    @Test
    void shouldValidateExistingCurrentSessions() {
        var valid = this.currentTenantIdentifierResolver.validateExistingCurrentSessions();

        assertTrue(valid);
    }
}
