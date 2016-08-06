package com.subhrajyoti.guardian.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.subhrajyoti.guardian.Db.DBController;
import com.subhrajyoti.guardian.Models.ContactModel;
import com.subhrajyoti.guardian.R;
import com.subhrajyoti.guardian.RecyclerAdapter;
import com.subhrajyoti.guardian.Utils.RecyclerTouchListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<ContactModel> arrayList;
    private LinearLayoutManager linearLayoutManager;
    private String name;
    private String number;
    private DBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);
        dbController = new DBController(this);
        arrayList = new ArrayList<>();
        try {
            arrayList = (ArrayList<ContactModel>) dbController.getAllContacts();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        recyclerAdapter = new RecyclerAdapter(arrayList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                dbController.deleteContact(arrayList.get(position));
                arrayList.remove(position);
                recyclerAdapter.notifyDataSetChanged();

            }
        }));
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (1) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            number = phones.getString(phones.getColumnIndex("data1"));
                        }
                        name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    }
                }
                ContactModel contactModel = new ContactModel(name,number);
                arrayList.add(contactModel);
                recyclerAdapter.notifyDataSetChanged();
                dbController.addContact(contactModel);
                break;
        }
    }

    @OnClick(R.id.fab)
    public void fabPressed(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onDestroy() {
        dbController.close();
        super.onDestroy();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
