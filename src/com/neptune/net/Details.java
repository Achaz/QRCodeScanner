package com.neptune.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neptune.net.util.ConnectionDetector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

public class Details extends Activity{

	
	ProgressDialog pDialog;
	
	static final String KEY_ID = "id";
	static final String KEY_STDNo="studentno";
	static final String KEY_STDREGNo="regno";
    static final String KEY_COURSE="Course";
    static final String KEY_FNAME= "firstname";
	static final String KEY_LNAME= "lastname";
	static final String KEY_TUITION="Amount";
	
	static final String KEY_STATUS="status";
	
	ArrayList<HashMap<String, String>> weatherDataCollection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		
		if(!cd.isConnectingToInternet()){
			
			showAlertDialog(Details.this,"Internet Connection Error",
		            "Please connect to working Internet connection",true);

		}else{
			
			new PostData().execute();
	
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
	class PostData extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Details.this);
			pDialog.setMessage("Searching.Please Wait ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Intent in =getIntent();
			
			String studentno=in.getStringExtra("stdNo");
			
			String URL="http://109.123.112.186/muk_student_sys/view_all_students.php?studentno="+studentno;
			
			try{
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(URL);
				
				weatherDataCollection = new ArrayList<HashMap<String,String>>();
		        
		        doc.getDocumentElement ().normalize ();
		        
	            NodeList weatherList = doc.getElementsByTagName("student_table");
		        
		        HashMap<String,String> map = null;
		        
		        for(int i=0;i<weatherList.getLength();i++){
		        	
		        	map = new HashMap<String,String>();
		        	Node firstWeatherNode = weatherList.item(i);
		        	
		        	if(firstWeatherNode.getNodeType() == Node.ELEMENT_NODE){
		        		
		        		Element firstWeatherElement = (Element)firstWeatherNode;

	                    NodeList stdidList = firstWeatherElement.getElementsByTagName(KEY_STDNo);
	                    Element  firstStIdElement =(Element)stdidList.item(0);
	                    NodeList textStdIdList =firstStIdElement.getChildNodes();
	                    
	                    map.put(KEY_STDNo, ((Node)textStdIdList.item(0)).getNodeValue().trim());
	                    
	                    NodeList stdRegIdList=firstWeatherElement.getElementsByTagName(KEY_STDREGNo);
	                    Element firstRegIdElement =(Element)stdRegIdList.item(0);
	                    NodeList textStdRegIdList = firstRegIdElement.getChildNodes();
	                    
	                    map.put(KEY_STDREGNo, ((Node)textStdRegIdList.item(0)).getNodeValue().trim());
	                    
	                    NodeList stdCourseIdList=firstWeatherElement.getElementsByTagName(KEY_COURSE);
	                    Element firstCourseIdElement =(Element)stdCourseIdList.item(0);
	                    NodeList textStdCousreIdList = firstCourseIdElement.getChildNodes();
	                    
	                    map.put(KEY_COURSE, ((Node)textStdCousreIdList.item(0)).getNodeValue().trim());

	                    NodeList stdtuitionIdList=firstWeatherElement.getElementsByTagName(KEY_TUITION);
	                    Element firsttuitionIdElement =(Element)stdtuitionIdList.item(0);
	                    NodeList textttuitionRegIdList = firsttuitionIdElement.getChildNodes();
	                    
	                    map.put(KEY_TUITION, ((Node)textttuitionRegIdList.item(0)).getNodeValue().trim());

	                    NodeList stdStatusIdList=firstWeatherElement.getElementsByTagName(KEY_STATUS);
	                    Element firststatusIdElement =(Element)stdStatusIdList.item(0);
	                    NodeList textStdStatusIdList = firststatusIdElement.getChildNodes();
	                    
	                    map.put(KEY_STATUS, ((Node)textStdStatusIdList.item(0)).getNodeValue().trim());

	                    NodeList stdfnameList = firstWeatherElement.getElementsByTagName(KEY_FNAME);
	                    Element firstnameIdElement = (Element)stdfnameList.item(0);
	                    NodeList textStnameIdList = firstnameIdElement.getChildNodes();
	                    
	                    map.put(KEY_FNAME, ((Node)textStnameIdList.item(0)).getNodeValue().trim());
	                    
	                    NodeList stdLnameList = firstWeatherElement.getElementsByTagName(KEY_LNAME);
	                    Element lastnameIdElement = (Element)stdLnameList.item(0);
	                    NodeList textlastStnameIdList = lastnameIdElement.getChildNodes();
	                    
	                    map.put(KEY_LNAME, ((Node)textlastStnameIdList.item(0)).getNodeValue().trim());
	     
	                    weatherDataCollection.add(map);
		        		
		        	}
	
		        }

			}catch (IOException ex) {
				 
				Log.e("Error", ex.getMessage());
			}
			catch (Exception ex) {
				
				Log.e("Error", "Loading exception");
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			
			for(int position=0;position<weatherDataCollection.size();position++){
				
				TextView stdnumber=(TextView)findViewById(R.id.tvStdNo);
				TextView stdRegNumber =(TextView)findViewById(R.id.tvStdRegNo);
				TextView stdFName=(TextView)findViewById(R.id.tvStdFName);
				TextView stdLName = (TextView)findViewById(R.id.tvStdLName);
				TextView stdCourse=(TextView)findViewById(R.id.tvStdCourse);
			    TextView stdStatus=(TextView)findViewById(R.id.tvStdStatus);
				TextView stdTuition=(TextView)findViewById(R.id.tvStdtuition);

				String studentnumber=weatherDataCollection.get(position).get(KEY_STDNo);
				String studentRegnumber=weatherDataCollection.get(position).get(KEY_STDREGNo);
		        String studentLastname = weatherDataCollection.get(position).get(KEY_LNAME);
		        String studentFirstname = weatherDataCollection.get(position).get(KEY_FNAME);
				String studentCourse=weatherDataCollection.get(position).get(KEY_COURSE);
				String StudentStatus=weatherDataCollection.get(position).get(KEY_STATUS);
				String studentTution=weatherDataCollection.get(position).get(KEY_TUITION);

				stdnumber.setText(studentnumber);
				stdRegNumber.setText(studentRegnumber);
				stdFName.setText(studentFirstname);
				stdLName.setText(studentLastname);
	            stdCourse.setText(studentCourse);
	            stdStatus.setText(StudentStatus);
				stdTuition.setText(studentTution);

				
			}
			
			
		}
	
	}
	

	
}
