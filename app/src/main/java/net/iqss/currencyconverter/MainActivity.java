package net.iqss.currencyconverter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.iqss.currencyconverter.MapDbDetails;
import net.iqss.currencyconverter.R;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
    private GoogleMap googleMap;
    double latitude1;
    double longitude1;
    String item;
    String currencyCode;
    String countryCode;
    String countryName;
    EditText editText1;
    double number1;
    double currencyValue;
    double exactCurrencyValue;
    String selectedCurrencyMarker;
    String enteredAmountMarker;
    String convertedAmountMarker;
    String currencyCodeMarker;
    String countryNameMarker;
    String latitudeMarker;
    String longitudeMarker;
    String currencySelectedCodeMarker;
    double latitudeMarkerPosition;
    double longitudeMarkerPosition;
    double convertedAmountEdit;
    MarkerOptions markerOptions = new MarkerOptions();
    Marker marker;
    Marker marker1;
    MapDbDetails databaseobj = new MapDbDetails(this);
    String selectedItem;
    String[] separated;
    private static final String TAG = "MyActivity";
    ArrayAdapter adapter;
    AutoCompleteTextView text;
    String[] countries = {"AED - United Arab Emirates", "AFN - Afghanistan", "ALL - Albania", "AMD - Armenia", "AOA - Angola", "ARS - Argentina", "AUD - Australia",
            "AWG - Aruba", "AZN - Azerbaijan", "BAM - Bosnia-Herzegovina", "BBD - Barbados", "BDT - Bangladesh", "BGN - Bulgaria", "BHD - Bahrain", "BIF - Burundi", "BMD - Bermuda", "BND - Brunei Darussalam", "BOB - Bolivia ",
            "BRL - Brazil", "BSD - Bahamas", "BTN - Bhutan", "BWP - Botswana", "BYR - Belarus", "BZD - Belize", "CAD - Canada", "CDF - Congo, Dem. Republic ",
            "CHF - Liechtenstein", "CLP - Chile", "CNY - China", "COP - Colombia", "CRC - Costa Rica", "CUC - Croatia", "CUP - Cuba", "CZK - Czech Republic",
            "DJF - Djibouti", "DKK - Denmark", "DOP - Dominican Republic", "DZD - Algeria", "EGP - Egypt", "ERN - Eritrea",
            "ETB - Ethiopia", "EUR - Cyprus", "FJD - Fiji", "FKP - Falkland Islands", "GBP - Great Britain", "GEL - Georgia", "GGP - Guernsey",
            "GHS - Ghana", "GIP - Gibraltar", "GMD - Gambia", "GNF - Guinea", "GTQ - Guatemala", "GYD - Guyana", "HKD - Hong Kong", "HNL - Honduras", "HRK - Croatia",
            "HTG - Haiti", "HUF - Hungary", "IDR - Indonesia", "ILS - Israel", "IMP - Isle of Man", "INR - India", "IQD - Iraq", "IRR - Iran", "ISK - Iceland", "JEP - Jersey",
            "JMD - Jamaica", "JOD - Jordan", "JPY -Japan", "KES - Kenya", "KGS - Kyrgyzstan", "KHR - Cambodia", "KMF - Comoros", "KPW - Korea (North)", "KRW - Korea (South)",
            "KWD - Kuwait", "KYD - Cayman Island",
            "KZT - Kazakhstan", "LAK - Laos", "LBP - Lebanon", "LKR - Laos", "LRD - Liberia", "LSL - Lesotho ", "LYD - Libya", "MAD - Morocco",
            "MDL - Moldova", "MKD - Macedonia", "MMK - Myanmar", "MNT - Mongolia", "MRO - Mauritania", "MUR - Mauritius", "MVR - Maldives", "MWK - Malawi", "MXN - Mexico",
            "MYR - Malaysia", "MZN - Mozambique", "NAD - Namibia", "NGN - Nigeria", "NIO - Nicaragua", "NOK - Norway",
            "NPR - Nepal", "NZD - New Zealand", "OMR - Oman", "PAB - Panama", "PEN - Peru", "PGK - Papua New Guinea", "PHP - Philippines",
            "PKR - Pakistan", "PLN - Poland", "QAR - Qatar", "RON - Romania", "RUB - Russia", "SAR - Saudi Arabia", "SBD - Solomon Islands", "SCR - Seychelles", "SDG - Sudan",
            "SEK - Sweden", "SGD - Singapore", "SHP - Saint Helena", "SLL - Sierra Leone", "SOS - Somalia", "SRD - Suriname", "STD - Sao Tome and Principe", "SVC - El Salvador",
            "SYP - Syria", "SZL - Swaziland", "THB - Thailand", "TJS - Tajikistan", "TMT - Turkmenistan", "TND - Tunisia", "TOP - Tonga", "TRY - Turkey",
            "TTD - Trinidad and Tobago", "TWD - Taiwan", "TZS - Tanzania", "UAH - Ukraine", "UGX - Uganda", "USD - United States", "UYU - Uruguay",
            "UZS - Uzbekistan", "VEF - Venezuela", "VND - Vietnam", "VUV - Vanuatu", "WST - Samoa", "XAF - Cameroon", "XCD - Antarctica", "XPF - Wallis and Futuna Islands",
            "YER - Yemen", "ZAR - South Africa", "ZMW - Zambia", "ZWD - Zimbabwe"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // showing instruction to user when user use app first time.
        if (isFirstTime()) {
            showAlertMsg();
        }

        // checking internet connection

        checkInternetConnection1();

        editText1 = (EditText) findViewById(R.id.editText2);
        try {
            text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countries);

            try {
                if (item == null || item == "") {
                    //  Toast.makeText(getApplicationContext(), "item : " + item, Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
            }

            text.setAdapter(adapter);

            text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        // Hiding keypad
                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(text.getWindowToken(), 0);

                        text.setCursorVisible(true);
                        item = text.getText().toString();
                        if (item.contains("-")) {
                            separated = item.split("-");
                            selectedItem = (separated.length == 0) ? "" : separated[0].trim();

                        }
                        if (currencyCode != null || currencyCode == "") {
                            invokeWS();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Unable to get details", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to get details", Toast.LENGTH_LONG).show();
        }

        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // Toast.makeText(getApplicationContext(), "IME_ACTION_DONE....1", Toast.LENGTH_LONG).show();
                //  Toast.makeText(getApplicationContext(), "item1:" + item, Toast.LENGTH_LONG).show();
                boolean handled = false;
                text.setCursorVisible(true);
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //hiding mobile keypad
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(text.getWindowToken(), 0);

                    item = text.getText().toString();
                    //  Toast.makeText(getApplicationContext(), "item2:" + item, Toast.LENGTH_LONG).show();

                    if (!Arrays.asList(countries).contains(item)) {
                        googleMap.clear();
                        //  item = null;
                        selectedItem = null;

                        handled = true;
                    }
                    handled = false;
                }
                return handled;
            }
        });

        // open google map
        googleMap = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.map)).getMap();

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.5937, 78.9629), 2.0f));

        // Calling method to display local db data in marker when user reopen the app.
        getDBDetails();


        // getting selected currency from local db and displaying in drop down list
        try {
            if (selectedCurrencyMarker != null && selectedCurrencyMarker != "") {
                //	Toast.makeText(getApplicationContext(), "Inside if", Toast.LENGTH_LONG).show();
                item = selectedCurrencyMarker;
                //	Toast.makeText(getApplicationContext(), "Inside if item" + item, Toast.LENGTH_LONG).show();
                text.setText(item);
                //	Toast.makeText(getApplicationContext(), "Inside if item" + item, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to get details", Toast.LENGTH_LONG).show();
        }

        try {

            if (selectedItem == null || selectedItem == "") {

                selectedItem = currencyCodeMarker;
                //Toast.makeText(getApplicationContext(), "selectedItem4 : " + selectedItem, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to get details", Toast.LENGTH_LONG).show();

        }

        // Setting a click event handler for the map
        googleMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                // Setting the position for the marker

                text.setCursorVisible(false);
                markerOptions.position(latLng);

                checkInternetConnection1();

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                latitude1 = latLng.latitude;
                longitude1 = latLng.longitude;
                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    //countryName = null;
                    List<Address> addresses = gcd.getFromLocation(latitude1, longitude1, 1);
                    if (addresses.size() > 0) {
                        //System.out.println(addresses.get(0).getLocality());
                        countryCode = addresses.get(0).getCountryCode();
                        countryName = addresses.get(0).getCountryName();
                        currencyCode = Currency.getInstance(new Locale("", addresses.get(0).getCountryCode())).getCurrencyCode();

                        // calling method to call web service and get converted currency value.
                        invokeWS();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid location", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    checkInternetConnection1();
                    //Toast.makeText(getApplicationContext(), "Error:::"+ e, Toast.LENGTH_LONG).show();
                    googleMap.clear();
                    Toast.makeText(getApplicationContext(), "Unable to get details.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

        });


        try {
            //String editNumber1 = editText1.getText().toString();
            //if (editText1 != null) {
            editText1.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //Toast.makeText(getApplicationContext(), "After text changed", Toast.LENGTH_SHORT).show();
                    //	googleMap.clear();
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (currencyValue == 0) {
                        //Toast.makeText(getApplicationContext(), "After if condition:" + currencyValue, Toast.LENGTH_LONG).show();
                        String countQuery1 = "SELECT  * FROM " + MapDbDetails.MAPDB_DETAILS_TABLE_IQSS_17;
                        SQLiteDatabase db = databaseobj.getReadableDatabase();
                        Cursor cursor1 = db.rawQuery(countQuery1, null);

                        if (cursor1.moveToFirst()) {
                            do {
                                // fetching data from local database
                                selectedCurrencyMarker = cursor1.getString(1);
                                convertedAmountMarker = cursor1.getString(7);
                                currencyCodeMarker = cursor1.getString(5);
                                countryNameMarker = cursor1.getString(4);
                                latitudeMarker = cursor1.getString(2);
                                longitudeMarker = cursor1.getString(3);
                                currencySelectedCodeMarker = cursor1.getString(6);

                                currencyValue = Double.parseDouble(convertedAmountMarker);
                                currencyCode = currencySelectedCodeMarker;
                                latitude1 = Double.parseDouble(latitudeMarker);
                                longitude1 = Double.parseDouble(longitudeMarker);

                                countryName = countryNameMarker;
                                //	Toast.makeText(getApplicationContext(), "currencyValue:" + currencyValue, Toast.LENGTH_LONG).show();
                            } while (cursor1.moveToNext());
                        }
                        cursor1.close();
                    }

                    if (currencyValue > 0.0 && currencyCode != null && currencyCode != "" && countryName != null && countryName != "" && latitude1 > 0 && longitude1 > 0) {
                        if (s.length() > 0) {
                            //Toast.makeText(getApplicationContext(), "After text changed......", Toast.LENGTH_SHORT).show();
                            googleMap.clear();

                            //marker
                            String editNumber = editText1.getText().toString();
                            number1 = Double.parseDouble(editNumber);
                            convertedAmountEdit = currencyValue * number1;
                            marker1 = googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(latitude1, longitude1))
                                    .title(countryName + " (" + currencyCode + ")" + ":" + convertedAmountEdit));
                            marker1.showInfoWindow();
                            insertData();
                        }
                    }
                }
            });

            //}
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Unable to get details", Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }

    }

    // method for closing app
    public void closeApp1() {
        finish();
        System.exit(0);
    }

    // method to clear text of text view
    public void clearText(View v) {
        try {
            // clearing text of autocomplete text view
            text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
            text.setText("");
            googleMap.clear();
            selectedItem = null;
            // clearing text of edit text
            // editText1 = (EditText) findViewById(R.id.editText2);
            // editText1.setText("");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error::" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void showInstruction(View v) {
        showAlertMsg();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                //Toast.makeText(getApplicationContext(), "Instructions", Toast.LENGTH_SHORT).show();
                showAlertMsg();
                break;
            case R.id.item2:
                //Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
                closeApp1();
                break;

        }
        return true;
    }

    // getting selected item from dropdown list.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            item = parent.getItemAtPosition(position).toString();
            if (item.contains("-")) {
                separated = item.split("-");
                selectedItem = separated[0].trim();
            }

            if (item.equalsIgnoreCase("Select Any Currency")) {
                Toast.makeText(getApplicationContext(), "Please Select Any Currency", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to get details", Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        //Toast.makeText(getApplicationContext(), "Please Select Any Currency", Toast.LENGTH_LONG).show();
    }

    // web service method to pass currency code to convert one currency to another currency.
    public void invokeWS() {

        checkInternetConnection1();
        //Toast.makeText(getApplicationContext(), "invoke web service method: Item: " + item, Toast.LENGTH_LONG).show();
        try {
            text.setCursorVisible(false);
            // getting edit text value/ entered amount
            editText1 = (EditText) findViewById(R.id.editText2);
            String editNumber = editText1.getText().toString();
            number1 = Double.parseDouble(editNumber);

            if (selectedItem == null || selectedItem == "") {

                Toast.makeText(getApplicationContext(), "Please select currency to be converted.", Toast.LENGTH_LONG).show();
                return;
            }
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            // Toast.makeText(getApplicationContext(), "client" + client, Toast.LENGTH_LONG).show();

            client.get("http://currencies.apps.grandtrunk.net/getlatest/" + selectedItem + "/" + currencyCode, new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {

                    currencyValue = Double.parseDouble(response.toString());
                    //Toast.makeText(getApplicationContext(), "currencyValue:::: " + currencyValue, Toast.LENGTH_SHORT).show();
                    googleMap.clear();
                    exactCurrencyValue = currencyValue * number1;
                    markerOptions.title(countryName + " (" + currencyCode + " ) : " + exactCurrencyValue);
                    googleMap.addMarker(markerOptions).showInfoWindow();

                    insertData();
                }

                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    // When Http response code is '404'
                    if (statusCode == 404) {
                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code is '500'
                    else if (statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    }

                }
            });

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Please enter amount and select any currency from dropdown list.", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Please Select Any Currency", Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);

        }
    }

    // method for getting local db data to display data in marker.
    public void getDBDetails() {

        try {
            //Toast.makeText(getApplicationContext(), "Inside getDBDetails", Toast.LENGTH_LONG).show();
            String countQuery = "SELECT  * FROM " + MapDbDetails.MAPDB_DETAILS_TABLE_IQSS_17;
            SQLiteDatabase db = databaseobj.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    // fetching data from local database
                    selectedCurrencyMarker = cursor.getString(1);
                    enteredAmountMarker = cursor.getString(0);
                    convertedAmountMarker = cursor.getString(7);
                    currencyCodeMarker = cursor.getString(5);
                    countryNameMarker = cursor.getString(4);
                    latitudeMarker = cursor.getString(2);
                    longitudeMarker = cursor.getString(3);
                    currencySelectedCodeMarker = cursor.getString(6);

                    // Setting values from local db to show map latest details
                    editText1 = (EditText) findViewById(R.id.editText2);
                    editText1.setText(enteredAmountMarker);

                    double enteredAmountMarker1 = Double.parseDouble(enteredAmountMarker);
                    double convertedAmountMarker2 = Double.parseDouble(convertedAmountMarker);
                    double calculatedAmountMarker = enteredAmountMarker1 * convertedAmountMarker2;

                    latitudeMarkerPosition = Double.parseDouble(latitudeMarker);
                    longitudeMarkerPosition = Double.parseDouble(longitudeMarker);
                    //	googleMap.clear();
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeMarkerPosition, longitudeMarkerPosition), 2.0f));

                    // display marker with local database values
                    marker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitudeMarkerPosition, longitudeMarkerPosition))
                            .title(countryNameMarker + " (" + currencySelectedCodeMarker + ")" + " : " + calculatedAmountMarker));
                    marker.showInfoWindow();
                } while (cursor.moveToNext());

            }
            cursor.close();
            // return count
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Unable to get details", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }
    }

    // For checking whether app is opening first time or not
    private boolean isFirstTime() {

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    // showing instruction when user will open app first time.
    private void showAlertMsg() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setNeutralButton(Html.fromHtml("<a href=\"https://iqss.net\">https://iqss.net</a>"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = "https://iqss.net";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            builder.setNegativeButton("V 1.5", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setIcon(R.drawable.logo_iqss);
            builder.setTitle(Constants.APP_NAME);

            builder.setMessage(Html.fromHtml("1. Enter the amount to be converted." + "<br/><br/>" + " 2.Search and select the currency to be converted." + "<br/><br/>" + "  3. Tap on the country to convert to its currency." + "<br/><br/>" + "  Note: The rates are updated every working day between 7:30 p.m. and 8:30 p.m. IST." ));
            AlertDialog dlg = builder.create();
            dlg.show();
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void insertData() {

        SQLiteDatabase sqliteDatabase = databaseobj.getWritableDatabase();
        sqliteDatabase.execSQL("delete from " + MapDbDetails.MAPDB_DETAILS_TABLE_IQSS_17);
        try {
            ContentValues values = new ContentValues();
            values.put(MapDbDetails.CURRENCY_AMOUNT_MAP_COL1_IQSS_17, number1 + "");
            values.put(MapDbDetails.CURRENCY_CODEITEM_MAP_COL2_IQSS_17, item);
            values.put(MapDbDetails.LATITUDE1_MAP_COL4_IQSS_17, latitude1 + "");
            values.put(MapDbDetails.LONGITUDE1_MAP_COL5_IQSS_17, longitude1 + "");
            values.put(MapDbDetails.COUNTRY_NAME_MAP_COL6_IQSS_17, countryName);
            values.put(MapDbDetails.CURRENCY_SELECTED_ITEMMAP_COL7_IQSS_17, selectedItem);
            values.put(MapDbDetails.CURRENCY_CODE_COL8_IQSS_17, currencyCode);
            values.put(MapDbDetails.CURRENCY_VALUE_MAP_COL3_IQSS_17, currencyValue + "");

            sqliteDatabase.insert(MapDbDetails.MAPDB_DETAILS_TABLE_IQSS_17, null, values);

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Unable to get details", Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void checkInternetConnection1() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (activeNetworkInfo == null) {

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);

                    }
                });
                builder.setIcon(R.drawable.networkalert);
                builder.setTitle(Constants.APP_NAME);
                builder.setMessage("Please check internet connection and Try again.");
                AlertDialog dlg = builder.create();
                dlg.show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error::" + e, Toast.LENGTH_LONG).show();
        }
    }
}

		
		
		