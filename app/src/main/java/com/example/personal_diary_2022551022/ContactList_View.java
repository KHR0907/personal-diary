package com.example.personal_diary_2022551022;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactList_View extends AppCompatActivity {
    DBModel dbManager;
    SQLiteDatabase db;
    ImageButton rtnHomeBtn;
    ImageButton  fab;

    RecyclerView contactListView;
    ContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        dbManager = new DBModel(this);
        db = dbManager.getWritableDatabase();

        rtnHomeBtn = findViewById(R.id.btn_rtnHome_contacts);
        rtnHomeBtn.setOnClickListener(v -> {
            finish();
        });
        fab = findViewById(R.id.btn_add_contacts);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactList_View.this, AddContect_View.class);
                startActivity(intent);
            }
        });

        Log.wtf("MAIN",dbManager.printData());

        contactListView = (RecyclerView) findViewById(R.id.review_contList_contacts);
        contactListAdapter = new ContactListAdapter();
        contactListAdapter.setOnItemClickListener(new ContactListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(ContactList_View.this, ContactDetail_View.class);
                intent.putExtra("phone", contactListAdapter.mContactList.get(pos).getDetail());
                startActivity(intent);
            }
        });
        contactListAdapter.setOnLongItemClickListener(new ContactListAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ContactList_View.this)
                        .setTitle("연락처 삭제")
                        .setMessage("연락처를 삭제하시겠습니까?\n삭제된 연락처는 복구할 수 없습니다.")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int result = dbManager.deleteContact(contactListAdapter.mContactList.get(pos).getDetail());
                                if(result >= 0){
                                    Toast.makeText(ContactList_View.this, "연락처가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    contactListAdapter.setContactList(dbManager.getContactList());
                                }else if(result == -3){
                                    Toast.makeText(ContactList_View.this, "연락처 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();
            }
        });
        contactListView.setAdapter(contactListAdapter);
        contactListView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ContactItem> contactList = dbManager.getContactList();
        contactListAdapter.setContactList(contactList);




    }

    protected void onResume() {
        super.onResume();
        contactListAdapter.setContactList(dbManager.getContactList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}