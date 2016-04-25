package com.example.mebms.mebms;

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

public class NewShiljiltFragment extends Fragment {

    CheckBox mal_check;
    CheckBox buteegdehuun_check;

    LinearLayout hudaldsan_mal_layout;
    LinearLayout hudaldsan_buteegdehuun_layout;

    Spinner hudaldsan_buteegdehuun_ner_spinner;
    Spinner negj_spinner;

    ArrayAdapter<CharSequence> hudaldsan_buteegdehuun_ner_adapter;
    ArrayAdapter<CharSequence> negj_adapter;

    EditText urhCodeEdt;
    EditText urhEzenNerEdt;
    EditText bagEdt;
    EditText gazarEdt;
    EditText hudaldsanButeegdehuunTooEdt;
    EditText latEdt;
    EditText lonEdt;

    EditText hudaldsan_honi_edt;
    EditText hudaldsan_yamaa_edt;
    EditText hudaldsan_uher_edt;
    EditText hudaldsan_mori_edt;
    EditText hudaldsan_temee_edt;

    Button saveBtn;

    Activity parentActivity;

    private ShiljiltNew mAuthTask = null;

    private static String url_shiljilt_new = "http://10.0.2.2:81/mebp/newshiljilt.php";
    JSONParser jsonParser = new JSONParser();

    String urh_code;
    String urh_ezen_ner;
    String bag_horoo;
    String gazar_ner;
    String shiljilt_turul_mal;
    String shiljilt_turul_buteegdehuun;
    String hudaldsan_buteegdehuun_ner;
    String hudaldsan_buteegdehuun_too;
    String negj;
    String latitude;
    String longitude;

    String hudaldsan_honi;
    String hudaldsan_yamaa;
    String hudaldsan_uher;
    String hudaldsan_mori;
    String hudaldsan_temee;

    Date date;

    // TODO: Rename and change types and number of parameters
    public static NewShiljiltFragment newInstance() {
        NewShiljiltFragment fragment = new NewShiljiltFragment();
        return fragment;
    }

    public NewShiljiltFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_shiljilt, container,
                false);

        hudaldsan_mal_layout = (LinearLayout) rootView.findViewById(R.id.hudaldsan_mal_layout);
        hudaldsan_buteegdehuun_layout = (LinearLayout) rootView.findViewById(R.id.hudaldsan_buteegdehuun_layout);

        mal_check = (CheckBox) rootView.findViewById(R.id.mal_check);
        mal_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    hudaldsan_mal_layout.setVisibility(View.VISIBLE);
                } else {
                    hudaldsan_mal_layout.setVisibility(View.GONE);
                }
            }
        });

        buteegdehuun_check = (CheckBox) rootView.findViewById(R.id.buteegdehuun_check);
        buteegdehuun_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    hudaldsan_buteegdehuun_layout.setVisibility(View.VISIBLE);
                } else {
                    hudaldsan_buteegdehuun_layout.setVisibility(View.GONE);
                }
            }
        });

        urhCodeEdt = (EditText) rootView.findViewById(R.id.urh_code_edt);
        urhEzenNerEdt = (EditText) rootView.findViewById(R.id.urh_ezen_ner_edt);
        bagEdt = (EditText) rootView.findViewById(R.id.bag_horoo_edt);
        gazarEdt = (EditText) rootView.findViewById(R.id.gazar_ner_edt);
        hudaldsanButeegdehuunTooEdt = (EditText) rootView.findViewById(R.id.gazar_ner_edt);
        latEdt = (EditText) rootView.findViewById(R.id.latitude_edt);
        lonEdt = (EditText) rootView.findViewById(R.id.longitude_edt);

        hudaldsan_honi_edt = (EditText) rootView.findViewById(R.id.hudaldsan_honi_edt);
        hudaldsan_yamaa_edt = (EditText) rootView.findViewById(R.id.hudaldsan_yamaa_edt);
        hudaldsan_uher_edt = (EditText) rootView.findViewById(R.id.hudaldsan_uher_edt);
        hudaldsan_mori_edt = (EditText) rootView.findViewById(R.id.hudaldsan_mori_edt);
        hudaldsan_temee_edt = (EditText) rootView.findViewById(R.id.hudaldsan_temee_edt);


        hudaldsan_buteegdehuun_ner_spinner = (Spinner) rootView.findViewById(R.id.hudaldsan_buteegdehuun_ner_spinner);
        hudaldsan_buteegdehuun_ner_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.buteegdehuun_ner_array, android.R.layout.simple_spinner_item);
        hudaldsan_buteegdehuun_ner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hudaldsan_buteegdehuun_ner_spinner.setAdapter(hudaldsan_buteegdehuun_ner_adapter);


        negj_spinner = (Spinner) rootView.findViewById(R.id.negj_spinner);
        negj_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.negj_array, android.R.layout.simple_spinner_item);
        negj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        negj_spinner.setAdapter(negj_adapter);

        saveBtn = (Button) rootView.findViewById(R.id.shiljilt_save);


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
                || gazarEdt.getText().toString().equals("") || hudaldsanButeegdehuunTooEdt.getText().toString().equals("")|| lonEdt.getText().toString().equals("")|| latEdt.getText().toString().equals("")) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү?", Toast.LENGTH_LONG).show();
        }else {
            urh_code = urhCodeEdt.getText().toString();
            urh_ezen_ner = urhEzenNerEdt.getText().toString();
            bag_horoo = bagEdt.getText().toString();
            gazar_ner = gazarEdt.getText().toString();
            if (mal_check.isChecked())
                shiljilt_turul_mal = "true";
            else
                shiljilt_turul_mal = "false";
            if (buteegdehuun_check.isChecked())
                shiljilt_turul_buteegdehuun = "true";
            else
                shiljilt_turul_buteegdehuun = "false";
            hudaldsan_buteegdehuun_ner = hudaldsan_buteegdehuun_ner_spinner.getSelectedItem().toString();
            hudaldsan_buteegdehuun_too = hudaldsanButeegdehuunTooEdt.getText().toString();
            negj = negj_spinner.getSelectedItem().toString();

            hudaldsan_honi = hudaldsan_honi_edt.getText().toString().equals("") ? "0" : hudaldsan_honi_edt.getText().toString();
            hudaldsan_yamaa = hudaldsan_yamaa_edt.getText().toString().equals("") ? "0" : hudaldsan_yamaa_edt.getText().toString();
            hudaldsan_uher = hudaldsan_uher_edt.getText().toString().equals("") ? "0" : hudaldsan_uher_edt.getText().toString();
            hudaldsan_mori = hudaldsan_mori_edt.getText().toString().equals("") ? "0" : hudaldsan_mori_edt.getText().toString();
            hudaldsan_temee = hudaldsan_temee_edt.getText().toString().equals("") ? "0" : hudaldsan_temee_edt.getText().toString();

            date = new Date(Calendar.getInstance().getTimeInMillis());
            longitude = lonEdt.getText().toString().equals("") ? "0" : lonEdt.getText().toString();
            latitude = latEdt.getText().toString().equals("") ? "0" : latEdt.getText().toString();
            mAuthTask = new ShiljiltNew(parentActivity);
            mAuthTask.execute();
        }
    }

    class ShiljiltNew extends AsyncTask<String, String, String> {
        private Activity pActivity;
        public ShiljiltNew(Activity parent) {
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
            params.add(new BasicNameValuePair("urh_ezen_ner", urh_ezen_ner));
            params.add(new BasicNameValuePair("bag_horoo", bag_horoo));
            params.add(new BasicNameValuePair("gazar_ner", gazar_ner));
            params.add(new BasicNameValuePair("shiljilt_turul_mal", shiljilt_turul_mal));
            params.add(new BasicNameValuePair("shiljilt_turul_buteegdehuun", shiljilt_turul_buteegdehuun));
            params.add(new BasicNameValuePair("hudaldsan_buteegdehuun_ner", hudaldsan_buteegdehuun_ner));
            params.add(new BasicNameValuePair("hudaldsan_buteegdehuun_too", hudaldsan_buteegdehuun_too));
            params.add(new BasicNameValuePair("negj", negj));
            params.add(new BasicNameValuePair("latitude", latitude));
            params.add(new BasicNameValuePair("longitude", longitude));

            params.add(new BasicNameValuePair("hudaldsan_honi", hudaldsan_honi));
            params.add(new BasicNameValuePair("hudaldsan_yamaa", hudaldsan_yamaa));
            params.add(new BasicNameValuePair("hudaldsan_uher", hudaldsan_uher));
            params.add(new BasicNameValuePair("hudaldsan_mori", hudaldsan_mori));
            params.add(new BasicNameValuePair("hudaldsan_temee", hudaldsan_temee));


            params.add(new BasicNameValuePair("date", date.toString()));
            Log.d("date", date.toString());

            JSONObject json = jsonParser.makeHttpRequest(url_shiljilt_new, "GET", params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(pActivity.getBaseContext(), "Амжилттай хадгалагдлаа.", Toast.LENGTH_LONG).show();
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
        ((HomeActivity) activity).onSectionAttached(5);
    }
}
