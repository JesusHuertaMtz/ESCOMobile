package com.example.master.escomobile_alpha.util

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

class CustomLinearLayoutManager( context: Context ): LinearLayoutManager( context ) {
	var isScrollEnabled = true

	override fun canScrollVertically(): Boolean {
		return isScrollEnabled && super.canScrollVertically()
	}
}