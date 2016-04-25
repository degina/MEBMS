package com.example.mebms.mebms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ShinjilgeeListFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class ShinjilgeeListFragment extends ListFragment implements OnItemClickListener {

	public static final String PREFS_NAME = "MEBP";
	public SharedPreferences prefs;

	private OnFragmentInteractionListener mListener;

	ArrayList<String> urh_codeArray = new ArrayList<String>();
	ArrayList<String> shinjilgee_turulArray = new ArrayList<String>();
	ArrayList<String> ognooArray = new ArrayList<String>();
	ArrayList<Integer> shinjilgeeID = new ArrayList<Integer>();

	private static String url_get_shinjilgee = "http://10.0.2.2:81/mebp/shinjilgeelist.php";
	JSONParser jsonParser = new JSONParser();
	private GetShinjilgee mAuthTask = null;

//	Spinner shinjilgee_turul_spinner;
//	ArrayAdapter<CharSequence> shinjilgee_turul_adapter;

	private Button btnChangeDate;
	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;
	private Calendar calendar;
	public Date date;

	ShinjilgeeListAdapter adapter;

	Activity parentActivity;

	String date_filter ="";
//	String type_filter ="";


	public static ShinjilgeeListFragment newInstance() {
		ShinjilgeeListFragment fragment = new ShinjilgeeListFragment();
		return fragment;
	}

	public ShinjilgeeListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shinjilgee_list,
				container, false);
		prefs = ((HomeActivity)parentActivity).getSharedPreferences(PREFS_NAME, 0);
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		calendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(parentActivity, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int y, int m, int d) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(y, m, d);
				date=newDate.getTime();
				btnChangeDate.setText(dateFormatter.format(newDate.getTime()));
				getList();
			}

		},calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		btnChangeDate = (Button)rootView.findViewById(R.id.changeDateBtn);
		btnChangeDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				datePickerDialog.show();
			}
		});

//		shinjilgee_turul_spinner = (Spinner) rootView
//				.findViewById(R.id.turul_spinner);
//		shinjilgee_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
//				R.array.shinjilgee_turul_array, android.R.layout.simple_spinner_item);
//		shinjilgee_turul_adapter
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		shinjilgee_turul_spinner.setAdapter(shinjilgee_turul_adapter);
//		shinjilgee_turul_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//				getList();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> adapterView) {
//
//			}
//		});

		adapter = new ShinjilgeeListAdapter(shinjilgeeID,urh_codeArray,shinjilgee_turulArray,ognooArray);
		setListAdapter(adapter);

		getList();

		return rootView;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		parentActivity = activity;
		((HomeActivity) activity).onSectionAttached(3);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}
	@Override
	public void onListItemClick(ListView l, View view, int position,
								long id) {
		// TODO Auto-generated method stub

		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("shinjilgeeID", shinjilgeeID.get(position));
		editor.commit();

		Log.d("on item click","asd");

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, GetShinjilgeeFragment.newInstance())
				.commit();
		Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub
//
//		SharedPreferences.Editor editor = prefs.edit();
//		editor.putInt("shinjilgeeID", shinjilgeeID.get(position));
//		editor.commit();
//
//		Log.d("on item click","asd");
//
//		FragmentManager fragmentManager = getFragmentManager();
//		fragmentManager.beginTransaction()
//				.replace(R.id.frame_container, GetShinjilgeeFragment.newInstance())
//				.commit();
		Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
	}


	private class ShinjilgeeListAdapter extends BaseAdapter {


		ArrayList<String> urh_codeArray = new ArrayList<String>();
		ArrayList<String> shinjilgee_turulArray = new ArrayList<String>();
		ArrayList<String> ognooArray = new ArrayList<String>();
		ArrayList<Integer> shinjilgeeID = new ArrayList<Integer>();

		public ShinjilgeeListAdapter(ArrayList<Integer> id,ArrayList<String> urh_code,ArrayList<String> shinjilgee_turul,ArrayList<String> ognoo) {
			this.urh_codeArray=urh_code;
			this.shinjilgee_turulArray=shinjilgee_turul;
			this.ognooArray=ognoo;
			this.shinjilgeeID=id;
		}
		@Override
		public int getCount() {
			return shinjilgeeID.size();
		}

		@Override
		public Object getItem(int i) {
			return null;
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// If we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.shinjilgee_list_row, null);
			}

			TextView idEdt = (TextView) convertView
					.findViewById(R.id.id);
			TextView urhCodeEdt = (TextView) convertView
					.findViewById(R.id.urh_code);
			TextView shinj_turulEdt = (TextView) convertView
					.findViewById(R.id.shinjilgee_turul);
			TextView ognooEdt = (TextView) convertView
					.findViewById(R.id.ognoo);
			idEdt.setText("Дугаар: "+shinjilgeeID.get(position).toString());
			urhCodeEdt.setText("Өрхийн код: "+urh_codeArray.get(position));
			shinj_turulEdt.setText("Шинжилгээ төрөл: "+shinjilgee_turulArray.get(position));
			ognooEdt.setText("Огноо: "+ognooArray.get(position));
			return convertView;
		}
	}

	private void getList() {
		if (mAuthTask != null) {
			return;
		}
			if(date!=null)
				date_filter = dateFormatter.format(date);
//			if(shinjilgee_turul_spinner.getSelectedItem()!=null)
//			type_filter = shinjilgee_turul_spinner.getSelectedItem().toString();

			new GetShinjilgee(parentActivity).execute();
	}
	class GetShinjilgee extends AsyncTask<String, String, String> {
		private Activity pActivity;

		public GetShinjilgee(Activity parent) {
			this.pActivity = parent;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("date_filter", date_filter));

//			params.add(new BasicNameValuePair("type_filter", type_filter));

			JSONObject json = jsonParser.makeHttpRequest(url_get_shinjilgee, "GET",
					params);

			try {
				int success = json.getInt("success");
				urh_codeArray.clear();
				shinjilgee_turulArray.clear();
				ognooArray.clear();
				shinjilgeeID.clear();

				if (success == 1) {
					for (int i = 0; i < json.getJSONArray("shinjilgee").length(); i++) {
						urh_codeArray.add(json.getJSONArray("shinjilgee")
								.getJSONObject(i).getString("urh_code"));
						shinjilgee_turulArray.add(json.getJSONArray("shinjilgee")
								.getJSONObject(i).getString("shinjilgee_turul"));
						ognooArray.add(json.getJSONArray("shinjilgee")
								.getJSONObject(i).getString("date"));
						shinjilgeeID.add(json.getJSONArray("shinjilgee")
								.getJSONObject(i).getInt("id"));
					}
					pActivity.runOnUiThread(new Runnable() {
						public void run() {
							setListAdapter(null);
							adapter = new ShinjilgeeListAdapter(shinjilgeeID,urh_codeArray,shinjilgee_turulArray,ognooArray);
							setListAdapter(adapter);
							adapter.notifyDataSetChanged();
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
}
