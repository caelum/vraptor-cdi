package org.vraptor.validator;

import java.util.List;

public interface Validator {

	void addAll(List<Message> errors);

}
