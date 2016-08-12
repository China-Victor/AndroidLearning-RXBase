package com.kioli.rx.com.kioli.rx.section.login;

import com.kioli.rx.core.binding.ClassFactoryImpl;
import com.kioli.rx.core.binding.ClassWiring;
import com.kioli.rx.core.data.manager.implementation.UserManagerImpl;
import com.kioli.rx.core.data.model.User;
import com.kioli.rx.section.login.LoginContract;
import com.kioli.rx.section.login.LoginPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AndroidSchedulers.class, Schedulers.class})
public class LoginPresenterImplTest {

	@Mock
	private ClassFactoryImpl factory;
	@Mock
	private UserManagerImpl userManager;
	@Mock
	private LoginContract.LoginView view;

	private LoginPresenterImpl loginPresenter;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(AndroidSchedulers.class);
		PowerMockito.mockStatic(Schedulers.class);

		loginPresenter = new LoginPresenterImpl();
		loginPresenter.attachView(view);

		when(Schedulers.immediate()).thenCallRealMethod();
		loginPresenter.setIoScheduler(Schedulers.immediate());
		loginPresenter.setMainScheduler(Schedulers.immediate());

		ClassWiring.getInstance().setClassFactory(factory);
	}

	@Test
	public void load_LoadRegisteredUsers_ReturnsResults() {
		final List<User> userList = getDummyUserList();
		doReturn(userManager).when(factory).getUserManager();
		doReturn(Single.just(userList)).when(userManager).getAllUsers();

		loginPresenter.clickListUsersButton();

		verify(view).showLoading();
		verify(view).hideLoading();
		verify(view).onListUsersSuccessful(userList);
		verify(view, never()).onFailure(any(Throwable.class));
	}

	private List<User> getDummyUserList() {
		List<User> users = new ArrayList<>();
		final User user1 = new User();
		user1.setEmail("email1");
		user1.setPassword("password1");
		user1.setToken("token1");
		users.add(user1);
		final User user2 = new User();
		user2.setEmail("email2");
		user2.setPassword("password2");
		user2.setToken("token2");
		users.add(user2);
		return users;
	}
}
