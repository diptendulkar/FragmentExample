package com.example.diptendudas.fragmentexample.contact;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.diptendudas.fragmentexample.R;

public class ContactsAdapter extends SimpleCursorAdapter {


	public ContactsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ImageView contactImageView = (ImageView) view.findViewById(R.id.contact_image_IV);
		TextView contactTextView = (TextView) view.findViewById(R.id.contact_name_TV);
		TextView phoneNumberTextView = (TextView) view.findViewById(R.id.contact_phone_TV);

		String contactName = cursor.getString(cursor.getColumnIndex(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
				ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
				ContactsContract.Contacts.DISPLAY_NAME));
		String imageUriStr = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
		//String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
		InputStream inputStream = null;

		if (imageUriStr != null) {
			try {
				inputStream = context.getContentResolver().openInputStream(Uri.parse(imageUriStr));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			contactImageView.setImageDrawable(Drawable.createFromStream(inputStream, imageUriStr));
		}else{
			contactImageView.setImageResource(R.drawable.contactplacholder);

		}
		contactTextView.setText(contactName);
	//	phoneNumberTextView.setText(phoneNumber);
	}
}
