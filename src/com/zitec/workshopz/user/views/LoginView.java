package com.zitec.workshopz.user.views;


import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.zitec.workshopz.R;
import com.zitec.workshopz.base.BaseView;
import com.zitec.workshopz.user.activities.LoginActivity;
import com.zitec.workshopz.user.entities.User;

public class LoginView extends BaseView {

	protected LoginActivity act;
	EditText username;
	EditText password;
	Button submit;
	Button register;
	SparseArray<String> errors;

	
	public LoginView(LoginActivity act){
		this.act = act;
		this.errors = new SparseArray<String>();
		this.errors.put(R.id.username, this.act.getResources().getString(R.string.empty_username_error));
		this.errors.put(R.id.password, this.act.getResources().getString(R.string.empty_password_error));
	}
	
	@Override
	public void initView() {
		this.act.setContentView(R.layout.login_activity);
		this.username = (EditText)this.act.findViewById(R.id.username);
		this.password = (EditText)this.act.findViewById(R.id.password);
		this.submit = (Button)this.act.findViewById(R.id.submit);
		this.register = (Button)this.act.findViewById(R.id.register);
	}

	@Override
	public void setActions() {
		this.submit.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				LoginView.this.submitLogin();
			}
		});
		this.register.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				LoginView.this.openRegisterForm();
			}
		});
		this.password.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(event == null){
					return false;
				}
				LoginView.this.submitLogin();
				return false;
			}
		});
	}

	protected void openRegisterForm(){

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this.act);
 
			alertDialogBuilder.setTitle("Register");
 
			LayoutInflater inflater = this.act.getLayoutInflater();
			alertDialogBuilder
				.setView(inflater.inflate(R.layout.dialog_register, null))
				.setMessage("Create an account")
				.setCancelable(false)
				.setPositiveButton("Register",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						User usr = new User();
						
						EditText name = (EditText) ((Dialog) dialog).findViewById(R.id.name);
						EditText password = (EditText) ((Dialog) dialog).findViewById(R.id.password);
						EditText position = (EditText) ((Dialog) dialog).findViewById(R.id.position);
						EditText email = (EditText) ((Dialog) dialog).findViewById(R.id.email);
						EditText phone_number = (EditText) ((Dialog) dialog).findViewById(R.id.phone_number);

						HashMap<String, String> params = new HashMap<String, String>();
						params.put("name", 			name.getText().toString());
						params.put("email", 		email.getText().toString());
						params.put("position", 		position.getText().toString());
						params.put("password", 		password.getText().toString());
						params.put("phone_number", 	phone_number.getText().toString());
						
						LoginView.this.act.register(params,dialog);
					}
				  })
				.setNegativeButton("Close",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
	}
	
	
	protected void submitLogin(){
		SparseArray<String> values = new SparseArray<String>();
		values.append(R.id.username, this.username.getText().toString());
		values.append(R.id.password, this.password.getText().toString());
		if(this.act.validateLogin(values)){
			this.act.login(this.username.getText().toString(),this.password.getText().toString());
		} else {
			this.showErrors();
		}
	}
	
	public void showErrors(){
		SparseArray<String> errs = this.act.getErrors();
		int nr = errs.size();
		for(int i = 0; i < nr; i++){
			((EditText)this.act.findViewById(errs.keyAt(i))).setError(errs.valueAt(i));
		}
	}
	
	public String getEmptyError(int id){
		return this.errors.get(id);
	}
}
