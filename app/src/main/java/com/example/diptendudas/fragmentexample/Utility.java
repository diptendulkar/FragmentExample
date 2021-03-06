package com.example.diptendudas.fragmentexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Utility {

    public static void addShortcutToHomeScreen(Context context)
    {
        //suman writing a comment
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setAction(Intent.ACTION_VIEW);

        intent1.putExtra("duplicate", false);
        ShortcutInfoCompat pinShortcutInfo = new ShortcutInfoCompat.Builder(context, "Test")
                .setShortLabel("Test")
              .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(intent1)
                .build();

        boolean b = ShortcutManagerCompat.requestPinShortcut(context, pinShortcutInfo, null);
    }

    public static  String getMobileNumber(Context context)
    {
        TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        Toast.makeText(context, "Line Number :" + tMgr.getLine1Number(), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "IMEI Number :" + tMgr.getImei(), Toast.LENGTH_SHORT).show();
        return mPhoneNumber;
    }
}

