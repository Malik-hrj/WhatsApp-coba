package com.example.whatsappcoba

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.whatsappcoba.activity.ContactsActivity
import com.example.whatsappcoba.activity.LoginActivity
import com.example.whatsappcoba.activity.ProfileActivity
import com.example.whatsappcoba.adapter.SectionPagerAdapter
import com.example.whatsappcoba.util.PERMISSION_REQUEST_READ_CONTACT
import com.example.whatsappcoba.util.REQUEST_NEW_CHATS
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private var sectionPagerAdapter: SectionPagerAdapter? = null

    companion object {
        const val PARAM_NAME = "name"
        const val PARAM_PHONE = "phone"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager)
        container_viewpager.adapter = sectionPagerAdapter

        container_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(
            TabLayout.ViewPagerOnTabSelectedListener(
                container_viewpager
            )
        )

        resizeTabs()
        tabs.getTabAt(1)?.select()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> fab.hide()
                    1 -> fab.show()
                    2 -> fab.hide()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        fab.setOnClickListener {
            onNewChat()
        }
    }

    private fun onNewChat() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder(this)
                    .setTitle("Contact Permission")
                    .setPositiveButton("Yes") { dialog, which ->
                        requestContactPermission()
                    }
                    .setNegativeButton("No") { dialog, which ->

                    }
                    .show()
            } else {
                requestContactPermission()
            }
        } else {
            startNewActivity()
        }
    }

    private fun startNewActivity() {
        startActivityForResult(Intent(this, ContactsActivity::class.java), REQUEST_NEW_CHATS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_NEW_CHATS -> {}
            }
        }
    }

    private fun requestContactPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), PERMISSION_REQUEST_READ_CONTACT)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_READ_CONTACT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startNewActivity()
                }
            }
        }
    }

    private fun resizeTabs() {
        val layout = (tabs.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 0.4f
        layout.layoutParams = layoutParams
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> onLogout()
            R.id.action_profile -> onProfile()
        }
        return super.onOptionsItemSelected(item)


    }

    private fun onLogout() {
        firebaseAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun onProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }
}

