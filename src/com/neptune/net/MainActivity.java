package com.neptune.net;

import com.neptune.net.util.ConnectionDetector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button scan,search;
	TextView tvStatus;
	TextView tvResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvStatus = (TextView) findViewById(R.id.tvStatus);
		tvResult = (TextView) findViewById(R.id.tvResult);
		search=(Button)findViewById(R.id.btnSearch);
		
		search.setOnClickListener(this);
	
		try{
			scan= (Button)findViewById(R.id.btnScan);
	        
	        scan.setOnClickListener(new View.OnClickListener() {
	             
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub 

	            	Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
	            	intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE"); 
	            	startActivityForResult(intent, 0);
	                
	            }
	        });
	        
			}catch (ActivityNotFoundException anfe) {
	            Log.e("onCreate", "Scanner Not Found", anfe);
	            
	            anfe.printStackTrace();
	            Toast.makeText(getApplicationContext(), "ERROR:" + anfe,Toast.LENGTH_LONG).show();
	        }
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		
            if(requestCode == 0){
			
			if(resultCode == RESULT_OK){
				
				 String contents = intent.getStringExtra("SCAN_RESULT");
				 String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				 
				 tvStatus.setText(format);
				 tvResult.setText(contents);
				
			}else if(resultCode == RESULT_CANCELED){
				
				Log.i("xZing", "Cancelled");
			}
			
			
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		
		if(!cd.isConnectingToInternet()){
			
			showAlertDialog(MainActivity.this,"Internet Connection Error",
		            "Please connect to working Internet connection",true);

		}else{
			
			EditText stdnumber_tv = (EditText)findViewById(R.id.edit_student_no);
			Intent in = new Intent(this,Details.class);
			in.putExtra("stdNo",stdnumber_tv.getText().toString());
			startActivity(in);
	
		}

	}
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status){
		
		final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		
		// Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
        
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            	
            	alertDialog.dismiss();
            }
        });
        
        alertDialog.setButton("Settings", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
				startActivity(intent);
			}
		});
     // Showing Alert Message
        alertDialog.show();
		
	}

}
