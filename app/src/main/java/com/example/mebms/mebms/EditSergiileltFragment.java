package com.example.mebms.mebms;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mikepenz.materialdrawer.Drawer;

public class EditSergiileltFragment extends Fragment {

    private EditSergiilelt editAuthTask = null;
    private GetSergiilelt getAuthTask = null;

    Spinner darhlaajuulalt_tuluv_spinner;
    Spinner darhlaajuulalt_ner_spinner;
    Spinner haldvarguitgesen_obekt_spinner;

    ArrayAdapter<CharSequence> sergiilelt_turul_adapter;
    ArrayAdapter<CharSequence> darhlaajuulalt_tuluv_adapter;
    ArrayAdapter<CharSequence> darhlaajuulalt_ner_adapter;
    ArrayAdapter<CharSequence> haldvarguitgesen_obekt_adapter;

    EditText urhCodeEdt;
    EditText urhEzenNerEdt;
    EditText bagEdt;
    EditText gazarEdt;
    EditText vaktsinNershilEdt;
    EditText latEdt;
    EditText lonEdt;

    EditText niit_honi_edt;
    EditText niit_yamaa_edt;
    EditText niit_uher_edt;
    EditText niit_mori_edt;
    EditText niit_temee_edt;

    EditText hamragdsan_honi_edt;
    EditText hamragdsan_yamaa_edt;
    EditText hamragdsan_uher_edt;
    EditText hamragdsan_mori_edt;
    EditText hamragdsan_temee_edt;

    LinearLayout darhlaajuulalt_layout;
    LinearLayout haldvarguitgel_layout;

    CheckBox darhlaajuulalt_check;
    CheckBox haldvarguitgel_check;

    int sergiilelt_id;

    JSONObject json;

    Button saveBtn;

    Activity parentActivity;

    private static String url_save_sergiilelt = "http://"+Const.IP_ADDRESS1+":81/mebp/savesergiilelt.php";
    private static String url_get_sergiilelt = "http://"+Const.IP_ADDRESS1+":81/mebp/getsergiilelt.php";
    JSONParser jsonParser = new JSONParser();

    String urh_code;
    String urh_ezen_ner;
    String bag_horoo;
    String gazar_ner;
    String sergiilelt_turul;
    String darhlaajuulalt_tuluv;
    String sergiilelt_turul_darhlaajuulalt;
    String sergiilelt_turul_haldvarguitgel;
    String darhlaajuulalt_ner;
    String vaktsin_nershil;
    String haldvarguitgesen_obekt;
    String latitude;
    String longitude;

    String niit_honi;
    String niit_yamaa;
    String niit_uher;
    String niit_mori;
    String niit_temee;

    String hamragdsan_honi;
    String hamragdsan_yamaa;
    String hamragdsan_uher;
    String hamragdsan_mori;
    String hamragdsan_temee;

    public static EditSergiileltFragment newInstance() {
        EditSergiileltFragment fragment = new EditSergiileltFragment();
        return fragment;
    }

    public EditSergiileltFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_sergiilelt,
                container, false);

        ((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        ((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity)parentActivity).getSupportActionBar().setHomeButtonEnabled(true);
        ((HomeActivity)parentActivity).result.setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, ListSergiileltFragment.newInstance())
                        .commit();
                ((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                ((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                return true;
            }
        });

        darhlaajuulalt_layout = (LinearLayout) rootView.findViewById(R.id.darhlaajuulalt_layout);
        haldvarguitgel_layout = (LinearLayout) rootView.findViewById(R.id.haldvarguitgel_layout);

        darhlaajuulalt_check = (CheckBox) rootView.findViewById(R.id.darhlaajuulalt_check);
        darhlaajuulalt_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    darhlaajuulalt_layout.setVisibility(View.VISIBLE);
                } else {
                    darhlaajuulalt_layout.setVisibility(View.GONE);
                }
            }
        });

        haldvarguitgel_check = (CheckBox) rootView.findViewById(R.id.haldvarguitgel_check);
        haldvarguitgel_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    haldvarguitgel_layout.setVisibility(View.VISIBLE);
                } else {
                    haldvarguitgel_layout.setVisibility(View.GONE);
                }
            }
        });

        urhCodeEdt = (EditText) rootView.findViewById(R.id.urh_code_edt);
        urhEzenNerEdt = (EditText) rootView.findViewById(R.id.urh_ezen_ner_edt);
        bagEdt = (EditText) rootView.findViewById(R.id.bag_horoo_edt);
        gazarEdt = (EditText) rootView.findViewById(R.id.gazar_ner_edt);
        if(darhlaajuulalt_check.isChecked())
            sergiilelt_turul_darhlaajuulalt="true";
        else
            sergiilelt_turul_darhlaajuulalt="false";
        if(haldvarguitgel_check.isChecked())
            sergiilelt_turul_haldvarguitgel="true";
        else
            sergiilelt_turul_haldvarguitgel="false";
        vaktsinNershilEdt = (EditText) rootView.findViewById(R.id.vaktsin_nershil_edt);
        latEdt = (EditText) rootView.findViewById(R.id.latitude_edt);
        lonEdt = (EditText) rootView.findViewById(R.id.longitude_edt);

        niit_honi_edt = (EditText) rootView.findViewById(R.id.niit_honi_edt);
        niit_yamaa_edt = (EditText) rootView.findViewById(R.id.niit_yamaa_edt);
        niit_uher_edt = (EditText) rootView.findViewById(R.id.niit_uher_edt);
        niit_mori_edt = (EditText) rootView.findViewById(R.id.niit_mori_edt);
        niit_temee_edt = (EditText) rootView.findViewById(R.id.niit_temee_edt);

        hamragdsan_honi_edt = (EditText) rootView.findViewById(R.id.hamragdsan_honi_edt);
        hamragdsan_yamaa_edt = (EditText) rootView.findViewById(R.id.hamragdsan_yamaa_edt);
        hamragdsan_uher_edt = (EditText) rootView.findViewById(R.id.hamragdsan_uher_edt);
        hamragdsan_mori_edt = (EditText) rootView.findViewById(R.id.hamragdsan_mori_edt);
        hamragdsan_temee_edt = (EditText) rootView.findViewById(R.id.hamragdsan_temee_edt);

        darhlaajuulalt_tuluv_spinner = (Spinner) rootView.findViewById(R.id.darhlaajuulalt_tuluv_spinner);
        darhlaajuulalt_tuluv_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.darhlaajuulalt_tuluv_array, android.R.layout.simple_spinner_item);
        darhlaajuulalt_tuluv_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        darhlaajuulalt_tuluv_spinner.setAdapter(darhlaajuulalt_tuluv_adapter);

        darhlaajuulalt_ner_spinner = (Spinner) rootView.findViewById(R.id.darhlaajuulalt_ner_spinner);
        darhlaajuulalt_ner_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.haldvart_ner_array, android.R.layout.simple_spinner_item);
        darhlaajuulalt_ner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        darhlaajuulalt_ner_spinner.setAdapter(darhlaajuulalt_ner_adapter);

        haldvarguitgesen_obekt_spinner = (Spinner) rootView.findViewById(R.id.haldvarguitgesen_obekt_spinner);
        haldvarguitgesen_obekt_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.haldvarguitgesen_obekt_array, android.R.layout.simple_spinner_item);
        haldvarguitgesen_obekt_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        haldvarguitgesen_obekt_spinner.setAdapter(haldvarguitgesen_obekt_adapter);

        saveBtn = (Button) rootView.findViewById(R.id.sergiilelt_save);

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
        sergiilelt_id =getActivity().getIntent().getIntExtra("selected_sergiilelt_id",0);

        if(sergiilelt_id==0) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            getAuthTask = new GetSergiilelt(parentActivity);
            getAuthTask.execute();
        }
    }
    private void attemptEdit() {
        if (editAuthTask != null) {
            return;
        }

        if(urhCodeEdt.getText().toString().equals("") || urhEzenNerEdt.getText().toString().equals("") || bagEdt.getText().toString().equals("")
                || gazarEdt.getText().toString().equals("") || lonEdt.getText().toString().equals("")|| latEdt.getText().toString().equals("")) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            urh_code = urhCodeEdt.getText().toString();
            urh_ezen_ner = urhEzenNerEdt.getText().toString();
            bag_horoo = bagEdt.getText().toString();
            gazar_ner = gazarEdt.getText().toString();

            darhlaajuulalt_tuluv = darhlaajuulalt_tuluv_spinner.getSelectedItem().toString();
            darhlaajuulalt_ner = darhlaajuulalt_ner_spinner.getSelectedItem().toString();
            vaktsin_nershil = vaktsinNershilEdt.getText().toString();
            haldvarguitgesen_obekt = haldvarguitgesen_obekt_spinner.getSelectedItem().toString();

            niit_honi = niit_honi_edt.getText().toString().equals("") ? "0" : niit_honi_edt.getText().toString();
            niit_yamaa = niit_yamaa_edt.getText().toString().equals("") ? "0" : niit_yamaa_edt.getText().toString();
            niit_uher = niit_uher_edt.getText().toString().equals("") ? "0" : niit_uher_edt.getText().toString();
            niit_mori = niit_mori_edt.getText().toString().equals("") ? "0" : niit_mori_edt.getText().toString();
            niit_temee = niit_temee_edt.getText().toString().equals("") ? "0" : niit_temee_edt.getText().toString();

            hamragdsan_honi = hamragdsan_honi_edt.getText().toString().equals("") ? "0" : hamragdsan_honi_edt.getText().toString();
            hamragdsan_yamaa = hamragdsan_yamaa_edt.getText().toString().equals("") ? "0" : hamragdsan_yamaa_edt.getText().toString();
            hamragdsan_uher = hamragdsan_uher_edt.getText().toString().equals("") ? "0" : hamragdsan_uher_edt.getText().toString();
            hamragdsan_mori = hamragdsan_mori_edt.getText().toString().equals("") ? "0" : hamragdsan_mori_edt.getText().toString();
            hamragdsan_temee = hamragdsan_temee_edt.getText().toString().equals("") ? "0" : hamragdsan_temee_edt.getText().toString();

            longitude = lonEdt.getText().toString().equals("") ? "0" : lonEdt.getText().toString();
            latitude = latEdt.getText().toString().equals("") ? "0" : latEdt.getText().toString();

            editAuthTask = new EditSergiilelt(parentActivity);
            editAuthTask.execute();
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

            json = jsonParser.makeHttpRequest(url_get_sergiilelt, "GET",
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

                                if(json.getString("sergiilelt_turul_darhlaajuulalt").equals("true")){
                                    darhlaajuulalt_check.setChecked(true);
                                    darhlaajuulalt_layout.setVisibility(View.VISIBLE);

                                    darhlaajuulalt_tuluv_spinner.setSelection(darhlaajuulalt_tuluv_adapter.getPosition(json.getString("darhlaajuulalt_tuluv")));
                                    darhlaajuulalt_ner_spinner.setSelection(darhlaajuulalt_ner_adapter.getPosition(json.getString("darhlaajuulalt_ner")));
                                    vaktsinNershilEdt.setText(json.getString("vaktsin_nershil"));

                                    niit_honi_edt.setText(json.getString("niit_honi"));
                                    niit_yamaa_edt.setText(json.getString("niit_yamaa"));
                                    niit_uher_edt.setText(json.getString("niit_uher"));
                                    niit_mori_edt.setText(json.getString("niit_mori"));
                                    niit_temee_edt.setText(json.getString("niit_temee"));

                                    hamragdsan_honi_edt.setText(json.getString("hamragdsan_honi"));
                                    hamragdsan_yamaa_edt.setText(json.getString("hamragdsan_yamaa"));
                                    hamragdsan_uher_edt.setText(json.getString("hamragdsan_uher"));
                                    hamragdsan_mori_edt.setText(json.getString("hamragdsan_mori"));
                                    hamragdsan_temee_edt.setText(json.getString("hamragdsan_temee"));

                                }

                                if(json.getString("sergiilelt_turul_haldvarguitgel").equals("true")) {
                                    haldvarguitgel_check.setChecked(true);
                                    haldvarguitgel_layout.setVisibility(View.VISIBLE);

                                    haldvarguitgesen_obekt_spinner.setSelection(haldvarguitgesen_obekt_adapter.getPosition(json.getString("haldvarguitgesen_obekt")));
                                }

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

    class EditSergiilelt extends AsyncTask<String, String, String> {
        private Activity pActivity;
        public EditSergiilelt(Activity parent) {
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
            params.add(new BasicNameValuePair("urh_code", urh_code));
            params.add(new BasicNameValuePair("urh_ezen_ner", urh_ezen_ner));
            params.add(new BasicNameValuePair("bag_horoo", bag_horoo));
            params.add(new BasicNameValuePair("gazar_ner", gazar_ner));
            params.add(new BasicNameValuePair("sergiilelt_turul", sergiilelt_turul));
            params.add(new BasicNameValuePair("darhlaajuulalt_tuluv", darhlaajuulalt_tuluv));
            params.add(new BasicNameValuePair("darhlaajuulalt_ner", darhlaajuulalt_ner));
            params.add(new BasicNameValuePair("vaktsin_nershil", vaktsin_nershil));
            params.add(new BasicNameValuePair("haldvarguitgesen_obekt", haldvarguitgesen_obekt));
            params.add(new BasicNameValuePair("niit_honi", niit_honi));
            params.add(new BasicNameValuePair("niit_yamaa", niit_yamaa));
            params.add(new BasicNameValuePair("niit_uher", niit_uher));
            params.add(new BasicNameValuePair("niit_mori", niit_mori));
            params.add(new BasicNameValuePair("niit_temee", niit_temee));
            params.add(new BasicNameValuePair("hamragdsan_honi", hamragdsan_honi));
            params.add(new BasicNameValuePair("hamragdsan_yamaa", hamragdsan_yamaa));
            params.add(new BasicNameValuePair("hamragdsan_uher", hamragdsan_uher));
            params.add(new BasicNameValuePair("hamragdsan_mori", hamragdsan_mori));
            params.add(new BasicNameValuePair("hamragdsan_temee", hamragdsan_temee));
            params.add(new BasicNameValuePair("latitude", latitude));
            params.add(new BasicNameValuePair("longitude", longitude));

            JSONObject json = jsonParser.makeHttpRequest(url_save_sergiilelt, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(pActivity.getBaseContext(),
                                    "Амжилттай хадгалагдлаа.",
                                    Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, ListSergiileltFragment.newInstance())
                                    .commit();
                            ((HomeActivity)parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            ((HomeActivity)parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
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