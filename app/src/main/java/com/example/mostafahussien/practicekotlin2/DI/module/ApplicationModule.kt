package com.example.mostafahussien.practicekotlin2.DI.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.example.mostafahussien.practicekotlin2.DI.ApplicationContext
import com.example.mostafahussien.practicekotlin2.DI.BaseUrl
import com.example.mostafahussien.practicekotlin2.MainApplication
import com.example.mostafahussien.practicekotlin2.R
import com.example.mostafahussien.practicekotlin2.data.Config
import com.example.mostafahussien.practicekotlin2.data.RecipeDataManager
import com.example.mostafahussien.practicekotlin2.data.RecipeDataMangerImp
import com.example.mostafahussien.practicekotlin2.data.db.RecipeFavoriteDBHelper
import com.example.mostafahussien.practicekotlin2.data.db.RecipeFavoriteDBHelperImp
import com.example.mostafahussien.practicekotlin2.data.db.room.AppDatabase
import com.example.mostafahussien.practicekotlin2.data.network.RecipeApiHelper
import com.example.mostafahussien.practicekotlin2.data.network.RecipeHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Created by Mostafa Hussien on  20/10/2018.
 */

@Module
class ApplicationModule {

    @Provides
    @ApplicationContext
    internal fun provideContext(mainApplication : MainApplication) : Context{
        return mainApplication.applicationContext
    }

    @Provides
    internal fun provideApplication(mainApplication : MainApplication): Application {
        return mainApplication
    }

    @Provides
    internal fun provideCompositeDisposable():CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @BaseUrl
    internal fun provideBaseUrl(): String {
        return Config.BASE_URL
    }

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context) : AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, Config.DB_NAME).build()
    }

    @Provides
    @Singleton
    internal fun provideDBHelper(recipeFavoriteDBHelperImp: RecipeFavoriteDBHelperImp) : RecipeFavoriteDBHelper {       // RecipeFavoriteDBHelperImp need AppDatabase which is provided above
        return recipeFavoriteDBHelperImp
    }

    @Provides
    @Singleton
    internal fun providAPIHelper(recipeApiHelper: RecipeApiHelper) : RecipeHelper {             // RecipeApiHelper need RecipeService which is provided in NetworkModule
        return recipeApiHelper
    }

    @Provides
    @Singleton
    internal fun provideDataManger( recipeDataMangerImp : RecipeDataMangerImp) : RecipeDataManager {  // recipeDataMangerImp need RecipeApiHelper and RecipeFavoriteDBHelperImp which are declared above
        return recipeDataMangerImp
    }

    @Provides   // for webview tab
    fun provideCustomTabsIntent(@ApplicationContext context: Context): CustomTabsIntent {
        return CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .addDefaultShareMenuItem()
                .build()
    }
}