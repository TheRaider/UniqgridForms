package com.uniqgrid.uniqgridforms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFormsFragment extends Fragment {


    public MyFormsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_my_forms, container, false);


        ((MainActivity) getActivity()).getSupportActionBar().setTitle("My Forms");

        setHasOptionsMenu(true);

        return  customView;
    }

}
