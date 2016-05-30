package com.example.mebms.mebms;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.rey.material.widget.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListAjiltanFragment extends ListFragment {
    private OnFragmentInteractionListener mListener;

    ArrayList<String> ajiltan_nerArray = new ArrayList<String>();
    ArrayList<String> ajiltan_codeArray = new ArrayList<String>();
    ArrayList<Integer> ajiltan_idArray = new ArrayList<Integer>();

    private static String url_get_ajiltan = "http://"+Const.IP_ADDRESS1+":81/mebp/ajiltanlist.php";
    private static String url_delete_ajiltan = "http://"+Const.IP_ADDRESS1+":81/mebp/deleteajiltan.php";
    JSONParser jsonParser = new JSONParser();
    private GetAjiltan mListAuthTask = null;
    private DeleteAjiltan mDeleteAuthTask = null;

    private FloatingActionButton addAjiltan;

    JSONObject json;

    ListAjiltanAdapter adapter;
    Activity parentActivity;

    public static ListAjiltanFragment newInstance() {
        ListAjiltanFragment fragment = new ListAjiltanFragment();
        return fragment;
    }

    public ListAjiltanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ajiltan_list,
                container, false);
        addAjiltan = (FloatingActionButton ) rootView.findViewById(R.id.addButtonFloat);
        addAjiltan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, SignUpFragment.newInstance())
                        .commit();
            }
        });



        adapter = new ListAjiltanAdapter(parentActivity,ajiltan_idArray,ajiltan_nerArray,ajiltan_codeArray);
        setListAdapter(adapter);

        getList();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onListItemClick(ListView l, View view, int position,
                                long id) {
        // TODO Auto-generated method stub

        getActivity().getIntent().putExtra("selected_ajiltan_id",ajiltan_idArray.get(position));



        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, GetAjiltanFragment.newInstance())
                .commit();
    }

    private class ListAjiltanAdapter extends BaseSwipeAdapter {

        private Context mContext;

        ArrayList<String> ajiltan_nerArray = new ArrayList<String>();
        ArrayList<String> ajiltan_codeArray = new ArrayList<String>();
        ArrayList<Integer> ajiltan_idArray = new ArrayList<Integer>();

        public ListAjiltanAdapter(Context mContext,ArrayList<Integer> id,ArrayList<String> ajiltan_ner,ArrayList<String> ajiltan_code) {
            this.ajiltan_codeArray=ajiltan_code;
            this.ajiltan_nerArray=ajiltan_ner;
            this.ajiltan_idArray=id;
            this.mContext = mContext;
        }
        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }
        @Override
        public int getCount() {
            return ajiltan_idArray.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View generateView(int position, ViewGroup parent) {
            // If we weren't given a view, inflate one
            View v = LayoutInflater.from(mContext).inflate(R.layout.ajiltan_list_row, null);

            final int p = position;

            SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
            return v;
        }
        @Override
        public void fillValues(int position, View convertView) {

            final int p= position;

            TextView idEdt = (TextView) convertView
                    .findViewById(R.id.id);
            TextView ajiltanNerEdt = (TextView) convertView
                    .findViewById(R.id.ajiltan_ner);
            TextView ajiltanCodeEdt = (TextView) convertView
                    .findViewById(R.id.ajiltan_code);
            idEdt.setText("Дугаар: "+ajiltan_idArray.get(position).toString());
            ajiltanNerEdt.setText("Ажилтаны нэр: "+ajiltan_nerArray.get(position));
            ajiltanCodeEdt.setText("Ажилтаны код: "+ajiltan_codeArray.get(position));
            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("selected_pos",String.valueOf(p));
                    deleteRow(ajiltan_idArray.get(p));
                }
            });
            convertView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("selected_pos",String.valueOf(p));
                    getActivity().getIntent().putExtra("selected_ajiltan_id",ajiltan_idArray.get(p));

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, EditAjiltanFragment.newInstance())
                            .commit();
                }
            });
        }
    }

    private void deleteRow(int ajiltan_id) {
        if (mDeleteAuthTask != null) {
            return;
        }

        mDeleteAuthTask = new DeleteAjiltan(parentActivity,ajiltan_id);
        mDeleteAuthTask.execute();
    }
    class DeleteAjiltan extends AsyncTask<String, String, String> {
        private Activity pActivity;
        private int ajiltan_id;
        public DeleteAjiltan(Activity parent,int ajiltan_id) {
            this.ajiltan_id=ajiltan_id;
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

            json = jsonParser.makeHttpRequest(url_delete_ajiltan, "GET",
                    params);

            try {
                int success = json.getInt("success");

                if (success == 1) {
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            getList();
                            Toast.makeText(pActivity.getBaseContext(),
                                    "Амжилттай устгалаа.", Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, ListAjiltanFragment.newInstance())
                                    .commit();
                        }
                    });
                    getList();
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
            mDeleteAuthTask = null;
        }
    }

    private void getList() {
        if (mListAuthTask != null) {
            return;
        }

        mListAuthTask = new GetAjiltan(parentActivity);
        mListAuthTask.execute();
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


            JSONObject json = jsonParser.makeHttpRequest(url_get_ajiltan, "GET",
                    params);

            try {
                int success = json.getInt("success");
                ajiltan_idArray.clear();
                ajiltan_codeArray.clear();
                ajiltan_nerArray.clear();

                if (success == 1) {
                    for (int i = 0; i < json.getJSONArray("ajiltan").length(); i++) {
                        ajiltan_idArray.add(json.getJSONArray("ajiltan")
                                .getJSONObject(i).getInt("ajiltan_id"));
                        ajiltan_nerArray.add(json.getJSONArray("ajiltan")
                                .getJSONObject(i).getString("ajiltan_ner"));
                        ajiltan_codeArray.add(json.getJSONArray("ajiltan")
                                .getJSONObject(i).getString("ajiltan_code"));
                    }
                    pActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            setListAdapter(null);
                            adapter = new ListAjiltanAdapter(pActivity,ajiltan_idArray,ajiltan_nerArray,ajiltan_codeArray);
                            setListAdapter(adapter);
                            adapter.notifyDataSetChanged();
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
            mListAuthTask=null;
        }
    }
}
