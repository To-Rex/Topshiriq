package app.app.topshiriq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button btnadrole;
    Button btnadsaqlash;
    EditText ediadpassword;
    EditText ediadname;
    EditText ediadnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ediadnum = findViewById(R.id.ediadnum);
        ediadname = findViewById(R.id.ediadname);
        ediadpassword = findViewById(R.id.ediadpassword);
        btnadsaqlash = findViewById(R.id.btnadsaqlash);
        btnadrole = findViewById(R.id.btnadrole);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        List<String> userList = new ArrayList<>();

        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    userList.add(ds.getKey());
                    Toast.makeText(Admin.this, ds.getKey(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Admin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnadrole.setOnClickListener(this::ShowPopupMenu);

        btnadsaqlash.setOnClickListener(v -> {
            String number = ediadnum.getText().toString();
            String name = ediadname.getText().toString();
            String password = ediadpassword.getText().toString();
            String role = "0";
            //chesk userList for number
            if (userList.contains(number)) {
                Toast.makeText(this, "Bunday raqamli foydalanuvchi mavjud", Toast.LENGTH_SHORT).show();
            } else {
                if (number.isEmpty() || name.isEmpty() || password.isEmpty() || role.equals(getString(R.string.role))) {
                    ediadname.setError("Barcha maydonlarni to'ldiring");
                    ediadpassword.setError("Barcha maydonlarni to'ldiring");
                    ediadnum.setError("Barcha maydonlarni to'ldiring");
                } else {
                    if (btnadrole.getText().toString().equals(getString(R.string.admin))) {
                        role = "1";
                    }
                    if (btnadrole.getText().toString().equals(getString(R.string.shop_assistant))) {
                        role = "2";
                    }
                    if (btnadrole.getText().toString().equals(getString(R.string.accountant))) {
                        role = "3";
                    }

                    String generatedPassword = null;
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(password.getBytes());
                        byte[] bytes = md.digest();
                        StringBuilder sb = new StringBuilder();
                        for (byte aByte : bytes) {
                            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                        }
                        generatedPassword = sb.toString();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    System.out.println(generatedPassword);
                    //write to database Users number in name and password

                    mDatabase.child("Users").child(number).child("name").setValue(name);
                    mDatabase.child("Users").child(number).child("password").setValue(generatedPassword);
                    mDatabase.child("Users").child(number).child("role").setValue(role);
                }
                Toast.makeText(this, "Foydalanuvchi saqlandi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void ShowPopupMenu(View v) {
        //popup menu for role
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.role, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.Admin:
                    btnadrole.setText(getString(R.string.admin));
                    return true;
                case R.id.Shop_assistant:
                    btnadrole.setText(getString(R.string.shop_assistant));
                    return true;
                case R.id.accountant:
                    btnadrole.setText(getString(R.string.accountant));
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }
}