package donduong.dmt.realtimedatabasefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {
    EditText email, password;
    Button signup, signin, forget, cancel;
    TextView error;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        matching();

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            Toast.makeText(getApplicationContext(),"Đăng Nhập thành công!!", Toast.LENGTH_LONG).show();
            finish();
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semail = email.getText().toString().trim();
                String spassword = password.getText().toString().trim();
                //checkemail
                if(TextUtils.isEmpty(semail)){
                    error.setText("Bạn hãy nhập email!!");
                    return;
                }
                //checkpass
                if(TextUtils.isEmpty(spassword)){
                    error.setText("Bạn hãy nhập mật khẩu!!");
                    return;
                }
                //checkkickthuocpass
                if(spassword.length()<=6){
                    error.setText("Mật khẩu quá ngắn!!");
                    return;
                }

                auth.signInWithEmailAndPassword(semail, spassword).addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            error.setText(task.getException().getMessage().toString());
                        }else
                        {
                            Toast.makeText(getApplicationContext(),"Đăng Nhập thành công!!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SigninActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void matching() {
        email = (EditText) findViewById(R.id.signin_et_Email);
        password = (EditText) findViewById(R.id.signin_et_Password);
        signup = (Button) findViewById(R.id.signin_btn_signup);
        signin = (Button) findViewById(R.id.signin_btn_Signin);
        forget = (Button) findViewById(R.id.signin_btn_forget);
        cancel = (Button) findViewById(R.id.signin_btn_Cancel);
        error = (TextView) findViewById(R.id.signin_tv_Error);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}