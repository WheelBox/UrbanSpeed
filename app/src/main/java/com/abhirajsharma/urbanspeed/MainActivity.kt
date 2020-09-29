package com.abhirajsharma.urbanspeed

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.abhirajsharma.urbanspeed.others.GlobalInfo
import com.bumptech.glide.Glide
import com.ferfalk.simplesearchview.SimpleSearchView
import com.ferfalk.simplesearchview.utils.DimensUtils
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

//, BaseExampleFragment.BaseExampleFragmentCallbacks

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private var searchView: SimpleSearchView? = null
    private lateinit var toggle: ActionBarDrawerToggle
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        val headerView: View = navigationView.getHeaderView(0)
//        headerView.findViewById(R.id.navUsername).text = "Your Text Here"


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.title = ""
        toolbar.subtitle = ""
        //toolbar.setLogo(R.drawable.ic_toolbar);


        searchView = findViewById(R.id.searchView)

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


        val headerView: View = navigationView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.nav_header_name).text = auth.currentUser?.displayName.toString()
        val imgView: ImageView = headerView.findViewById(R.id.nav_header_image)
        Glide.with(this).load(auth.currentUser?.photoUrl.toString()).into(imgView)


        searchView!!.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("checkMe", "Submit:$query")
                GlobalInfo.searchFragment = true
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("checkMe", "Text changed:$newText")
                Log.d("checkMe", "Text Change " + GlobalInfo.searchFragment.toString())
                if (newText.isNotEmpty()) {
                    GlobalInfo.textSearch = newText
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragContainer, SearchFrag())
                            .addToBackStack(null)
                            .commit()
                }
                GlobalInfo.searchFragment = true
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Log.d("checkMe", "Text cleared")
                return false
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_and_cart_icon, menu)
//        setupSearchView(menu!!)
        return true
    }

    private fun setupSearchView(menu: Menu) {
        val item = menu.findItem(R.id.productsearchMenu)!!
        searchView!!.setMenuItem(item)
        // Adding padding to the animation because of the hidden menu item
        val revealCenter = searchView!!.revealAnimationCenter!!
        revealCenter.x -= DimensUtils.convertDpToPx(40, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (searchView!!.onActivityResult(requestCode, resultCode, data)) {
            Toast.makeText(this, "Activity Result: Searched clicked", Toast.LENGTH_SHORT).show()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
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
        if (item.itemId == R.id.productcartMenu) {
            startActivity(Intent(this, MyCart::class.java))
        }
//        if (item.itemId == R.id.productsearchMenu) {
//            Toast.makeText(this, "Searched clicked", Toast.LENGTH_SHORT).show()
//            supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.fragContainer, SearchFrag())
//                    .addToBackStack(null)
//                    .commit()
//        }
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

//        val fragments: List<*> = supportFragmentManager.fragments
//        Log.d("checkMe",fragments.size.toString())
//
//        val currentFragment = fragments[fragments.size - 2] as BaseExampleFragment
//        if (!currentFragment.onActivityBackPress()) {
//            super.onBackPressed()
//        }


        val f = this.supportFragmentManager.findFragmentById(R.id.fragContainer)!!
        if (f is SearchFrag) {
            supportFragmentManager.popBackStack()
        }
//        Log.d("checkMe", "Back Press " + GlobalInfo.searchFragment.toString())
//        if (GlobalInfo.searchFragment){
//            Log.d("checkMe", "Back Press when global true" + GlobalInfo.searchFragment.toString())
//            supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.fragContainer, HomeFrag())
//                    .commit()
//            GlobalInfo.searchFragment = false
//        }
        if (searchView!!.onBackPressed()) {
            supportFragmentManager.popBackStack()
            return
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onAttachSearchViewToDrawer(searchView: FloatingSearchView?) {
//
//    }

}