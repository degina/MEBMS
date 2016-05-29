package com.example.mebms.mebms;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class GetShiljiltFragment extends Fragment {

    private GetShiljilt mAuthTask = null;

    TextView shiljilt_turul_text;
    TextView hudaldsan_buteegdehuun_ner_text;
    TextView hudaldsan_buteegdehuun_too_text;
    TextView negj_text;

    TextView hudaldsan_honi_text;
    TextView hudaldsan_yamaa_text;
    TextView hudaldsan_uher_text;
    TextView hudaldsan_mori_text;
    TextView hudaldsan_temee_text;

    TextView date_text;
    TextView urh_code_text;
    TextView urh_ezen_ner_text;
    TextView bag_text;
    TextView gazar_text;
    TextView lat_text;
    TextView lon_text;

    LinearLayout hudaldsan_mal_layout;
    LinearLayout hudaldsan_buteegdehuun_layout;

    Button editBtn;
    Button deleteBtn;

    Activity parentActivity;

    private static String url_get_shiljilt = "http://10.0.2.2:81/mebp/getshiljilt.php";
    JSONParser jsonParser = new JSONParser();

    int shiljilt_id;

    JSONObject json;

    public static GetShiljiltFragment newInstance() {
        GetShiljiltFragment fragment = new GetShiljiltFragment();
        return fragment;
    }

    public GetShiljiltFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_shiljilt, container, false);

        urh_code_text = (TextView) rootView.findViewById(R.id.urh_code_text);
        urh_ezen_ner_text = (TextView) rootView.findViewById(R.id.urh_ezen_ner_text);
        bag_text = (TextView) rootView.findViewById(R.id.bag_horoo_text);
        gazar_text = (TextView) rootView.findViewById(R.id.gazar_ner_text);
        lat_text = (TextView) rootView.findViewById(R.id.latitude_text);
        lon_text = (TextView) rootView.findViewById(R.id.longitude_text);
        date_text = (TextView) rootView.findViewById(R.id.date_text);

        shiljilt_turul_text = (TextView) rootView.findViewById(R.id.shiljilt_turul_text);
        hudaldsan_buteegdehuun_ner_text = (TextView) rootView.findViewById(R.id.hudaldsan_buteegdehuun_ner_text);
        hudaldsan_buteegdehuun_too_text = (TextView) rootView.findViewById(R.id.hudaldsan_buteegdehuun_too_text);
        negj_text = (TextView) rootView.findViewById(R.id.negj_text);

        hudaldsan_honi_text = (TextView) rootView.findViewById(R.id.hudaldsan_honi_text);
        hudaldsan_yamaa_text = (TextView) rootView.findViewById(R.id.hudaldsan_yamaa_text);
        hudaldsan_uher_text = (TextView) rootView.findViewById(R.id.hudaldsan_uher_text);
        hudaldsan_mori_text = (TextView) rootView.findViewById(R.id.hudaldsan_mori_text);
        hudaldsan_temee_text = (TextView) rootView.findViewById(R.id.hudaldsan_temee_text);

        hudaldsan_mal_layout = (LinearLayout) rootView.findViewById(R.id.hudaldsan_mal_layout);
        hudaldsan_buteegdehuun_layout = (LinearLayout) rootView.findViewById(R.id.hudaldsan_buteegdehuun_layout);

        editBtn = (Button) rootView.findViewById(R.id.edit_shiljilt);
        deleteBtn = (Button) rootView.findViewById(R.id.delete_shiljilt);

        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, EditShiljiltFragment.newInstance())
                        .commit();
            }
        });
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        getInfo();

        return rootView;
    }

    private void getInfo() {
        if (mAuthTask != null) {
            return;
        }
        shiljilt_id =getActivity().getIntent().getIntExtra("selected_shiljilt_id",0);

        if(shiljilt_id==0) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            mAuthTask = new GetShiljilt(parentActivity);
            mAuthTask.execute();
        }
    }
    class GetShiljilt extends AsyncTask<String, String, String> {
        private Activity pActivity;
        public GetShiljilt(Activity parent) {
            this.pActivity = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sergiilelt_id", String.valueOf(shiljilt_id)));

            json = jsonParser.makeHttpRequest(url_get_shiljilt, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {

                            try {
                                urh_code_text.setText(json.getString("urh_code"));
                                urh_ezen_ner_text.setText(json.getString("urh_ezen_ner"));
                                bag_text.setText(json.getString("bag_horoo"));
                                gazar_text.setText(json.getString("gazar_ner"));

                                shiljilt_turul_text.setText(json.getString("shiljilt_turul"));
                                if(json.getString("shiljilt_turul_mal").equals(true)) {

                                    hudaldsan_honi_text.setText(json.getString("hudaldsan_honi"));
                                    hudaldsan_yamaa_text.setText(json.getString("hudaldsan_yamaa"));
                                    hudaldsan_uher_text.setText(json.getString("hudaldsan_uher"));
                                    hudaldsan_mori_text.setText(json.getString("hudaldsan_mori"));
                                    hudaldsan_temee_text.setText(json.getString("hudaldsan_temee"));

                                    hudaldsan_mal_layout.setVisibility(View.VISIBLE);
                                }
                                if(json.getString("shiljilt_turul_buteegdehuun").equals(true)) {
                                    hudaldsan_buteegdehuun_ner_text.setText(json.getString("hudaldsan_buteegdehuun_ner_text"));
                                    hudaldsan_buteegdehuun_too_text.setText(json.getString("hudaldsan_buteegdehuun_too_text"));
                                    negj_text.setText(json.getString("negj"));

                                    hudaldsan_buteegdehuun_layout.setVisibility(View.VISIBLE);
                                }

                                lat_text.setText(json.getString("latitude"));
                                lon_text.setText(json.getString("longitude"));
                                date_text.setText(json.getString("date"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
        ((HomeActivity) activity).onSectionAttached(4);
    }
}