package com.example.mostafahussien.practicekotlin2.data

import com.example.mostafahussien.practicekotlin2.data.db.RecipeFavoriteDBHelper
import com.example.mostafahussien.practicekotlin2.data.network.RecipeHelper

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
interface RecipeDataManager : RecipeHelper, RecipeFavoriteDBHelper {          // todo create DataManager, make RecipeDataManager imp it and put this DataManger instead of RecipeDataManager in BasePresenterImp instead of RecipeDataManager
}