package com.example.personal_diary_2022551022;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetail extends AppCompatActivity {
    ImageButton rtnContactsListBtn;
    Button updateContactBtn;
    Button shareContactBtn;
    Button cancelBtn;
    String originalData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        String phoneParam = getIntent().getStringExtra("phone");
        DataBaseModel dbManager = new DataBaseModel(this);
        ContactVO contact = dbManager.getContact(phoneParam);
        EditText name = findViewById(R.id.edit_name);
        TextView phone = findViewById(R.id.edit_phone);
        EditText email = findViewById(R.id.edit_email);
        EditText company = findViewById(R.id.edit_company);
        EditText title = findViewById(R.id.edit_title);
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());
        company.setText(contact.getCompany());
        title.setText(contact.getTitle());
        originalData = name.getText().toString() + phone.getText().toString() + email.getText().toString() + company.getText().toString() + title.getText().toString();

        rtnContactsListBtn = findViewById(R.id.btn_rtnHome_detailContacts);
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
            String modifiedData = nameStr + phoneStr + emailStr + companyStr + titleStr;
            if (modifiedData.equals(originalData)) {
                finish();
            } else {
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ContactDetail.this)
                        .setTitle("경고")
                        .setMessage("수정한 내용이 저장되지 않습니다. 정말로 나가시겠습니까?")
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
        updateContactBtn = findViewById(R.id.btn_save);
        updateContactBtn.setOnClickListener(v -> {
            String nameStr = name.getText().toString();
            String phoneStr = phone.getText().toString();
            String emailStr = email.getText().toString();
            String companyStr = company.getText().toString();
            String titleStr = title.getText().toString();
            if (nameStr.isEmpty() || phoneStr.isEmpty()) {
                Toast.makeText(this, "이름과 전화번호는 필수입력 필드입니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            ContactVO updatedContact = new ContactVO(nameStr, phoneStr, emailStr, companyStr, titleStr);
            int result = dbManager.updateContact(updatedContact);
            switch (result) {
                case 0:
                    Toast.makeText(this, "저장됨", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(this, "이름과 전화번호는 필수입력 필드입니다.", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(this, "연락처가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case -3:
                    Toast.makeText(this, "연락처 데이터 수정 실패", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        shareContactBtn = findViewById(R.id.btn_share);
        shareContactBtn.setOnClickListener(v -> {
            String nameStr = name.getText().toString();
            String phoneStr = phone.getText().toString();
            String emailStr = email.getText().toString();
            String companyStr = company.getText().toString();
            String titleStr = title.getText().toString();
            String shareText = "이름: " + nameStr + "\n전화번호: " + phoneStr + "\n이메일: " + emailStr + "\n회사: " + companyStr + "\n직책: " + titleStr;

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "연락처 공유ㅜ");
            startActivity(shareIntent);
//            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ContactDetail_View.this)
//                    .setTitle("연락처 공유")
//                    .setMessage(shareText)
//                    .setPositiveButton("공유", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            // 공유 기능 추가
//                            Intent sendIntent = new Intent();
//                            sendIntent.setAction(Intent.ACTION_SEND);
//                            sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
//                            sendIntent.setType("text/plain");
//
//                            Intent shareIntent = Intent.createChooser(sendIntent, null);
//                            startActivity(shareIntent);
//                        }
//                    })
//                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//            AlertDialog msgDlg = msgBuilder.create();
//            msgDlg.show();
        });
    }
}
