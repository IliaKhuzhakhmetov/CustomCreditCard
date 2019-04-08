package com.example.stripe_pj

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stripe_pj.view.TYPE
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var cursor = 0
    val cards = arrayListOf(
        TYPE.VISA,
        TYPE.MASTERCARD,
        TYPE.UNIONPAY,
        TYPE.DINERS_CLUB,
        TYPE.AMERICAN_EXPRESS,
        TYPE.DISCOVER,
        TYPE.JCB,
        TYPE.UNKNOWN
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        card.addWatchers(number = cardNumber, cvv = cvv)


        val mCard = Card("5469 5200 2115 4158", 6, 2019, "247")
        val stripe = Stripe(this, "pk_test_TYooMQauvdEDq54NiTphI7jx")
        stripe.createToken(
            mCard,
            object : TokenCallback {
                override fun onError(error: Exception?) {
                    Toast.makeText(applicationContext, "err", Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(token: Token) {

                }

            }
        )

        card.setOnClickListener {
            if (++cursor < cards.size) card.setType(cards[cursor]) else {
                cursor = 0
                card.setType(cards[cursor])
            }
        }
    }
}
