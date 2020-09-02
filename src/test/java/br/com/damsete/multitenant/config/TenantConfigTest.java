package br.com.damsete.multitenant.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class TenantConfigTest {

    @Mock
    private CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl;
    @Mock
    private MultiTenantConnectionProvider multiTenantConnectionProviderImpl;
    @Mock
    private JpaProperties jpaProperties;
    @Mock
    private DataSource dataSource;

    private TenantConfig tenantConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.tenantConfig = new TenantConfig(this.jpaProperties);
    }

    @Test
    void shouldCreateEntityManager() {
        var em = this.tenantConfig.entityManagerFactory(this.dataSource, this.multiTenantConnectionProviderImpl,
                this.currentTenantIdentifierResolverImpl);

        assertNotNull(em);
        assertEquals(em.getDataSource(), this.dataSource);
    }

    @Test
    void shouldCreateJpaVendorAdapter() {
        var jpaVendorAdapter = this.tenantConfig.jpaVendorAdapter();

        assertNotNull(jpaVendorAdapter);
        assertSame(jpaVendorAdapter.getClass(), HibernateJpaVendorAdapter.class);
    }
}
