package com.example.sim;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.sim.category.CategoriesAdapter;
import com.example.sim.category.CategoryEditActivity;
import com.example.sim.dto.category.CategoryItemDTO;
import com.example.sim.services.ApplicationNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    RecyclerView rcCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcCategories = findViewById(R.id.rcCategories);
        onLoadApp();
    }

    private void onClickEditCategory(CategoryItemDTO category) {
//        Toast.makeText(this, category.getName(), Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(MainActivity.this, CategoryEditActivity.class);
        Intent intent = new Intent(MainActivity.this, CategoryEditActivity.class);
        Bundle b = new Bundle();
        b.putInt("id", category.getId());
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    private void onLoadApp() {
        rcCategories.setHasFixedSize(true);
        rcCategories.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));

        ApplicationNetwork
                .getInstance()
                .getCategoriesApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        List<CategoryItemDTO> items = response.body();
                        CategoriesAdapter ca = new CategoriesAdapter(items,
                                MainActivity.this::onClickEditCategory);
                        rcCategories.setAdapter(ca);
                        //Log.d("--list categories--", String.valueOf(items.size()));
                    }
                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable throwable) {

                    }
                });
    }
}