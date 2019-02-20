package com.example.diptendudas.fragmentexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment1 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    TextView textView;
    static int counter =0;
    public Fragment1() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment1 newInstance(int sectionNumber) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
          textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Fragment 1 = " + getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        counter =0;
        Button btnUpdate = (Button) rootView.findViewById(R.id.button);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                textView.setText("Button Pressed in Fragment 1: " + counter);
            }
        });
        return rootView;
    }
}
