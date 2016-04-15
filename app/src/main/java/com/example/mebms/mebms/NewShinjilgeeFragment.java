package com.example.mebms.mebms;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewShinjilgeeFragment extends Fragment {

	private NewShinjilgee mAuthTask = null;

	Spinner shinjilgee_turul_spinner;
	Spinner sorits_turul_spinner;
	Spinner sorits_nas_spinner;
	Spinner sorits_huis_spinner;
	Spinner sorits_deej_spinner;
	Spinner huleen_avah_baiguulga_spinner;
	Spinner ilgeesen_arga_spinner;

	ArrayAdapter<CharSequence> shinjilgee_turul_adapter;
	ArrayAdapter<CharSequence> sorits_turul_adapter;
	ArrayAdapter<CharSequence> sorits_nas_adapter;
	ArrayAdapter<CharSequence> sorits_huis_adapter;
	ArrayAdapter<CharSequence> sorits_deej_adapter;
	ArrayAdapter<CharSequence> huleen_avah_baiguulga_adapter;
	ArrayAdapter<CharSequence> ilgeesen_arga_adapter;

	EditText urhCodeEdt;
	EditText urhEzenNerEdt;
	EditText bagEdt;
	EditText gazarEdt;
	EditText soritsHemjeeEdt;
	EditText soritsBehjuulsenArgaEdt;

	Button saveBtn;

	Activity parentActivity;

	private static String url_new_shijilgee = "http://10.0.2.2:81/mbms/newshinjilgee.php";
	JSONParser jsonParser = new JSONParser();


	String urh_code;
	String urh_ezen;
	String bag;
	String gazar;
	String shinjilgee_turul;
	String sorits_turul;
	String sorits_nas;
	String sorits_huis;
	String sorits_deej;
	String sorits_hemjee;
	String sorits_behjuulsen_arga;
	String huleen_avah_baiguulga;
	String ilgeesen_arga;

	Date date;



	public static NewShinjilgeeFragment newInstance() {
		NewShinjilgeeFragment fragment = new NewShinjilgeeFragment();
		return fragment;
	}

	public NewShinjilgeeFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_new_shinjilgee,
				container, false);

		urhCodeEdt = (EditText) rootView.findViewById(R.id.urh_code_edt);
		urhEzenNerEdt = (EditText) rootView.findViewById(R.id.urh_ezen_ner_edt);
		bagEdt = (EditText) rootView.findViewById(R.id.bag_ner_edt);
		gazarEdt = (EditText) rootView.findViewById(R.id.gazar_ner_edt);
		soritsHemjeeEdt = (EditText) rootView.findViewById(R.id.sorits_hemjee_edt);
		soritsBehjuulsenArgaEdt = (EditText) rootView.findViewById(R.id.sorits_behjuulsen_arga_edt);


		shinjilgee_turul_spinner = (Spinner) rootView
				.findViewById(R.id.shinjilgee_turul_spinner);
		shinjilgee_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.shinj_turul_array, android.R.layout.simple_spinner_item);
		shinjilgee_turul_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shinjilgee_turul_spinner.setAdapter(shinjilgee_turul_adapter);



		sorits_turul_spinner = (Spinner) rootView
				.findViewById(R.id.sorits_turul_spinner);
		sorits_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.sorits_turul_array, android.R.layout.simple_spinner_item);
		sorits_turul_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sorits_turul_spinner.setAdapter(sorits_turul_adapter);

		sorits_nas_spinner = (Spinner) rootView
				.findViewById(R.id.sorits_nas_spinner);
		sorits_nas_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.sorits_nas_array, android.R.layout.simple_spinner_item);
		sorits_nas_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sorits_nas_spinner.setAdapter(sorits_nas_adapter);

		sorits_huis_spinner = (Spinner) rootView
				.findViewById(R.id.sorits_huis_spinner);
		sorits_huis_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.sorits_huis_array, android.R.layout.simple_spinner_item);
		sorits_huis_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sorits_huis_spinner.setAdapter(sorits_huis_adapter);

		sorits_deej_spinner = (Spinner) rootView
				.findViewById(R.id.sorits_deej_spinner);
		sorits_deej_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.sorits_deej_array, android.R.layout.simple_spinner_item);
		sorits_deej_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sorits_deej_spinner.setAdapter(sorits_deej_adapter);

		huleen_avah_baiguulga_spinner = (Spinner) rootView
				.findViewById(R.id.huleen_avah_baiguulga_spinner);
		huleen_avah_baiguulga_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.huleen_avah_baiguulga_array, android.R.layout.simple_spinner_item);
		huleen_avah_baiguulga_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		huleen_avah_baiguulga_spinner.setAdapter(huleen_avah_baiguulga_adapter);


		ilgeesen_arga_spinner = (Spinner) rootView
				.findViewById(R.id.ilgeesen_arga_spinner);
		ilgeesen_arga_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.ilgeesen_arga_array, android.R.layout.simple_spinner_item);
		ilgeesen_arga_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ilgeesen_arga_spinner.setAdapter(ilgeesen_arga_adapter);

		saveBtn = (Button) rootView.findViewById(R.id.shinjilgee_save);

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
		urh_ezen = urhEzenNerEdt.getText().toString();
		bag = bagEdt.getText().toString();
		gazar = gazarEdt.getText().toString();
		shinjilgee_turul = shinjilgee_turul_spinner.getSelectedItem().toString();
		sorits_turul = sorits_turul_spinner.getSelectedItem().toString();
		sorits_nas = sorits_nas_spinner.getSelectedItem().toString();
		sorits_huis = sorits_huis_spinner.getSelectedItem().toString();
		sorits_deej = sorits_deej_spinner.getSelectedItem().toString();
		sorits_hemjee = soritsHemjeeEdt.getText().toString();
		sorits_behjuulsen_arga = soritsBehjuulsenArgaEdt.getText().toString();
		huleen_avah_baiguulga = huleen_avah_baiguulga_spinner.getSelectedItem().toString();
		ilgeesen_arga = ilgeesen_arga_spinner.getSelectedItem().toString();
		date = new Date(Calendar.getInstance().getTimeInMillis());

		mAuthTask = new NewShinjilgee(parentActivity);
		mAuthTask.execute();
	}



	class NewShinjilgee extends AsyncTask<String, String, String> {
		private Activity pActivity;

		public NewShinjilgee(Activity parent) {
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
			params.add(new BasicNameValuePair("urh_ezen", urh_ezen));
			params.add(new BasicNameValuePair("bag", bag));
			params.add(new BasicNameValuePair("gazar", gazar));
			params.add(new BasicNameValuePair("shinjilgee_turul", shinjilgee_turul));
			params.add(new BasicNameValuePair("sorits_turul", sorits_turul));
			params.add(new BasicNameValuePair("sorits_nas", sorits_nas));
			params.add(new BasicNameValuePair("sorits_huis", sorits_huis));
			params.add(new BasicNameValuePair("sorits_deej", sorits_deej));
			params.add(new BasicNameValuePair("sorits_hemjee", sorits_hemjee));
			params.add(new BasicNameValuePair("sorits_behjuulsen_arga", sorits_behjuulsen_arga));
			params.add(new BasicNameValuePair("huleen_avah_baiguulga", huleen_avah_baiguulga));
			params.add(new BasicNameValuePair("ilgeesen_arga", ilgeesen_arga));
			Log.d("date",date.toLocaleString());
			params.add(new BasicNameValuePair("date", date.toString()));

			JSONObject json = jsonParser.makeHttpRequest(url_new_shijilgee, "GET",
					params);

			try {
				int success = json.getInt("success");

				if (success == 1) {
					pActivity.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(pActivity.getBaseContext(),
									"Амжилттай хадгалагдлаа.",
									Toast.LENGTH_LONG).show();
						}
					});
				} else {
					pActivity.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(pActivity.getBaseContext(),
									"Алдаа гарлаа!", Toast.LENGTH_LONG).show();
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
		parentActivity = activity;
		((HomeActivity) activity).onSectionAttached(2);
	}
}