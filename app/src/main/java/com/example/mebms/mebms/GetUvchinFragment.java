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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mikepenz.materialdrawer.Drawer;

public class GetUvchinFragment extends Fragment {

    private GetUvchin mAuthTask = null;

    CheckBox zuvlusun_check;
    CheckBox ustgasan_check;
    CheckBox emchilsen_check;
    CheckBox horogdson_check;

    LinearLayout zuvlusun_layout;
    LinearLayout ustgasan_layout;
    LinearLayout emchilsen_layout;
    LinearLayout horogdson_layout;
    LinearLayout ustgah_arga_layout;

    TextView uvchin_turul_text;
    TextView uvchin_ner_text;

    TextView zuvlusun_h_text;
    TextView zuvlusun_y_text;
    TextView zuvlusun_u_text;
    TextView zuvlusun_m_text;
    TextView zuvlusun_t_text;

    TextView ustgasan_h_text;
    TextView ustgasan_y_text;
    TextView ustgasan_u_text;
    TextView ustgasan_m_text;
    TextView ustgasan_t_text;

    TextView emchilsen_h_text;
    TextView emchilsen_y_text;
    TextView emchilsen_u_text;
    TextView emchilsen_m_text;
    TextView emchilsen_t_text;

    TextView horogdson_h_text;
    TextView horogdson_y_text;
    TextView horogdson_u_text;
    TextView horogdson_m_text;
    TextView horogdson_t_text;

    TextView date_text;
    TextView urh_code_text;
    TextView urh_ezen_ner_text;
    TextView bag_text;
    TextView gazar_text;
    TextView ustgah_arga_text;
    TextView lat_text;
    TextView lon_text;

    Button editBtn;
    Activity parentActivity;

    private static String url_get_uvchin = "http://10.0.2.2:81/mebp/getuvchin.php";
    JSONParser jsonParser = new JSONParser();

    int uvchin_id;

    JSONObject json;

    public static GetUvchinFragment newInstance() {
        GetUvchinFragment fragment = new GetUvchinFragment();
        return fragment;
    }

    public GetUvchinFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_uvchin,
                container, false);

        ((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        ((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)parentActivity).getSupportActionBar().setHomeButtonEnabled(true);
        ((HomeActivity)parentActivity).result.setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, ListUvchinFragment.newInstance())
                        .commit();
                ((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                ((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                return true;
            }
        });

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

        urh_code_text = (TextView) rootView.findViewById(R.id.urh_code_text);
        urh_ezen_ner_text = (TextView) rootView.findViewById(R.id.urh_ezen_ner_text);
        bag_text = (TextView) rootView.findViewById(R.id.bag_horoo_text);
        gazar_text = (TextView) rootView.findViewById(R.id.gazar_ner_text);
        uvchin_turul_text = (TextView) rootView.findViewById(R.id.uvchin_turul_text);
        uvchin_ner_text = (TextView) rootView.findViewById(R.id.uvchin_ner_text);
        ustgah_arga_text = (TextView) rootView.findViewById(R.id.ustgah_arga_text);
        lat_text = (TextView) rootView.findViewById(R.id.latitude_text);
        lon_text = (TextView) rootView.findViewById(R.id.longitude_text);
        date_text = (TextView) rootView.findViewById(R.id.date_text);

        ustgasan_h_text = (TextView) rootView.findViewById(R.id.ustgasan_honi_text);
        ustgasan_y_text = (TextView) rootView.findViewById(R.id.ustgasan_yamaa_text);
        ustgasan_u_text = (TextView) rootView.findViewById(R.id.ustgasan_uher_text);
        ustgasan_m_text = (TextView) rootView.findViewById(R.id.ustgasan_mori_text);
        ustgasan_t_text = (TextView) rootView.findViewById(R.id.ustgasan_temee_text);

        zuvlusun_h_text = (TextView) rootView.findViewById(R.id.zuvlusun_honi_text);
        zuvlusun_y_text = (TextView) rootView.findViewById(R.id.zuvlusun_yamaa_text);
        zuvlusun_u_text = (TextView) rootView.findViewById(R.id.zuvlusun_uher_text);
        zuvlusun_m_text = (TextView) rootView.findViewById(R.id.zuvlusun_mori_text);
        zuvlusun_t_text = (TextView) rootView.findViewById(R.id.zuvlusun_temee_text);

        emchilsen_h_text = (TextView) rootView.findViewById(R.id.emchilsen_honi_text);
        emchilsen_y_text = (TextView) rootView.findViewById(R.id.emchilsen_yamaa_text);
        emchilsen_u_text = (TextView) rootView.findViewById(R.id.emchilsen_uher_text);
        emchilsen_m_text = (TextView) rootView.findViewById(R.id.emchilsen_mori_text);
        emchilsen_t_text = (TextView) rootView.findViewById(R.id.emchilsen_temee_text);

        horogdson_h_text = (TextView) rootView.findViewById(R.id.horogdson_honi_text);
        horogdson_y_text = (TextView) rootView.findViewById(R.id.horogdson_yamaa_text);
        horogdson_u_text = (TextView) rootView.findViewById(R.id.horogdson_uher_text);
        horogdson_m_text = (TextView) rootView.findViewById(R.id.horogdson_mori_text);
        horogdson_t_text = (TextView) rootView.findViewById(R.id.horogdson_temee_text);

        editBtn = (Button) rootView.findViewById(R.id.edit_uvchin);

        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, EditUvchinFragment.newInstance())
                        .commit();
            }
        });
        getInfo();
        return rootView;
    }

    private void getInfo() {
        if (mAuthTask != null) {
            return;
        }
        uvchin_id =getActivity().getIntent().getIntExtra("selected_uvchin_id",0);

        if(uvchin_id==0) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            mAuthTask = new GetUvchin(parentActivity);
            mAuthTask.execute();
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
                                urh_code_text.setText(json.getString("urh_code"));
                                urh_ezen_ner_text.setText(json.getString("urh_ezen_ner"));
                                bag_text.setText(json.getString("bag_horoo"));
                                gazar_text.setText(json.getString("gazar_ner"));
                                uvchin_turul_text.setText(json.getString("uvchin_turul"));
                                uvchin_ner_text.setText(json.getString("uvchin_ner"));

                                ustgasan_h_text.setText(json.getString("ustgasan_h"));
                                ustgasan_y_text.setText(json.getString("ustgasan_y"));
                                ustgasan_u_text.setText(json.getString("ustgasan_u"));
                                ustgasan_m_text.setText(json.getString("ustgasan_m"));
                                ustgasan_t_text.setText(json.getString("ustgasan_t"));

                                zuvlusun_h_text.setText(json.getString("zuvlusun_h"));
                                zuvlusun_y_text.setText(json.getString("zuvlusun_y"));
                                zuvlusun_u_text.setText(json.getString("zuvlusun_u"));
                                zuvlusun_m_text.setText(json.getString("zuvlusun_m"));
                                zuvlusun_t_text.setText(json.getString("zuvlusun_t"));

                                emchilsen_h_text.setText(json.getString("emchilsen_h"));
                                emchilsen_y_text.setText(json.getString("emchilsen_y"));
                                emchilsen_u_text.setText(json.getString("emchilsen_u"));
                                emchilsen_m_text.setText(json.getString("emchilsen_m"));
                                emchilsen_t_text.setText(json.getString("emchilsen_t"));

                                horogdson_h_text.setText(json.getString("horogdson_h"));
                                horogdson_y_text.setText(json.getString("horogdson_y"));
                                horogdson_u_text.setText(json.getString("horogdson_u"));
                                horogdson_m_text.setText(json.getString("horogdson_m"));
                                horogdson_t_text.setText(json.getString("horogdson_t"));

                                ustgah_arga_text.setText(json.getString("ustgah_arga"));
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
            // dismiss the dialog once done
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
        ((HomeActivity) activity).onSectionAttached(4);
    }
}