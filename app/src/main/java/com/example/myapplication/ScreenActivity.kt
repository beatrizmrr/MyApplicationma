package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class ScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        val background = object: Thread(){
            override fun run() {
                    try {
                        Thread.sleep(2000)

                        val intent = Intent (baseContext, MainActivity::class.java)
                        startActivity(intent)
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        background.start()
        }
    }





