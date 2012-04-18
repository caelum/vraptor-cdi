package org.vraptor.extensions;

import org.vraptor.impl.Route;

public interface PathNameStrategy {
	String pathFor(Route route);
}
