package org.vraptor.impl.converter;

import java.util.Set;

import javax.enterprise.inject.spi.Bean;

import org.jboss.weld.environment.se.StartMain;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.Before;
import org.vraptor.impl.core.Localization;

public class WeldTest {

	protected WeldContainer weld;
//	private Weld server;

	@Before
	public void bootstrapSetup() {
		StartMain main = new StartMain(new String[0]);
		this.weld = main.go();
//		this.server = new Weld();
//		this.weld = server.initialize();
		LocaleBasedCalendarConverter x = get(LocaleBasedCalendarConverter.class);
		System.out.println(x);
//		weld.instance().select(MyApplicationBean.class).get();
	}
	
	protected <T> T get(Class<T> t) {
		return weld.instance().select(t).get();
	}

	@After
	public void tearDown() {
//		server.shutdown();
//		server = null;
		weld = null;
	}

}
