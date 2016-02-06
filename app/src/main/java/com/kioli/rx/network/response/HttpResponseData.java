package com.kioli.rx.network.response;

import android.util.Log;

import com.kioli.rx.network.util.DateFormatterUtil;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;


/**
 * Hold http request data
 */
public class HttpResponseData {
	private final int _statusCode;
	private final String _lastModified;
	private final InputStream _stream;
	private Date _valueLastModified;

	public HttpResponseData(final int statusCode, final String lastModified, final InputStream stream) {
		super();
		this._statusCode = statusCode;
		this._lastModified = lastModified;
		this._stream = stream;
	}

	public int getStatusCode() {
		return _statusCode;
	}

	public Date getLastModified() {
		if (_valueLastModified == null) {
			_valueLastModified = parseDateHeader(_lastModified);
		}
		return _valueLastModified;
	}

	public InputStream getStream() {
		return _stream;
	}

	private Date parseDateHeader(final String strDateRfc1123) {
		if (strDateRfc1123 == null) {
			return null;
		}

		Date result = null;
		try {
			result = DateFormatterUtil.parseFileLastUpdate(strDateRfc1123);
		} catch (final ParseException ex) {
			Log.w(this.toString(), "Unable to parse the server date [" + strDateRfc1123 + "]: " + ex.toString());
		}

		return result;
	}

	@Override
	public String toString() {
		return "ResponseData [_statusCode=" + _statusCode + ", _lastModified=" + _lastModified + "]";
	}
}
