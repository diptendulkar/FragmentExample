package com.example.diptendudas.fragmentexample;

import android.Manifest;

import android.database.Cursor;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diptendudas.fragmentexample.contact.ContactsAdapter;

import java.util.ArrayList;
// 3rd party lib for progressbar
import is.arontibo.library.ElasticDownloadView;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;


public class Fragment2 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    private static final int CONTACT_ID_INDEX = 0;
    private static final int CONTACTS_LOADER_ID = 1;
    private ContactsAdapter mAdapter;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static String TAG = "Fragment2";

    static int Fragment2counter = 0;

    ListView lvContacts;

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    // @InjectView(R.id.elastic_download_view)
    ElasticDownloadView mElasticDownloadView;

    public Fragment2() {
    }


    public static Fragment2 newInstance(int sectionNumber) {
        Log.i(TAG, "test");
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

        Fragment2counter = 0;

        //  mElasticDownloadView = rootView.findViewById(R.id.elastic_download_view);
        Log.i(TAG, "test");

        lvContacts = (ListView) rootView.findViewById(R.id.lvContacts);
        showContacts();
        return rootView;
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {

            android.support.v4.app.LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(CONTACTS_LOADER_ID, null, this);
            setupCursorAdapter();


            lvContacts.setAdapter(mAdapter);
            lvContacts.setOnItemClickListener(this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
        Log.i(TAG, "listContacts size: ");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define the columns to retrieve
        String[] projectionFields = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.LOOKUP_KEY,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                        ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI};
        // Construct the loader
        // Return the loader for use
        return new CursorLoader(getContext(),
                ContactsContract.Contacts.CONTENT_URI, // URI
                projectionFields, // projection fields
                null, // the selection criteria
                null, // the selection args
                null // the sort order
        );
    }

    private void setupCursorAdapter() {


        // Column data from cursor to bind views from
        String[] uiBindFrom = {Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI};
        // View IDs which will have the respective column data inserted
        int[] uiBindTo = {R.id.contact_name_TV, R.id.contact_image_IV};
        // Create the simple cursor adapter to use for our list
        // specifying the template to inflate (item_contact),
        mAdapter = new ContactsAdapter(
                getContext(), R.layout.contacts_list_item,
                null, uiBindFrom, uiBindTo,
                0);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


}
