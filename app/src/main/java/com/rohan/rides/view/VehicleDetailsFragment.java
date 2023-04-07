package com.rohan.rides.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rohan.rides.R;
import com.rohan.rides.model.Vehicle;
import com.rohan.rides.utils.Utils;

import java.util.Objects;

public class VehicleDetailsFragment extends Fragment {

    private TextView mVinTextView;
    private TextView mMakeAndModelTextView;
    private TextView mColorTextView;
    private TextView mCarTypeTextView;

    private Vehicle vehicle;
    private Button mCalculateEmissionsButton;
    private BottomSheetDialog bottomSheetDialog;

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
        mCalculateEmissionsButton = view.findViewById(R.id.carbon_emissions_button);


        if (getArguments() != null) {
            vehicle = getArguments().getParcelable("vehicle");
            Log.i(this.getClass().getCanonicalName(), "Vehicle Object received is : " + vehicle.toString());

            mVinTextView.setText(vehicle.getVin());
            mMakeAndModelTextView.setText(vehicle.getMakeAndModel());
            mColorTextView.setText(vehicle.getColor());
            mCarTypeTextView.setText(vehicle.getCarType());
        }

        mCalculateEmissionsButton.setOnClickListener(v -> showBottomSheetDialog());

        return view;
    }

    private void showBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        TextView carbonEmissionsTextView = bottomSheetView.findViewById(R.id.carbon_emissions_textview);

        double kmTravelled = Double.parseDouble(vehicle.getKilometrage());
        double carbonEmissions = Utils.calculateCarbonEmissions(kmTravelled);

        String carbonEmissionsText = getString(R.string.carbon_emissions_text, carbonEmissions);
        carbonEmissionsTextView.setText(carbonEmissionsText);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        Button closeButton = bottomSheetDialog.findViewById(R.id.close_button);
        Objects.requireNonNull(closeButton).setOnClickListener(v -> bottomSheetDialog.dismiss());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //in order to save the app from crash upon changing the layout of screen
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

}
