package com.example.mebms.mebms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import android.app.FragmentManager;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

public class NewEmchilgeeFragment extends Fragment {

    Spinner emchilgee_turul_spinner;
    Spinner shimegchteh_ner_spinner;

    ArrayAdapter<CharSequence> emchilgee_turul_adapter;
    ArrayAdapter<CharSequence> shimegchteh_ner_adapter;

    EditText urhCodeEdt;
    EditText urhEzenNerEdt;
    EditText bagEdt;
    EditText gazarEdt;
    EditText beldmelEdt;
    EditText latEdt;
    EditText lonEdt;

    EditText shimegchteh_honi_edt;
    EditText shimegchteh_yamaa_edt;
    EditText shimegchteh_uher_edt;
    EditText shimegchteh_mori_edt;
    EditText shimegchteh_temee_edt;

    Button saveBtn;

    Activity parentActivity;

    private EmchilgeeNew mAuthTask = null;

    private static String url_emchilgee_new = "http://"+Const.IP_ADDRESS1+":81/mebp/newemchilgee.php";
    JSONParser jsonParser = new JSONParser();

    public static final String PREFS_NAME = "MEBP";
    public SharedPreferences prefs;
    private int user_id;

    String urh_code;
    String urh_ezen_ner;
    String bag_horoo;
    String gazar_ner;
    String emchilgee_turul;
    String shimegchteh_ner;
    String beldmel_ner;
    String latitude;
    String longitude;

    String shimegchteh_honi;
    String shimegchteh_yamaa;
    String shimegchteh_uher;
    String shimegchteh_mori;
    String shimegchteh_temee;

    Date date;

    public static NewEmchilgeeFragment newInstance() {
        NewEmchilgeeFragment fragment = new NewEmchilgeeFragment();
        return fragment;
    }

    public NewEmchilgeeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_emchilgee, container,
                false);

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
                        .replace(R.id.frame_container, ListEmchilgeeFragment.newInstance())
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
        beldmelEdt = (EditText) rootView.findViewById(R.id.beldmel_ner_edt);
        latEdt = (EditText) rootView.findViewById(R.id.latitude_edt);
        lonEdt = (EditText) rootView.findViewById(R.id.longitude_edt);

        shimegchteh_honi_edt = (EditText) rootView.findViewById(R.id.shimegchteh_honi_edt);
        shimegchteh_yamaa_edt = (EditText) rootView.findViewById(R.id.shimegchteh_yamaa_edt);
        shimegchteh_uher_edt = (EditText) rootView.findViewById(R.id.shimegchteh_uher_edt);
        shimegchteh_mori_edt = (EditText) rootView.findViewById(R.id.shimegchteh_mori_edt);
        shimegchteh_temee_edt = (EditText) rootView.findViewById(R.id.shimegchteh_temee_edt);

        emchilgee_turul_spinner = (Spinner) rootView.findViewById(R.id.emchilgee_turul_spinner);
        emchilgee_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.emchilgee_turul_array, android.R.layout.simple_spinner_item);
        emchilgee_turul_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emchilgee_turul_spinner.setAdapter(emchilgee_turul_adapter);

        shimegchteh_ner_spinner = (Spinner) rootView.findViewById(R.id.shimegchteh_ner_spinner);
        shimegchteh_ner_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.shimegchteh_ner_array, android.R.layout.simple_spinner_item);
        shimegchteh_ner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shimegchteh_ner_spinner.setAdapter(shimegchteh_ner_adapter);

        saveBtn = (Button) rootView.findViewById(R.id.emchilgee_save);


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
                || gazarEdt.getText().toString().equals("") || beldmelEdt.getText().toString().equals("")|| lonEdt.getText().toString().equals("")|| latEdt.getText().toString().equals("")) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            urh_code = urhCodeEdt.getText().toString();
            urh_ezen_ner = urhEzenNerEdt.getText().toString();
            bag_horoo = bagEdt.getText().toString();
            gazar_ner = gazarEdt.getText().toString();
            emchilgee_turul = emchilgee_turul_spinner.getSelectedItem().toString();
            shimegchteh_ner = shimegchteh_ner_spinner.getSelectedItem().toString();
            beldmel_ner = beldmelEdt.getText().toString();
            longitude = lonEdt.getText().toString().equals("") ? "0" : lonEdt.getText().toString();
            latitude = latEdt.getText().toString().equals("") ? "0" : latEdt.getText().toString();

            shimegchteh_honi = shimegchteh_honi_edt.getText().toString().equals("") ? "0" : shimegchteh_honi_edt.getText().toString();
            shimegchteh_yamaa = shimegchteh_yamaa_edt.getText().toString().equals("") ? "0" : shimegchteh_yamaa_edt.getText().toString();
            shimegchteh_uher = shimegchteh_uher_edt.getText().toString().equals("") ? "0" : shimegchteh_uher_edt.getText().toString();
            shimegchteh_mori = shimegchteh_mori_edt.getText().toString().equals("") ? "0" : shimegchteh_mori_edt.getText().toString();
            shimegchteh_temee = shimegchteh_temee_edt.getText().toString().equals("") ? "0" : shimegchteh_temee_edt.getText().toString();

            date = new Date(Calendar.getInstance().getTimeInMillis());
            mAuthTask = new EmchilgeeNew(parentActivity);
            mAuthTask.execute();
        }
    }

    class EmchilgeeNew extends AsyncTask<String, String, String> {
        private Activity pActivity;
        public EmchilgeeNew(Activity parent) {
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
            params.add(new BasicNameValuePair("emchilgee_turul", emchilgee_turul));
            params.add(new BasicNameValuePair("shimegchteh_ner", shimegchteh_ner));
            params.add(new BasicNameValuePair("beldmel_ner", beldmel_ner));
            params.add(new BasicNameValuePair("latitude", latitude));
            params.add(new BasicNameValuePair("longitude", longitude));

            params.add(new BasicNameValuePair("shimegchteh_honi", shimegchteh_honi));
            params.add(new BasicNameValuePair("shimegchteh_yamaa", shimegchteh_yamaa));
            params.add(new BasicNameValuePair("shimegchteh_uher", shimegchteh_uher));
            params.add(new BasicNameValuePair("shimegchteh_mori", shimegchteh_mori));
            params.add(new BasicNameValuePair("shimegchteh_temee", shimegchteh_temee));

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d("date",df.format(date));
            params.add(new BasicNameValuePair("date", df.format(date)));
            Log.d("date", date.toString());

            JSONObject json = jsonParser.makeHttpRequest(url_emchilgee_new, "GET", params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(pActivity.getBaseContext(), "Амжилттай хадгалагдлаа.", Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, ListEmchilgeeFragment.newInstance())
                                    .commit();
                            ((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            ((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
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
        parentActivity = activity;
        ((HomeActivity) activity).onSectionAttached(3);
    }
}
