package sg.edu.rp.c346.id20016584.demodatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
Button btninsert, btngettask;
TextView tvResult;
ListView lv;
ArrayList<Task> al;
ArrayAdapter<Task> aa;
EditText etTask, etDate;
boolean asc=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btninsert=findViewById(R.id.buttoninsert);
        btngettask=findViewById(R.id.buttongettask);
        tvResult=findViewById(R.id.textViewresult);
        lv=findViewById(R.id.lv);
        etDate=findViewById(R.id.editTextdate);
        etTask=findViewById(R.id.editTextTask);
        al= new ArrayList<>();
        aa= new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh=new DBHelper(MainActivity.this);
                dbh.insertTask(etTask.getText().toString(), etDate.getText().toString());
            }
        });
        btngettask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db= new DBHelper(MainActivity.this);
                al= db.getTasks(asc);
                db.close();
                asc=!asc;
                aa=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,al);
                lv.setAdapter(aa);


            }
        });
    }
}