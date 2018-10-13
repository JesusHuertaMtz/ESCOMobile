package com.example.master.escomobile_alpha.util

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import android.content.DialogInterface
import android.view.LayoutInflater
import com.example.master.escomobile_alpha.R


class CustomProgressBar {
    companion object {
        private var dialog: Dialog? = null

        fun show(context: Context): Dialog {
            return show(context, null)
        }

        fun show(context: Context, title: CharSequence?): Dialog {
            return show(context, title, false)
        }

        fun show(context: Context, title: CharSequence?, cancelable: Boolean): Dialog {
            return show(context, title, cancelable, null)
        }

        fun show(context: Context, title: CharSequence?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?): Dialog {
            val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflator.inflate( R.layout.progress_bar, null )

            if( title != null ) {
                val tv = view.findViewById( R.id.id_title ) as TextView
                tv.text = title
            }

            dialog = Dialog( context, R.style.NewDialog )
            dialog!!.setContentView(view)
            dialog!!.setCancelable(cancelable)
            dialog!!.setOnCancelListener(cancelListener)
            dialog!!.show()

            return dialog!!
        }

        fun getDialog(): Dialog? {
            return dialog
        }
    }
}