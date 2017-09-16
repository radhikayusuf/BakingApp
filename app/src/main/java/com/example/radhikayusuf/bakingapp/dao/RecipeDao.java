package com.example.radhikayusuf.bakingapp.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author radhikayusuf.
 */

public class RecipeDao implements Parcelable {

    private int id, servings;
    private String name, image;
    private List<IngredientsDao> ingredients;
    private List<StepsDao> steps;

    public RecipeDao(int id, int servings, String name, String image, List<IngredientsDao> ingredients, List<StepsDao> steps) {
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    protected RecipeDao(Parcel in) {
        id = in.readInt();
        servings = in.readInt();
        name = in.readString();
        image = in.readString();
        ingredients = in.createTypedArrayList(IngredientsDao.CREATOR);
        steps = in.createTypedArrayList(StepsDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(servings);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeDao> CREATOR = new Creator<RecipeDao>() {
        @Override
        public RecipeDao createFromParcel(Parcel in) {
            return new RecipeDao(in);
        }

        @Override
        public RecipeDao[] newArray(int size) {
            return new RecipeDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<IngredientsDao> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsDao> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepsDao> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsDao> steps) {
        this.steps = steps;
    }

    public static Creator<RecipeDao> getCREATOR() {
        return CREATOR;
    }
}
