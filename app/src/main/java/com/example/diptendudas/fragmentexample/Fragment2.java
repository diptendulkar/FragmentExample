package com.example.diptendudas.fragmentexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment2 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    TextView textView;
    static int Fragment2counter =0;
    public Fragment2() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment2 newInstance(int sectionNumber) {
        Fragment2 fragment = new Fragment2();
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
        textView.setText("Fragment 2 = " + getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        Fragment2counter =0;
        Button btnUpdate = (Button) rootView.findViewById(R.id.button);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment2counter++;
                textView.setText("Button Pressed in Fragment 2: " + Fragment2counter);
            }
        });
        return rootView;
    }
}
