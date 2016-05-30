package com.example.mebms.mebms;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

	private Button uvchinListBtn;
	private Button sergiileltListBtn;
	private Button emchilgeeListBtn;
	private Button shinjilgeeListBtn;
	public static HomeFragment newInstance() {
		HomeFragment fragment = new HomeFragment();
		return fragment;
	}

	public HomeFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,  
				    false);
		uvchinListBtn = (Button) rootView.findViewById(R.id.uvchin_list_btn);
		sergiileltListBtn = (Button) rootView.findViewById(R.id.sergiilelt_list_btn);
		emchilgeeListBtn = (Button) rootView.findViewById(R.id.emchilgee_list_btn);
		shinjilgeeListBtn = (Button) rootView.findViewById(R.id.shinjilgee_list_btn);

		uvchinListBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, ListUvchinFragment.newInstance())
						.commit();
			}
		});
		sergiileltListBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, ListSergiileltFragment.newInstance())
						.commit();
			}
		});
		emchilgeeListBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, ListEmchilgeeFragment.newInstance())
						.commit();
			}
		});
		shinjilgeeListBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, ListShinjilgeeFragment.newInstance())
						.commit();
			}
		});

		return rootView;
	}


	@Override  
	 public void onAttach(Activity activity) {  
	  super.onAttach(activity);  
	  ((HomeActivity) activity).onSectionAttached(1);
	 } 
}
