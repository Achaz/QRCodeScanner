package com.neptune.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity{

	EditText username,password,fname,lname,phonenumber,email;
	Button registerbtn;
	public String URL="http://109.123.112.186/muk_student_sys/android/android.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userregister);
		
		username=(EditText)findViewById(R.id.forum_username);
		password =(EditText)findViewById(R.id.forum_password);
		fname = (EditText)findViewById(R.id.fname);
		lname= (EditText)findViewById(R.id.lname);
		phonenumber =(EditText)findViewById(R.id.forum_phoneNumber);
		email =(EditText)findViewById(R.id.forum_email);
		registerbtn=(Button)findViewById(R.id.forum_register);
		
		registerbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                new DataTransimitterAsyncTask().execute();
			}
		});

	}
	
	class DataTransimitterAsyncTask extends AsyncTask<JSONObject, Void, String>{

		public ProgressDialog progressDialog = new ProgressDialog(Register.this);
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog.setMessage("Registering...");
		    progressDialog.show();
		    progressDialog.setOnCancelListener(new OnCancelListener() {
		    	
		            public void onCancel(DialogInterface diaInterface) {
		            	
		            	DataTransimitterAsyncTask.this.cancel(true);
		                diaInterface.dismiss();
		            }
		        });
		}
		
		String output = "";
		@Override
		protected String doInBackground(JSONObject... params) {
			// TODO Auto-generated method stub
			try{
				
				String fm_username=username.getText().toString();
				String fm_password=password.getText().toString();
				String fn_fname=fname.getText().toString();
				String fn_lname=lname.getText().toString();
				String fm_phone=phonenumber.getText().toString();
				String fm_email=email.getText().toString();
				
				String res =  JSONParser.getJSONStringfromURL(URL+"?type=data&lname="+fn_lname+"&fname="+fn_fname+"&password="+fm_password+"&username="+fm_username+"&email="+fm_email+"&phonenumber="+fm_phone);
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
					
					Intent intent = new Intent(Register.this,MainActivity.class);
					startActivity(intent);
					
				}
				
				
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				//Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
			}

		}

	}

}
