package com.example.stripe_pj.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.stripe_pj.R

class CustomCreditCard : LinearLayout {

    internal lateinit var rootView: View

    var mTYPE = TYPE.VISA
    private lateinit var logo: AppCompatImageView
    private lateinit var gradient: AppCompatImageView
    private lateinit var cardNumber: AppCompatTextView
    private lateinit var cvvNumber: AppCompatTextView

    private val cardMask = "____ ____ ____ ____"

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
        cardNumber = findViewById(R.id.card_number_label)
        cvvNumber = findViewById(R.id.cvv_label)

        Log.d("Test", "${mTYPE.type}")

        setType(mTYPE)
    }

    fun addWatchers(number: EditText? = null, cvv: EditText? = null) {
        number?.addTextChangedListener(
            object : CustomTextWatcher {
                override fun textChanged(text: String) {
                    cardNumber.text = text.toMask(cardMask)
                }
            }
        )

        cvv?.addTextChangedListener(
            object : CustomTextWatcher {
                override fun textChanged(text: String) {
                    cvvNumber.text = text
                }
            }
        )
    }

    fun setType(type: TYPE) {
        when (type) {
            TYPE.VISA -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.visa_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.visa_gradient))
            }
            TYPE.MASTERCARD -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.mastercard_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.mastercard_gradient))
            }
            TYPE.UNIONPAY -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.unionpay_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.unionpay_gradient))
            }
            TYPE.DINERS_CLUB -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.diners_club_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.diner_club_gradient))
            }
            TYPE.AMERICAN_EXPRESS -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.amex_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.amex_gradient))
            }
            TYPE.DISCOVER -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.discover_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.discover_gradient))
            }
            TYPE.JCB -> {
                logo.setImageDrawable(context.resources.getDrawable(R.drawable.jcb_logo))
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.jcb_gradient))
            }
            else -> {
                logo.setImageDrawable(null)
                gradient.setImageDrawable(context.resources.getDrawable(R.drawable.diner_club_gradient))
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