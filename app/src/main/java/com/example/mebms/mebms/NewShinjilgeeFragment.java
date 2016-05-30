package com.example.mebms.mebms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import android.app.FragmentManager;
import android.content.SharedPreferences;
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

import com.mikepenz.materialdrawer.Drawer;

public class NewShinjilgeeFragment extends Fragment {

	private NewShinjilgee mAuthTask = null;

	Spinner shinjilgee_turul_spinner;
	Spinner sorits_ner_spinner;
	Spinner sorits_turul_spinner;
	Spinner mal_nas_spinner;
	Spinner mal_huis_spinner;
	Spinner sorits_too_spinner;
	Spinner negj_spinner;
	Spinner huleen_avah_baiguullaga_spinner;
	Spinner ilgeeh_arga_spinner;

	ArrayAdapter<CharSequence> shinjilgee_turul_adapter;
	ArrayAdapter<CharSequence> sorits_ner_adapter;
	ArrayAdapter<CharSequence> sorits_turul_adapter;
	ArrayAdapter<CharSequence> mal_nas_adapter;
	ArrayAdapter<CharSequence> mal_huis_adapter;
	ArrayAdapter<CharSequence> sorits_too_adapter;
	ArrayAdapter<CharSequence> negj_adapter;
	ArrayAdapter<CharSequence> huleen_avah_baiguullaga_adapter;
	ArrayAdapter<CharSequence> ilgeeh_arga_adapter;

	EditText urhCodeEdt;
	EditText urhEzenNerEdt;
	EditText bagEdt;
	EditText gazarEdt;
	EditText soritsBehjuulsenArgaEdt;
	EditText latEdt;
	EditText lonEdt;

	Button saveBtn;

	Activity parentActivity;

	private static String url_new_shijilgee = "http://10.0.2.2:81/mebp/newshinjilgee.php";
	JSONParser jsonParser = new JSONParser();

	public static final String PREFS_NAME = "MEBP";
	public SharedPreferences prefs;
	private int user_id;


	String urh_code;
	String urh_ezen_ner;
	String bag_horoo;
	String gazar_ner;
	String shinjilgee_turul;
	String sorits_ner;
	String sorits_turul;
	String mal_nas;
	String mal_huis;
	String sorits_too;
	String negj;
	String sorits_behjuulsen_arga;
	String huleen_avah_baiguullaga;
	String ilgeeh_arga;
	Date date;
	String latitude;
	String longitude;

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

		prefs = parentActivity.getSharedPreferences(PREFS_NAME, 0);
		user_id = prefs.getInt("userId", 0);

		((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
		((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		((HomeActivity)parentActivity).getSupportActionBar().setHomeButtonEnabled(true);
		((HomeActivity)parentActivity).result.setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
			@Override
			public boolean onNavigationClickListener(View clickedView) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, ListShinjilgeeFragment.newInstance())
						.commit();
				((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
				((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
				return true;
			}
		});

		urhCodeEdt = (EditText) rootView.findViewById(R.id.urh_code_edt);
		urhEzenNerEdt = (EditText) rootView.findViewById(R.id.urh_ezen_ner_edt);
		bagEdt = (EditText) rootView.findViewById(R.id.bag_horoo_edt);
		gazarEdt = (EditText) rootView.findViewById(R.id.gazar_ner_edt);
		soritsBehjuulsenArgaEdt = (EditText) rootView.findViewById(R.id.sorits_behjuulsen_arga_edt);
		latEdt = (EditText) rootView.findViewById(R.id.latitude_edt);
		lonEdt = (EditText) rootView.findViewById(R.id.longitude_edt);

		shinjilgee_turul_spinner = (Spinner) rootView
				.findViewById(R.id.shinjilgee_turul_spinner);
		shinjilgee_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.shinjilgee_turul_array, android.R.layout.simple_spinner_item);
		shinjilgee_turul_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shinjilgee_turul_spinner.setAdapter(shinjilgee_turul_adapter);

		sorits_ner_spinner = (Spinner) rootView
				.findViewById(R.id.sorits_ner_spinner);
		sorits_ner_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.sorits_ner_array, android.R.layout.simple_spinner_item);
		sorits_ner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sorits_ner_spinner.setAdapter(sorits_ner_adapter);

		sorits_turul_spinner = (Spinner) rootView
				.findViewById(R.id.sorits_turul_spinner);
		sorits_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.mal_turul_array, android.R.layout.simple_spinner_item);
		sorits_turul_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sorits_turul_spinner.setAdapter(sorits_turul_adapter);

		mal_nas_spinner = (Spinner) rootView
				.findViewById(R.id.mal_nas_spinner);
		mal_nas_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.mal_nas_array, android.R.layout.simple_spinner_item);
		mal_nas_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mal_nas_spinner.setAdapter(mal_nas_adapter);

		mal_huis_spinner = (Spinner) rootView
				.findViewById(R.id.mal_huis_spinner);
		mal_huis_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.mal_huis_array, android.R.layout.simple_spinner_item);
		mal_huis_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mal_huis_spinner.setAdapter(mal_huis_adapter);

		sorits_too_spinner = (Spinner) rootView
				.findViewById(R.id.sorits_too_spinner);
		sorits_too_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.sorits_too_array, android.R.layout.simple_spinner_item);
		sorits_too_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sorits_too_spinner.setAdapter(sorits_too_adapter);

		negj_spinner = (Spinner) rootView
				.findViewById(R.id.negj_spinner);
		negj_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.negj_array, android.R.layout.simple_spinner_item);
		negj_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		negj_spinner.setAdapter(negj_adapter);

		huleen_avah_baiguullaga_spinner = (Spinner) rootView
				.findViewById(R.id.huleen_avah_baiguullaga_spinner);
		huleen_avah_baiguullaga_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.huleen_avah_baiguullaga_array, android.R.layout.simple_spinner_item);
		huleen_avah_baiguullaga_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		huleen_avah_baiguullaga_spinner.setAdapter(huleen_avah_baiguullaga_adapter);

		ilgeeh_arga_spinner = (Spinner) rootView
				.findViewById(R.id.ilgeeh_arga_spinner);
		ilgeeh_arga_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.ilgeeh_arga_array, android.R.layout.simple_spinner_item);
		ilgeeh_arga_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ilgeeh_arga_spinner.setAdapter(ilgeeh_arga_adapter);

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

		if(urhCodeEdt.getText().toString().equals("") || urhEzenNerEdt.getText().toString().equals("") || bagEdt.getText().toString().equals("")
				|| gazarEdt.getText().toString().equals("") || soritsBehjuulsenArgaEdt.getText().toString().equals("")|| lonEdt.getText().toString().equals("")|| latEdt.getText().toString().equals("")) {
			Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
		}else {
			urh_code = urhCodeEdt.getText().toString();
			urh_ezen_ner = urhEzenNerEdt.getText().toString();
			bag_horoo = bagEdt.getText().toString();
			gazar_ner = gazarEdt.getText().toString();
			shinjilgee_turul = shinjilgee_turul_spinner.getSelectedItem().toString();
			sorits_ner = sorits_ner_spinner.getSelectedItem().toString();
			sorits_turul = sorits_turul_spinner.getSelectedItem().toString();
			mal_nas = mal_nas_spinner.getSelectedItem().toString();
			mal_huis = mal_huis_spinner.getSelectedItem().toString();
			sorits_too = sorits_too_spinner.getSelectedItem().toString();
			negj = negj_spinner.getSelectedItem().toString();
			sorits_behjuulsen_arga = soritsBehjuulsenArgaEdt.getText().toString();
			huleen_avah_baiguullaga = huleen_avah_baiguullaga_spinner.getSelectedItem().toString();
			ilgeeh_arga = ilgeeh_arga_spinner.getSelectedItem().toString();
			date = new Date(Calendar.getInstance().getTimeInMillis());
			Log.i("DATE",date.toString());
			longitude = lonEdt.getText().toString().equals("") ? "0" : lonEdt.getText().toString();
			latitude = latEdt.getText().toString().equals("") ? "0" : latEdt.getText().toString();

			mAuthTask = new NewShinjilgee(parentActivity);
			mAuthTask.execute();
		}
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
			params.add(new BasicNameValuePair("user_id", String.valueOf(user_id)));
			params.add(new BasicNameValuePair("urh_code", urh_code));
			params.add(new BasicNameValuePair("urh_ezen_ner", urh_ezen_ner));
			params.add(new BasicNameValuePair("bag_horoo", bag_horoo));
			params.add(new BasicNameValuePair("gazar_ner", gazar_ner));
			params.add(new BasicNameValuePair("shinjilgee_turul", shinjilgee_turul));
			params.add(new BasicNameValuePair("sorits_ner", sorits_ner));
			params.add(new BasicNameValuePair("sorits_turul", sorits_turul));
			params.add(new BasicNameValuePair("mal_nas", mal_nas));
			params.add(new BasicNameValuePair("mal_huis", mal_huis));
			params.add(new BasicNameValuePair("sorits_too", sorits_too));
			params.add(new BasicNameValuePair("negj", negj));
			params.add(new BasicNameValuePair("sorits_behjuulsen_arga", sorits_behjuulsen_arga));
			params.add(new BasicNameValuePair("huleen_avah_baiguullaga", huleen_avah_baiguullaga));
			params.add(new BasicNameValuePair("ilgeeh_arga", ilgeeh_arga));
			params.add(new BasicNameValuePair("latitude", latitude));
			params.add(new BasicNameValuePair("longitude", longitude));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Log.d("date",df.format(date));
			params.add(new BasicNameValuePair("date", df.format(date)));

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
							FragmentManager fragmentManager = getFragmentManager();
							fragmentManager.beginTransaction()
									.replace(R.id.frame_container, ListShinjilgeeFragment.newInstance())
									.commit();
							((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
							((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
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
		((HomeActivity) activity).onSectionAttached(4);
	}
}