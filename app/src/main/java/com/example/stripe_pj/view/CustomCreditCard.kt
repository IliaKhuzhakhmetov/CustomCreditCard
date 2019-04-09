package com.example.stripe_pj.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.stripe_pj.R

class CustomCreditCard : LinearLayout {

    internal lateinit var rootView: View

    var mTYPE = TYPE.VISA
    var mMONTH = MONTH.JANUARY
    private lateinit var logo: AppCompatImageView
    private lateinit var gradient: AppCompatImageView
    private lateinit var cardNumber: AppCompatTextView
    private lateinit var cvvNumber: AppCompatTextView
    private lateinit var month: AppCompatTextView

    private val cardMask = "____ ____ ____ ____"

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getArrs(attrs)

        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getArrs(attrs)

        init(context)
    }

    private fun init(context: Context) {
        rootView = View.inflate(context, R.layout.view_card, this)

        logo = findViewById(R.id.logo)
        gradient = findViewById(R.id.gradient)
        cardNumber = findViewById(R.id.card_number_label)
        cvvNumber = findViewById(R.id.cvv_label)
        month = findViewById(R.id.expirationMLabel)

        setType(mTYPE)
        setMonth(mMONTH)
    }

    fun addWatchers(number: EditText? = null, cvv: EditText? = null) {
        number?.addTextChangedListener(
            object : CustomTextWatcher {
                override fun textChanged(text: String, start: Int, before: Int) {
                    val out = text.toMask(cardMask)
                    cardNumber.text = out
                }
            }
        )

        cvv?.addTextChangedListener(
            object : CustomTextWatcher {
                override fun textChanged(text: String, start: Int, before: Int) {
                    cvvNumber.text = text
                }
            }
        )
    }

    private fun getArrs(attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCreditCard,
            0, 0
        )
        try {
            mTYPE = TYPE.fromId(a.getInt(R.styleable.CustomCreditCard_cardType, 0))
            mMONTH = MONTH.fromId(a.getInt(R.styleable.CustomCreditCard_month, 0))
        } finally {
            a.recycle()
        }
    }

    fun setType(type: TYPE) {
        when (type) {
            TYPE.VISA -> {
                logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.visa_logo))
                gradient.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.visa_gradient))
            }
            TYPE.MASTERCARD -> {
                logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mastercard_logo))
                gradient.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mastercard_gradient))
            }
            TYPE.UNIONPAY -> {
                logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unionpay_logo))
                gradient.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unionpay_gradient))
            }
            TYPE.DINERS_CLUB -> {
                logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.diners_club_logo))
                gradient.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.diner_club_gradient))
            }
            TYPE.AMERICAN_EXPRESS -> {
                logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.amex_logo))
                gradient.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.amex_gradient))
            }
            TYPE.DISCOVER -> {
                logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.discover_logo))
                gradient.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.discover_gradient))
            }
            TYPE.JCB -> {
                logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.jcb_logo))
                gradient.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.jcb_gradient))
            }
            else -> {
                logo.setImageDrawable(null)
                gradient.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.diner_club_gradient))
            }
        }
    }

    fun setMonth(m: MONTH){
        month.text = m.value
    }
}

enum class MONTH(var value: String) {
    DECEMBER("December"),
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November");

    companion object {
        fun fromId(type: Int): MONTH {
            for (f in MONTH.values())
                if (f.ordinal == type) return f
            throw IllegalArgumentException()
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