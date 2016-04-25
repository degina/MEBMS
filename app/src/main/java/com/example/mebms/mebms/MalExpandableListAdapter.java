package com.example.mebms.mebms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Personal on 4/12/2016.
 */
public class MalExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, List<Integer>> malCollections;
    private List<Integer> malToo;

    public MalExpandableListAdapter(Activity context, List<Integer> malToo,
                                    Map<String, List<Integer>> malCollections) {
        this.context = context;
        this.malCollections = malCollections;
        this.malToo = malToo;
    }

    public Object getChild(int groupPosition, int childPosition) {
        String mal = "";
        switch (groupPosition){
            case 0:
                mal= context.getResources().getString(R.string.honi);
                break;
            case 1:
                mal= context.getResources().getString(R.string.yamaa);
                break;
            case 2:
                mal= context.getResources().getString(R.string.uher);
                break;
            case 3:
                mal= context.getResources().getString(R.string.mori);
                break;
            case 4:
                mal= context.getResources().getString(R.string.temee);
                break;
        }
        return malCollections.get(mal).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Integer malTooT = (Integer) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mal_uvchin_too_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.arga_hemjee);
        switch (childPosition){
            case 0:
                item.setText(R.string.zuvlusun);
                break;
            case 1:
                item.setText(R.string.emchilsen);
                break;
            case 2:
                item.setText(R.string.edgersen);
                break;
            case 3:
                item.setText(R.string.uhsen);
                break;
            case 4:
                item.setText(R.string.ustgasan);
                break;

        }

        EditText too = (EditText) convertView.findViewById(R.id.arga_hemjee_too_edt);
        too.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int all = 0;
                all = all + (Integer)getChild(groupPosition,0);
                all = all + (Integer)getChild(groupPosition,1);
                all = all + (Integer)getChild(groupPosition,2);
                all = all + (Integer)getChild(groupPosition,3);
                all = all + (Integer)getChild(groupPosition,4);

                malCollections.get(malToo.get(groupPosition)).set(childPosition,Integer.parseInt(s.toString()));

                malToo.set(groupPosition,all);

                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        Log.d("size1:",String.valueOf(malCollections.get(malToo.get(groupPosition)).size()));
        return malCollections.get(malToo.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return malToo.get(groupPosition);
    }

    public int getGroupCount() {
        Log.d("size2:",String.valueOf(malToo.size()));
        return malToo.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.mal_uvchin_too_header,
                    null);
        }
        TextView malTextView = (TextView) convertView.findViewById(R.id.mal);
        TextView malTooTextView = (TextView) convertView.findViewById(R.id.mal_too);

        malTextView.setTypeface(null, Typeface.BOLD);

        switch (groupPosition){
            case 1:
                malTextView.setText(R.string.honi);
                break;
            case 2:
                malTextView.setText(R.string.yamaa);
                break;
            case 3:
                malTextView.setText(R.string.uher);
                break;
            case 4:
                malTextView.setText(R.string.mori);
                break;
            case 5:
                malTextView.setText(R.string.temee);
                break;
        }

        malTooTextView.setText(malToo.get(groupPosition).toString());
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

}
