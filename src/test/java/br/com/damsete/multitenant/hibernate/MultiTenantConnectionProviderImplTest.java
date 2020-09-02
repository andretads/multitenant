package br.com.damsete.multitenant.hibernate;

import br.com.damsete.multitenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static br.com.damsete.multitenant.TenantContext.DEFAULT_TENANT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

class MultiTenantConnectionProviderImplTest {

    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private Statement statement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.multiTenantConnectionProvider = new MultiTenantConnectionProviderImpl(this.dataSource);
    }

    @Test
    void shouldGetAnyConnection() throws SQLException {
        when(this.dataSource.getConnection()).thenReturn(this.connection);

        var connection = this.multiTenantConnectionProvider.getAnyConnection();

        assertNotNull(connection);
    }

    @Test
    void shouldReleaseAnyConnection() throws SQLException {
        when(this.connection.isClosed()).thenReturn(true);

        this.multiTenantConnectionProvider.releaseAnyConnection(this.connection);

        assertTrue(this.connection.isClosed());
    }

    @Test
    void shouldGetConnection() throws SQLException {
        when(this.dataSource.getConnection()).thenReturn(this.connection);
        when(this.connection.createStatement()).thenReturn(this.statement);

        var connection = this.multiTenantConnectionProvider.getConnection("mytenant");

        assertNotNull(connection);
    }

    @Test
    void shouldReleaseConnection() throws SQLException {
        when(this.connection.createStatement()).thenReturn(this.statement);

        this.multiTenantConnectionProvider.releaseConnection("mytenant", this.connection);

        assertEquals(DEFAULT_TENANT, TenantContext.getCurrentTenant());
    }

    @Test
    void shouldIsUnwrappableAs() {
        var isUnwrappableAs = this.multiTenantConnectionProvider.isUnwrappableAs(Long.class);

        assertFalse(isUnwrappableAs);
    }

    @Test
    void shouldUnwrap() {
        var unwrap = this.multiTenantConnectionProvider.unwrap(Long.class);

        assertNull(unwrap);
    }

    @Test
    void shouldSupportsAggressiveRelease() {
        var support = this.multiTenantConnectionProvider.supportsAggressiveRelease();

        assertTrue(support);
    }
}
