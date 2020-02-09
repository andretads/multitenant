package br.com.damsete.multitenant.hibernate;

import br.com.damsete.multitenant.TenantContext;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

    private static final long serialVersionUID = 1L;

    private transient DataSource dataSource;
    private transient Logger logger;

    @Autowired
    public MultiTenantConnectionProviderImpl(DataSource dataSource,
                                             Logger logger) {
        this.dataSource = dataSource;
        this.logger = logger;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        this.logger.info("connection.getConnection >> " + tenantIdentifier);
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantIdentifier);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        this.logger.info("connection.releaseConnection >> " + tenantIdentifier);
        connection.setSchema(TenantContext.DEFAULT_TENANT);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }
}
