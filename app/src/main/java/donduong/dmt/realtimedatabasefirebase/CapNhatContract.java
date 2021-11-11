package donduong.dmt.realtimedatabasefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CapNhatContract extends AppCompatActivity {
    EditText id,name,sex,email,address,mobile,home,office;
    Button Update, Delete, Cancle;
    String i_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_contract);
        
        matching();
        // lấy id
        
        getContactDetail();

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("contacts");
                reference.child(String.valueOf(i_id)).removeValue();
                Toast toast = Toast.makeText(getApplicationContext(), "Xóa liên hệ thành công" + i_id, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 100, 200);
                toast.show();
                finish();
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    Toast toast = Toast.makeText(getApplicationContext(),"Sửa thành công contact"+sid, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 100, 200);
                    toast.show();
                    finish();
                } catch (Exception e) {
                    Log.d("error update", e.toString());
                }
            }
        });
    }

    private void getContactDetail() {
        Intent intent = getIntent();
        i_id = intent.getStringExtra("ID");
        id.setEnabled(false);
        id.setText(String.valueOf(i_id));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("contacts");
        reference.child(String.valueOf(i_id)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                HashMap<String,Object> hashMap = (HashMap<String, Object>)  snapshot.getValue();
                name.setText(hashMap.get("name").toString());
                address.setText(hashMap.get("address").toString());
                sex.setText(hashMap.get("gender").toString());
                email.setText(hashMap.get("email").toString());
                home.setText(hashMap.get("home").toString());
                mobile.setText(hashMap.get("mobile").toString());
                office.setText(hashMap.get("office").toString());
                } catch (Exception e) {
                    Log.d("Loi_Json", e.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Loi_DETAIL", error.toString());
            }
        });

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
        Update = (Button) findViewById(R.id.btn_update);
        Cancle = (Button) findViewById(R.id.btn_cancle);
        Delete = (Button) findViewById(R.id.btn_dele);
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}