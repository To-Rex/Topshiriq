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
                    usersRef.child(s.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String name = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                                if (!name.isEmpty()){
                                    value = name.substring(1,2);
                                    Toast.makeText(Login.this, value, Toast.LENGTH_SHORT).show();
                                    names = name.substring(1, name.length() - 1).split(",")[0].split("=")[1];
                                    txtlogname.setText(names);
                                    password = name.substring(1, name.length() - 1).split(",")[1].split("=")[1];
                                    Toast.makeText(Login.this, password, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    txtlogname.setText("");
                                }
                            } else {
                                Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("TAG", "getUser:onCancelled", databaseError.toException());
                        }
                    });
                }
            }
        });

        btnsubmit.setOnClickListener(v -> {
            if(!edilogpass.getText().toString().isEmpty()){
                if (edilogpass.getText().toString().equals(password)){
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.putExtra("name",names);
                    intent.putExtra("value",value);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }else {
                edilogpass.setError("Enter Password");
            }

        });
    }
}