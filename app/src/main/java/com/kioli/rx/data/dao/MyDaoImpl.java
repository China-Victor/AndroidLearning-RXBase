package com.kioli.rx.data.dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.kioli.rx.data.model.AcronymResult;
import com.kioli.rx.data.model.MemeResult;
import com.kioli.rx.network.MyException;
import com.kioli.rx.network.parsing.BitmapParser;
import com.kioli.rx.network.parsing.JsonParser;
import com.kioli.rx.network.request.HttpDao;
import com.kioli.rx.network.request.HttpRequestData;
import com.kioli.rx.network.request.ServiceArguments;
import com.kioli.rx.network.response.HttpResult;
import com.kioli.rx.network.serializer.BitmapSerializer;

/**
 * Production implementation of {@link MyDao} to retrieve my data
 */
public class MyDaoImpl extends HttpDao implements MyDao {

	private final static String URL_MEME_MAIN = "https://api.imgflip.com/get_memes";
	private final static String URL_ACRONYM_MAIN = "http://www.nactem.ac.uk/software/acromine/dictionary.py";

	private Context _context;

	public MyDaoImpl(@NonNull final Context context) {
		_context = context;
	}

	@Override
	public AcronymResult getAcronymExplanation(@NonNull final String acronym) throws MyException {
		final ServiceArguments serviceArguments = ServiceArguments.createHttpGet();
		serviceArguments.addQueryParameter("sf", acronym);
		final HttpRequestData requestData = new HttpRequestData(URL_ACRONYM_MAIN, serviceArguments);
		final HttpResult<AcronymResult[]> result = doRequest(_context,
				requestData,
				new JsonParser<>(AcronymResult[].class));
		return result.result[0];
	}

	@Override
	public MemeResult getMemes() throws MyException {
		final ServiceArguments serviceArguments = ServiceArguments.createHttpGet();
		final HttpRequestData requestData = new HttpRequestData(URL_MEME_MAIN, serviceArguments);
		final HttpResult<MemeResult> result = doRequest(_context,
				requestData,
				new JsonParser<>(MemeResult.class));
		return result.result;
	}

	@Override
	public Bitmap getMemeImage(@NonNull final String url,
							   final int width,
							   final int height,
							   @NonNull final BitmapSerializer.ScaleType scaleType)
			throws MyException {
		final ServiceArguments serviceArguments = ServiceArguments.createHttpGet();
		final HttpRequestData requestData = new HttpRequestData(url, serviceArguments);
		requestData.setContentType("application/octet-stream; charset=UTF-8");
		final HttpResult<Bitmap> result = doRequest(_context,
				requestData,
				new BitmapParser(scaleType, width, height));
		return result.result;
	}
}
