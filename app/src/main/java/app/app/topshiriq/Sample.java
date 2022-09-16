package app.app.topshiriq;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Sample extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        //get the intent that started this activity
        Intent intent = getIntent();
        //get the string that we passed as an extra
        String name = intent.getStringExtra("name");
        String role = intent.getStringExtra("role");
        String keys = intent.getStringExtra("id");
        String password = intent.getStringExtra("password");

        TextView tv = findViewById(R.id.textView3);
        TextView tv2 = findViewById(R.id.textView4);
        TextView tv3 = findViewById(R.id.textView5);

        tv.setText(keys  +" ----  "+ name);
        if (role.equals("0")) {
            tv2.setText(role+"  "+"Admin");
        }
        if (role.equals("1")) {
            tv2.setText(role+"  "+getString(R.string.admin));
        }
        if (role.equals("2")) {
            tv2.setText(role+"  "+getString(R.string.shop_assistant));
        }
        if (role.equals("3")) {
            tv2.setText(role+"  "+getString(R.string.accountant));
        }
        tv3.setText(password);
    }
    //on back pressed
    /*@Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/

}