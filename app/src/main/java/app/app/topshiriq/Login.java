package app.app.topshiriq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Login extends AppCompatActivity {

    String password, names;
    String role;
    boolean value = false;

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
                    usersRef.child(edilogusernum.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                value = true;
                                names = Objects.requireNonNull(snapshot.child("name").getValue()).toString();
                                password = Objects.requireNonNull(snapshot.child("password").getValue()).toString();
                                role = Objects.requireNonNull(snapshot.child("role").getValue()).toString();
                                Toast.makeText(Login.this, password, Toast.LENGTH_SHORT).show();
                                txtlogname.setText(names);
                            } else {
                                value = false;
                                txtlogname.setText("User not found");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else {
                    txtlogname.setText(null);
                }
            }
        });

        btnsubmit.setOnClickListener(v -> {
            if (!edilogusernum.getText().toString().isEmpty() && !edilogpass.getText().toString().isEmpty()) {
                String edipass = null;
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(edilogpass.getText().toString().getBytes());
                    byte[] bytes = md.digest();
                    StringBuilder sb = new StringBuilder();
                    for (byte aByte : bytes) {
                        sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                    }
                    edipass = sb.toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                System.out.println(edipass+ "\n"+ password);
                if (role.equals("0") && Objects.equals(edipass, password)) {
                    startActivity(new Intent(Login.this, Admin.class));
                    password = null;
                    edilogpass.setText(null);
                    edilogusernum.setText(null);
                    txtlogname.setText(null);
                } else {
                    assert edipass != null;
                    if (edipass.equals(password)) {
                        Intent intent = new Intent(Login.this,Sample.class);
                        intent.putExtra("name",names);
                        intent.putExtra("role",role);
                        intent.putExtra("id",edilogusernum.getText().toString());
                        intent.putExtra("password",password);
                        startActivity(intent);
                        password = null;
                        edilogpass.setText(null);
                        edilogusernum.setText(null);
                        txtlogname.setText(null);
                    } else {
                        Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        edilogpass.setText(null);
                    }
                }
            } else {
                edilogpass.setError("Please enter password");
                edilogusernum.setError("Please enter user number");
                Toast.makeText(Login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> {
                    edilogpass.setError(null);
                    edilogusernum.setError(null);
                }, 1500);
            }
        });
    }
}