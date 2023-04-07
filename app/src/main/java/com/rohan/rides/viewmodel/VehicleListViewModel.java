package com.rohan.rides.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rohan.rides.model.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VehicleListViewModel extends ViewModel {

    private final MutableLiveData<List<Vehicle>> vehicleList = new MutableLiveData<>();

    public LiveData<List<Vehicle>> getVehicleList() {
        return vehicleList;
    }

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public void setIsLoading(MutableLiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    public void retrieveVehicles(int count) {
        isLoading.postValue(true); // Set isLoading to true before making the API call
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("random-data-api.com")
                .addPathSegment("api")
                .addPathSegment("vehicle")
                .addPathSegment("random_vehicle")
                .addQueryParameter("size", Integer.toString(count))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String json = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        List<Vehicle> vehicles = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String makeAndModel = jsonObject.getString("make_and_model");
                            String vin = jsonObject.getString("vin");
                            String color = jsonObject.getString("color");
                            String carType = jsonObject.getString("car_type");
                            String kilometrage = jsonObject.getString("kilometrage");
                            vehicles.add(new Vehicle(makeAndModel, vin, color, carType, kilometrage));
                        }
                        Collections.sort(vehicles, new Comparator<Vehicle>() {
                            @Override
                            public int compare(Vehicle o1, Vehicle o2) {
                                return o1.getVin().compareTo(o2.getVin());
                            }
                        });
                        vehicleList.postValue(vehicles);
                        isLoading.postValue(false); // Set isLoading to false after the API call is complete

                    } catch (JSONException e) {
                        e.printStackTrace();
                        isLoading.postValue(false); // Set isLoading to false upon getting exceptions
                        Log.e(this.getClass().getCanonicalName(), "Failed to retrieve vehicles: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Handle failure silently
                isLoading.postValue(false); // Set isLoading to false on failure as well
                Log.e(this.getClass().getCanonicalName(), "Failed to retrieve vehicles: " + e.getMessage());
            }
        });
    }

}
