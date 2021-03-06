package br.com.damsete.multitenant.filter;

import br.com.damsete.multitenant.TenantContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "X-TenantID";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        try {
            var tenant = request.getHeader(TENANT_HEADER);
            if (StringUtils.isEmpty(tenant)) {
                tenant = TenantContext.DEFAULT_TENANT;
            }

            TenantContext.setCurrentTenant(tenant);

            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
