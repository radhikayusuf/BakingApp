package com.example.radhikayusuf.bakingapp.api;

import com.example.radhikayusuf.bakingapp.dao.RecipeDao;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author radhikayusuf.
 */

public interface ApiRequestInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<List<RecipeDao>> getRecipes();

}
