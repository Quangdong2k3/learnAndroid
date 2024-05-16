package com.tlu.intentexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button btn_request;
    private EditText txt_a, txt_b, txt_kq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_request = findViewById(R.id.btn_main);
        txt_a = findViewById(R.id.txt_a);
        txt_b = findViewById(R.id.txt_b);
        txt_kq = findViewById(R.id.txt_kq);

        btn_request.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "adasdasd", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            Bundle bundle = new Bundle();
            bundle.putInt("txt_a", Integer.parseInt("0" + txt_a.getText()));
            bundle.putInt("txt_b", Integer.parseInt("0" + txt_b.getText()));
            intent.putExtra("main_data", bundle);
            startActivityForResult(intent,99);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 33 && requestCode == 99) {
            txt_kq.setText(String.valueOf(data.getIntExtra("kq", 0)));
        }
        if(resultCode == 34 && requestCode == 99){
            txt_kq.setText(String.valueOf(data.getIntExtra("kq", 0)));
        }

    }
}