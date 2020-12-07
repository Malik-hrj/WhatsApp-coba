package com.example.whatsappcoba.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsappcoba.fragment.ChatsFragment
import com.example.whatsappcoba.fragment.StatusListFragment
import com.example.whatsappcoba.fragment.StatusUpdateFragment

class SectionPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {

    private val chatsFragment = ChatsFragment()
    private val statusUpdateFragment = StatusUpdateFragment()
    private val statusFragment = StatusListFragment()

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> statusUpdateFragment
            1 -> chatsFragment
            2 -> statusFragment
            else -> chatsFragment
        }
    }

}