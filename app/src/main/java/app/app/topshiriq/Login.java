package app.app.topshiriq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    String password,names;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_login);

        EditText edilogusernum = findViewById(R.id.edilogusernum);
        TextView txtlogname = findViewById(R.id.txtlogname);
        EditText edilogpass = findViewById(R.id.edilogpass);
        Button btnsubmit = findViewById(R.id.btnsubmit);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Users");

        edilogusernum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2) {

                }
            }
        });

        btnsubmit.setOnClickListener(v -> {
           startActivity(new Intent(Login.this,Admin.class));
        });
    }
}