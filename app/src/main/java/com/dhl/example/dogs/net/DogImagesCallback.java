package com.dhl.example.dogs.net;



import com.dhl.example.dogs.model.DogBreedImages;
import retrofit2.Callback;

public abstract class DogImagesCallback implements Callback<DogBreedImages> {
    private String breed;

    protected DogImagesCallback(String breed) {
        this.breed = breed;
    }

    protected String getBreed() {
        return this.breed;
    }

}
