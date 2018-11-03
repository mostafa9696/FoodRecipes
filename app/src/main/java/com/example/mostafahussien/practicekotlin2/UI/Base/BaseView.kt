package com.example.mostafahussien.practicekotlin2.UI.Base

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
interface BaseView {

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)

    fun showError(message: String)
}