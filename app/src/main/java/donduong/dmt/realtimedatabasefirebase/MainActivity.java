package donduong.dmt.realtimedatabasefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matching();

        adapter  = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("contacts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                for (DataSnapshot data: snapshot.getChildren()
                     ) {
                    String key = data.getKey();
                    String value = data.getValue().toString();
                    adapter.add(key + "\n"+value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase", "LoadPost:onCancelled", error.toException());
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String data = adapter.getItem(position);
                String key = data.split("\n")[0];
                Intent intent = new Intent(getApplicationContext(),CapNhatContract.class);
                intent.putExtra("ID", key);
                startActivity(intent);
            }
        });
    }

    private void matching() {
        lv = (ListView) findViewById(R.id.lv_lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnuAdd){
            // new activity
            String data = adapter.getItem(adapter.getCount()-1);
            String sid = data.split("\n")[0];
            int i_id = Integer.parseInt(sid);
            Intent intent = new Intent(this,ThemContact.class);
            intent.putExtra("ID",i_id+1);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.mnuIntro){
            Toast.makeText(this, "Welcome to Mobile Programming",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.mnuSignup){
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}