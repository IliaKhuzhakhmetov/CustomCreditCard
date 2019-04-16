package com.example.stripe_pj

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.creditcardlibrary.view.TYPE
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
        card.setCreditNumber("3052 5777 6889 38")

        card.setOnClickListener {
            if (++cursor < cards.size) card.setType(cards[cursor]) else {
                cursor = 0
                card.setType(cards[cursor])
            }
        }
    }
}
