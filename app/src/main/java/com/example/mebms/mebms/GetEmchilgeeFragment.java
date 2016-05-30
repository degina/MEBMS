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

import com.mikepenz.materialdrawer.Drawer;

public class GetEmchilgeeFragment extends Fragment {

    private GetEmchilgee mAuthTask = null;

    TextView emchilgee_turul_text;
    TextView shimegchteh_ner_text;
    TextView beldmel_ner_text;

    TextView shimegchteh_honi_text;
    TextView shimegchteh_yamaa_text;
    TextView shimegchteh_uher_text;
    TextView shimegchteh_mori_text;
    TextView shimegchteh_temee_text;

    TextView id_text;
    TextView date_text;
    TextView urh_code_text;
    TextView urh_ezen_ner_text;
    TextView bag_text;
    TextView gazar_text;
    TextView lat_text;
    TextView lon_text;

    Button editBtn;

    Activity parentActivity;

    private static String url_get_emchilgee = "http://"+Const.IP_ADDRESS1+":81/mebp/getemchilgee.php";
    JSONParser jsonParser = new JSONParser();

    int emchilgee_id;

    JSONObject json;

    public static GetEmchilgeeFragment newInstance() {
        GetEmchilgeeFragment fragment = new GetEmchilgeeFragment();
        return fragment;
    }

    public GetEmchilgeeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_emchilgee,
                container, false);

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


        id_text = (TextView)rootView.findViewById(R.id.id_text);
        id_text.setText(String.valueOf(getActivity().getIntent().getIntExtra("selected_emchilgee_id",0)));
        urh_code_text = (TextView) rootView.findViewById(R.id.urh_code_text);
        urh_ezen_ner_text = (TextView) rootView.findViewById(R.id.urh_ezen_ner_text);
        bag_text = (TextView) rootView.findViewById(R.id.bag_horoo_text);
        gazar_text = (TextView) rootView.findViewById(R.id.gazar_ner_text);
        lat_text = (TextView) rootView.findViewById(R.id.latitude_text);
        lon_text = (TextView) rootView.findViewById(R.id.longitude_text);

        date_text = (TextView) rootView.findViewById(R.id.date_text);
        emchilgee_turul_text = (TextView) rootView.findViewById(R.id.emchilgee_turul_text);
        shimegchteh_ner_text = (TextView) rootView.findViewById(R.id.shimegchteh_ner_text);
        beldmel_ner_text = (TextView) rootView.findViewById(R.id.beldmel_ner_text);

        shimegchteh_honi_text = (TextView) rootView.findViewById(R.id.shimegchteh_honi_text);
        shimegchteh_yamaa_text = (TextView) rootView.findViewById(R.id.shimegchteh_yamaa_text);
        shimegchteh_uher_text = (TextView) rootView.findViewById(R.id.shimegchteh_uher_text);
        shimegchteh_mori_text = (TextView) rootView.findViewById(R.id.shimegchteh_mori_text);
        shimegchteh_temee_text = (TextView) rootView.findViewById(R.id.shimegchteh_temee_text);

        editBtn = (Button) rootView.findViewById(R.id.edit_emchilgee);

        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, EditEmchilgeeFragment.newInstance())
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
        emchilgee_id = getActivity().getIntent().getIntExtra("selected_emchilgee_id",0);

        if(emchilgee_id == 0) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү", Toast.LENGTH_LONG).show();
        }else {
            mAuthTask = new GetEmchilgee(parentActivity);
            mAuthTask.execute();
        }
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
            params.add(new BasicNameValuePair("emchilgee_id", String.valueOf(emchilgee_id)));

            json = jsonParser.makeHttpRequest(url_get_emchilgee, "GET",
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
                                emchilgee_turul_text.setText(json.getString("emchilgee_turul"));
                                shimegchteh_ner_text.setText(json.getString("shimegchteh_ner"));
                                beldmel_ner_text.setText(json.getString("beldmel_ner"));

                                shimegchteh_honi_text.setText(json.getString("shimegchteh_honi"));
                                shimegchteh_yamaa_text.setText(json.getString("shimegchteh_yamaa"));
                                shimegchteh_uher_text.setText(json.getString("shimegchteh_uher"));
                                shimegchteh_mori_text.setText(json.getString("shimegchteh_mori"));
                                shimegchteh_temee_text.setText(json.getString("shimegchteh_temee"));

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