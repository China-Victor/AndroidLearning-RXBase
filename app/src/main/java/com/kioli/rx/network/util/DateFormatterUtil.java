package com.kioli.rx.network.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static java.util.Locale.US;

/**
 * Utility class to facilitate operations around dates
 */
public final class DateFormatterUtil {

	private static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");

	private static SimpleDateFormat _formatLastUpdatedFile;

	private DateFormatterUtil() {
	}

	private static void initFormatters() {
		_formatLastUpdatedFile = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", US);
		_formatLastUpdatedFile.setTimeZone(GMT_ZONE);
	}

	/**
	 * This format is used for files last modified date
	 */
	public static String formatFileLastUpdate(final long timestamp) {
		initFormatters();
		return _formatLastUpdatedFile.format(new Date(timestamp));
	}

	/**
	 * This parsing is used for files last modified date
	 */
	public static Date parseFileLastUpdate(final String date) throws ParseException {
		initFormatters();
		return _formatLastUpdatedFile.parse(date);
	}
}
