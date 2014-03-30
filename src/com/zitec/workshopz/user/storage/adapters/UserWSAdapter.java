package com.zitec.workshopz.user.storage.adapters;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.zitec.workshopz.R;
import com.zitec.workshopz.base.storage.adapters.BaseWSStorageAdapter;
import com.zitec.workshopz.user.entities.User;

public class UserWSAdapter extends BaseWSStorageAdapter{

	String methodName = "users";
	
	public UserWSAdapter(Context ctx) {
		super(ctx);
	}

	public void getEntity(String username, String password){
		String url = this.getBaseUrl() + "/" + this.methodName + "?username="+username+"&password="+password;
		Log.d("Volley", url);
		final String usr = username;
		final String pass = password;
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, this, this){
			@Override
		    public HashMap<String, String> getParams() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("username", usr);
				params.put("password", pass);
				return params;
			}
			
			@Override
			public HashMap<String, String> getHeaders(){
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/json");
				params.put("Accept", "application/json");
				return params;
			}
		};
		this.queue.add(req);
	}

//	@Override
//	public String getBaseUrl(){
//		String url = super.getBaseUrl();
//		return url.replace("http://", "https://");
//		
//	}
	
	@Override
	public void onResponse(JSONObject response) {
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			data.put("email", response.getString("email"));
			data.put("position", response.getString("position"));
			data.put("remote_id", response.getString("id"));
			data.put("name", response.getString("name"));
			data.put("phone_number", response.getString("phone_number"));
			
			this.mapper.onSuccess(data);
		} catch (JSONException e) {
			this.mapper.onError(this.context.getResources().getString(R.string.network_error));
		}
	}

	public void registerEntity(HashMap<String, String> params) {
		String url = this.getBaseUrl() + "/" + this.methodName ;
		Log.d("Volley", url);
		final String r_name = 			params.get("name");
		final String r_email = 			params.get("email");
		final String r_position = 		params.get("position");
		final String r_password = 		params.get("password");
		final String r_phone_number = 	params.get("phone_number");
		
		StringRequest postRequest = new StringRequest(Request.Method.POST, url, 
			    new Response.Listener<String>() 
			    {
			        @Override
			        public void onResponse(String response) {
			        	JSONObject responseObj;
			        	HashMap<String, String> data = null;
						try {
							responseObj = new JSONObject(response);
				    		data = new HashMap<String, String>();
			    			data.put("id", responseObj.getString("id"));
						} catch (JSONException e) {
							UserWSAdapter.this.mapper.onError(UserWSAdapter.this.context.getResources().getString(R.string.user_not_registered));
						}

			            Log.d("Response", response);
			            UserWSAdapter.this.mapper.onSuccess(data);
			        }
			    }, 
			    new Response.ErrorListener() 
			    {
			         @Override
			         public void onErrorResponse(VolleyError error) {
			             // error
			             Log.d("Error.Response", error.getMessage());
			       }

			    }
			) {     
			    @Override
			    protected Map<String, String> getParams() 
			    {  
			            Map<String, String>  params = new HashMap<String, String>();  
			            params.put("name", r_name);  
			            params.put("email", r_email);
			            params.put("position", r_position);
			            params.put("password", r_password);
			            params.put("phone_number", r_phone_number);
			             
			            return params;  
			    }
			};
			queue.add(postRequest);
			
	}
	
	
	
}
