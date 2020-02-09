package br.com.damsete.multitenant.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class TenantConfigTest {

    @Mock
    private CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl;
    @Mock
    private MultiTenantConnectionProvider multiTenantConnectionProviderImpl;
    @Mock
    private JpaProperties jpaProperties;
    @Mock
    private DataSource dataSource;

    private TenantConfig tenantConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.tenantConfig = new TenantConfig(this.jpaProperties);
    }

    @Test
    public void should_create_entity_manager() {
        LocalContainerEntityManagerFactoryBean em = this.tenantConfig.entityManagerFactory(this.dataSource, this.multiTenantConnectionProviderImpl, this.currentTenantIdentifierResolverImpl);

        assertNotNull(em);
        assertEquals(em.getDataSource(), this.dataSource);
    }

    @Test
    public void should_create_jpa_vendor_adapter() {
        JpaVendorAdapter jpaVendorAdapter = this.tenantConfig.jpaVendorAdapter();

        assertNotNull(jpaVendorAdapter);
        assertSame(jpaVendorAdapter.getClass(), HibernateJpaVendorAdapter.class);
    }
}