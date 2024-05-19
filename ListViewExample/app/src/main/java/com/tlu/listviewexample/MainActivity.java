package com.tlu.listviewexample;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tlu.listviewexample.model.Category;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

        final ArrayList<Category> lst = new ArrayList<>();
        lst.add(new Category(R.drawable.img,"Iphone"));
        lst.add(new Category(R.drawable.readme,"Readme"));
        lst.add(new Category(R.drawable.samsung,"SAMSUNG"));


        MyAdapter adapter = new MyAdapter(this,lst);

        ListView lstView = findViewById(R.id.my_list_view);
        lstView.setAdapter(adapter);


        TextView text_txt = findViewById(R.id.txt_main);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                text_txt.setText(lst.get(position).getTitle());
            }
        });



    }
}