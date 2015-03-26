package com.neptune.net;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{

	EditText username,password;
	Button login,register;
	public String URL="http://109.123.112.186/muk_student_sys/android/android.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		username=(EditText)findViewById(R.id.USERNAME);
		password=(EditText)findViewById(R.id.PASSWORD);
		login = (Button)findViewById(R.id.SIGNIN_BTN);
		register=(Button)findViewById(R.id.REGISTER_BTN);
		
		
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	              new DatatransmitterAsync().execute();
			}
		});
		
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this,Register.class);
				startActivity(intent);

			}
		});

	}
	
	class DatatransmitterAsync extends AsyncTask<JSONObject, Void, String>{

		public ProgressDialog progressDialog = new ProgressDialog(Login.this);
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog.setMessage("Authenticating...");
		    progressDialog.show();
		    progressDialog.setOnCancelListener(new OnCancelListener() {
		    	
		            public void onCancel(DialogInterface diaInterface) {
		            	
		            	DatatransmitterAsync.this.cancel(true);
		                diaInterface.dismiss();
		            }
		        });
		}
		String output = "";
		@Override
		protected String doInBackground(JSONObject... params) {
			// TODO Auto-generated method stub
			try{
				
				String User=username.getText().toString();
				String Pass=password.getText().toString();
				
				String res =  JSONParser.getJSONStringfromURL(URL+"?type=login&username="+User+"&password="+Pass);
		        output = res;
			
			}catch (Exception e1) {
				
	        	output = "error"+e1.getMessage();
	        	
            }
		
			return output;
		}

		@Override
		protected void onPostExecute(String response) {
			// TODO Auto-generated method stub
			this.progressDialog.dismiss();
			
			try{
				JSONObject json = new JSONObject(response);
				String succ = json.getString("success");
				
				if(succ.equals("1")){
					
					Intent out = new Intent(Login.this,MainActivity.class);
					startActivity(out);
					
				}
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				//Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
			}
			
		}
		
		
	}

}
