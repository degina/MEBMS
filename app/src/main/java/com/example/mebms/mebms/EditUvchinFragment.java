package com.example.mebms.mebms;

/**
 * Created by user on 5/4/2016.
 */

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

public class EditUvchinFragment extends Fragment {

    CheckBox zuvlusun_check;
    CheckBox ustgasan_check;
    CheckBox emchilsen_check;
    CheckBox horogdson_check;

    LinearLayout zuvlusun_layout;
    LinearLayout ustgasan_layout;
    LinearLayout emchilsen_layout;
    LinearLayout horogdson_layout;
    LinearLayout ustgah_arga_layout;

    Spinner uvchin_turul_spinner;
    Spinner uvchin_ner_spinner;
    Spinner ustgah_arga_spinner;

    ArrayAdapter<CharSequence> uvchin_turul_adapter;
    ArrayAdapter<CharSequence> uvchin_ner_adapter;
    ArrayAdapter<CharSequence> ustgah_arga_adapter;

    EditText urhCodeEdt;
    EditText urhEzenNerEdt;
    EditText bagEdt;
    EditText gazarEdt;
    EditText latEdt;
    EditText lonEdt;

    EditText zuvlusun_h_edt;
    EditText zuvlusun_y_edt;
    EditText zuvlusun_u_edt;
    EditText zuvlusun_m_edt;
    EditText zuvlusun_t_edt;

    EditText ustgasan_h_edt;
    EditText ustgasan_y_edt;
    EditText ustgasan_u_edt;
    EditText ustgasan_m_edt;
    EditText ustgasan_t_edt;

    EditText emchilsen_h_edt;
    EditText emchilsen_y_edt;
    EditText emchilsen_u_edt;
    EditText emchilsen_m_edt;
    EditText emchilsen_t_edt;

    EditText horogdson_h_edt;
    EditText horogdson_y_edt;
    EditText horogdson_u_edt;
    EditText horogdson_m_edt;
    EditText horogdson_t_edt;

    Button saveBtn;

    Activity parentActivity;

    private EditUvchin editAuthTask = null;
    private GetUvchin getAuthTask = null;

    int uvchin_id;

    private static String url_save_uvchin = "http://10.0.2.2:81/mebp/saveuvchin.php";
    private static String url_get_uvchin = "http://10.0.2.2:81/mebp/getuvchin.php";
    JSONParser jsonParser = new JSONParser();

    JSONObject json;

    String urh_code;
    String urh_ezen_ner;
    String bag_horoo;
    String gazar_ner;
    String uvchin_turul;
    String uvchin_ner;
    String ustgah_arga;
    String latitude;
    String longitude;

    String zuvlusun_h;
    String zuvlusun_y;
    String zuvlusun_u;
    String zuvlusun_m;
    String zuvlusun_t;

    String ustgasan_h;
    String ustgasan_y;
    String ustgasan_u;
    String ustgasan_m;
    String ustgasan_t;

    String emchilsen_h;
    String emchilsen_y;
    String emchilsen_u;
    String emchilsen_m;
    String emchilsen_t;

    String horogdson_h;
    String horogdson_y;
    String horogdson_u;
    String horogdson_m;
    String horogdson_t;

    Date date;

    // TODO: Rename and change types and number of parameters
    public static EditUvchinFragment newInstance() {
        EditUvchinFragment fragment = new EditUvchinFragment();
        return fragment;
    }

    public EditUvchinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_uvchin, container,
                false);

        zuvlusun_layout = (LinearLayout) rootView.findViewById(R.id.zuvlusun_layout);
        ustgasan_layout = (LinearLayout) rootView.findViewById(R.id.ustgasan_layout);
        emchilsen_layout = (LinearLayout) rootView.findViewById(R.id.emchilsen_layout);
        horogdson_layout = (LinearLayout) rootView.findViewById(R.id.horogdson_layout);
        ustgah_arga_layout = (LinearLayout) rootView.findViewById(R.id.ustgah_arga_layout);

        zuvlusun_check = (CheckBox) rootView.findViewById(R.id.zuvlusun_check);
        zuvlusun_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    zuvlusun_layout.setVisibility(View.VISIBLE);
                } else {
                    zuvlusun_layout.setVisibility(View.GONE);
                }
            }
        });
        ustgasan_check = (CheckBox) rootView.findViewById(R.id.ustgasan_check);
        ustgasan_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    ustgasan_layout.setVisibility(View.VISIBLE);
                    ustgah_arga_layout.setVisibility(View.VISIBLE);
                } else {
                    ustgasan_layout.setVisibility(View.GONE);
                    ustgah_arga_layout.setVisibility(View.GONE);
                }
            }
        });
        emchilsen_check = (CheckBox) rootView.findViewById(R.id.emchilsen_check);
        emchilsen_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    emchilsen_layout.setVisibility(View.VISIBLE);
                } else {
                    emchilsen_layout.setVisibility(View.GONE);
                }
            }
        });
        horogdson_check = (CheckBox) rootView.findViewById(R.id.horogdson_check);
        horogdson_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    horogdson_layout.setVisibility(View.VISIBLE);
                } else {
                    horogdson_layout.setVisibility(View.GONE);
                }
            }
        });

        urhCodeEdt = (EditText) rootView.findViewById(R.id.urh_code_edt);
        urhEzenNerEdt = (EditText) rootView.findViewById(R.id.urh_ezen_ner_edt);
        bagEdt = (EditText) rootView.findViewById(R.id.bag_horoo_edt);
        gazarEdt = (EditText) rootView.findViewById(R.id.gazar_ner_edt);
        latEdt = (EditText) rootView.findViewById(R.id.latitude_edt);
        lonEdt = (EditText) rootView.findViewById(R.id.longitude_edt);

        zuvlusun_h_edt = (EditText) rootView.findViewById(R.id.zuvlusun_honi_edt);
        zuvlusun_y_edt = (EditText) rootView.findViewById(R.id.zuvlusun_yamaa_edt);
        zuvlusun_u_edt = (EditText) rootView.findViewById(R.id.zuvlusun_uher_edt);
        zuvlusun_m_edt = (EditText) rootView.findViewById(R.id.zuvlusun_mori_edt);
        zuvlusun_t_edt = (EditText) rootView.findViewById(R.id.zuvlusun_temee_edt);

        ustgasan_h_edt = (EditText) rootView.findViewById(R.id.ustgasan_honi_edt);
        ustgasan_y_edt = (EditText) rootView.findViewById(R.id.ustgasan_yamaa_edt);
        ustgasan_u_edt = (EditText) rootView.findViewById(R.id.ustgasan_uher_edt);
        ustgasan_m_edt = (EditText) rootView.findViewById(R.id.ustgasan_mori_edt);
        ustgasan_t_edt = (EditText) rootView.findViewById(R.id.ustgasan_temee_edt);

        emchilsen_h_edt = (EditText) rootView.findViewById(R.id.emchilsen_honi_edt);
        emchilsen_y_edt = (EditText) rootView.findViewById(R.id.emchilsen_yamaa_edt);
        emchilsen_u_edt = (EditText) rootView.findViewById(R.id.emchilsen_uher_edt);
        emchilsen_m_edt = (EditText) rootView.findViewById(R.id.emchilsen_mori_edt);
        emchilsen_t_edt = (EditText) rootView.findViewById(R.id.emchilsen_temee_edt);

        horogdson_h_edt = (EditText) rootView.findViewById(R.id.horogdson_honi_edt);
        horogdson_y_edt = (EditText) rootView.findViewById(R.id.horogdson_yamaa_edt);
        horogdson_u_edt = (EditText) rootView.findViewById(R.id.horogdson_uher_edt);
        horogdson_m_edt = (EditText) rootView.findViewById(R.id.horogdson_mori_edt);
        horogdson_t_edt = (EditText) rootView.findViewById(R.id.horogdson_temee_edt);


        uvchin_turul_spinner = (Spinner) rootView.findViewById(R.id.uvchin_turul_spinner);
        uvchin_turul_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.uvchin_turul_array, android.R.layout.simple_spinner_item);
        uvchin_turul_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uvchin_turul_spinner.setAdapter(uvchin_turul_adapter);

        uvchin_ner_spinner = (Spinner) rootView.findViewById(R.id.uvchin_ner_spinner);
        uvchin_ner_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.haldvart_ner_array, android.R.layout.simple_spinner_item);

        ustgah_arga_spinner = (Spinner) rootView.findViewById(R.id.ustgah_arga_spinner);
        ustgah_arga_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.ustgal_arga_array, android.R.layout.simple_spinner_item);
        ustgah_arga_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ustgah_arga_spinner.setAdapter(ustgah_arga_adapter);

        saveBtn = (Button) rootView.findViewById(R.id.uvchin_save);

        uvchin_turul_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos) {
                    case 0:
                        uvchin_ner_adapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.haldvart_ner_array, android.R.layout.simple_spinner_item);
                        break;
                    case 1:
                        uvchin_ner_adapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.shimegchteh_ner_array, android.R.layout.simple_spinner_item);
                        break;
                    case 2:
                        uvchin_ner_adapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.haldvargui_ner_array, android.R.layout.simple_spinner_item);
                        break;
                    default:
                        break;
                }
                uvchin_ner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                uvchin_ner_spinner.setAdapter(uvchin_ner_adapter);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        uvchin_ner_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        saveBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                attemptEdit();
            }
        });

        getInfo();

        return rootView;
    }

    private void getInfo() {
        if (getAuthTask != null) {
            return;
        }
        uvchin_id =getActivity().getIntent().getIntExtra("selected_uvchin_id",0);

        if(uvchin_id==0) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            getAuthTask = new GetUvchin(parentActivity);
            getAuthTask.execute();
        }
    }

    private void attemptEdit() {
        if (editAuthTask != null) {
            editAuthTask=null;
            return;
        }

        if(urhCodeEdt.getText().toString().equals("") || urhEzenNerEdt.getText().toString().equals("") || bagEdt.getText().toString().equals("")
                || gazarEdt.getText().toString().equals("")|| lonEdt.getText().toString().equals("")|| latEdt.getText().toString().equals("")) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            urh_code = urhCodeEdt.getText().toString();
            urh_ezen_ner = urhEzenNerEdt.getText().toString();
            bag_horoo = bagEdt.getText().toString();
            gazar_ner = gazarEdt.getText().toString();
            uvchin_turul = uvchin_turul_spinner.getSelectedItem().toString();
            uvchin_ner = uvchin_ner_spinner.getSelectedItem().toString();
            if(ustgah_arga_spinner.getSelectedItem()!=null)
                ustgah_arga = ustgah_arga_spinner.getSelectedItem().toString();
            else
                ustgah_arga = "";

            zuvlusun_h = zuvlusun_h_edt.getText().toString().equals("") ? "0" : zuvlusun_h_edt.getText().toString();
            zuvlusun_y = zuvlusun_y_edt.getText().toString().equals("") ? "0" : zuvlusun_y_edt.getText().toString();
            zuvlusun_u = zuvlusun_u_edt.getText().toString().equals("") ? "0" : zuvlusun_u_edt.getText().toString();
            zuvlusun_m = zuvlusun_m_edt.getText().toString().equals("") ? "0" : zuvlusun_m_edt.getText().toString();
            zuvlusun_t = zuvlusun_t_edt.getText().toString().equals("") ? "0" : zuvlusun_t_edt.getText().toString();

            ustgasan_h = ustgasan_h_edt.getText().toString().equals("") ? "0" : ustgasan_h_edt.getText().toString();
            ustgasan_y = ustgasan_y_edt.getText().toString().equals("") ? "0" : ustgasan_y_edt.getText().toString();
            ustgasan_u = ustgasan_u_edt.getText().toString().equals("") ? "0" : ustgasan_u_edt.getText().toString();
            ustgasan_m = ustgasan_m_edt.getText().toString().equals("") ? "0" : ustgasan_m_edt.getText().toString();
            ustgasan_t = ustgasan_t_edt.getText().toString().equals("") ? "0" : ustgasan_t_edt.getText().toString();

            emchilsen_h = emchilsen_h_edt.getText().toString().equals("") ? "0" : emchilsen_h_edt.getText().toString();
            emchilsen_y = emchilsen_y_edt.getText().toString().equals("") ? "0" : emchilsen_y_edt.getText().toString();
            emchilsen_u = emchilsen_u_edt.getText().toString().equals("") ? "0" : emchilsen_u_edt.getText().toString();
            emchilsen_m = emchilsen_m_edt.getText().toString().equals("") ? "0" : emchilsen_m_edt.getText().toString();
            emchilsen_t = emchilsen_t_edt.getText().toString().equals("") ? "0" : emchilsen_t_edt.getText().toString();

            horogdson_h = horogdson_h_edt.getText().toString().equals("") ? "0" : horogdson_h_edt.getText().toString();
            horogdson_y = horogdson_y_edt.getText().toString().equals("") ? "0" : horogdson_y_edt.getText().toString();
            horogdson_u = horogdson_u_edt.getText().toString().equals("") ? "0" : horogdson_u_edt.getText().toString();
            horogdson_m = horogdson_m_edt.getText().toString().equals("") ? "0" : horogdson_m_edt.getText().toString();
            horogdson_t = horogdson_t_edt.getText().toString().equals("") ? "0" : horogdson_t_edt.getText().toString();

            date = new Date(Calendar.getInstance().getTimeInMillis());

            longitude = lonEdt.getText().toString().equals("") ? "0" : lonEdt.getText().toString();
            latitude = latEdt.getText().toString().equals("") ? "0" : latEdt.getText().toString();
            editAuthTask = new EditUvchin(parentActivity);
            editAuthTask.execute();
        }
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
            params.add(new BasicNameValuePair("uvchin_id", String.valueOf(uvchin_id)));

            json = jsonParser.makeHttpRequest(url_get_uvchin, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {

                            try {
                                urhCodeEdt.setText(json.getString("urh_code"));
                                urhEzenNerEdt.setText(json.getString("urh_ezen_ner"));
                                bagEdt.setText(json.getString("bag_horoo"));
                                gazarEdt.setText(json.getString("gazar_ner"));
                                uvchin_turul_spinner.setSelection(uvchin_turul_adapter.getPosition(json.getString("uvchin_turul")));
                                uvchin_ner_spinner.setSelection(uvchin_ner_adapter.getPosition(json.getString("uvchin_ner")));

                                zuvlusun_h_edt.setText(json.getString("zuvlusun_h"));
                                zuvlusun_y_edt.setText(json.getString("zuvlusun_y"));
                                zuvlusun_u_edt.setText(json.getString("zuvlusun_u"));
                                zuvlusun_m_edt.setText(json.getString("zuvlusun_m"));
                                zuvlusun_t_edt.setText(json.getString("zuvlusun_t"));

                                ustgasan_h_edt.setText(json.getString("ustgasan_h"));
                                ustgasan_y_edt.setText(json.getString("ustgasan_y"));
                                ustgasan_u_edt.setText(json.getString("ustgasan_u"));
                                ustgasan_m_edt.setText(json.getString("ustgasan_m"));
                                ustgasan_t_edt.setText(json.getString("ustgasan_t"));

                                emchilsen_h_edt.setText(json.getString("emchilsen_h"));
                                emchilsen_y_edt.setText(json.getString("emchilsen_y"));
                                emchilsen_u_edt.setText(json.getString("emchilsen_u"));
                                emchilsen_m_edt.setText(json.getString("emchilsen_m"));
                                emchilsen_t_edt.setText(json.getString("emchilsen_t"));

                                horogdson_h_edt.setText(json.getString("horogdson_h"));
                                horogdson_y_edt.setText(json.getString("horogdson_y"));
                                horogdson_u_edt.setText(json.getString("horogdson_u"));
                                horogdson_m_edt.setText(json.getString("horogdson_m"));
                                horogdson_t_edt.setText(json.getString("horogdson_t"));


                                ustgah_arga_spinner.setSelection(uvchin_ner_adapter.getPosition(json.getString("ustgah_arga")));
                                latEdt.setText(json.getString("latitude"));
                                lonEdt.setText(json.getString("longitude"));
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
            // dismiss the dialog once done
        }
    }

    class EditUvchin extends AsyncTask<String, String, String> {
        private Activity pActivity;
        public EditUvchin(Activity parent) {
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

            params.add(new BasicNameValuePair("urh_code", urh_code));
            params.add(new BasicNameValuePair("urh_ezen_ner", urh_ezen_ner));
            params.add(new BasicNameValuePair("bag_horoo", bag_horoo));
            params.add(new BasicNameValuePair("gazar_ner", gazar_ner));
            params.add(new BasicNameValuePair("uvchin_turul", uvchin_turul));
            params.add(new BasicNameValuePair("uvchin_ner", uvchin_ner));
            params.add(new BasicNameValuePair("ustgah_arga", ustgah_arga));
            params.add(new BasicNameValuePair("latitude", latitude));
            params.add(new BasicNameValuePair("longitude", longitude));

            params.add(new BasicNameValuePair("zuvlusun_h", zuvlusun_h));
            params.add(new BasicNameValuePair("zuvlusun_y", zuvlusun_y));
            params.add(new BasicNameValuePair("zuvlusun_u", zuvlusun_u));
            params.add(new BasicNameValuePair("zuvlusun_m", zuvlusun_m));
            params.add(new BasicNameValuePair("zuvlusun_t", zuvlusun_t));

            params.add(new BasicNameValuePair("ustgasan_h", ustgasan_h));
            params.add(new BasicNameValuePair("ustgasan_y", ustgasan_y));
            params.add(new BasicNameValuePair("ustgasan_u", ustgasan_u));
            params.add(new BasicNameValuePair("ustgasan_m", ustgasan_m));
            params.add(new BasicNameValuePair("ustgasan_t", ustgasan_t));

            params.add(new BasicNameValuePair("emchilsen_h", emchilsen_h));
            params.add(new BasicNameValuePair("emchilsen_y", emchilsen_y));
            params.add(new BasicNameValuePair("emchilsen_u", emchilsen_u));
            params.add(new BasicNameValuePair("emchilsen_m", emchilsen_m));
            params.add(new BasicNameValuePair("emchilsen_t", emchilsen_t));

            params.add(new BasicNameValuePair("horogdson_h", horogdson_h));
            params.add(new BasicNameValuePair("horogdson_y", horogdson_y));
            params.add(new BasicNameValuePair("horogdson_u", horogdson_u));
            params.add(new BasicNameValuePair("horogdson_m", horogdson_m));
            params.add(new BasicNameValuePair("horogdson_t", horogdson_t));

            params.add(new BasicNameValuePair("date", date.toString()));
            Log.d("date", date.toString());

            JSONObject json = jsonParser.makeHttpRequest(url_save_uvchin, "GET", params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(pActivity.getBaseContext(), "Амжилттай хадгалагдлаа.", Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, ListUvchinFragment.newInstance())
                                    .commit();
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
        ((HomeActivity) activity).onSectionAttached(1);
    }
}

