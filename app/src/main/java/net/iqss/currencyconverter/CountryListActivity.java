package net.iqss.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.CurrencyCode;

import java.util.List;

public class CountryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_country_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


        }

    public void getAllCountry(View v) {
        try {
        Toast.makeText(getApplicationContext(), "getAllCountry", Toast.LENGTH_SHORT).show();

        CurrencyCode currency = CurrencyCode.getByCode("USD");

            //CurrencyCode currency1 = "USD";
            //String currency_Code = "USD";
            Toast.makeText(getApplicationContext(), "currency: " + currency, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "CurrencyCode.getByCode(\"usd\"): " + CurrencyCode.getByCode("USD"), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "currency.getName(): " + currency.getName(), Toast.LENGTH_SHORT).show();

        // Get the list of countries using the currency.

        List<CountryCode> countryCodeList = currency.getCountryList();

        // For each country.
        for (CountryCode country : countryCodeList) {
            // Print the country code and the country name.
            Toast.makeText(getApplicationContext(), "country code: " + country, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "country Name: " + country.getName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "currency.getName() inside for loop: " + currency.getName(), Toast.LENGTH_SHORT).show();
        }
    }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}