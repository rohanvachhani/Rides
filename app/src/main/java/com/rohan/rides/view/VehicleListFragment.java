package com.rohan.rides.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rohan.rides.R;
import com.rohan.rides.utils.Utils;
import com.rohan.rides.viewmodel.VehicleListViewModel;

public class VehicleListFragment extends Fragment {

    private EditText mInputField;
    private Button mRetrieveButton;
    private RecyclerView mRecyclerView;

    private VehicleListAdapter adapter;
    private VehicleListViewModel viewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        mInputField = view.findViewById(R.id.input_field);
        mRetrieveButton = view.findViewById(R.id.retrieve_button);
        mRecyclerView = view.findViewById(R.id.vehicle_list_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        progressBar = view.findViewById(R.id.progress_bar);

        // Initialize RecyclerView and adapter
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new VehicleListAdapter();
        mRecyclerView.setAdapter(adapter);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(VehicleListViewModel.class);

        // Observe the vehicle list LiveData from the ViewModel and update the RecyclerView when it changes
        viewModel.getVehicleList().observe(getViewLifecycleOwner(), vehicles -> {
            adapter.setVehicleList(vehicles);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading && !swipeRefreshLayout.isRefreshing()) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        // Set click listener on the retrieve button to trigger the API call
        mRetrieveButton.setOnClickListener(v -> {
            hideKeyboard();
            retrieveVehicles();
        });

        // Set up pull-to-refresh functionality
        swipeRefreshLayout.setOnRefreshListener(() -> {
            hideKeyboard();
            retrieveVehicles();
            swipeRefreshLayout.setRefreshing(false);
        });


        // Set click listener on the adapter to navigate to the VehicleDetailsFragment
        adapter.setOnVehicleClickListener((position, vehicle) -> {
            // Navigate to the VehicleDetailsFragment passing the selected vehicle as an argument
            VehicleListFragmentDirections.ActionVehicleListFragmentToVehicleDetailsFragment action =
                    VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailsFragment(vehicle);
            NavHostFragment.findNavController(this).navigate(action);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getActivity().getCurrentFocus();
        // If no view is in focus, there will be NPE
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void retrieveVehicles() {
        int count;
        if (mInputField.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Please enter a value!")
                    .setTitle("No Values Found")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mInputField.setText("");
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
        try {
            count = Integer.parseInt(mInputField.getText().toString());
            // do something with the value
        } catch (NumberFormatException e) {
            // handle the exception
            Toast.makeText(getActivity(), "The value is too large!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Utils.isValueInRange(count)) {
            viewModel.retrieveVehicles(count);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Value must be in the range 1 to 100")
                    .setTitle("Invalid Input")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mInputField.setText("");
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
