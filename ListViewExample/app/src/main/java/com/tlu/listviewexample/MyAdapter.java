package com.tlu.listviewexample;
import com.tlu.listviewexample.model.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import java.util.ArrayList;


public class MyAdapter extends ArrayAdapter<Category> {
    public MyAdapter(@NonNull Context context, ArrayList<Category> lst) {
        super(context, 0, lst);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_view, parent, false);

        }
        Category category = getItem(position);
        ImageView imageView = (ImageView) currentItemView.findViewById(R.id.img_product);
        TextView txtView = (TextView) currentItemView.findViewById(R.id.txt_title);


        assert category != null;
        imageView.setImageResource(category.getImg());

        txtView.setText(category.getTitle());

        return currentItemView;


    }
}
