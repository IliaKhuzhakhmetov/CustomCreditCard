package com.example.creditcardlibrary.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.creditcardlibrary.R

/**
 *  It's view help you integrate in your app unical credit card view
 *  @author IliaKhuzhakhmetov
 *  @property mTYPE Visa, MasterCard and etc.
 *  @property mMONTH expiration month January, February and etc.
 *  @property backgroundSrc Background under gradient
 *  @property mYear expiration Year
 *  @property number credit card number ("1234 1234 1234 1234")
 *
 *  In next time this card will be automatically set the Type of card when we set the number
 */
class CustomCreditCard : LinearLayout {

    internal lateinit var rootView: View
    val TAG = CustomCreditCard::class.java.simpleName

    var mTYPE = TYPE.VISA //
    var mMONTH = MONTH.JANUARY
    var backgroundSrc = ContextCompat.getDrawable(context, R.drawable.cbimage)
    var mYear = 2020
    var number = context.resources.getString(R.string.card_number_def)

    //region regex
    private val amex_regex = Regex("^3[47][0-9]{13}\$")
    private val dinnersclub_regex = Regex("^3(?:0[0-5]|[68][0-9])[0-9]{11,13}\$")
    private val visa_regex = Regex("^4[0-9]{12}(?:[0-9]{3})?\$")
    private val mastercard_regex =
        Regex("^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))\$")
    private val unionpay_regex = Regex("^(62[0-9]{14,17})\$")
    private val discover_regex =
        Regex("^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})\$")
    private val jcb_regex = Regex("^(?:2131|1800|35\\d{3})\\d{11}\$")
    //endregion

    private lateinit var logo: AppCompatImageView           // Card logo (Visa, Mastercard)
    private lateinit var gradient: AppCompatImageView
    private lateinit var cardNumber: AppCompatTextView
    private lateinit var cvvNumber: AppCompatTextView
    private lateinit var month: AppCompatTextView
    private lateinit var year: AppCompatTextView
    private lateinit var backgroundImage: AppCompatImageView

    private val cardMask = "____ ____ ____ ____"

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(attrs)

        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getAttrs(attrs)

        init(context)
    }

    private fun init(context: Context) {

        rootView = View.inflate(context, R.layout.view_card, this)

        logo = findViewById(R.id.creditcard_logo)
        gradient = findViewById(R.id.gradient)
        cardNumber = findViewById(R.id.creditcard_card_number_label)
        cvvNumber = findViewById(R.id.creditcard_cvv_label)
        month = findViewById(R.id.creditcard_expirationMLabel)
        backgroundImage = findViewById(R.id.background)
        year = findViewById(R.id.creditcard_expiration_y_label)

        setType(mTYPE)
        setMonth(mMONTH)
        setSrc(backgroundSrc!!)
        setYear(mYear)
    }

    fun addWatchers(number: EditText? = null, cvv: EditText? = null) {
        number?.addTextChangedListener(
            object : CustomTextWatcher {
                override fun textChanged(text: String, start: Int, before: Int) {
                    val out = text.toMask(cardMask)
                    setCreditNumber(out)
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

    private fun getAttrs(attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCreditCard,
            0, 0
        )
        try {
            mTYPE = TYPE.fromId(a.getInt(R.styleable.CustomCreditCard_cardType, 0))
            mMONTH = MONTH.fromId(a.getInt(R.styleable.CustomCreditCard_month, 0))
            backgroundSrc = a.getDrawable(R.styleable.CustomCreditCard_scrBackground)
            val tmpYear = a.getInteger(R.styleable.CustomCreditCard_year, 2020)
            mYear = if (tmpYear in 2000..3000) tmpYear else 2020
        } finally {
            a.recycle()
        }
    }

    /**
     * This method will allow you to set the year of the card in the range from 2000 to 3000
     * @param value year in Int value
     */
    fun setYear(value: Int): CustomCreditCard =
        apply {
            if (value in 2000..3000) {
                mYear = value
                year.text = value.toString()
            } else throw java.lang.Exception("Год должен быть в диапазоне 2000..3000")
        }


    /**
     * The method will allow you to change the background of the card.
     * @param id resId (drawable only)
     */
    fun setSrc(id: Int): CustomCreditCard =
        apply {
            try {
                backgroundSrc = ContextCompat.getDrawable(context, id)
                backgroundImage.setImageDrawable(ContextCompat.getDrawable(context, id))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    /**
     * The method will allow you to change the background of the card.
     * @param drawable
     */
    fun setSrc(drawable: Drawable): CustomCreditCard =
        apply {
            try {
                backgroundSrc = drawable
                backgroundImage.setImageDrawable(drawable)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    /**
     * The method will allow you to set the card number
     * Now this method also automatically supports the definition of card types.
     * @param value string like this "1234 1234 1234 1234"
     *     without chars
     */
    fun setCreditNumber(value: String): CustomCreditCard =
        apply {
            if (value.isNumber() || value.isEmpty()) {
                number = value.toMask(cardMask)
                cardNumber.text = value
                val num = number.replace(" ", "")
                when {

                    amex_regex.containsMatchIn(num) -> {
                        setType(TYPE.AMERICAN_EXPRESS)
                        Log.d(TAG, "AMEX setup!")
                    }
                    dinnersclub_regex.containsMatchIn(num) -> {
                        setType(TYPE.DINERS_CLUB)
                        Log.d(TAG, "Diners club setup!")
                    }
                    visa_regex.containsMatchIn(num) -> {
                        setType(TYPE.VISA)
                        Log.d(TAG, "Visa setup!")
                    }
                    mastercard_regex.containsMatchIn(num) -> {
                        setType(TYPE.MASTERCARD)
                        Log.d(TAG, "Mastercard setup!")
                    }
                    discover_regex.containsMatchIn(num) -> {
                        setType(TYPE.DISCOVER)
                        Log.d(TAG, "Discover setup!")
                    }
                    unionpay_regex.containsMatchIn(num) -> {
                        setType(TYPE.UNIONPAY)
                        Log.d(TAG, "Unionpay setup!")
                    }
                    jcb_regex.containsMatchIn(num) -> {
                        setType(TYPE.JCB)
                        Log.d(TAG, "JCB setup")
                    }

                    else -> setType(TYPE.UNKNOWN)

                }
            } else throw Exception("Number contains chars")
        }

    /**
     * The method will allow you to set the type of card (visa, master card, etc.)
     * @param type example TYPE.VISA
     */
    fun setType(type: TYPE): CustomCreditCard =
        apply {
            mTYPE = type
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

    fun setMonth(m: MONTH): CustomCreditCard =
        apply {
            this.mMONTH = m
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