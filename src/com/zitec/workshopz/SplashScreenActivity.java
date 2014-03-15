package com.zitec.workshopz;
import android.os.Bundle;
import android.os.UserManager;

import com.zitec.workshopz.R;
import com.zitec.workshopz.base.BaseActivity;
import com.zitec.workshopz.base.BaseView;
import com.zitec.workshopz.user.storage.mappers.UserMapper;


public class SplashScreenActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle aa){
		super.onCreate(aa);
		BaseView view = new SplashScreenView(this);
		view.initView();
		this.checkIdentity();
	}
	
	public void checkIdentity(){
		if(BaseActivity.identity != null){
			this.loadWorkshops();
			return;
		}
		UserMapper mapper = new UserMapper();
		UserDbAdapter adapter= new UserDbAdapter(this);
		mapper.setAdapter(adapter);
		
	}
	
	public void loadWorkshops(){
		
	}
}
