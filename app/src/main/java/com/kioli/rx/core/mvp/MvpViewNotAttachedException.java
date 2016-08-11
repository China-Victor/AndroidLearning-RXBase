package com.kioli.rx.core.mvp;

public class MvpViewNotAttachedException extends RuntimeException {

	public MvpViewNotAttachedException() {
		super("Call Presenter.attachView(MVPView) before requesting data to the Presenter");
	}
}