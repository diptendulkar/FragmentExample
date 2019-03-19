package com.example.diptendudas.fragmentexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diptendudas.fragmentexample.contact.Contact;
import com.example.diptendudas.fragmentexample.contact.ContactFetcher;
import com.example.diptendudas.fragmentexample.contact.ContactsAdapter;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    TextView textView;
    static int Fragment2counter =0;
    ArrayList<Contact> listContacts;
    ListView lvContacts;
    ContactsAdapter adapterContacts;
    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
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
        View rootView = inflater.inflate(R.layout.contacts_list_view, container, false);
//          textView = (TextView) rootView.findViewById(R.id.section_label);
//        textView.setText("Fragment 2 = " + getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        Fragment2counter =0;
      /*  Button btnUpdate = (Button) rootView.findViewById(R.id.button);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment2counter++;
                textView.setText("Button Pressed in Fragment 2: " + Fragment2counter);
            }
        });*/


        lvContacts = (ListView) rootView.findViewById(R.id.lvContacts);
        showContacts();
        return rootView;
    }

    private  void showContacts(){
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            ContactFetcher contactFetcher = new ContactFetcher(getContext());
            listContacts = new ArrayList<>();
            ReadContact readContact = new ReadContact(contactFetcher);
            readContact.execute();


            adapterContacts = new ContactsAdapter(getContext(), listContacts);
            lvContacts.setAdapter(adapterContacts);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ReadContact extends AsyncTask<Void, Void, Boolean> {

        ContactFetcher mContactFetcher;
        public ReadContact(ContactFetcher contactFetcher) {
            mContactFetcher = contactFetcher;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            Looper.prepare();
            listContacts = mContactFetcher.fetchAll();
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                adapterContacts.addAll(listContacts);
                adapterContacts.notifyDataSetChanged();
            }
            //mContactListener.contactComplete(success);
        }

        @Override
        protected void onCancelled() {
            //mContactListener.contactCancelled();
        }
    }


    /*public interface contactListener {
        void contactComplete(final Boolean success);
        void contactCancelled();
    }*/
}
