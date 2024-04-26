package com.example.sim;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.category.CategoriesAdapter;
import com.example.sim.category.CategoryEditActivity;
import com.example.sim.contants.Urls;
import com.example.sim.dto.category.CategoryItemDTO;
import com.example.sim.services.ApplicationNetwork;
import com.example.sim.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends BaseActivity {
    RecyclerView rcCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcCategories = findViewById(R.id.rcCategories);
        rcCategories.setHasFixedSize(true);
        rcCategories.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        onLoadData();
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

    private void onClickDeleteCategory(CategoryItemDTO category) {
        //Toast.makeText(this, category.getName(), Toast.LENGTH_LONG).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Видалити "+ category.getName()+"?")
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            ApplicationNetwork.getInstance()
                                    .getCategoriesApi()
                                    .delete(category.getId())
                                    .enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if(response.isSuccessful()) {
                                                onLoadData();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable throwable) {

                                        }
                                    });

                    }
                })
                .setNegativeButton("Ні", null) // No action when user clicks No
                .show();
    }

    private void onLoadData() {
        CommonUtils.showLoading();
        ApplicationNetwork
                .getInstance()
                .getCategoriesApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        CommonUtils.hideLoading();
                        List<CategoryItemDTO> items = response.body();
                        CategoriesAdapter ca = new CategoriesAdapter(items,
                                MainActivity.this::onClickEditCategory,
                                MainActivity.this::onClickDeleteCategory);
                        rcCategories.setAdapter(ca);
                        //Log.d("--list categories--", String.valueOf(items.size()));
                    }
                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable throwable) {
                        CommonUtils.hideLoading();

                    }
                });
    }
}