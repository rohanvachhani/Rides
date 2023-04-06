package com.rohan.rides.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rohan.rides.R;
import com.rohan.rides.model.Vehicle;

public class VehicleDetailsFragment extends Fragment {

    private TextView mVinTextView;
    private TextView mMakeAndModelTextView;
    private TextView mColorTextView;
    private TextView mCarTypeTextView;

    private Vehicle vehicle;

    public VehicleDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_details, container, false);

        mVinTextView = view.findViewById(R.id.vin_value);
        mMakeAndModelTextView = view.findViewById(R.id.make_and_model_value);
        mColorTextView = view.findViewById(R.id.color_value);
        mCarTypeTextView = view.findViewById(R.id.car_type_value);

        if (getArguments() != null) {
            vehicle = getArguments().getParcelable("vehicle");
            Log.i(this.getClass().getCanonicalName(), "Vehicle Object received is : " + vehicle.toString());

            // Display the vehicle details in the TextViews
            mVinTextView.setText(vehicle.getVin());
            mMakeAndModelTextView.setText(vehicle.getMakeAndModel());
            mColorTextView.setText(vehicle.getColor());
            mCarTypeTextView.setText(vehicle.getCarType());
        }

        return view;
    }

}
