package com.example.sim.network;

import com.example.sim.dto.category.CategoryItemDTO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface CategoriesApi {
    @GET("/api/categories")
    public Call<List<CategoryItemDTO>> list();

    @GET("/api/categories/{id}")
    public Call<CategoryItemDTO> getById(@Path("id") int id);

    @Multipart
    @POST("/api/categories")
    public Call<CategoryItemDTO> create(@PartMap Map<String, RequestBody> params,
                                        @Part MultipartBody.Part image);

    @Multipart
    @PUT("/api/categories")
    public Call<CategoryItemDTO> edit(@PartMap Map<String, RequestBody> params,
                                        @Part MultipartBody.Part image);

}
