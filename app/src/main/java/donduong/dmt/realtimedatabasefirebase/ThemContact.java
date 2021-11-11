package donduong.dmt.realtimedatabasefirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemContact extends AppCompatActivity {
    EditText id,name,sex,email,address,mobile,home,office;
    Button Add, Cancle;
    int i_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_contact);

        matching();

        Intent intent = getIntent();
        i_id = intent.getIntExtra("ID", -1);
        id.setEnabled(false);
        id.setText(String.valueOf(i_id));
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulythemmoi();
            }
        });
    }

    private void xulythemmoi() {
        try{
            String sid = id.getText().toString().trim();
            String sname = name.getText().toString().trim();
            String ssex = sex.getText().toString().trim();
            String semail = email.getText().toString().trim();
            String saddress = address.getText().toString().trim();
            String smobile = mobile.getText().toString().trim();
            String shome = home.getText().toString().trim();
            String soffice = office.getText().toString().trim();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("contacts");
            reference.child(sid).child("name").setValue(sname);
            reference.child(sid).child("email").setValue(semail);
            reference.child(sid).child("address").setValue(saddress);
            reference.child(sid).child("gender").setValue(ssex);
            reference.child(sid).child("mobile").setValue(smobile);
            reference.child(sid).child("home").setValue(shome);
            reference.child(sid).child("office").setValue(soffice);
            Toast.makeText(getApplicationContext(),"Thêm thành công contact"+sid, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d("error add", e.toString());
        }
    }

    private void matching() {
        id = (EditText) findViewById(R.id.et_id);
        name = (EditText) findViewById(R.id.et_name);
        sex = (EditText) findViewById(R.id.et_sex);
        email = (EditText) findViewById(R.id.et_email);
        address = (EditText) findViewById(R.id.et_address);
        mobile = (EditText) findViewById(R.id.et_mobile);
        home = (EditText) findViewById(R.id.et_home);
        office = (EditText) findViewById(R.id.et_office);
        Add = (Button) findViewById(R.id.btn_add);
        Cancle = (Button) findViewById(R.id.btn_cancle);
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}