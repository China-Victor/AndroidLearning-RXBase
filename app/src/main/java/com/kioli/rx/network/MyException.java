package com.kioli.rx.network;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;

/**
 * The MyException class wraps an exception, providing categorization of the exception into one
 * of the {@link Kind exception types} for easier use by the exception handling
 * system.
 * Exceptions are handled in different ways by the exception handling system, based on their
 * type. How a specific type is handled depends on the IExceptionHandlerFactory implementation.
 */
public final class MyException extends Exception {

	/**
	 * Exceptions are categorised into these types for easy handling. Exceptions are handled in
	 * different ways by the exception handling system, based on their type. How a specific type is
	 * handled depends on the IExceptionHandlerFactory implementation.
	 */
	public enum Kind {

		/**
		 * There is no network connectivity
		 */
		NO_NETWORK,

		/**
		 * A server-side error occurred, i.e. the server replied with an unexpected response
		 */
		SERVER,

		/**
		 * A client-side error occurred, i.e. a malformed request, loading/storing a file failed, etc
		 */
		CLIENT,

		/**
		 * This is the catch-all for exceptions not falling under any other type
		 */
		GENERAL_ERROR,
	}

	private final Kind _errorKind;

	/**
	 * Converts any throwable into a managed exception by wrapping it in a new MyException
	 * instance and setting the appropriate type. If the supplied Throwable is already a
	 * MyException it is returned as-is.
	 *
	 * @param t The throwable to convert to a typed MyException
	 *
	 * @return A new MyException wrapping the throwable, or the throwable if it is already
	 * wrapped
	 */
	public static MyException wrap(@NonNull final Throwable t) {
		if (t instanceof MyException) {
			return (MyException) t;
		}

		if (t instanceof NetworkErrorException) {
			return new MyException(Kind.NO_NETWORK, t);
		}

		return new MyException(Kind.GENERAL_ERROR, t);
	}

	/**
	 * Factory method for a new MyException instance. Creates a new MangedException with the
	 * given kind, but without a wrapped exception. Usable if you want to trigger a specific
	 * exception behaviour without having a thrown exception.
	 *
	 * @param kind The desired kind of the MyException instance
	 *
	 * @return A new MyException instance of the given kind
	 */
	public static MyException ofKind(@NonNull final Kind kind) {
		return new MyException(kind);
	}

	/**
	 * Factory method for a new MyException instance. Creates a new MangedException with the
	 * given kind, but without a wrapped exception. Usable if you want to trigger a specific
	 * exception behaviour without having a thrown exception.
	 *
	 * @param kind          The desired kind of the MyException instance
	 * @param detailMessage the detail message for this exception.
	 *
	 * @return A new MyException instance of the given kind
	 */
	public static MyException ofKind(@NonNull final Kind kind,
									 @NonNull final String detailMessage) {
		return new MyException(kind, detailMessage);
	}

	/**
	 * Factory method for a new MyException instance. Creates a new MangedException with the
	 * given kind, but without a wrapped exception. Usable if you want to trigger a specific
	 * exception behaviour without having a thrown exception.
	 *
	 * @param kind      The desired kind of the MyException instance
	 * @param throwable the underlying throwable
	 *
	 * @return A new MyException instance of the given kind
	 */
	public static MyException ofKind(@NonNull final Kind kind,
									 @NonNull final Throwable throwable) {
		return new MyException(kind, throwable);
	}

	/**
	 * Constructs a new MyException of the given kind, but without throwable
	 *
	 * @param kind The desired kind of the MyException instance
	 */
	private MyException(Kind kind) {
		super();
		_errorKind = kind;
	}

	/**
	 * Constructs a new MyException of the given kind, but without throwable
	 *
	 * @param kind          The desired kind of the MyException instance
	 * @param detailMessage the detail message for this exception.
	 */
	private MyException(@NonNull final Kind kind,
						@NonNull final String detailMessage) {
		super(detailMessage);
		_errorKind = kind;
	}

	/**
	 * Constructs a new MyException of the given kind, wrapping the given throwable
	 *
	 * @param kind      The desired kind of the MyException instance
	 * @param throwable The throwable to wrap
	 */
	private MyException(@NonNull final Kind kind,
						@NonNull final Throwable throwable) {
		super(throwable);
		_errorKind = kind;
	}

	/**
	 * @return the MyException type of the wrapped exception
	 */
	public Kind getErrorType() {
		return _errorKind;
	}
}
