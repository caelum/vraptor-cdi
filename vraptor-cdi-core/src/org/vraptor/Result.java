package org.vraptor;

public interface Result {
	Result include(String key, Object value);
	void sendError(int statusCode);
	void forwardTo(String path);
	void text(String text);
}
