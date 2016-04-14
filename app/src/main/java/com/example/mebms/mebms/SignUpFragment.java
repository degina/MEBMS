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

    DatePicker birthdayPicker;

    ArrayAdapter<CharSequence> aimag_adapter;
    ArrayAdapter<CharSequence> sum_adapter;

    Spinner aimag_spinner;
    Spinner sum_spinner;


    RadioButton maleRadioBtn;
    RadioButton femaleRadioBtn;
    RadioGroup genderRadioGroup;
    Button okBtn;

    private static String url_sign_up = "http://10.0.2.2:81/mbms/signup.php";
    JSONParser jsonParser = new JSONParser();

    String username;
    String password;
    String firstname;
    String lastname;
    String aimag;
    String sum;
    String emch_dugaar;
    Date birthday;
    String gender;

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
        emch_dugaarEdt = (EditText) rootView.findViewById(R.id.emch_id_edt);

        birthdayPicker = (DatePicker) rootView.findViewById(R.id.birthday_picker);

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



        maleRadioBtn = (RadioButton) rootView.findViewById(R.id.male_radio_button);
        femaleRadioBtn = (RadioButton) rootView.findViewById(R.id.female_radio_button);
        genderRadioGroup = (RadioGroup) rootView.findViewById(R.id.gender_radio_group);

        okBtn = (Button) rootView.findViewById(R.id.ok_btn);


        okBtn.setOnClickListener(new OnClickListener() {
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
        username = usernameEdt.getText().toString();
        password = passwordEdt.getText().toString();
        firstname = lastnameEdt.getText().toString();
        lastname = lastnameEdt.getText().toString();
        emch_dugaar = emch_dugaarEdt.getText().toString();
        aimag = aimag_spinner.getSelectedItem().toString();
        sum = sum_spinner.getSelectedItem().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.set(birthdayPicker.getYear(), birthdayPicker.getMonth(), birthdayPicker.getDayOfMonth(),
                0, 0, 0);
        birthday = new Date(calendar.getTimeInMillis());
        if (genderRadioGroup.getCheckedRadioButtonId() == maleRadioBtn
                .getId())
                gender="male";
        else
            gender="female";

        mAuthTask = new SignUp(parentActivity);
        mAuthTask.execute();
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
            params.add(new BasicNameValuePair("birthday", birthday.toString()));
            params.add(new BasicNameValuePair("gender", gender));

            JSONObject json = jsonParser.makeHttpRequest(url_sign_up, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(pActivity.getBaseContext(),
                                    "Амжилттай бүртэглээ.", Toast.LENGTH_LONG).show();
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
        ((HomeActivity) activity).onSectionAttached(3);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
