package com.kibtechnologies.captainshieid.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kibtechnologies.captainshieid.R;

public class DasboardActvity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();

    }

    private void init() {
      /*  recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        DashboardOptionsAdapter adapter = new DashboardOptionsAdapter();
        recyclerView.setAdapter(adapter);*/
    }
}