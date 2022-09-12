package com.judai.database_management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class danh_muc_screen extends AppCompatActivity {
    GridView gvdm;
    ArrayList<String> _list;
    ArrayAdapter<String> adapter;
    ImageButton adddm;
    public static String _nameDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_screen);
        gvdm = findViewById(R.id.gvdm);
        adddm = findViewById(R.id.adddm);
        _list = new ArrayList<>();
        show_data();
        adddm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaydialog();
            }
        });
        gvdm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                _nameDM = adapter.getItem(i).toString().trim();
                Intent gotoSP = new Intent(danh_muc_screen.this,san_pham_screen.class);
                startActivity(gotoSP);
            }
        });
        gvdm.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String a = adapter.getItem(i).toString().trim();
                Toast.makeText(getBaseContext(),a,Toast.LENGTH_LONG).show();
                MainActivity.myRef.child("Data/DanhMuc").child(a).removeValue();
                Intent reload = new Intent(danh_muc_screen.this,danh_muc_screen.class);
                startActivity(reload);
                return true;
            }
        });

    }

    public void show_data(){
        MainActivity.myRef.child("Data/DanhMuc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String d = snapshot.getKey().toString();
                _list.add(d);
                Set<String> set = new HashSet<String>(_list);
                List<String> xoatrung = new ArrayList<String>(set);
                adapter = new ArrayAdapter<>(danh_muc_screen.this, android.R.layout.simple_list_item_1,xoatrung);
                gvdm.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void displaydialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_custom,null);
        EditText addnamedm = (EditText) alertLayout.findViewById(R.id.tendm);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Nhập thông tin danh mục");
        alert.setView(alertLayout);
        alert.setCancelable(false);
       alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               Toast.makeText(getBaseContext(),addnamedm.getText().toString().trim(),Toast.LENGTH_SHORT).show();
               MainActivity.myRef.child("Data/DanhMuc/"+addnamedm.getText().toString().trim()).setValue(addnamedm.getText().toString().trim());
           }
       });
       alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

           }
       });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}