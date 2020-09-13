package com.abhirajsharma.urbanspeed

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ss = SaveSharedPreference()
        val userName = ss.getUserName(this)
        val userId = ss.getUserID(this)
        val userImg = ss.getUserImg(this)
        val userMail = ss.getUserMail(this)
        Log.d("checkMe", "$userName $userId $userMail $userImg")


        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, HomeFrag()) //MoviesFrag()
                    .commit()
            nav_view.setCheckedItem(R.id.nav_item_home) //nav_movies
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        if (item.itemId == R.id.nav_cart) {
            startActivity(Intent(this, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_home -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragContainer, HomeFrag())
                        .commit()
            }
            R.id.nav_item_categories -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragContainer, CategoriesFrag())
                        .commit()
            }
            R.id.nav_item_offers -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragContainer, OffersFrag())
                        .commit()
            }
            R.id.nav_item_profile -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragContainer, ProfileFrag())
                        .commit()
            }
            R.id.nav_item_wish -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragContainer, WishListFrag())
                        .commit()
            }
            R.id.nav_item_refer -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragContainer, ReferralFrag())
                        .commit()
            }
            R.id.nav_item_about -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragContainer, AboutFrag())
                        .commit()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}