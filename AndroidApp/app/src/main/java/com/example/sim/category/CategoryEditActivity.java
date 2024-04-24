package com.example.sim.category;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.BaseActivity;
import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.contants.Urls;
import com.example.sim.dto.category.CategoryItemDTO;
import com.example.sim.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryEditActivity extends BaseActivity {

    int id=0;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String filePath;
    TextInputLayout tlCategoryNameEdit;
    TextInputLayout tlCategoryDescriptionEdit;
    private ImageView ivSelectImageEdit;

    private final String TAG="CategoryEditActivity";
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        Bundle b = getIntent().getExtras();
        if(b!=null)
            id=b.getInt("id");
        initData();
    }

    private void initData() {

        isStoragePermissionGranted();

        tlCategoryNameEdit = findViewById(R.id.tlCategoryNameEdit);
        tlCategoryDescriptionEdit = findViewById(R.id.tlCategoryDescriptionEdit);
        ivSelectImageEdit = findViewById(R.id.ivSelectImageEdit);

        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                .getById(id)
                .enqueue(new Callback<CategoryItemDTO>() {
                    @Override
                    public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                        if(response.isSuccessful()) {
                            CategoryItemDTO item = response.body();
                            tlCategoryNameEdit.getEditText().setText(item.getName());
                            tlCategoryDescriptionEdit.getEditText().setText(item.getDescription());
                            String image = item.getImage();

                            String url = "https://img.freepik.com/free-vector/man-saying-no-concept-illustration_114360-19591.jpg";
                            if(image!=null) {
                                url = Urls.BASE+"/images/"+image;
                            }
                            Glide
                                    .with(CategoryEditActivity.this)
                                    .load(url)
                                    .apply(new RequestOptions().override(300))
                                    .into(ivSelectImageEdit);
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {

                    }
                });

    }

    public void openGalleryEdit(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            Uri uri = data.getData();

            Glide
                    .with(this)
                    .load(uri)
                    .apply(new RequestOptions().override(300))
                    .into(ivSelectImageEdit);

            // If you want to get the file path from the URI, you can use the following code:
            filePath = getPathFromURI(uri);
        }
    }

    // This method converts the image URI to the direct file system path of the image file
    private String getPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }

    public void onClickEditCategory(View view) {
        try {
            String name = tlCategoryNameEdit.getEditText().getText().toString().trim();
            String description = tlCategoryDescriptionEdit.getEditText().getText().toString().trim();

            Map<String, RequestBody> params = new HashMap<>();
            params.put("id", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id)));
            params.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
            params.put("description", RequestBody.create(MediaType.parse("text/plain"), description));

            MultipartBody.Part imagePart=null;
            if (filePath != null) {
                File imageFile = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
                imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
            }

            ApplicationNetwork.getInstance()
                    .getCategoriesApi()
                    .edit(params, imagePart)
                    .enqueue(new Callback<CategoryItemDTO>() {
                        @Override
                        public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                            if(response.isSuccessful())
                            {
                                Intent intent = new Intent(CategoryEditActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {

                        }
                    });
        }
        catch(Exception ex) {
            Log.e("--CategoryCreateActivity--", "Problem create "+ ex.getMessage());
        }
    }


}