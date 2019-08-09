package com.tech.deliverydetailspage.core.platform.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.tech.deliverydetailspage.R

const val PROGRESS_BAR_TEXT = "PROGRESS_BAR_TEXT"

class ProgressBarDialog : androidx.fragment.app.DialogFragment() {

    companion object {
        const val PROGRESS_BAR_TAG = "PROGRESS_BAR_TAG"

        fun newInstance(text: String?): ProgressBarDialog {
            val progressBarDialog = ProgressBarDialog()
            val bundle = Bundle()
            bundle.putString(PROGRESS_BAR_TEXT, text)
            progressBarDialog.arguments = bundle
            return progressBarDialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        val dialog = Dialog(activity!!, R.style.ProgressDialogTheme)
        @SuppressLint("InflateParams") val view =
            activity!!.layoutInflater.inflate(R.layout.dialog_progress_circular, null, false)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        setText(view)
        return dialog
    }

    private fun setText(view: View) {
        if (arguments != null) {
            val text = arguments!!.getString(PROGRESS_BAR_TEXT)
            val textView = view.findViewById<View>(R.id.tv_progress) as TextView
            if (TextUtils.isEmpty(text)) {
                textView.visibility = View.GONE
            } else {
                textView.text = text
                textView.visibility = View.VISIBLE
            }
        }
    }
}
