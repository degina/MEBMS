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
import android.content.Context;
import android.content.DialogInterface;
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

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.rey.material.widget.FloatingActionButton;

public class ListSergiileltFragment extends ListFragment implements OnItemClickListener {
    private OnFragmentInteractionListener mListener;

    ArrayList<String> urh_codeArray = new ArrayList<String>();
    ArrayList<String> sergiilelt_turulArray = new ArrayList<String>();
    ArrayList<String> ognooArray = new ArrayList<String>();
    ArrayList<Integer> sergiileltID = new ArrayList<Integer>();

    private static String url_get_sergiilelt = "http://10.0.2.2:81/mebp/sergiileltlist.php";
    private static String url_delete_sergiilelt = "http://10.0.2.2:81/mebp/deletesergiilelt.php";
    JSONParser jsonParser = new JSONParser();
    private GetSergiilelt mListAuthTask = null;
    private DeleteSergiilelt mDeleteAuthTask = null;

    private FloatingActionButton addSergiilelt;
    private Button btnChangeDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar calendar;
    public Date date;

    JSONObject json;

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
        datePickerDialog = new DatePickerDialog(parentActivity, null ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    final DatePicker picker = datePickerDialog.getDatePicker();
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
                    date=newDate.getTime();
                    btnChangeDate.setText(dateFormatter.format(newDate.getTime()));
                    getList();
                }
            }
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("Datepicker","cancel");
                    date=null;
                    btnChangeDate.setText(getResources().getString(R.string.date_filter));
                    getList();
                }
            }
        });
        btnChangeDate = (Button)rootView.findViewById(R.id.changeDateBtn);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        addSergiilelt = (FloatingActionButton ) rootView.findViewById(R.id.addButtonFloat);
        addSergiilelt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, NewSergiileltFragment.newInstance())
                        .commit();
            }
        });

        adapter = new SergiileltListAdapter(parentActivity,sergiileltID,urh_codeArray,sergiilelt_turulArray,ognooArray);
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


    private class SergiileltListAdapter extends BaseSwipeAdapter {

        private Context mContext;

        ArrayList<String> urh_codeArray = new ArrayList<String>();
        ArrayList<String> sergiilelt_turulArray = new ArrayList<String>();
        ArrayList<String> ognooArray = new ArrayList<String>();
        ArrayList<Integer> sergiileltID = new ArrayList<Integer>();

        public SergiileltListAdapter(Context mContext,ArrayList<Integer> id,ArrayList<String> urh_code,ArrayList<String> sergiilelt_turul,ArrayList<String> ognoo) {
            this.urh_codeArray=urh_code;
            this.sergiilelt_turulArray=sergiilelt_turul;
            this.ognooArray=ognoo;
            this.sergiileltID=id;
            this.mContext = mContext;
        }
        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
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
        public View generateView(int position, ViewGroup parent) {
            // If we weren't given a view, inflate one
            View v = LayoutInflater.from(mContext).inflate(R.layout.sergiilelt_list_row, null);

            final int p = position;

            SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {
//					YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                }
            });
            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
                }
            });
            return v;
        }
        @Override
        public void fillValues(int position, View convertView) {

            final int p= position;

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
            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("selected_pos",String.valueOf(p));
                    deleteRow(sergiileltID.get(p));
                }
            });
            convertView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("selected_pos",String.valueOf(p));
                    getActivity().getIntent().putExtra("selected_sergiilelt_id",sergiileltID.get(p));

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, EditSergiileltFragment.newInstance())
                            .commit();
                }
            });
        }
    }

    private void deleteRow(int sergiilelt_id) {
        if (mDeleteAuthTask != null) {
            return;
        }

        mDeleteAuthTask = new DeleteSergiilelt(parentActivity,sergiilelt_id);
        mDeleteAuthTask.execute();
    }
    class DeleteSergiilelt extends AsyncTask<String, String, String> {
        private Activity pActivity;
        private int sergiilelt_id;
        public DeleteSergiilelt(Activity parent,int sergiilelt_id) {
            this.sergiilelt_id=sergiilelt_id;
            this.pActivity = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sergiilelt_id", String.valueOf(sergiilelt_id)));

            json = jsonParser.makeHttpRequest(url_delete_sergiilelt, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            getList();
                            Toast.makeText(pActivity.getBaseContext(),
                                    "Амжилттай устаглаа.", Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, ListSergiileltFragment.newInstance())
                                    .commit();
                        }
                    });
                    getList();
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
            mDeleteAuthTask = null;
        }
    }
    private void getList() {
        if (mListAuthTask != null) {
            return;
        }
        if(date!=null)
            date_filter = dateFormatter.format(date);
        else{
            date_filter="";
        }
//			if(shinjilgee_turul_spinner.getSelectedItem()!=null)
//			type_filter = shinjilgee_turul_spinner.getSelectedItem().toString();

        mListAuthTask = new GetSergiilelt(parentActivity);
        mListAuthTask.execute();
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
                            adapter = new SergiileltListAdapter(pActivity,sergiileltID,urh_codeArray,sergiilelt_turulArray,ognooArray);
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
            mListAuthTask=null;
        }
    }
}
