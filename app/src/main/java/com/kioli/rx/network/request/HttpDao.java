package com.kioli.rx.network.request;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kioli.rx.Constants;
import com.kioli.rx.binding.ClassWiring;
import com.kioli.rx.network.MyException;
import com.kioli.rx.network.parsing.BitmapParser;
import com.kioli.rx.network.parsing.Parser;
import com.kioli.rx.network.response.HttpResponseData;
import com.kioli.rx.network.response.HttpResult;
import com.kioli.rx.network.util.ConnectionUtil;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Base class for Dao implementation that need http manager
 */
public abstract class HttpDao {

	// Long responses will be printed in chunks of this size to avoid truncation
	private final static int RESPONSE_SIZE = 4000;

	protected <T> HttpResult<T> doRequest(
			@NonNull Context context,
			@NonNull HttpRequestData request,
			@NonNull Parser<T> parser) throws MyException {
		final boolean connected = ConnectionUtil.hasInternetConnection(context);
		if (!connected) {
			throw MyException.ofKind(MyException.Kind.NO_NETWORK);
		}

		final HttpRequestExecutor requestExecuter = ClassWiring.getConnectionManager().getExecutor();
		final HttpResult<T> cafHttpResult = new HttpResult<>();
		InputStream stream = null;

		try {
			logRequest(request);
			final HttpResponseData httpResponseData = requestExecuter.executeHttpRequest(request);

			if (httpResponseData == null) {
				throw MyException.ofKind(MyException.Kind.NO_NETWORK);
			} else {
				cafHttpResult.resultCode = httpResponseData.getStatusCode();
				final Date lastModified = httpResponseData.getLastModified();
				if (lastModified != null) {
					cafHttpResult.lastModified = lastModified.getTime();
				}

				stream = httpResponseData.getStream();

				if (parser instanceof BitmapParser) {
					logResponseBitmap(cafHttpResult.resultCode);
				} else {
					stream = logResponseJSON(cafHttpResult.resultCode, stream);
				}

				// parse result, with provided parser
				cafHttpResult.result = parser.parse(stream);
			}
		} catch (IOException e) {
			throw MyException.wrap(e);
		} finally {
			IOUtils.closeQuietly(stream);
		}

		return cafHttpResult;
	}

	private void logRequest(@NonNull HttpRequestData request) {
		Log.i("HTTP",
				"Thread:\n\t\t" + Thread.currentThread().getName() +
						"\nRequest url:\n\t\t" + request.getUrl() +
						"\nService arguments:\t" + request.getServiceArguments().toString());
	}

	private InputStream logResponseJSON(final int response,
										@NonNull final InputStream inputStream)
			throws IOException {
		InputStream out = null;
		InputStream copiedStream = null;

		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();

			final byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();

			// Open new InputStreams using the recorded bytes
			copiedStream = new ByteArrayInputStream(baos.toByteArray());
			out = new ByteArrayInputStream(baos.toByteArray());

			final String body = IOUtils.toString(copiedStream, Constants.UTF8_FORMAT);
			logChunkedJSON(response, body);

		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(copiedStream);
		}

		return out;
	}

	private void logChunkedJSON(final int responseCode,
								@NonNull final String body) {
		int from = 0;
		final int max = body.length();
		while (from < max) {
			int to = from + RESPONSE_SIZE;
			if (to > max) {
				to = max;
			}
			final String chunk = body.substring(from, to);
			if (0 == from) {
				Log.i("HTTP",
						"Thread:\n\t\t" + Thread.currentThread().getName() +
								"\nResponse code:\n\t\t" + responseCode +
								"\nResponse:\n\t\t" + chunk);
			} else {
				Log.i("HTTP", chunk);
			}

			from = to;
		}
	}

	private void logResponseBitmap(final int responseCode) {
		Log.i("HTTP",
				"Thread: \t" + Thread.currentThread().getName() +
						"\nResponse code: \t" + responseCode);
	}
}
