package org.vraptor.extension;

import org.vraptor.impl.Route;

public interface PathNameStrategy {
	String pathFor(Route route);
}
