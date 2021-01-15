package com.aphrodite.demo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aphrodite on 2021/1/11.
 */
public class ContactsUtils {

    public static String getAllContacts(Context context) {
        if (null == context) {
            throw new NullPointerException("Context is null");
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (null == contentResolver) {
            throw new NullPointerException("ContentResolver is null");
        }
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (null == cursor) {
            throw new NullPointerException("Cursor is null");
        }

        Map<Object, Object> map = new HashMap<>();
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            map.put(ContactsContract.Contacts._ID, contactId);
            map.put(ContactsContract.Contacts.DISPLAY_NAME, name);
            Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            if (null == phoneCursor) {
                throw new NullPointerException("Cursor of phone is null");
            }
            List<String> phones = new ArrayList<>();
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                phones.add(phone);
            }
            map.put("phone", phones.toString());
            Cursor noteCursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Nickname.NAME},
                    ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE + "'",
                    new String[]{contactId}, null);
            if (null == noteCursor) {
                throw new NullPointerException("Cursor of nickname is null");
            }
            List<String> notes = new ArrayList<>();
            if (noteCursor.moveToFirst()) {
                do {
                    String note = noteCursor.getString(noteCursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                    notes.add(note);
                } while (noteCursor.moveToNext());
            }
            map.put("nickname", notes.toString());
            phoneCursor.close();
            noteCursor.close();
        }
        cursor.close();
        return map.toString();
    }
}
