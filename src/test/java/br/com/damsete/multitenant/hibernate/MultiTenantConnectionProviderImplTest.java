package br.com.damsete.multitenant.hibernate;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MultiTenantConnectionProviderImplTest {

    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private Statement statement;
    @Mock
    private Logger logger;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.multiTenantConnectionProvider = new MultiTenantConnectionProviderImpl(this.dataSource, this.logger);
    }

    @Test
    public void should_get_any_connection() throws SQLException {
        when(this.dataSource.getConnection()).thenReturn(this.connection);

        Connection connection = this.multiTenantConnectionProvider.getAnyConnection();

        assertNotNull(connection);
    }

    @Test
    public void should_release_any_connection() throws SQLException {
        when(this.connection.isClosed()).thenReturn(true);

        this.multiTenantConnectionProvider.releaseAnyConnection(this.connection);

        assertTrue(this.connection.isClosed());
    }

    @Test
    public void should_get_connection() throws SQLException {
        when(this.dataSource.getConnection()).thenReturn(this.connection);
        when(this.connection.createStatement()).thenReturn(this.statement);

        Connection connection = this.multiTenantConnectionProvider.getConnection("mytenant");

        assertNotNull(connection);
    }

    @Test
    public void should_release_connection() throws SQLException {
        when(this.connection.createStatement()).thenReturn(this.statement);

        this.multiTenantConnectionProvider.releaseConnection("mytenant", this.connection);
    }

    @Test
    public void should_is_unwrappable_as() {
        boolean isUnwrappableAs = this.multiTenantConnectionProvider.isUnwrappableAs(Long.class);

        assertFalse(isUnwrappableAs);
    }

    @Test
    public void should_unwrap() {
        Long unwrap = this.multiTenantConnectionProvider.unwrap(Long.class);

        assertNull(unwrap);
    }

    @Test
    public void should_supports_aggressive_release() {
        boolean support = this.multiTenantConnectionProvider.supportsAggressiveRelease();

        assertTrue(support);
    }
}