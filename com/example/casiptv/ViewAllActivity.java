package com.example.casiptv;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAllActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all); // تأكد من إنشاء هذا الملف في layout

        String title = getIntent().getStringExtra("category_title");
        setTitle(title);

        RecyclerView rv = findViewById(R.id.rvAllItems);
        rv.setLayoutManager(new GridLayoutManager(this, 3)); // عرض 3 أعمدة

        // هنا نقوم بجلب القائمة وتمريرها للمحول (Adapter)
        // rv.setAdapter(new MoviesAdapter(this, list));
    }
}