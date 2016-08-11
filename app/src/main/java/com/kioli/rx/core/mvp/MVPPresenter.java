package com.kioli.rx.core.mvp;

/**
 * Common interface shared by presenters where asynchronous calls with RX are done
 */
public interface MVPPresenter<V extends MVPView> {

	void attachView(V baseView);

	void detachView();

}
