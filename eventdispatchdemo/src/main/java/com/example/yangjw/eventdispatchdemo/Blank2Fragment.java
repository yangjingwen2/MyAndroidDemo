package com.example.yangjw.eventdispatchdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Blank2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Blank2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Blank2Fragment extends Fragment {


    public Blank2Fragment() {
        // Required empty public constructor
    }


    public static Blank2Fragment newInstance() {
        Blank2Fragment fragment = new Blank2Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false);
    }


}
