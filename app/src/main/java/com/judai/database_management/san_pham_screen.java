package com.judai.database_management;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class san_pham_screen extends AppCompatActivity {
    ImageButton addSP;
    ListView listView;
    ArrayList<SanPham> arrayListSP;
    san_pham_adapter adapterSP;
    String _name = "", _price = "", _amout = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_screen);
        addSP = findViewById(R.id.addsp);
        addSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_SP_to_list();
            }
        });
        show_listsp();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String b = arrayListSP.get(i).get_nameSP().toString();
                Toast.makeText(san_pham_screen.this,""+b,Toast.LENGTH_SHORT).show();
                change_data(b);
                
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String b = arrayListSP.get(i).get_nameSP().toString();
                Toast.makeText(san_pham_screen.this,""+b,Toast.LENGTH_SHORT).show();
                arrayListSP.remove(i);
                AlertDialog.Builder alert = new AlertDialog.Builder(san_pham_screen.this);
                alert.setTitle("Xóa sản phẩm").setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapterSP.notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
                return true;
            }
        });
    }

    private void add_SP_to_list() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_sp_dialog, null);
        EditText addnamesp = alertLayout.findViewById(R.id.editname);
        EditText addpricesp = alertLayout.findViewById(R.id.editprice);
        EditText addamoutsp = alertLayout.findViewById(R.id.editamout);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Nhập thông tin sản phẩm")
                .setView(alertLayout)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SanPham sanPham = new SanPham(addnamesp.getText().toString().trim(),
                                Integer.parseInt(addamoutsp.getText().toString().trim()),
                                Integer.parseInt(addpricesp.getText().toString().trim()));
                        MainActivity.myRef.child("Data/DanhMuc/" + danh_muc_screen._nameDM + "/" + addnamesp.getText().toString().trim()).setValue(sanPham);
                        action_reload();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void action_reload(){
        Intent reload = new Intent(san_pham_screen.this,san_pham_screen.class);
        startActivity(reload);
    }

    private void show_listsp() {
        listView = findViewById(R.id.listSP);
        arrayListSP = new ArrayList<>();
        adapterSP = new san_pham_adapter(this,arrayListSP,R.layout.san_pham_adapter);
        listView.setAdapter(adapterSP);
        arrayListSP.clear();
        MainActivity.myRef.child("Data/DanhMuc/" + danh_muc_screen._nameDM.trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String url = snap.getKey();
                    MainActivity.myRef.child("Data/DanhMuc/" + danh_muc_screen._nameDM.trim() + "/" + snap.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           _name= snapshot.child("_nameSP").getValue().toString();
                           _price = snapshot.child("_priceSP").getValue().toString();
                           _amout = snapshot.child("_amoutSP").getValue().toString();
                            arrayListSP.add(new SanPham(_name,Integer.parseInt(_amout),Integer.parseInt(_price)));
                            adapterSP.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    
    public void change_data(String _name){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.change_data_layout,null);
        EditText amoutchange = (EditText) alertLayout.findViewById(R.id.changeamout);
        TextView tvnpsp = alertLayout.findViewById(R.id.htname);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thay đổi số lượng hàng hóa");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        tvnpsp.setText(_name);
        MainActivity.myRef.child("Data/DanhMuc/"+danh_muc_screen._nameDM.trim()+"/"+_name.trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              amoutchange.setText(snapshot.child("_amoutSP").getValue().toString());
                Toast.makeText(san_pham_screen.this,snapshot.child("_amoutSP").getValue().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int amout = Integer.parseInt(amoutchange.getText().toString());
                Toast.makeText(san_pham_screen.this,"Data/DanhMuc/"+danh_muc_screen._nameDM.trim()+"/"+_name.trim()+"/_amoutSP",Toast.LENGTH_LONG).show();
                MainActivity.myRef.child("Data/DanhMuc/"+danh_muc_screen._nameDM.trim()+"/"+_name.trim()+"/_amoutSP").setValue(amout);
                action_reload();
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