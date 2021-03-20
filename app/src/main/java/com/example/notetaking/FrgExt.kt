package com.example.notetaking

import androidx.fragment.app.Fragment


fun Fragment.getViewModelsFactory(): ViewModelFactory {
    val repo =  ( requireContext().applicationContext as App).noteRepo
    return ViewModelFactory(repo,this,null)
}
