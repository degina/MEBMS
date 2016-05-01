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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class GetShinjilgeeFragment extends Fragment {

    private GetShinjilgee mAuthTask = null;

    TextView shinjilgee_turul_text;
    TextView sorits_ner_text;
    TextView sorits_turul_text;
    TextView mal_nas_text;
    TextView mal_huis_text;
    TextView sorits_too_text;
    TextView negj_text;
    TextView huleen_avah_baiguullaga_text;
    TextView ilgeeh_arga_text;

    TextView date_text;
    TextView urh_code_text;
    TextView urh_ezen_ner_text;
    TextView bag_text;
    TextView gazar_text;
    TextView sorits_behjuulsen_arga_text;
    TextView lat_text;
    TextView lon_text;

    Button editBtn;
    Button deleteBtn;

    Activity parentActivity;

    private static String url_get_shijilgee = "http://10.0.2.2:81/mebp/getshinjilgee.php";
    JSONParser jsonParser = new JSONParser();

    int shinjilgee_id;

    JSONObject json;

    public static GetShinjilgeeFragment newInstance() {
        GetShinjilgeeFragment fragment = new GetShinjilgeeFragment();
        return fragment;
    }

    public GetShinjilgeeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_shinjilgee,
                container, false);


        urh_code_text = (TextView) rootView.findViewById(R.id.urh_code_text);
        urh_ezen_ner_text = (TextView) rootView.findViewById(R.id.urh_ezen_ner_text);
        bag_text = (TextView) rootView.findViewById(R.id.bag_horoo_text);
        gazar_text = (TextView) rootView.findViewById(R.id.gazar_ner_text);
        sorits_behjuulsen_arga_text = (TextView) rootView.findViewById(R.id.sorits_behjuulsen_arga_text);
        lat_text = (TextView) rootView.findViewById(R.id.latitude_text);
        lon_text = (TextView) rootView.findViewById(R.id.longitude_text);

        date_text = (TextView) rootView.findViewById(R.id.date_text);
        shinjilgee_turul_text = (TextView) rootView.findViewById(R.id.urh_code_text);
        sorits_ner_text = (TextView) rootView.findViewById(R.id.sorits_ner_text);
        sorits_turul_text = (TextView) rootView.findViewById(R.id.sorits_turul_text);
        mal_nas_text = (TextView) rootView.findViewById(R.id.mal_nas_text);
        mal_huis_text = (TextView) rootView.findViewById(R.id.mal_huis_text);
        sorits_too_text = (TextView) rootView.findViewById(R.id.sorits_too_text);
        negj_text = (TextView) rootView.findViewById(R.id.negj_text);
        huleen_avah_baiguullaga_text = (TextView) rootView.findViewById(R.id.huleen_avah_baiguullaga_text);
        ilgeeh_arga_text = (TextView) rootView.findViewById(R.id.ilgeeh_arga_text);

        editBtn = (Button) rootView.findViewById(R.id.edit_shinjilgee);
        deleteBtn = (Button) rootView.findViewById(R.id.delete_shinjilgee);

        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, EditShinjilgeeFragment.newInstance())
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
        shinjilgee_id =getActivity().getIntent().getIntExtra("selected_shinjilgee_id",0);

        if(shinjilgee_id==0) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            mAuthTask = new GetShinjilgee(parentActivity);
            mAuthTask.execute();
        }
    }
    class GetShinjilgee extends AsyncTask<String, String, String> {
        private Activity pActivity;
        public GetShinjilgee(Activity parent) {
            this.pActivity = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("shinjilgee_id", String.valueOf(shinjilgee_id)));

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
                                shinjilgee_turul_text.setText(json.getString("shinjilgee_turul"));
                                sorits_ner_text.setText(json.getString("sorits_ner"));
                                sorits_turul_text.setText(json.getString("sorits_turul"));
                                mal_nas_text.setText(json.getString("mal_nas"));
                                mal_huis_text.setText(json.getString("mal_huis"));
                                sorits_too_text.setText(json.getString("sorits_too"));
                                negj_text.setText(json.getString("negj"));
                                sorits_behjuulsen_arga_text.setText(json.getString("sorits_behjuulsen_arga"));
                                huleen_avah_baiguullaga_text.setText(json.getString("huleen_avah_baiguullaga"));
                                ilgeeh_arga_text.setText(json.getString("ilgeeh_arga"));
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