package com.example.mostafahussien.practicekotlin2.DI

import javax.inject.Qualifier

/**
 * Created by Mostafa Hussien on 20/10/2018.
 */

@Qualifier                                  // Qualifier used to generate annotation that could help differentiate the same type of class object with different argument sent to it’s constructor
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl