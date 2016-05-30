package net.iqss.currencyconverter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashu.gupta on 5/12/2016.
 */
public class MapViewDbDetails extends SQLiteOpenHelper {

    //Database Name
    public static String MapView_SqliteMap_DataBaseName = "MapView_SqliteMap_DataBaseName.db" ;
    public static final int DB_VERSION = 1;

    // Table Name
    public static final String MAPVIEW_TABLE = "MAPDB_DETAILS_TABLE_IQSS_17A";

    // Columns

    public static final String COL1_COUNTRY_CODE= "COL1_COUNTRY_CODE";
    public static final String  COL2_COUNTRY_NAME = "COL2_COUNTRY_NAME";
    public static final String  COL3_COUNTRY_LATITUDE= "COL3_COUNTRY_LATITUDE";
    public static final String COL4_COUNTRY_LONGITUDE = "COL4_COUNTRY_LONGITUDE";


    public MapViewDbDetails(Context context) {
        super(context, MapView_SqliteMap_DataBaseName, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlQueryToCreateTable = "create table if not exists " + MAPVIEW_TABLE + " ( " + COL1_COUNTRY_CODE + " Text, "
                + COL2_COUNTRY_NAME + " Text, " + COL3_COUNTRY_LATITUDE + " Text, "+ COL4_COUNTRY_LONGITUDE + " Text); ";
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
