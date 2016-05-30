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
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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


public class ListUvchinFragment extends ListFragment implements OnItemClickListener {
    private OnFragmentInteractionListener mListener;

    ArrayList<String> urh_codeArray = new ArrayList<String>();
    ArrayList<String> uvchin_turulArray = new ArrayList<String>();
    ArrayList<String> ognooArray = new ArrayList<String>();
    ArrayList<Integer> uvchinID = new ArrayList<Integer>();

    private static String url_get_uvchin = "http://10.0.2.2:81/mebp/uvchinlist.php";
    private static String url_delete_uvchin = "http://10.0.2.2:81/mebp/deleteuvchin.php";
    JSONParser jsonParser = new JSONParser();
    private GetUvchin mListAuthTask = null;
    private DeleteUvchin mDeleteAuthTask = null;

    private FloatingActionButton addUvchin;
    private Button btnChangeDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar calendar;
    public Date date;

    public static final String PREFS_NAME = "MEBP";
    public SharedPreferences prefs;
    private int user_id;


    JSONObject json;

    ListUvchinAdapter adapter;
    Activity parentActivity;
    String date_filter ="";

    public static ListUvchinFragment newInstance() {
        ListUvchinFragment fragment = new ListUvchinFragment();
        return fragment;
    }

    public ListUvchinFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uvchin_list,
                container, false);

        prefs = parentActivity.getSharedPreferences(PREFS_NAME, 0);
        user_id = prefs.getInt("userId", 0);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(parentActivity, null ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.filter), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    final DatePicker picker = datePickerDialog.getDatePicker();
                    Log.d("Datepicker","set"+picker.getYear()+picker.getMonth()+picker.getDayOfMonth());
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
                    date=newDate.getTime();
                    btnChangeDate.setText(dateFormatter.format(newDate.getTime()));
                    getList();
                }
            }
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.back), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("Datepicker","cancel");
                    date=null;
                    btnChangeDate.setText(getResources().getString(R.string.date_filter));
                    getList();
                }
            }
        });

        addUvchin = (FloatingActionButton ) rootView.findViewById(R.id.addButtonFloat);
        addUvchin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, NewUvchinFragment.newInstance())
                        .commit();
            }
        });

        btnChangeDate = (Button)rootView.findViewById(R.id.changeDateBtn);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });


        adapter = new ListUvchinAdapter(parentActivity,uvchinID,urh_codeArray,uvchin_turulArray,ognooArray);
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

        getActivity().getIntent().putExtra("selected_uvchin_id",uvchinID.get(position));



        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, GetUvchinFragment.newInstance())
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

    private class ListUvchinAdapter extends BaseSwipeAdapter {

        private Context mContext;

        ArrayList<String> urh_codeArray = new ArrayList<String>();
        ArrayList<String> uvchin_turulArray = new ArrayList<String>();
        ArrayList<String> ognooArray = new ArrayList<String>();
        ArrayList<Integer> uvchinID = new ArrayList<Integer>();

        public ListUvchinAdapter(Context mContext,ArrayList<Integer> id,ArrayList<String> urh_code,ArrayList<String> uvchin_turul,ArrayList<String> ognoo) {
            this.urh_codeArray=urh_code;
            this.uvchin_turulArray=uvchin_turul;
            this.ognooArray=ognoo;
            this.uvchinID=id;
            this.mContext = mContext;
        }
        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }
        @Override
        public int getCount() {
            return uvchinID.size();
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.uvchin_list_row, null);

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
                    .findViewById(R.id.uvchin_turul);
            TextView ognooEdt = (TextView) convertView
                    .findViewById(R.id.ognoo);
            idEdt.setText("Дугаар: "+uvchinID.get(position).toString());
            urhCodeEdt.setText("Өрхийн код: "+urh_codeArray.get(position));
            shinj_turulEdt.setText("Өвчний төрөл: "+uvchin_turulArray.get(position));
            ognooEdt.setText("Огноо: "+ognooArray.get(position));
            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("selected_pos",String.valueOf(p));
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    deleteRow(uvchinID.get(p));
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                    builder.setMessage("Та устахыг хүсч байна уу??").setPositiveButton(R.string.ok, dialogClickListener)
                            .setNegativeButton(R.string.back, dialogClickListener).show();
                }
            });
            convertView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("selected_pos",String.valueOf(p));
                    getActivity().getIntent().putExtra("selected_uvchin_id",uvchinID.get(p));

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, EditUvchinFragment.newInstance())
                            .commit();
                }
            });
        }
    }

    private void deleteRow(int uvchin_id) {
        if (mDeleteAuthTask != null) {
            return;
        }

        mDeleteAuthTask = new DeleteUvchin(parentActivity,uvchin_id);
        mDeleteAuthTask.execute();
    }
    class DeleteUvchin extends AsyncTask<String, String, String> {
        private Activity pActivity;
        private int uvchin_id;
        public DeleteUvchin(Activity parent,int uvchin_id) {
            this.uvchin_id=uvchin_id;
            this.pActivity = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uvchin_id", String.valueOf(uvchin_id)));

            json = jsonParser.makeHttpRequest(url_delete_uvchin, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            getList();
                            Toast.makeText(pActivity.getBaseContext(),
                                    "Амжилттай устгалаа.", Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, ListUvchinFragment.newInstance())
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
        Log.d("Datepicker","1");
        if (mListAuthTask != null) {
            Log.d("Datepicker","2");
            return;
        }
        if(date!=null)
            date_filter = dateFormatter.format(date);
        else{
            date_filter="";
        }
        Log.d("Datepicker",date_filter);
//			if(shinjilgee_turul_spinner.getSelectedItem()!=null)
//			type_filter = shinjilgee_turul_spinner.getSelectedItem().toString();


        mListAuthTask = new GetUvchin(parentActivity);
        mListAuthTask.execute();
    }
    class GetUvchin extends AsyncTask<String, String, String> {
        private Activity pActivity;

        public GetUvchin(Activity parent) {
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
            params.add(new BasicNameValuePair("user_id", String.valueOf(user_id)));

            JSONObject json = jsonParser.makeHttpRequest(url_get_uvchin, "GET",
                    params);

            try {
                int success = json.getInt("success");
                urh_codeArray.clear();
                uvchin_turulArray.clear();
                ognooArray.clear();
                uvchinID.clear();

                if (success == 1) {
                    for (int i = 0; i < json.getJSONArray("uvchin").length(); i++) {
                        urh_codeArray.add(json.getJSONArray("uvchin")
                                .getJSONObject(i).getString("urh_code"));
                        uvchin_turulArray.add(json.getJSONArray("uvchin")
                                .getJSONObject(i).getString("uvchin_turul"));
                        ognooArray.add(json.getJSONArray("uvchin")
                                .getJSONObject(i).getString("date"));
                        uvchinID.add(json.getJSONArray("uvchin")
                                .getJSONObject(i).getInt("id"));
                    }
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            setListAdapter(null);
                            adapter = new ListUvchinAdapter(pActivity,uvchinID,urh_codeArray,uvchin_turulArray,ognooArray);
                            setListAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else if(success == 0) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(pActivity.getBaseContext(),
                                    "Мэдээлэл алга!", Toast.LENGTH_LONG).show();
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
