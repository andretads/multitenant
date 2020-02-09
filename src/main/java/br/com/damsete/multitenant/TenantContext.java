package br.com.damsete.multitenant;

public class TenantContext {

    final public static String DEFAULT_TENANT = "public";

    private TenantContext() {
        super();
    }

    private static ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
