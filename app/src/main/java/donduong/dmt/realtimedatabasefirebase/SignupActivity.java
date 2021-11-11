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

public class SignupActivity extends AppCompatActivity {
    EditText email, password;
    Button signup, signin, forget, cancel;
    TextView error;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        matching();

        auth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Signup
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

                auth.createUserWithEmailAndPassword(semail, spassword)
                        .addOnCompleteListener(SignupActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    error.setText(task.getException().getMessage().toString());
                                }else
                                {
                                    Toast.makeText(getApplicationContext(),"Đăng ký thành công!!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });


            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Signin

            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new fogetPass
            }
        });
    }

    private void matching() {
        email = (EditText) findViewById(R.id.signup_et_Email);
        password = (EditText) findViewById(R.id.signup_et_Password);
        signup = (Button) findViewById(R.id.signup_btn_Signup);
        signin = (Button) findViewById(R.id.signup_btn_Signin);
        forget = (Button) findViewById(R.id.signup_btn_Forgetpassword);
        cancel = (Button) findViewById(R.id.signup_btn_Cancel);
        error = (TextView) findViewById(R.id.signup_tv_Error);

    }
}