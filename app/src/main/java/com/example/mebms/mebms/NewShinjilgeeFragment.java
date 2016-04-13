package com.example.mebms.mebms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
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

	Spinner aimag_spinner;
	Spinner sum_spinner;
	Spinner golomt_spinner;
	Spinner shinj_turul_spinner;
	Spinner deej_turul_spinner;
	Spinner arga_spinner;
	Spinner urval_spinner;
	ArrayList<Integer> golomtID = new ArrayList<Integer>();
	ArrayAdapter<CharSequence> aimag_adapter;
	ArrayAdapter<CharSequence> sum_adapter;
	ArrayAdapter<String> golomt_adapter;
	ArrayAdapter<CharSequence> shinj_turul_adapter;
	ArrayAdapter<CharSequence> deej_turul_adapter;
	ArrayAdapter<CharSequence> arga_adapter;
	ArrayAdapter<CharSequence> urval_adapter;
	List<String> spinnerArray;

	EditText urhiincodeEdt;
	EditText urhiinterguunEdt;
	EditText ezencodeEdt;
	EditText ezennerEdt;
	Button saveBtn;

	Activity parentActivity;

	private static String url_log_in = "http://10.0.2.2/mbms/newgshinjilgee.php";
	private static String url_get_golomt = "http://10.0.2.2/mbms/getgolomt.php";
	private static String url_new_shijilgee = "http://10.0.2.2/mbms/newshinjilgee.php";
	JSONParser jsonParser = new JSONParser();


	String urhiincode;
	String urhiinterguun;
	String ezencode;
	String ezenner;
	String aimag;
	String sum;
	String golomt;
	String shinj_turul;
	String deej_turul;
	String arga;
	String urval;



	// TODO: Rename and change types and number of parameters
	public static NewShinjilgeeFragment newInstance() {
		NewShinjilgeeFragment fragment = new NewShinjilgeeFragment();
		return fragment;
	}

	public NewShinjilgeeFragment() {
		// Required empty public constructor
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

		urhiincodeEdt = (EditText) rootView.findViewById(R.id.urhiincodetext);
		urhiinterguunEdt = (EditText) rootView.findViewById(R.id.urhiinterguuntext);
		ezencodeEdt = (EditText) rootView.findViewById(R.id.ezencodetext);
		ezennerEdt = (EditText) rootView.findViewById(R.id.ezennertext);
		
		aimag_spinner = (Spinner) rootView
				.findViewById(R.id.aimag_spinner_shinjilgee);
		aimag_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.aimag_array, android.R.layout.simple_spinner_item);
		aimag_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		aimag_spinner.setAdapter(aimag_adapter);

		sum_spinner = (Spinner) rootView
				.findViewById(R.id.sum_spinner_shinjilgee);

		golomt_spinner = (Spinner) rootView
				.findViewById(R.id.golomt_spinner_shinjilgee);
		
		shinj_turul_spinner = (Spinner) rootView
				.findViewById(R.id.turul_spinner_shinjilgee);
		shinj_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.shinj_turul_array, android.R.layout.simple_spinner_item);
		shinj_turul_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shinj_turul_spinner.setAdapter(shinj_turul_adapter);

		deej_turul_spinner = (Spinner) rootView
				.findViewById(R.id.deej_spinner_shinjilgee);
		deej_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.deej_turul_array, android.R.layout.simple_spinner_item);
		deej_turul_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		deej_turul_spinner.setAdapter(deej_turul_adapter);

		arga_spinner = (Spinner) rootView
				.findViewById(R.id.arga_spinner_shinjilgee);
		arga_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.arga_array, android.R.layout.simple_spinner_item);
		arga_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		arga_spinner.setAdapter(arga_adapter);

		urval_spinner = (Spinner) rootView
				.findViewById(R.id.urval_spinner_shinjilgee);
		urval_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
				R.array.urval_array, android.R.layout.simple_spinner_item);
		urval_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		urval_spinner.setAdapter(urval_adapter);
		

		saveBtn = (Button) rootView.findViewById(R.id.golomt_save);

		aimag_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				switch (pos) {
				case 0:
					sum_adapter = ArrayAdapter.createFromResource(
							parent.getContext(), R.array.sum_array_1,
							android.R.layout.simple_spinner_item);
					break;
				case 1:
					sum_adapter = ArrayAdapter.createFromResource(
							parent.getContext(), R.array.sum_array_2,
							android.R.layout.simple_spinner_item);
					break;
				default:
					break;
				}
				sum_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sum_spinner.setAdapter(sum_adapter);

			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});
		golomt_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// An item was selected. You can retrieve the selected item
				// using
				// parent.getItemAtPosition(pos)
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});
		sum_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// An item was selected. You can retrieve the selected item
				// using
				// parent.getItemAtPosition(pos)
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ShinjilgeeSave(parentActivity).execute();
			}
		});
		
		new NewShinjilgee(parentActivity).execute();
		
		return rootView;
	}


	private void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		urhiincode = urhiincodeEdt.getText().toString();
		urhiinterguun = urhiinterguunEdt.getText().toString();
		ezencode = ezencodeEdt.getText().toString();
		ezenner = ezennerEdt.getText().toString();
		aimag = aimag_spinner.getSelectedItem().toString();
		sum = sum_spinner.getSelectedItem().toString();
		golomt = golomt_spinner.getSelectedItem().toString();
		shinj_turul = shinj_turul_spinner.getSelectedItem().toString();
		deej_turul = deej_turul_spinner.getSelectedItem().toString();
		arga = arga_spinner.getSelectedItem().toString();
		urval = urval_spinner.getSelectedItem().toString();

		mAuthTask = new NewShinjilgee(parentActivity);
		mAuthTask.execute();
	}



	class ShinjilgeeSave extends AsyncTask<String, String, String> {
		private Activity pActivity;

		public ShinjilgeeSave(Activity parent) {
			this.pActivity = parent;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... args) {


			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("urhiincode", urhiincode));
			params.add(new BasicNameValuePair("urhiinterguun", urhiinterguun));
			params.add(new BasicNameValuePair("ezencode", ezencode));
			params.add(new BasicNameValuePair("ezenner", ezenner));
			params.add(new BasicNameValuePair("aimag", aimag));
			params.add(new BasicNameValuePair("sum", sum));
			params.add(new BasicNameValuePair("golomt", golomt));
			params.add(new BasicNameValuePair("shinj_turul", shinj_turul));
			params.add(new BasicNameValuePair("deej_turul", deej_turul));
			params.add(new BasicNameValuePair("arga", arga));
			params.add(new BasicNameValuePair("urval", urval));

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
			params.add(new BasicNameValuePair("list", "all"));

			JSONObject json = jsonParser.makeHttpRequest(url_get_golomt, "GET",
					params);

			try {
				int success = json.getInt("success");

				if (success == 1) {
					spinnerArray = new ArrayList<String>();
					for (int i = 0; i < json.getJSONArray("golomt").length(); i++) {
						spinnerArray.add(json.getJSONArray("golomt")
								.getJSONObject(i).getString("name"));

						golomtID.add(json.getJSONArray("golomt")
								.getJSONObject(i).getInt("id"));
					}
					pActivity.runOnUiThread(new Runnable() {
						public void run() {
							golomt_adapter = new ArrayAdapter<String>(pActivity
									.getBaseContext(),
									android.R.layout.simple_spinner_item,
									spinnerArray);

							golomt_adapter
									.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							golomt_spinner.setAdapter(golomt_adapter);
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