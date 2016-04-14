package com.example.mebms.mebms;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewUvchinFragment extends Fragment {


	Spinner uvchin_turul_spinner;
	Spinner uvchin_ner_spinner;
	Spinner ustgah_arga_spinner;
	ArrayAdapter<CharSequence> uvchin_turul_adapter;
	ArrayAdapter<CharSequence> uvchin_ner_adapter;
	ArrayAdapter<CharSequence> ustgah_arga_adapter;
	
	EditText urhCodeEdt;
	EditText urhEzenNerEdt;
	EditText bagEdt;
	EditText gazarEdt;
	EditText latEdt;
	EditText lonEdt;

	EditText h_zuvluguu_Edt;
	EditText h_emchilgee_Edt;
	EditText h_edgersen_Edt;
	EditText h_uhsen_Edt;
	EditText h_ustgasan_Edt;

	EditText y_zuvluguu_Edt;
	EditText y_emchilgee_Edt;
	EditText y_edgersen_Edt;
	EditText y_uhsen_Edt;
	EditText y_ustgasan_Edt;

	EditText u_zuvluguu_Edt;
	EditText u_emchilgee_Edt;
	EditText u_edgersen_Edt;
	EditText u_uhsen_Edt;
	EditText u_ustgasan_Edt;

	EditText m_zuvluguu_Edt;
	EditText m_emchilgee_Edt;
	EditText m_edgersen_Edt;
	EditText m_uhsen_Edt;
	EditText m_ustgasan_Edt;

	EditText t_zuvluguu_Edt;
	EditText t_emchilgee_Edt;
	EditText t_edgersen_Edt;
	EditText t_uhsen_Edt;
	EditText t_ustgasan_Edt;


	Button saveBtn;
	
	Activity parentActivity;

	private UvchinNew mAuthTask = null;

	private static String url_uvchin_new = "http://10.0.2.2:81/mbms/newuvchin.php";
    JSONParser jsonParser = new JSONParser();


	String urh_code;
	String urh_ezen_ner;
	String bag_ner;
	String gazar_ner;
	String uvchin_turul;
	String uvchin_ner;
	String ustgah_arga;
	String latitude;
	String longitude;

	String h_zuvluguu;
	String h_emchilgee;
	String h_edgersen;
	String h_uhsen;
	String h_ustgasan;

	String y_zuvluguu;
	String y_emchilgee;
	String y_edgersen;
	String y_uhsen;
	String y_ustgasan;

	String u_zuvluguu;
	String u_emchilgee;
	String u_edgersen;
	String u_uhsen;
	String u_ustgasan;

	String m_zuvluguu;
	String m_emchilgee;
	String m_edgersen;
	String m_uhsen;
	String m_ustgasan;

	String t_zuvluguu;
	String t_emchilgee;
	String t_edgersen;
	String t_uhsen;
	String t_ustgasan;

	Date date;
	
	// TODO: Rename and change types and number of parameters
	public static NewUvchinFragment newInstance() {
		NewUvchinFragment fragment = new NewUvchinFragment();
		return fragment;
	}

	public NewUvchinFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_new_uvchin, container,  
			    false);



		urhCodeEdt = (EditText)rootView.findViewById(R.id.urh_code_edt);
		urhEzenNerEdt = (EditText)rootView.findViewById(R.id.urh_ezen_ner_edt);
		bagEdt = (EditText)rootView.findViewById(R.id.bag_ner_edt);
		gazarEdt = (EditText)rootView.findViewById(R.id.gazar_ner_edt);
		latEdt = (EditText)rootView.findViewById(R.id.latitudenum);
		lonEdt = (EditText)rootView.findViewById(R.id.longitudenum);


		h_zuvluguu_Edt = (EditText)rootView.findViewById(R.id.honi_zuvluguu_edt);
		h_emchilgee_Edt = (EditText)rootView.findViewById(R.id.honi_emchilgee_edt);
		h_edgersen_Edt = (EditText)rootView.findViewById(R.id.honi_edgersen_edt);
		h_uhsen_Edt = (EditText)rootView.findViewById(R.id.honi_uhsen_edt);
		h_ustgasan_Edt = (EditText)rootView.findViewById(R.id.honi_ustgasan_edt);


		y_zuvluguu_Edt = (EditText)rootView.findViewById(R.id.yamaa_zuvluguu_edt);
		y_emchilgee_Edt = (EditText)rootView.findViewById(R.id.yamaa_emchilgee_edt);
		y_edgersen_Edt = (EditText)rootView.findViewById(R.id.yamaa_edgersen_edt);
		y_uhsen_Edt = (EditText)rootView.findViewById(R.id.yamaa_uhsen_edt);
		y_ustgasan_Edt = (EditText)rootView.findViewById(R.id.yamaa_ustgasan_edt);

		u_zuvluguu_Edt = (EditText)rootView.findViewById(R.id.uher_zuvluguu_edt);
		u_emchilgee_Edt = (EditText)rootView.findViewById(R.id.uher_emchilgee_edt);
		u_edgersen_Edt = (EditText)rootView.findViewById(R.id.uher_edgersen_edt);
		u_uhsen_Edt = (EditText)rootView.findViewById(R.id.uher_uhsen_edt);
		u_ustgasan_Edt = (EditText)rootView.findViewById(R.id.uher_ustgasan_edt);

		m_zuvluguu_Edt = (EditText)rootView.findViewById(R.id.mori_zuvluguu_edt);
		m_emchilgee_Edt = (EditText)rootView.findViewById(R.id.mori_emchilgee_edt);
		m_edgersen_Edt = (EditText)rootView.findViewById(R.id.mori_edgersen_edt);
		m_uhsen_Edt = (EditText)rootView.findViewById(R.id.mori_uhsen_edt);
		m_ustgasan_Edt = (EditText)rootView.findViewById(R.id.mori_ustgasan_edt);

		t_zuvluguu_Edt = (EditText)rootView.findViewById(R.id.temee_zuvluguu_edt);
		t_emchilgee_Edt = (EditText)rootView.findViewById(R.id.temee_emchilgee_edt);
		t_edgersen_Edt = (EditText)rootView.findViewById(R.id.temee_edgersen_edt);
		t_uhsen_Edt = (EditText)rootView.findViewById(R.id.temee_uhsen_edt);
		t_ustgasan_Edt = (EditText)rootView.findViewById(R.id.temee_ustgasan_edt);

		uvchin_turul_spinner = (Spinner) rootView.findViewById(R.id.uvchin_turul_spinner);
		uvchin_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.aimag_array, android.R.layout.simple_spinner_item);
		uvchin_turul_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		uvchin_turul_spinner.setAdapter(uvchin_turul_adapter);


		uvchin_ner_spinner = (Spinner) rootView.findViewById(R.id.uvchin_ner_spinner);
		uvchin_ner_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.sum_array_1, android.R.layout.simple_spinner_item);

		ustgah_arga_spinner = (Spinner) rootView.findViewById(R.id.ustgah_arga_spinner);
		ustgah_arga_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.arga_array, android.R.layout.simple_spinner_item);
		ustgah_arga_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ustgah_arga_spinner.setAdapter(ustgah_arga_adapter);
		
		saveBtn = (Button)rootView.findViewById(R.id.uvchin_save);
		
		uvchin_turul_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
				switch (pos) 
	            {
	                case 0: uvchin_ner_adapter = ArrayAdapter.createFromResource(parent.getContext(),
					        R.array.sum_array_1, android.R.layout.simple_spinner_item);
	                        break;
	                case 1: uvchin_ner_adapter = ArrayAdapter.createFromResource(parent.getContext(),
					        R.array.sum_array_2, android.R.layout.simple_spinner_item);
	                        break;
	                default:
	                        break;
	            }
                uvchin_ner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		uvchin_ner_spinner.setAdapter(uvchin_ner_adapter);
				
		    }

		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
		});
		uvchin_ner_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        // parent.getItemAtPosition(pos)
		    }

		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
		});
		
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				attemptLogin();
			}
		});







		
		
			  return rootView;
	}


	private void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}


		urh_code = urhCodeEdt.getText().toString();
		urh_ezen_ner = urhEzenNerEdt.getText().toString();
		bag_ner = bagEdt.getText().toString();
		gazar_ner = gazarEdt.getText().toString();
		uvchin_turul = uvchin_turul_spinner.getSelectedItem().toString();
		uvchin_ner = uvchin_ner_spinner.getSelectedItem().toString();
		ustgah_arga = ustgah_arga_spinner.getSelectedItem().toString();

		h_zuvluguu = h_zuvluguu_Edt.getText().toString();
		h_emchilgee = h_emchilgee_Edt.getText().toString();
		h_edgersen = h_edgersen_Edt.getText().toString();
		h_uhsen = h_uhsen_Edt.getText().toString();
		h_ustgasan = h_ustgasan_Edt.getText().toString();

		y_zuvluguu = y_zuvluguu_Edt.getText().toString();
		y_emchilgee = y_emchilgee_Edt.getText().toString();
		y_edgersen = y_edgersen_Edt.getText().toString();
		y_uhsen = y_uhsen_Edt.getText().toString();
		y_ustgasan = y_ustgasan_Edt.getText().toString();

		u_zuvluguu = u_zuvluguu_Edt.getText().toString();
		u_emchilgee = u_emchilgee_Edt.getText().toString();
		u_edgersen = u_edgersen_Edt.getText().toString();
		u_uhsen = u_uhsen_Edt.getText().toString();
		u_ustgasan = u_ustgasan_Edt.getText().toString();

		m_zuvluguu = m_zuvluguu_Edt.getText().toString();
		m_emchilgee = m_emchilgee_Edt.getText().toString();
		m_edgersen = m_edgersen_Edt.getText().toString();
		m_uhsen = m_uhsen_Edt.getText().toString();
		m_ustgasan = m_ustgasan_Edt.getText().toString();

		t_zuvluguu = t_zuvluguu_Edt.getText().toString();
		t_emchilgee = t_emchilgee_Edt.getText().toString();
		t_edgersen = t_edgersen_Edt.getText().toString();
		t_uhsen = t_uhsen_Edt.getText().toString();
		t_ustgasan = t_ustgasan_Edt.getText().toString();

		date = new Date(Calendar.getInstance().getTimeInMillis());

		longitude = lonEdt.getText().toString();
		latitude = latEdt.getText().toString();

		mAuthTask = new UvchinNew(parentActivity);
		mAuthTask.execute();
	}


class UvchinNew extends AsyncTask<String, String, String> {
    	private Activity pActivity;
    	public UvchinNew(Activity parent){
    		this.pActivity = parent;
    	}
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();

            
        }

		@Override
		protected String doInBackground(String... args) {

	        
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("urh_code", urh_code));
	        params.add(new BasicNameValuePair("urh_ezen_ner", urh_ezen_ner));
	        params.add(new BasicNameValuePair("bag_ner", bag_ner));
	        params.add(new BasicNameValuePair("gazar_ner", gazar_ner));
			params.add(new BasicNameValuePair("uvchin_turul", uvchin_turul));
			params.add(new BasicNameValuePair("uvchin_ner", uvchin_ner));
			params.add(new BasicNameValuePair("ustgah_arga", ustgah_arga));
			params.add(new BasicNameValuePair("latitude", latitude));
			params.add(new BasicNameValuePair("longitude", longitude));

			params.add(new BasicNameValuePair("h_zuvluguu", h_zuvluguu));
			params.add(new BasicNameValuePair("h_emchilgee", h_emchilgee));
			params.add(new BasicNameValuePair("h_edgersen", h_edgersen));
			params.add(new BasicNameValuePair("h_uhsen", h_uhsen));
			params.add(new BasicNameValuePair("h_ustgasan", h_ustgasan));

			params.add(new BasicNameValuePair("y_zuvluguu", y_zuvluguu));
			params.add(new BasicNameValuePair("y_emchilgee", y_emchilgee));
			params.add(new BasicNameValuePair("y_edgersen", y_edgersen));
			params.add(new BasicNameValuePair("y_uhsen", y_uhsen));
			params.add(new BasicNameValuePair("y_ustgasan", y_ustgasan));

			params.add(new BasicNameValuePair("u_zuvluguu", u_zuvluguu));
			params.add(new BasicNameValuePair("u_emchilgee", u_emchilgee));
			params.add(new BasicNameValuePair("u_edgersen", u_edgersen));
			params.add(new BasicNameValuePair("u_uhsen", u_uhsen));
			params.add(new BasicNameValuePair("u_ustgasan", u_ustgasan));

			params.add(new BasicNameValuePair("m_zuvluguu", m_zuvluguu));
			params.add(new BasicNameValuePair("m_emchilgee", m_emchilgee));
			params.add(new BasicNameValuePair("m_edgersen", m_edgersen));
			params.add(new BasicNameValuePair("m_uhsen", m_uhsen));
			params.add(new BasicNameValuePair("m_ustgasan", m_ustgasan));

			params.add(new BasicNameValuePair("t_zuvluguu", t_zuvluguu));
			params.add(new BasicNameValuePair("t_emchilgee", t_emchilgee));
			params.add(new BasicNameValuePair("t_edgersen", t_edgersen));
			params.add(new BasicNameValuePair("t_uhsen", t_uhsen));
			params.add(new BasicNameValuePair("t_ustgasan", t_ustgasan));

			params.add(new BasicNameValuePair("date", date.toString()));

			Log.d("date",date.toString());

	        JSONObject json = jsonParser.makeHttpRequest(url_uvchin_new, "GET", params);

            
	        try {
	            int success = json.getInt("success");

	            if (success == 1) {
	                pActivity.runOnUiThread(new Runnable() {
	            		  public void run() {
	            		    Toast.makeText(pActivity.getBaseContext(), "Амжилттай хадгалагдлаа.", Toast.LENGTH_LONG).show();
	            		  }
	            		});
	            } else {
	            	pActivity.runOnUiThread(new Runnable() {
	            		  public void run() {
	            		    Toast.makeText(pActivity.getBaseContext(), "Алдаа гарлаа!", Toast.LENGTH_LONG).show();
	            		  }
	            		});
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
			
			return null;
		}
		
		
		protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
        }
    }

	@Override  
	 public void onAttach(Activity activity) {  
	  super.onAttach(activity);  
	  parentActivity=activity;
	  ((HomeActivity) activity).onSectionAttached(2);
	 } 
}
