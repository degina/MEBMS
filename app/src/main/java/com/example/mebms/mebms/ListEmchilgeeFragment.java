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
public class ListEmchilgeeFragment extends ListFragment implements OnItemClickListener {

    private OnFragmentInteractionListener mListener;

    ArrayList<String> urh_codeArray = new ArrayList<String>();
    ArrayList<String> ognooArray = new ArrayList<String>();
    ArrayList<Integer> emchilgeeID = new ArrayList<Integer>();
    ArrayList<String> emchilgee_turulArray = new ArrayList<String>();

    private static String url_get_emchilgee = "http://"+Const.IP_ADDRESS1+":81/mebp/emchilgeelist.php";
    private static String url_delete_emchilgee = "http://"+Const.IP_ADDRESS1+":81/mebp/deleteemchilgee.php";
    JSONParser jsonParser = new JSONParser();
    private GetEmchilgee mListAuthTask = null;
    private DeleteEmchilgee mDeleteAuthTask = null;

    private FloatingActionButton addEmchilgee;
    private Button btnChangeDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar calendar;
    public Date date;

    public static final String PREFS_NAME = "MEBP";
    public SharedPreferences prefs;
    private int user_id;

    JSONObject json;

    EmchilgeeListAdapter adapter;

    Activity parentActivity;

    String date_filter ="";

    public static ListEmchilgeeFragment newInstance() {
        ListEmchilgeeFragment fragment = new ListEmchilgeeFragment();
        return fragment;
    }

    public ListEmchilgeeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emchilgee_list,
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
        btnChangeDate = (Button)rootView.findViewById(R.id.changeDateBtn);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        addEmchilgee = (FloatingActionButton ) rootView.findViewById(R.id.addButtonFloat);
        addEmchilgee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, NewEmchilgeeFragment.newInstance())
                        .commit();
            }
        });

        adapter = new EmchilgeeListAdapter(parentActivity,emchilgeeID,urh_codeArray,emchilgee_turulArray,ognooArray);
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
    public void onListItemClick(ListView l, View view, int position, long id) {
        getActivity().getIntent().putExtra("selected_emchilgee_id",emchilgeeID.get(position));

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, GetEmchilgeeFragment.newInstance())
                .commit();
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    private class EmchilgeeListAdapter extends BaseSwipeAdapter {

        private Context mContext;

        ArrayList<String> urh_codeArray = new ArrayList<String>();
        ArrayList<String> emchilgee_turulArray = new ArrayList<String>();
        ArrayList<String> ognooArray = new ArrayList<String>();
        ArrayList<Integer> emchilgeeID = new ArrayList<Integer>();

        public EmchilgeeListAdapter(Context mContext,ArrayList<Integer> id,ArrayList<String> urh_code,ArrayList<String> emchilgee_turul,ArrayList<String> ognoo) {
            this.urh_codeArray=urh_code;
            this.emchilgee_turulArray=emchilgee_turul;
            this.ognooArray=ognoo;
            this.emchilgeeID=id;
            this.mContext = mContext;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }
        @Override
        public int getCount() {
            return emchilgeeID.size();
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.emchilgee_list_row, null);

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
            TextView emchilgee_turulEdt = (TextView) convertView
                    .findViewById(R.id.emchilgee_turul);
            TextView ognooEdt = (TextView) convertView
                    .findViewById(R.id.ognoo);
            idEdt.setText("Дугаар: "+emchilgeeID.get(position).toString());
            urhCodeEdt.setText("Өрхийн код: "+urh_codeArray.get(position));
            emchilgee_turulEdt.setText("Эмчилгээний төрөл: "+emchilgee_turulArray.get(position));
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
                                    deleteRow(emchilgeeID.get(p));
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
                    getActivity().getIntent().putExtra("selected_emchilgee_id",emchilgeeID.get(p));

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, EditEmchilgeeFragment.newInstance())
                            .commit();
                }
            });
        }
    }

    private void deleteRow(int emchilgee_id) {
        if (mDeleteAuthTask != null) {
            return;
        }

        mDeleteAuthTask = new DeleteEmchilgee(parentActivity,emchilgee_id);
        mDeleteAuthTask.execute();
    }
    class DeleteEmchilgee extends AsyncTask<String, String, String> {
        private Activity pActivity;
        private int emchilgee_id;
        public DeleteEmchilgee(Activity parent,int emchilgee_id) {
            this.emchilgee_id = emchilgee_id;
            this.pActivity = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("emchilgee_id", String.valueOf(emchilgee_id)));

            json = jsonParser.makeHttpRequest(url_delete_emchilgee, "GET",
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
                                    .replace(R.id.frame_container, ListEmchilgeeFragment.newInstance())
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

        mListAuthTask = new GetEmchilgee(parentActivity);
        mListAuthTask.execute();
    }
    class GetEmchilgee extends AsyncTask<String, String, String> {
        private Activity pActivity;

        public GetEmchilgee(Activity parent) {
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

            json = jsonParser.makeHttpRequest(url_get_emchilgee, "GET", params);

            try {
                int success = json.getInt("success");
                urh_codeArray.clear();
                emchilgee_turulArray.clear();
                ognooArray.clear();
                emchilgeeID.clear();

                if (success == 1) {
                    for (int i = 0; i < json.getJSONArray("emchilgee").length(); i++) {
                        urh_codeArray.add(json.getJSONArray("emchilgee")
                                .getJSONObject(i).getString("urh_code"));
                        emchilgee_turulArray.add(json.getJSONArray("emchilgee")
                                .getJSONObject(i).getString("emchilgee_turul"));
                        ognooArray.add(json.getJSONArray("emchilgee")
                                .getJSONObject(i).getString("date"));
                        emchilgeeID.add(json.getJSONArray("emchilgee")
                                .getJSONObject(i).getInt("id"));
                    }
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            setListAdapter(null);
                            adapter = new EmchilgeeListAdapter(pActivity,emchilgeeID,urh_codeArray,emchilgee_turulArray,ognooArray);
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
            mListAuthTask = null;
        }
    }
}
