package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_hoje.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_hoje -> {
                message.setText(R.string.title_hoje)
                replaceFragment(HojeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_analise -> {
                message.setText(R.string.title_analise)
                replaceFragment(AnaliseFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_configuracao -> {
                message.setText(R.string.title_configuracao)
                replaceFragment(ConfiguracaoFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(HojeFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val tb = findViewById<Toolbar>(R.id.toolbar_hj)
        setSupportActionBar(toolbar_hj)
        //setSupportActionBar()
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)

        fragmentTransaction.commit()
    }


    fun ClickMain(view: View) {
        var intent: Intent = Intent(this, BluetoothActivity::class.java)
        startActivity(intent)
    }

}




