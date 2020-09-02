package br.com.damsete.multitenant;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static br.com.damsete.multitenant.TenantContext.DEFAULT_TENANT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class TenantContextTest {

    private final Faker faker = new Faker();

    @Test
    void shouldSetTenant() {
        var tenant = this.faker.name().name();

        TenantContext.setCurrentTenant(tenant);

        assertNotNull(TenantContext.getCurrentTenant());
        assertEquals(tenant, TenantContext.getCurrentTenant());
    }

    @Test
    void shouldClearTenant() {
        TenantContext.clear();

        assertNotNull(TenantContext.getCurrentTenant());
        assertEquals(DEFAULT_TENANT, TenantContext.getCurrentTenant());
    }
}
