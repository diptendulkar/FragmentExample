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
	private static final int CONTACT_ID_INDEX = 0;

	Context mContext;
	public ContactsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		mContext = context;
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
		// String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
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
	 	phoneNumberTextView.setText(getContactDetails(cursor, cursor.getPosition()));
	}

	public String getContactDetails(Cursor c, int position)
	{

		StringBuilder stringBuilder = new StringBuilder();
		// Get the Cursor
		Cursor cursor = c;
		// Move to the selected contact
		cursor.moveToPosition(position);
		// Get the _ID value
		long mContactId = cursor.getLong(CONTACT_ID_INDEX);

		//Get all phone numbers for the contact
		Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + mContactId, null, null);
		if ((phones != null ? phones.getCount() : 0) > 0)

			stringBuilder.append("Phones \n");

		while (phones != null && phones.moveToNext()) {
			String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

			stringBuilder.append(number + " ");

			switch (type) {
				case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
					stringBuilder.append("(Home)\n");
					break;
				case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
					stringBuilder.append("(Mobile)\n");
					break;
				case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
					stringBuilder.append("(Work)\n");
					break;
			}
		}
		if (phones != null) {
			phones.close();
		}

		//Get all emails for the contact
		stringBuilder.append("\n");


		Cursor emails =  mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + mContactId, null, null);
		if ((emails != null ? emails.getCount() : 0) > 0)
			stringBuilder.append("Emails \n");
		while (emails != null && emails.moveToNext()) {
			String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
			stringBuilder.append(email + " \n");
		}
		if (emails != null) {
			emails.close();
		}
		return stringBuilder.toString();
	}
}
