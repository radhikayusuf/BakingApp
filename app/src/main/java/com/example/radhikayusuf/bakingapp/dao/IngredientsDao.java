package com.example.radhikayusuf.bakingapp.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author radhikayusuf.
 */

public class IngredientsDao implements Parcelable{

    private float quantity;
    private String measure, ingredient;


    protected IngredientsDao(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IngredientsDao> CREATOR = new Creator<IngredientsDao>() {
        @Override
        public IngredientsDao createFromParcel(Parcel in) {
            return new IngredientsDao(in);
        }

        @Override
        public IngredientsDao[] newArray(int size) {
            return new IngredientsDao[size];
        }
    };


    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public static Creator<IngredientsDao> getCREATOR() {
        return CREATOR;
    }
}
