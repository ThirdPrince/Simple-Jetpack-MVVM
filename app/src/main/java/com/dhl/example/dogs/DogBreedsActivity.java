package com.dhl.example.dogs;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.dhl.example.dogs.model.DogBreed;
import com.dhl.example.dogs.viewmodel.DogBreedsViewModel;
import com.dhl.uimode.AppMode;
import com.dhl.uimode.Mode;
import com.dhl.uimode.R;
import com.dhl.uimode.databinding.ActivityDogBreedsBinding;
import java.util.List;


public class DogBreedsActivity extends AppCompatActivity {


    private static final String TAG = "DogBreedsActivity";
    private DogBreedsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dog_breeds);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityDogBreedsBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_dog_breeds);
        viewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(DogBreedsViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityBinding.setModel(viewModel);
        activityBinding.setLifecycleOwner(this);
        setupListUpdate();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.fetchList();
                AppMode.INSTANCE.update(Mode.INSTANCE.getUIModeNight());
               // viewModel.fetchList();
            }
        }, 2000);
    }

    private void setupListUpdate() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();
        viewModel.getBreeds().observe(this, new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dogBreeds) {
                Log.e(TAG, "onChanged");
                viewModel.loading.set(View.GONE);
                if (dogBreeds.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setDogBreedsInAdapter(dogBreeds);
                }
            }
        });


        setupListClick();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupListClick();
            }
        }, 1000);
    }

    private void setupListClick() {
        if (!viewModel.getSelected().hasObservers()) {
            viewModel.getSelected().observe(this, new Observer<DogBreed>() {
                @Override
                public void onChanged(DogBreed dogBreed) {
                    if (dogBreed != null) {
                        Toast.makeText(DogBreedsActivity.this, "You selected a " + dogBreed.getBreed(), Toast.LENGTH_SHORT).show();
                    }

                    Log.e(TAG, "getSelected");
                }
            });
        }

        viewModel.getSelectedText().observe(this, new Observer<DogBreed>() {
            @Override
            public void onChanged(DogBreed dogBreed) {
                if (dogBreed != null) {
                    Toast.makeText(DogBreedsActivity.this, "You selected text " + dogBreed.getBreed(), Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "getSelectedText");
            }
        });
    }
}
