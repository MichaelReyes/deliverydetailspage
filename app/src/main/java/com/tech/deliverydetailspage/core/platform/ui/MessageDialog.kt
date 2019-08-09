package com.tech.deliverydetailspage.core.platform.ui

import android.os.Bundle
import android.view.View
import com.tech.deliverydetailspage.R
import kotlinx.android.synthetic.main.dialog_message.*

class MessageDialog() : BaseDialog(), View.OnClickListener {

    private var callback: Callback? = null

    companion object {
        const val ARGS_MESSAGE_HEADER = "_message_header"
        const val ARGS_MESSAGE_BODY = "_message_body"
        const val ARGS_MESSAGE_POSITIVE_ACTION = "_message_positive_action"
        const val ARGS_MESSAGE_NEGATIVE_ACTION = "_message_negative_action"

        fun newInstance(args: Bundle?, callback: Callback?): MessageDialog {
            val fragment = MessageDialog()
            fragment.callback = callback
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutId() = R.layout.dialog_message

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsFromArgs()
    }

    private fun initializeViewsFromArgs() {
        arguments?.let { args ->
            if (args.containsKey(ARGS_MESSAGE_HEADER))
                tvHeader.text = args.getString(ARGS_MESSAGE_HEADER)

            if (args.containsKey(ARGS_MESSAGE_BODY))
                tvMessage.text = args.getString(ARGS_MESSAGE_BODY)

            if (args.containsKey(ARGS_MESSAGE_POSITIVE_ACTION))
                bActionPositive.text = args.getString(ARGS_MESSAGE_POSITIVE_ACTION)
            else
                bActionPositive.visibility = View.GONE

            if (args.containsKey(ARGS_MESSAGE_NEGATIVE_ACTION))
                bActionNegative.text = args.getString(ARGS_MESSAGE_NEGATIVE_ACTION)
            else
                bActionNegative.visibility = View.GONE

            bActionPositive.setOnClickListener(this)
            bActionNegative.setOnClickListener(this)

        }
    }

    override fun onClick(v: View?) {
        v?.let{view ->
            when(view){
                bActionPositive -> {
                    callback?.onClickPositiveAction()
                    dialog.dismiss()
                }
                bActionNegative -> {
                    callback?.onClickNegativeAction()
                    dialog.dismiss()
                }else -> {}
            }
        }
    }

    interface Callback {
        fun onClickPositiveAction()

        fun onClickNegativeAction()
    }

}
