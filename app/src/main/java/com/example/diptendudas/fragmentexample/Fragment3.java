package com.example.diptendudas.fragmentexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment3 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    TextView textView;
    static int Fragment3counter =0;
    int PERMISSIONS_REQUEST_READ_PHONE_STATE = 111;
    public Fragment3() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment3 newInstance(int sectionNumber) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        requestPermissions(new String[]{Manifest.permission.READ_PHONE_NUMBERS}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
          textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Fragment 3 = " + getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        Fragment3counter =0;
        Button btnUpdate = (Button) rootView.findViewById(R.id.button);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment3counter++;
                textView.setText("Button Pressed in Fragment 3: " + Fragment3counter);
            }
        });
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                textView.setText("Current Phone Number is :" + Utility.getMobileNumber(getContext()));
                Toast.makeText(getContext(), "Phone Number :" + Utility.getMobileNumber(getContext()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, we cannot display the Phone Number", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
