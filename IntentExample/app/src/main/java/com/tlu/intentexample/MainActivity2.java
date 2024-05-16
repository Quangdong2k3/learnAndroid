package com.tlu.intentexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    private Button btn_sum, btn_tru;
    private EditText txt_a, txt_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        btn_sum = findViewById(R.id.btn_sum);
        btn_tru = findViewById(R.id.btn_tru);
        txt_a = findViewById(R.id.get_A);
        txt_b = findViewById(R.id.get_B);


        Intent intent_get_data = getIntent();

        Bundle bundle = intent_get_data.getBundleExtra("main_data");

        assert bundle != null;
        txt_a.setText(String.valueOf(bundle.getInt("txt_a")));
        txt_b.setText(String.valueOf(bundle.getInt("txt_b")));

        btn_sum.setOnClickListener(v -> {
            int sum = Integer.parseInt(String.valueOf(txt_a.getText()))+Integer.parseInt(String.valueOf(txt_b.getText()));
            intent_get_data.putExtra("kq",sum);
            setResult(33,intent_get_data);
            finish();
        });
        btn_tru.setOnClickListener(v->{
            int tru = Integer.parseInt(String.valueOf(txt_a.getText()))-Integer.parseInt(String.valueOf(txt_b.getText()));
            intent_get_data.putExtra("kq",tru);
            setResult(34,intent_get_data);
            finish();
        });
    }
}