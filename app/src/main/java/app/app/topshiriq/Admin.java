package app.app.topshiriq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.password4j.Hash;
import com.password4j.PBKDF2Function;
import com.password4j.Password;
import com.password4j.types.Hmac;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

        btnadrole.setOnClickListener(this::ShowPopupMenu);

        btnadsaqlash.setOnClickListener(v -> {
            String number = ediadnum.getText().toString();
            String name = ediadname.getText().toString();
            String password = ediadpassword.getText().toString();
            String generatedPassword = null;
            try
            {
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
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").child(number).child("name").setValue(name);
            mDatabase.child("Users").child(number).child("password").setValue(generatedPassword);
            Toast.makeText(Admin.this, "Saqlandi", Toast.LENGTH_SHORT).show();
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