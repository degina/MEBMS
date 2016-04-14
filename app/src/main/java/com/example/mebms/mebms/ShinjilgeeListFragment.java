package com.example.mebms.mebms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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
	

	private OnFragmentInteractionListener mListener;

	private ArrayAdapter<String> adapter;
	List<String> listArray;
	private static String url_get_shinjilgee = "http://10.0.2.2:81/mbms/getshinjilgee.php";
	JSONParser jsonParser = new JSONParser();
	ArrayList<Integer> golomtID = new ArrayList<Integer>();

	Activity parentActivity;
	
	public static ShinjilgeeListFragment newInstance() {
		ShinjilgeeListFragment fragment = new ShinjilgeeListFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public ShinjilgeeListFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shinjilgee_list,
				container, false);

		new GetShinjilgee(parentActivity).execute();
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
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
			params.add(new BasicNameValuePair("list", "all"));

			JSONObject json = jsonParser.makeHttpRequest(url_get_shinjilgee, "GET",
					params);

			try {
				int success = json.getInt("success");

				if (success == 1) {
					listArray = new ArrayList<String>();
					for (int i = 0; i < json.getJSONArray("golomt").length(); i++) {
						listArray.add(json.getJSONArray("golomt")
								.getJSONObject(i).getString("name"));

						golomtID.add(json.getJSONArray("golomt")
								.getJSONObject(i).getInt("id"));
					}
					pActivity.runOnUiThread(new Runnable() {
						public void run() {
							adapter = new ArrayAdapter<String>(pActivity
									.getBaseContext(),
									android.R.layout.simple_spinner_item,
									listArray);
					        setListAdapter(adapter);
					        getListView().setOnItemClickListener(ShinjilgeeListFragment.this);
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
