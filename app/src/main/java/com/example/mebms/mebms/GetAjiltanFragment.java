package com.example.mebms.mebms;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GetAjiltanFragment extends Fragment {

    Activity parentActivity;

    private GetAjiltan mAuthTask = null;

    TextView idText;
    TextView usernameText;
    TextView firstnameText;
    TextView lastnameText;
    TextView emch_dugaarText;
    TextView aimagText;
    TextView sumText;

    Button editBtn;

    private static String url_get_ajiltan = "http://"+Const.IP_ADDRESS1+":81/mebp/getajiltan.php";
    JSONParser jsonParser = new JSONParser();
    JSONObject json;

    String id;
    String username;
    String firstname;
    String lastname;
    String aimag;
    String sum;
    String emch_dugaar;

    int ajiltan_id;

    public static GetAjiltanFragment newInstance() {
        GetAjiltanFragment fragment = new GetAjiltanFragment();
        return fragment;
    }

    public GetAjiltanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_get_ajiltan, container, false);

        ((HomeActivity) parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        ((HomeActivity) parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity) parentActivity).getSupportActionBar().setHomeButtonEnabled(true);
        ((HomeActivity) parentActivity).result.setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, ListAjiltanFragment.newInstance())
                        .commit();
                ((HomeActivity) parentActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                ((HomeActivity) parentActivity).result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                return true;
            }
        });

        idText = (TextView) rootView.findViewById(R.id.id_text);
        idText.setText(String.valueOf(getActivity().getIntent().getIntExtra("selected_ajiltan_id", 0)));
        usernameText = (TextView) rootView.findViewById(R.id.username_text);
        firstnameText = (TextView) rootView.findViewById(R.id.firstname_text);
        lastnameText = (TextView) rootView.findViewById(R.id.lastname_text);
        emch_dugaarText = (TextView) rootView.findViewById(R.id.emch_code_text);

        aimagText = (TextView) rootView.findViewById(R.id.aimag_text);
        sumText = (TextView) rootView.findViewById(R.id.sum_text);


        editBtn = (Button) rootView.findViewById(R.id.edit_btn);


        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, EditAjiltanFragment.newInstance())
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
        ajiltan_id = getActivity().getIntent().getIntExtra("selected_ajiltan_id", 0);

        if (ajiltan_id == 0) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        } else {
            mAuthTask = new GetAjiltan(parentActivity);
            mAuthTask.execute();
        }
    }


    class GetAjiltan extends AsyncTask<String, String, String> {
        private Activity pActivity;

        public GetAjiltan(Activity parent) {
            this.pActivity = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("ajiltan_id", String.valueOf(ajiltan_id)));

            json = jsonParser.makeHttpRequest(url_get_ajiltan, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                usernameText.setText(json.getString("username"));
                                lastnameText.setText(json.getString("lastname"));
                                firstnameText.setText(json.getString("firstname"));
                                emch_dugaarText.setText(json.getString("emch_dugaar"));
                                aimagText.setText(json.getString("aimag"));
                                sumText.setText(json.getString("sum"));

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
                pActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(pActivity.getBaseContext(),
                                "Алдаа гарлаа!", Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            mAuthTask = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        parentActivity = activity;
        ((HomeActivity) activity).onSectionAttached(7);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
