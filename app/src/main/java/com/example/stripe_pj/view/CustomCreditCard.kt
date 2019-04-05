package com.example.stripe_pj.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.example.stripe_pj.R

class CustomCreditCard : LinearLayout {

    internal lateinit var rootView: View

    var mTYPE = TYPE.VISA
    internal lateinit var logo: AppCompatImageView
    internal lateinit var gradient: AppCompatImageView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCreditCard,
            0, 0
        )
        try {
            mTYPE = TYPE.fromId(a.getInt(R.styleable.CustomCreditCard_cardType, 0))
        } finally {
            a.recycle()
        }

        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCreditCard,
            0, 0
        )
        try {
            mTYPE = TYPE.fromId(a.getInt(R.styleable.CustomCreditCard_cardType, 0))
        } finally {
            a.recycle()
        }

        init(context)
    }

    private fun init(context: Context) {
        rootView = View.inflate(context, R.layout.view_card, this)

        logo = findViewById(R.id.logo)
        gradient = findViewById(R.id.gradient)

        Log.d("Test", "${mTYPE.type}")

        when (mTYPE) {
            TYPE.VISA -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.visa_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.visa_gradient))
            }
            TYPE.MASTERCARD -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.mastercard_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.mastercard_gradient))
            }
        }
    }

}

enum class TYPE(var type: Int) {
    VISA(0),
    MASTERCARD(1),
    UNIONPAY(2),
    DINERS_CLUB(3),
    AMERICAN_EXPRESS(4),
    DISCOVER(5),
    JCB(6),
    UNKNOWN(-1);

    companion object {
        fun fromId(type: Int): TYPE {
            for (f in values())
                if (f.type == type) return f
            throw IllegalArgumentException()
        }
    }

}