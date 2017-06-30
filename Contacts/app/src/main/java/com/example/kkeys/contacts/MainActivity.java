package com.example.kkeys.contacts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

        private ListView mListView;
        String phoneNumber = null;
        StringBuffer outputName;
        StringBuffer outputNumber;
        TextView textView;
        private ProgressDialog pDialog;
        private Handler updateBarHandler;
        ArrayList<String> contactListName;
        ArrayList<String> contactListNumber;

    Cursor cursor;
        int counter;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Reading contacts...");
            pDialog.setCancelable(false);
            pDialog.show();
            mListView = (ListView) findViewById(R.id.listview);
            textView = (TextView) findViewById(R.id.textView);
            updateBarHandler =new Handler();
            // Since reading contacts takes more time, let's run it on a separate thread.

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getContacts();
                }
            }).start();
            // Set onclicklistener to the list item.

        }
        public void getContacts() {
            contactListName = new ArrayList<String>();
            contactListNumber = new ArrayList<String>();
            Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
            String _ID = ContactsContract.Contacts._ID;
            String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
            String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
            Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

            ContentResolver contentResolver = getContentResolver();
            cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
            // Iterate every contact in the phone
            if (cursor.getCount() > 0) {
                counter = 0;
                while (cursor.moveToNext()) {
                    outputName = new StringBuffer();
                    outputNumber = new StringBuffer();
                    // Update the progress message
                    updateBarHandler.post(new Runnable() {
                        public void run() {
                            pDialog.setMessage("Reading contacts : "+ counter++ +"/"+cursor.getCount());
                        }
                    });
                    String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                    String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
                    if (hasPhoneNumber > 0) {
                        outputName.append(name);
                        //This is to read multiple phone numbers associated with the same contact
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            outputNumber.append(phoneNumber);

                            mListView.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    //TODO Do whatever you want with the list data
                                    Toast.makeText(getApplicationContext(), "item clicked : \n"+ contactListName.get(position), Toast.LENGTH_SHORT).show();
                                    Log.e("Numbers", contactListName.get(position));
                                    Log.d(TAG,"Numbers"+ contactListNumber.get(position).toString());
                                    textView.setText(contactListNumber.get(position).toString());
                                }
                            });
                        }
                        phoneCursor.close();
                    }
                    // Add the contact to the ArrayList
                    contactListName.add(outputName.toString() + " " + outputNumber.toString());
                    contactListNumber.add(outputNumber.toString());
                }
                // ListView has to be updated using a ui thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, contactListName);
                        mListView.setAdapter(adapter);
                    }
                });
                // Dismiss the progressbar after 500 millisecondds
                updateBarHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.cancel();
                    }
                }, 500);
            }
        }
    }
//    TextView textDetail;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textDetail = (TextView) findViewById(R.id.textView);
//        readContacts();
//    }
//
//    public void readContacts() {
//        StringBuffer sb = new StringBuffer();
//        sb.append("......Contact Details.....");
//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        String phone = null;
//        if (cur.getCount() > 0) {
//
//            while (cur.moveToNext()) {
//
//                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//
//                    sb.append("\n Name: " + name);
//                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
//
//                    while (pCur.moveToNext()) {
//                        phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        sb.append(" "+phone);
////                        System.out.println("phone" + phone);
//                        //textDetail.setText(name + " " + phone);
//                    }
//
//                    pCur.close();
//
//                }
//            }
//        }
//        textDetail.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view)
//            {
//                if(view.getId()==R.id.textView1)
//                {
//                    Toast.makeText(MainActivity.this, "clicked "+textDetail.getText(), Toast.LENGTH_SHORT).show();
//                    Intent i=new Intent();
//                    i.setClass(getApplicationContext(), MainActivity.class);
//                    startActivity(i);
//                }
//            }
//        });
//
//        textDetail.setMovementMethod(new ScrollingMovementMethod());
//        textDetail.setText(sb);
//    }
//}
//
//
