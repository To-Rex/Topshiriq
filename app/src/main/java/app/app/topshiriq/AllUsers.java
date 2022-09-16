package app.app.topshiriq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import app.app.topshiriq.adapters.AdapterAllUser;
import app.app.topshiriq.model.Alldata;

public class AllUsers extends AppCompatActivity {

    ListView listalldata;

    ArrayList<Alldata> dataModalArrayList;
    AdapterAllUser adapterAllUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_all_users);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        listalldata = findViewById(R.id.listalldata);
        listalldata.setDivider(null);
        listalldata.setDividerHeight(20);
        dataModalArrayList = new ArrayList<>();
        adapterAllUser = new AdapterAllUser(dataModalArrayList, this);
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Alldata alldata = new Alldata();
                    alldata.setNumber(ds.getKey());
                    alldata.setName(Objects.requireNonNull(ds.child("name").getValue()).toString());
                    alldata.setPassword(Objects.requireNonNull(ds.child("password").getValue()).toString());
                    alldata.setRole(Objects.requireNonNull(ds.child("role").getValue()).toString());
                    dataModalArrayList.add(alldata);
                    listalldata.setAdapter(adapterAllUser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}