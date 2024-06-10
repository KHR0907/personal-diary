package com.example.personal_diary_2022551022;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContect_View extends AppCompatActivity{

    ImageButton rtnContactsListBtn;
    Button addContactBtn;
    Button cancelBtn;

    EditText name;
    EditText phone;
    EditText email;
    EditText company;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);
        name = findViewById(R.id.edit_name);
        phone = findViewById(R.id.edit_phone);
        email = findViewById(R.id.edit_email);
        company = findViewById(R.id.edit_company);
        title = findViewById(R.id.edit_title);

        rtnContactsListBtn = findViewById(R.id.btn_rtnHome_addContacts);
        rtnContactsListBtn.setOnClickListener(v -> {
            finish();
        });
        cancelBtn = findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(v -> {
            String nameStr = name.getText().toString();
            String phoneStr = phone.getText().toString();
            String emailStr = email.getText().toString();
            String companyStr = company.getText().toString();
            String titleStr = title.getText().toString();
            if(nameStr.isEmpty() && phoneStr.isEmpty() && emailStr.isEmpty() && companyStr.isEmpty() && titleStr.isEmpty()) {
                finish();
            }else{
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(AddContect_View.this)
                        .setTitle("경고")
                        .setMessage("작성한 내용이 저장되지 않습니다. 정말로 나가시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
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
        addContactBtn = findViewById(R.id.btn_save);
        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get name,phone,email,company.title from name EditText and add new record to database
                String nameStr = name.getText().toString();
                String phoneStr = phone.getText().toString();
                String emailStr = email.getText().toString();
                String companyStr = company.getText().toString();
                String titleStr = title.getText().toString();
                if(nameStr.isEmpty() || phoneStr.isEmpty()) {
                    Toast toast = Toast.makeText(AddContect_View.this, "이름과 전화번호는 필수 입력필드입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(phoneStr.length()<11){
                    Toast toast = Toast.makeText(AddContect_View.this, "전화번호는 11자리 이상 입력해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                DBModel dbModel = new DBModel(AddContect_View.this);
                dbModel.addContact(nameStr, phoneStr, emailStr, companyStr, titleStr);

                Intent intent = new Intent(AddContect_View.this, ContactList_View.class);
                startActivity(intent);
            }
        });


    }
}
