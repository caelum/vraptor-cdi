package org.vraptor.result;

import java.util.Map;

/**
 * A resource requisition result.
 *
 * @author Guilherme Silveira
 */
public interface Result {

    /**
     * Stores an attribute in the result.
     * 
     * @param key a String specifying the key of the attribute
     * @param value the object to be stored
     * @return this own class
     */
    Result include(String key, Object value);
    
    /**
     * Stores an attribute in the result. The key for the object is defined by 
     * extracting the value class.
     * 
     * @param value the object to be stored
     * @return this own class
     * @see TypeNameExtractor
     */
    Result include(Object value);
	
    /**
     * Add an {@link Exception} to be handled by Exception Handler.
     * 
     * @param exception The exception to handle.
     * @throws A {@link NullPointerException} if exception is null.
     */
	Result on(Class<? extends Exception> exception);

	/**
	 * Whether this result was used.
	 */
    boolean isCommited();

    /**
     * Return all included attributes via Result.include();
     * @return
     */
    Map<String, Object> included();

    /**
     * A shortcut to result.use(page()).forwardTo(uri);
     * @see PageResult#forwardTo(String)
     */
	void forwardTo(String uri);

	/**
     * A shortcut to result.use(page()).redirectTo(uri);
     * @see PageResult#forwardTo(String)
     */
	void redirectTo(String uri);

	/**
	 * A shortcut to result.use(logic()).forwardTo(controller)
	 * @see LogicResult#forwardTo(Class)
	 * @param controller
	 */
	<T> T forwardTo(Class<T> controller);

	/**
	 * A shortcut to result.use(logic()).redirectTo(controller)
	 * @see LogicResult#redirectTo(Class)
	 * @param controller
	 */
	<T> T redirectTo(Class<T> controller);

	/**
	 * A shortcut to result.use(page()).of(controller)
	 * @see PageResult#of(Class)
	 * @param controller
	 */
	<T> T of(Class<T> controller);

	/**
	 * A shortcut to result.use(logic()).redirectTo(controller.getClass())
	 * so you can use on your controller:<br>
	 *
	 * result.redirectTo(this).aMethod();
	 *
	 * @param controller
	 * @see LogicResult#redirectTo(Class)
	 */
	<T> T redirectTo(T controller);

	/**
	 * A shortcut to result.use(logic()).forwardTo(controller.getClass())
	 * so you can use on your controller:<br>
	 *
	 * result.forwardTo(this).aMethod();
	 *
	 * @param controller
	 * @see LogicResult#forwardTo(Class)
	 */
	<T> T forwardTo(T controller);

	/**
	 * A shortcut to result.use(page()).of(controller.getClass())
	 * so you can use on your controller:<br>
	 *
	 * result.of(this).aMethod();
	 *
	 * @param controller
	 * @see PageResult#of(Class)
	 */
	<T> T of(T controller);

	/**
	 * A shortcut to result.use(nothing())
	 */
	void nothing();

	/**
	 * A shortcut to result.use(status()).notFound();
	 */
	void notFound();

	/**
	 * A shortcut to result.use(status()).movedPermanentlyTo(uri).
	 *
	 * @param uri
	 * @see StatusResult#movedPermanentlyTo(String)
	 */
	void permanentlyRedirectTo(String uri);

	/**
	 * A shortcut to result.use(status()).movedPermanentlyTo(controller).
	 *
	 * @param controller
	 * @see StatusResult#movedPermanentlyTo(Class)
	 */
	<T> T permanentlyRedirectTo(Class<T> controller);

	/**
	 * A shortcut to result.use(status()).movedPermanentlyTo(controller.getClass()).
	 * so you can use on your controller:<br>
	 *
	 * result.permanentlyRedirectTo(this).aMethod();
	 *
	 * @param controller
	 * @see StatusResult#movedPermanentlyTo(Class)
	 */
	<T> T permanentlyRedirectTo(T controller);
}

