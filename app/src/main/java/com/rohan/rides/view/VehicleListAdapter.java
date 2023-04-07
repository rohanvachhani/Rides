package com.rohan.rides.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohan.rides.R;
import com.rohan.rides.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder> {

    private List<Vehicle> vehicleList = new ArrayList<>();

    private OnVehicleClickListener onVehicleClickListener;

    public void setOnVehicleClickListener(OnVehicleClickListener listener) {
        this.onVehicleClickListener = listener;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);
        holder.mMakeAndModelTextView.setText(vehicle.getMakeAndModel());
        holder.mVinTextView.setText(vehicle.getVin());
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public interface OnVehicleClickListener {
        void onVehicleClick(int position, Vehicle vehicle);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mMakeAndModelTextView;
        public TextView mVinTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMakeAndModelTextView = itemView.findViewById(R.id.make_and_model_textview);
            mVinTextView = itemView.findViewById(R.id.vin_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onVehicleClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Get the vehicle at the clicked position
                    Vehicle vehicle = vehicleList.get(position);
                    // Navigate to the VehicleDetailsFragment
                    onVehicleClickListener.onVehicleClick(position, vehicle);
                }
            }
        }
    }

}

