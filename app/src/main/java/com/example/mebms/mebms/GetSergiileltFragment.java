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

public class GetSergiileltFragment extends Fragment {

    private GetSergiilelt mAuthTask = null;

    TextView sergiilelt_turul_text;
    TextView darhlaajuulalt_tuluv_text;
    TextView darhlaajuulalt_ner_text;
    TextView vaktsin_nershil_text;

    TextView niit_honi_text;
    TextView niit_yamaa_text;
    TextView niit_uher_text;
    TextView niit_mori_text;
    TextView niit_temee_text;

    TextView hamragdsan_honi_text;
    TextView hamragdsan_yamaa_text;
    TextView hamragdsan_uher_text;
    TextView hamragdsan_mori_text;
    TextView hamragdsan_temee_text;

    TextView haldvarguitgesen_obekt_text;

    TextView date_text;
    TextView urh_code_text;
    TextView urh_ezen_ner_text;
    TextView bag_text;
    TextView gazar_text;
    TextView lat_text;
    TextView lon_text;

    LinearLayout darhlaajuulalt_layout;
    LinearLayout haldvarguitgel_layout;

    Button editBtn;
    Button deleteBtn;

    Activity parentActivity;

    private static String url_get_shijilgee = "http://10.0.2.2:81/mebp/getshinjilgee.php";
    JSONParser jsonParser = new JSONParser();

    int sergiilelt_id;

    JSONObject json;

    public static GetSergiileltFragment newInstance() {
        GetSergiileltFragment fragment = new GetSergiileltFragment();
        return fragment;
    }

    public GetSergiileltFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_sergiilelt,
                container, false);


        urh_code_text = (TextView) rootView.findViewById(R.id.urh_code_text);
        urh_ezen_ner_text = (TextView) rootView.findViewById(R.id.urh_ezen_ner_text);
        bag_text = (TextView) rootView.findViewById(R.id.bag_horoo_text);
        gazar_text = (TextView) rootView.findViewById(R.id.gazar_ner_text);
        lat_text = (TextView) rootView.findViewById(R.id.latitude_text);
        lon_text = (TextView) rootView.findViewById(R.id.longitude_text);

        date_text = (TextView) rootView.findViewById(R.id.date_text);
        sergiilelt_turul_text = (TextView) rootView.findViewById(R.id.sergiilelt_turul_text);
        darhlaajuulalt_tuluv_text = (TextView) rootView.findViewById(R.id.darhlaajuulalt_tuluv_text);
        darhlaajuulalt_ner_text = (TextView) rootView.findViewById(R.id.darhlaajuulalt_ner_text);
        vaktsin_nershil_text = (TextView) rootView.findViewById(R.id.vaktsin_nershil_text);

        niit_honi_text = (TextView) rootView.findViewById(R.id.niit_honi_text);
        niit_yamaa_text = (TextView) rootView.findViewById(R.id.niit_yamaa_text);
        niit_uher_text = (TextView) rootView.findViewById(R.id.niit_uher_text);
        niit_mori_text = (TextView) rootView.findViewById(R.id.niit_mori_text);
        niit_temee_text = (TextView) rootView.findViewById(R.id.niit_temee_text);

        hamragdsan_honi_text = (TextView) rootView.findViewById(R.id.hamragdsan_honi_text);
        hamragdsan_yamaa_text = (TextView) rootView.findViewById(R.id.hamragdsan_yamaa_text);
        hamragdsan_uher_text = (TextView) rootView.findViewById(R.id.hamragdsan_uher_text);
        hamragdsan_mori_text = (TextView) rootView.findViewById(R.id.hamragdsan_mori_text);
        hamragdsan_temee_text = (TextView) rootView.findViewById(R.id.hamragdsan_temee_text);

        darhlaajuulalt_layout = (LinearLayout) rootView.findViewById(R.id.darhlaajuulalt_layout);
        haldvarguitgel_layout = (LinearLayout) rootView.findViewById(R.id.haldvarguitgel_layout);

        editBtn = (Button) rootView.findViewById(R.id.edit_sergiilelt);
        deleteBtn = (Button) rootView.findViewById(R.id.delete_sergiilelt);

        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, EditSergiileltFragment.newInstance())
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
        sergiilelt_id =getActivity().getIntent().getIntExtra("selected_sergiilelt_id",0);

        if(sergiilelt_id==0) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            mAuthTask = new GetSergiilelt(parentActivity);
            mAuthTask.execute();
        }
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
            params.add(new BasicNameValuePair("sergiilelt_id", String.valueOf(sergiilelt_id)));

            json = jsonParser.makeHttpRequest(url_get_shijilgee, "GET",
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

                                sergiilelt_turul_text.setText(json.getString("sergiilelt_turul"));
                                if(json.getString("sergiilelt_turul").equals(getResources().getStringArray(R.array.darhlaajuulalt_tuluv_array)[0])) {
                                    darhlaajuulalt_tuluv_text.setText(json.getString("darhlaajuulalt_tuluv"));
                                    darhlaajuulalt_ner_text.setText(json.getString("darhlaajuulalt_ner"));
                                    vaktsin_nershil_text.setText(json.getString("vaktsin_nershil"));
                                    niit_honi_text.setText(json.getString("niit_honi"));
                                    niit_yamaa_text.setText(json.getString("niit_yamaa"));
                                    niit_uher_text.setText(json.getString("niit_uher"));
                                    niit_mori_text.setText(json.getString("niit_mori"));
                                    niit_temee_text.setText(json.getString("niit_temee"));

                                    hamragdsan_honi_text.setText(json.getString("hamragdsan_honi"));
                                    hamragdsan_yamaa_text.setText(json.getString("hamragdsan_yamaa"));
                                    hamragdsan_uher_text.setText(json.getString("hamragdsan_uher"));
                                    hamragdsan_mori_text.setText(json.getString("hamragdsan_mori"));
                                    hamragdsan_temee_text.setText(json.getString("hamragdsan_temee"));
                                    darhlaajuulalt_layout.setVisibility(View.VISIBLE);
                                }
                                if(json.getString("sergiilelt_turul").equals(getResources().getStringArray(R.array.darhlaajuulalt_tuluv_array)[1])) {
                                    haldvarguitgesen_obekt_text.setText(json.getString("haldvarguitgesen_obekt"));
                                    haldvarguitgel_layout.setVisibility(View.VISIBLE);
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