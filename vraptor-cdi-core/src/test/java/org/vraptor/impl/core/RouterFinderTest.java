package org.vraptor.impl.core;

import static org.mockito.Mockito.*;

import java.io.File;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vraptor.impl.Executor;
import org.vraptor.impl.Route;
import org.vraptor.impl.Router;

public class RouterFinderTest {

	@Mock private Executor executor;
	@Mock private Router router;
	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock private FilterChain filterChain;
	@Mock private ServletContext context;

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ifRealStaticResourcesCallFilterChainMethodDoFilter() throws Exception {
    	File file = File.createTempFile("_test", ".xml");
    	String key = file.getAbsolutePath();
    	when(request.getRequestURI()).thenReturn("/contextName/" +key);
        when(request.getContextPath()).thenReturn("/contextName/");
        when(context.getResource(key)).thenReturn(file.toURI().toURL());

        new RouterFinder(router, executor).execute(request, response, filterChain, context);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void ifNonStaticResourcesCallExecutorMethodExecute() throws Exception {
        File file = new File("_test_unknown.xml");
        String key = file.getAbsolutePath();
        Route route = mock(Route.class);

		when(request.getRequestURI()).thenReturn("/contextName/" +key);
        when(request.getContextPath()).thenReturn("/contextName/");
        when(context.getResource(key)).thenReturn(null);
        when(context.getContextPath()).thenReturn("/contextName/");
		when(router.routeFor(key)).thenReturn(route);

        new RouterFinder(router, executor).execute(request, response, filterChain, context);

        verify(executor).execute(request, response, context, route);
    }

}
