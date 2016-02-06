package com.kioli.rx.network.request;

import android.content.ContentValues;

import com.kioli.rx.Constants;
import com.kioli.rx.network.response.HttpResponseData;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class HttpRequestExecutorImpl implements HttpRequestExecutor {

	private static final int TIMEOUT_CONNECTION_MS = 5000;
	private static final int TIMEOUT_SOCKET_MS = 30000;

	private static final String LAST_MODIFIED_HEADER_FIELD = "Last-Modified";

	/**
	 * Execute an http request by creating a new URL connection and returning its response data
	 */
	@Override
	public HttpResponseData executeHttpRequest(final HttpRequestData httpRequestData) throws IOException {
		final URL url = new URL(buildUrl(httpRequestData));

		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(TIMEOUT_SOCKET_MS);
		conn.setConnectTimeout(TIMEOUT_CONNECTION_MS);
		conn.setRequestMethod(httpRequestData.getServiceArguments().getHttpMethod().getMethod());
		conn.setDoInput(true);

		setHeader(conn, httpRequestData);

		final ServiceArguments arg = httpRequestData.getServiceArguments();

		if ((arg.getHttpMethod() == HttpMethod.POST ||
				arg.getHttpMethod() == HttpMethod.PUT) &&
				arg.getBody() != null) {
			conn.setDoOutput(true);
			byte[] outputInBytes = arg.getBody().getBytes(Constants.UTF8_FORMAT);
			OutputStream os = conn.getOutputStream();
			os.write(outputInBytes);
			os.close();
		}

		conn.connect();

		final int responseCode = conn.getResponseCode();
		final String lastModified = conn.getHeaderField(LAST_MODIFIED_HEADER_FIELD);

		return new HttpResponseData(responseCode, lastModified, conn.getInputStream());
	}

	private void setHeader(HttpURLConnection conn, HttpRequestData requestData) {
		final ContentValues headers = requestData.getServiceArguments().getHttpHeaders();
		for (Map.Entry<String, Object> entry : headers.valueSet()) {
			conn.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
		}
	}

	/**
	 * Build URL for web services
	 *
	 * @param requestData data of the http request
	 *
	 * @return uri as a String
	 *
	 * @throws IOException
	 */
	public static String buildUrl(final HttpRequestData requestData) throws IOException {

		final StringBuilder uriBuilder = new StringBuilder();
		uriBuilder.append(requestData.getUrl());

		// add query params
		final ContentValues queryParams = requestData.getServiceArguments().getQueryParams();
		if (queryParams != null) {
			final Set<Map.Entry<String, Object>> set = queryParams.valueSet();
			final int queryParamsSize = queryParams.size();
			int i = 0;

			for (Map.Entry<String, Object> nvp : set) {
				final String key = nvp.getKey();
				if (key != null) {
					if (i == 0) {
						uriBuilder.append("?");
					}

					uriBuilder.append(URLEncoder.encode(key, Constants.UTF8_FORMAT));
					uriBuilder.append("=");
					uriBuilder.append(URLEncoder.encode((String) nvp.getValue(), Constants.UTF8_FORMAT));

					if (i != queryParamsSize - 1) {
						uriBuilder.append("&");
					}
				}
				i++;
			}
		}

		return uriBuilder.toString();
	}

}
