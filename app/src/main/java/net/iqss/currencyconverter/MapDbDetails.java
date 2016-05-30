package net.iqss.currencyconverter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashu.gupta on 4/1/2016.
 */
public class MapDbDetails extends SQLiteOpenHelper {

    // Database attributes
    public static String SqliteMap_DataBaseName_IQSS_17 = "SqliteMap_DataBase_IQSS_17A.db" ;

    public static final int DB_VERSION = 1;

    // Table attributes
    public static final String MAPDB_DETAILS_TABLE_IQSS_17 = "MAPDB_DETAILS_TABLE_IQSS_17A";

    public static final String CURRENCY_AMOUNT_MAP_COL1_IQSS_17= "CURRENCY_AMOUNT_MAP_COL1_IQSS_17A";
    public static final String  CURRENCY_CODEITEM_MAP_COL2_IQSS_17 = "CURRENCY_CODEITEM_MAP_COL2_IQSS_17A";
    public static final String CURRENCY_VALUE_MAP_COL3_IQSS_17= "CURRENCY_VALUE_MAP_COL3_IQSS_17A";

    public static final String LATITUDE1_MAP_COL4_IQSS_17 = "LATITUDE1_MAP_COL4_IQSS_17A";
    public static final String  LONGITUDE1_MAP_COL5_IQSS_17 = "LONGITUDE1_MAP_COL5_IQSS_17A";
    public static final String COUNTRY_NAME_MAP_COL6_IQSS_17 = "COUNTRY_NAME_MAP_COL6_IQSS_17A";
    public static final String CURRENCY_SELECTED_ITEMMAP_COL7_IQSS_17 = "CURRENCY_SELECTED_ITEMMAP_COL7_IQSS_17A";
    public static final String CURRENCY_CODE_COL8_IQSS_17 = "CURRENCY_CODE_COL8_IQSS_17A";
    public MapDbDetails(Context context) {
        super(context, SqliteMap_DataBaseName_IQSS_17, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       // db = this.openOrCreateDatabase("MapDetails_DB.db", MODE_PRIVATE, null);
        String sqlQueryToCreateTable = "create table if not exists " + MAPDB_DETAILS_TABLE_IQSS_17 + " ( " + CURRENCY_AMOUNT_MAP_COL1_IQSS_17 + " Text, "
                + CURRENCY_CODEITEM_MAP_COL2_IQSS_17 + " Text, " + LATITUDE1_MAP_COL4_IQSS_17 + " Text, " + LONGITUDE1_MAP_COL5_IQSS_17 + " Text, " + COUNTRY_NAME_MAP_COL6_IQSS_17 + " Text, "  + CURRENCY_SELECTED_ITEMMAP_COL7_IQSS_17 + " Text, "
                + CURRENCY_CODE_COL8_IQSS_17 + " Text, "+ CURRENCY_VALUE_MAP_COL3_IQSS_17 + " Text); ";
        // Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
        db.execSQL(sqlQueryToCreateTable);

        System.out.print(sqlQueryToCreateTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2){
            // Upgrade the database
        }
    }
}

