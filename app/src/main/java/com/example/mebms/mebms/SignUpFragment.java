package com.example.mebms.mebms;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class SignUpFragment extends Fragment {

    Activity parentActivity;

    private SignUp mAuthTask = null;

    EditText usernameEdt;
    EditText passwordEdt;
    EditText repeatEdt;
    EditText firstnameEdt;
    EditText lastnameEdt;
    EditText emch_dugaarEdt;

    ArrayAdapter<CharSequence> aimag_adapter;
    ArrayAdapter<CharSequence> sum_adapter;

    Spinner aimag_spinner;
    Spinner sum_spinner;


    Button okBtn;

    private static String url_sign_up = "http://10.0.2.2:81/mebp/signup.php";
    JSONParser jsonParser = new JSONParser();

    String username;
    String password;
    String firstname;
    String lastname;
    String aimag;
    String sum;
    String emch_dugaar;

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }
    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_sign_up, container, false);

        usernameEdt = (EditText) rootView.findViewById(R.id.username_edt);
        passwordEdt = (EditText) rootView.findViewById(R.id.password_edt);
        repeatEdt = (EditText) rootView.findViewById(R.id.repeat_edt);
        firstnameEdt = (EditText) rootView.findViewById(R.id.firstname_edt);
        lastnameEdt = (EditText) rootView.findViewById(R.id.lastname_edt);
        emch_dugaarEdt = (EditText) rootView.findViewById(R.id.emch_code_edt);

        aimag_spinner = (Spinner) rootView.findViewById(R.id.aimag_spinner);
        aimag_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.aimag_array, android.R.layout.simple_spinner_item);
        aimag_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aimag_spinner.setAdapter(aimag_adapter);

        aimag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long id) {
                int array_id=R.array.sum_array_1;

                switch(position){
                    case 0:
                        array_id=R.array.sum_array_1;
                        break;
                    case 1:
                        array_id=R.array.sum_array_2;
                        break;
                    case 2:
                        array_id=R.array.sum_array_3;
                        break;
                    case 3:
                        array_id=R.array.sum_array_4;
                        break;
                    case 4:
                        array_id=R.array.sum_array_5;
                        break;
                    case 5:
                        array_id=R.array.sum_array_6;
                        break;
                    case 6:
                        array_id=R.array.sum_array_7;
                        break;
                    case 7:
                        array_id=R.array.sum_array_8;
                        break;
                    case 8:
                        array_id=R.array.sum_array_9;
                        break;
                    case 9:
                        array_id=R.array.sum_array_10;
                        break;
                    case 10:
                        array_id=R.array.sum_array_11;
                        break;
                    case 11:
                        array_id=R.array.sum_array_12;
                        break;
                    case 12:
                        array_id=R.array.sum_array_13;
                        break;
                    case 13:
                        array_id=R.array.sum_array_14;
                        break;
                    case 14:
                        array_id=R.array.sum_array_15;
                        break;
                    case 15:
                        array_id=R.array.sum_array_16;
                        break;
                    case 16:
                        array_id=R.array.sum_array_17;
                        break;
                    case 17:
                        array_id=R.array.sum_array_18;
                        break;
                    case 18:
                        array_id=R.array.sum_array_19;
                        break;
                    case 19:
                        array_id=R.array.sum_array_20;
                        break;
                    case 20:
                        array_id=R.array.sum_array_21;
                        break;
                    case 21:
                        array_id=R.array.sum_array_22;
                        break;
                }

                sum_adapter = ArrayAdapter.createFromResource(parentActivity.getBaseContext(),
                        array_id, android.R.layout.simple_spinner_item);
                sum_spinner.setAdapter(sum_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        sum_spinner = (Spinner) rootView.findViewById(R.id.sum_spinner);
        sum_adapter = ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.sum_array_1, android.R.layout.simple_spinner_item);
        sum_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sum_spinner.setAdapter(sum_adapter);




        okBtn = (Button) rootView.findViewById(R.id.ok_btn);


        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }

        });

        return rootView;
    }

    private void attemptSignUp() {
        if (mAuthTask != null) {
            return;
        }
        if(usernameEdt.getText().toString().equals("") || passwordEdt.getText().toString().equals("") || firstnameEdt.getText().toString().equals("")
                || lastnameEdt.getText().toString().equals("") || emch_dugaarEdt.getText().toString().equals("")) {
            Toast.makeText(parentActivity.getBaseContext(), "Шаардлагатай нүдийг бөглөнө үү!", Toast.LENGTH_LONG).show();
        }else {
            username = usernameEdt.getText().toString();
            password = passwordEdt.getText().toString();
            firstname = firstnameEdt.getText().toString();
            lastname = lastnameEdt.getText().toString();
            emch_dugaar = emch_dugaarEdt.getText().toString();
            aimag = aimag_spinner.getSelectedItem().toString();
            sum = sum_spinner.getSelectedItem().toString();


            mAuthTask = new SignUp(parentActivity);
            mAuthTask.execute();
        }
    }

    class SignUp extends AsyncTask<String, String, String> {
        private Activity pActivity;

        public SignUp(Activity parent) {
            this.pActivity = parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("firstname", firstname));
            params.add(new BasicNameValuePair("lastname", lastname));
            params.add(new BasicNameValuePair("emch_dugaar", emch_dugaar));
            params.add(new BasicNameValuePair("aimag", aimag));
            params.add(new BasicNameValuePair("sum", sum));
            params.add(new BasicNameValuePair("birthday",""));
            params.add(new BasicNameValuePair("gender", ""));

            JSONObject json = jsonParser.makeHttpRequest(url_sign_up, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(pActivity.getBaseContext(),
                                    "Амжилттай бүртгэлээ.", Toast.LENGTH_LONG).show();
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
