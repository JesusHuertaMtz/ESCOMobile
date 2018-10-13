package com.example.master.escomobile_alpha.viewholder.tabadapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TabAdapter( fragmentManager: FragmentManager ) : FragmentStatePagerAdapter( fragmentManager ) {
	private var fragmentList = mutableListOf<Fragment>()
	private var fragmentTitleList = mutableListOf<String>()

	override fun getItem(position: Int): Fragment {
		return fragmentList[position]
	}

	override fun getCount(): Int {
		return fragmentList.size
	}

	override fun getPageTitle( position: Int ): CharSequence? {
		return fragmentTitleList[position]
	}

	fun addFragment( fragment: Fragment, title: String ) {
		fragmentList.add( fragment )
		fragmentTitleList.add( title )
	}
}