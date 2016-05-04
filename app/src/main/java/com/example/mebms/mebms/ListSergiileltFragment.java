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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListSergiileltFragment extends ListFragment implements OnItemClickListener {
    private OnFragmentInteractionListener mListener;

    ArrayList<String> urh_codeArray = new ArrayList<String>();
    ArrayList<String> sergiilelt_turulArray = new ArrayList<String>();
    ArrayList<String> ognooArray = new ArrayList<String>();
    ArrayList<Integer> sergiileltID = new ArrayList<Integer>();

    private static String url_get_sergiilelt = "http://10.0.2.2:81/mebp/sergiileltlist.php";
    JSONParser jsonParser = new JSONParser();
    private GetSergiilelt mAuthTask = null;

    private Button btnChangeDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar calendar;
    public Date date;

    SergiileltListAdapter adapter;
    Activity parentActivity;
    String date_filter ="";

    public static ListSergiileltFragment newInstance() {
        ListSergiileltFragment fragment = new ListSergiileltFragment();
        return fragment;
    }

    public ListSergiileltFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sergiilelt_list,
                container, false);
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

        adapter = new SergiileltListAdapter(sergiileltID,urh_codeArray,sergiilelt_turulArray,ognooArray);
        setListAdapter(adapter);
        getList();
        return rootView;
    }

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

        getActivity().getIntent().putExtra("selected_sergiilelt_id",sergiileltID.get(position));


        Log.d("on item click","asd");

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, GetSergiileltFragment.newInstance())
                .commit();
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
//		Log.d("on item click","asd");
//
//		FragmentManager fragmentManager = getFragmentManager();
//		fragmentManager.beginTransaction()
//				.replace(R.id.frame_container, GetShinjilgeeFragment.newInstance())
//				.commit();
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }


    private class SergiileltListAdapter extends BaseAdapter {

        ArrayList<String> urh_codeArray = new ArrayList<String>();
        ArrayList<String> sergiilelt_turulArray = new ArrayList<String>();
        ArrayList<String> ognooArray = new ArrayList<String>();
        ArrayList<Integer> sergiileltID = new ArrayList<Integer>();

        public SergiileltListAdapter(ArrayList<Integer> id,ArrayList<String> urh_code,ArrayList<String> sergiilelt_turul,ArrayList<String> ognoo) {
            this.urh_codeArray=urh_code;
            this.sergiilelt_turulArray=sergiilelt_turul;
            this.ognooArray=ognoo;
            this.sergiileltID=id;
        }
        @Override
        public int getCount() {
            return sergiileltID.size();
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
                        R.layout.sergiilelt_list_row, null);
            }

            TextView idEdt = (TextView) convertView
                    .findViewById(R.id.id);
            TextView urhCodeEdt = (TextView) convertView
                    .findViewById(R.id.urh_code);
            TextView shinj_turulEdt = (TextView) convertView
                    .findViewById(R.id.sergiilelt_turul);
            TextView ognooEdt = (TextView) convertView
                    .findViewById(R.id.ognoo);
            idEdt.setText("Дугаар: "+sergiileltID.get(position).toString());
            urhCodeEdt.setText("Өрхийн код: "+urh_codeArray.get(position));
            shinj_turulEdt.setText("Шинжилгээний төрөл: "+sergiilelt_turulArray.get(position));
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

        new GetSergiilelt(parentActivity).execute();
    }
    class GetSergiilelt extends AsyncTask<String, String, String> {
        private Activity pActivity;

        public GetSergiilelt(Activity parent) {
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

            JSONObject json = jsonParser.makeHttpRequest(url_get_sergiilelt, "GET",
                    params);

            try {
                int success = json.getInt("success");
                urh_codeArray.clear();
                sergiilelt_turulArray.clear();
                ognooArray.clear();
                sergiileltID.clear();

                if (success == 1) {
                    for (int i = 0; i < json.getJSONArray("sergiilelt").length(); i++) {
                        urh_codeArray.add(json.getJSONArray("sergiilelt")
                                .getJSONObject(i).getString("urh_code"));
                        sergiilelt_turulArray.add(json.getJSONArray("sergiilelt")
                                .getJSONObject(i).getString("sergiilelt_turul"));
                        ognooArray.add(json.getJSONArray("sergiilelt")
                                .getJSONObject(i).getString("date"));
                        sergiileltID.add(json.getJSONArray("sergiilelt")
                                .getJSONObject(i).getInt("id"));
                    }
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            setListAdapter(null);
                            adapter = new SergiileltListAdapter(sergiileltID,urh_codeArray,sergiilelt_turulArray,ognooArray);
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
