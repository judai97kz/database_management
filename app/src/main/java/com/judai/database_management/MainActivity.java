package com.judai.database_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText _username,_password;
    Button _login_button;
    String _uninput,_pwinput;

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map();
    }

    void Map(){
        _username = findViewById(R.id.username);
        _password = findViewById(R.id.password);
        _login_button = findViewById(R.id.buttonLogin);
        _login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_Process();
            }
        });
    }

    void Login_Process(){
        _uninput = _username.getText().toString();
        _pwinput = _password.getText().toString();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Data/User/"+_uninput)){
                    myRef.child("Data/User/"+_uninput).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue().toString().trim().contains(_pwinput)==true){
                                Toast.makeText(MainActivity.this,"Thành công",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,danh_muc_screen.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

