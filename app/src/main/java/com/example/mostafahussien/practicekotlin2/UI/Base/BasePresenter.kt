package com.example.mostafahussien.practicekotlin2.UI.Base

/**
 * Created by Mostafa Hussien on 19/10/2018.
 */
interface BasePresenter <V: BaseView> {
    fun onAttach(baseView: V)
    fun onDeAttach()
}