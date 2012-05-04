package org.vraptor.impl.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DefaultStaticContentHandlerTest {

    @Mock private HttpServletRequest request;
    @Mock private ServletContext context;

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnsTrueForRealStaticResources() throws Exception {
    	File file = File.createTempFile("_test", ".xml");
    	String key = file.getAbsolutePath();
    	when(request.getRequestURI()).thenReturn("/contextName/" +key);
        when(request.getContextPath()).thenReturn("/contextName/");
        when(context.getResource(key)).thenReturn(file.toURI().toURL());

        boolean result = new DefaultStaticContentHandler(context).requestingStaticFile(request);

        assertThat(result, is(equalTo(true)));
    }

    @Test
    public void returnsFalseForNonStaticResources() throws Exception {
        File file = new File("_test_unknown.xml");
        String key = file.getAbsolutePath();
        when(request.getRequestURI()).thenReturn("/contextName/" +key);
        when(request.getContextPath()).thenReturn("/contextName/");
        when(context.getResource(key)).thenReturn(null);

        boolean result = new DefaultStaticContentHandler(context).requestingStaticFile(request);

        assertThat(result, is(equalTo(false)));
    }

}
