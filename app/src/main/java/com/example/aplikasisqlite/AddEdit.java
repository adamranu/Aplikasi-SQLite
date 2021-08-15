package com.example.aplikasisqlite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasisqlite.helper.DbHelper;

public class AddEdit extends AppCompatActivity {
    EditText txt_id, txt_name, txt_address;
    Button btn_submit, btn_cancel;
    DbHelper SQLite = new DbHelper(this);
    String id, name, address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id      = (EditText) findViewById(R.id.txt_id);
        txt_address = (EditText) findViewById(R.id.txt_address);
        txt_name    = (EditText) findViewById(R.id.txt_name);
        btn_cancel  = (Button)  findViewById(R.id.btn_cancel);
        btn_submit  = (Button) findViewById(R.id.btn_submit);

        id          = getIntent().getStringExtra(MainActivity.TAG_ID);
        name        = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address     = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        if (id == null || id == ""){
            setTitle("Add Data");
        }else {
            setTitle("Edit Data");
            txt_id.setText(id);
            txt_name.setText(name);
            txt_address.setText(address);
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (txt_id.getText().toString().equals("")){
                        save();
                    }else {
                        edit();
                    }
                }catch (Exception e){
                    Log.e("Submit", e.toString());
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }

    public void onBackPressed(){
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
            blank();
            this.finish();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank(){
        txt_name.requestFocus();
        txt_id.setText(null);
        txt_name.setText(null);
        txt_address.setText(null);
    }

    //menyimpan data ke sqlite
    private void save(){
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
        String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("")){
            Toast.makeText(getApplicationContext(), "Please Input name or address . . . ", Toast.LENGTH_LONG).show();
        }else {
            SQLite.insert(txt_name.getText().toString().trim(), txt_address.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void edit(){
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("")){
            Toast.makeText(getApplicationContext(), "Please Input name or address . . . ", Toast.LENGTH_LONG).show();
        }else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()), txt_name.getText().toString().trim(),
                    txt_address.getText().toString().trim());
            blank();
            finish();
        }
    }
}
