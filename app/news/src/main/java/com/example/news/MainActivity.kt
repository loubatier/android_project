package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.news.fragments.FilterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = FilterFragment()

        change(fragment)
    }
}

fun FragmentActivity.change(fragment: Fragment){

    supportFragmentManager.beginTransaction().apply {

        replace(R.id.fragment_container, fragment)
        addToBackStack(null)

    }.commit()
}
