# CustomCreditCard
View credit card for your projects. Supports the standard types of cards of the worlds spread banks.
<p align="center">
  <img src="https://media.giphy.com/media/eMsCaHHtTlGWvFlUVH/source.gif" width="350" title="CustomCreditCard">
</p>
<br>
What can the library <br>
- Change of banks (Visa, MasterCard, American Express, etc.)<br>
- Change card background<br>
- Change card number, cvv / cvv, month, year<br>
- The client can get all the values of the card<br>

<h1>Setup</h1>

In dependecies:
```
dependencies {
   ***
    implementation 'com.example.creditcardlibrary:cardview:0.0.15'
   ***
}
```
Now that's all, you can use the library!

<h1>Use the library</h1>
You can add credit card view to your activity markup. Like this:

```
<com.example.creditcardlibrary.view.CustomCreditCard
            android:id="@+id/card"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:cardType="VISA"
            app:month="JUNE"
            app:year="2041"
            app:scrBackground="@drawable/cbimage"/>
 ```
 
 Or add this view programmatically. Like this:
```
CustomCreditCard(this)
```

View settings: <br>
programmatically <br>
```
card.addWatchers(number = cardNumber, cvv = cvv)
card.setYear(2021)
card.setCreditNumber("1234 1234 1234 3457")
card.setMonth(MONTH.JANUARY)
card.setSrc(R.drawable.visa_gradient)

or like this
card.setCreditNumber("2121")
            .setYear(2039)
            .setType(TYPE.VISA)
            .setSrc(R.drawable.cbimage)
            .setMonth(MONTH.JUNE)
            
or like this
card.apply { 
            setYear(2039)
            setMonth(MONTH.JANUARY)
            setCreditNumber("1234 1234 1234 1234")
        }
```

In order to bind field changes to edith text, you need to add them to the card by the method:
```
card.addWatchers(number = cardNumber, cvv = cvv)
or 
card.addWatchers(number = cardNumber)
or
card.addWatchers(cvv = cvv)
``` 
It gives such an effect.
<p align="center">
  <img src="https://media.giphy.com/media/efrDuFQnW8iYPoyBke/source.gif" width="350" title="CustomCreditCard">
</p>
This is just the beginning, then a bunch of new features will be added.
