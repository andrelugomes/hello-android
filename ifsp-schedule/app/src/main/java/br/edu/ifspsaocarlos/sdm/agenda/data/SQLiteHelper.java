package br.edu.ifspsaocarlos.sdm.agenda.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by agomes on 30/10/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "agenda.db";
    public static final String DATABASE_TABLE = "contatos";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "nome";
    public static final String KEY_FONE = "fone";
    public static final String KEY_FONE_2 = "fone2";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NIVER = "niver";
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_FONE + " TEXT, " +
            KEY_EMAIL + " TEXT," +
            KEY_FONE_2 + " TEXT, " +
            KEY_NIVER + " TEXT );";

    private static final String DATABASE_ALTER_ADD_FONE_2 = "ALTER TABLE "
            + DATABASE_TABLE + " ADD COLUMN " + KEY_FONE_2 + " text;";

    private static final String DATABASE_ALTER_ADD_ANIVERSARIO = "ALTER TABLE "
            + DATABASE_TABLE + " ADD COLUMN " + KEY_NIVER + " text;";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    //para fazer update de versões e colocar mudanças no Banco de Dados
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        /*
        cuidados com versionamento debanco de dados... não pode pular versoe
        Switch case sem o BREAK ou IF
         */


        //versão 3.0
        if (oldVersion < 2) {
            database.execSQL(DATABASE_ALTER_ADD_FONE_2);
        }

        //Versão 4.0
        if (oldVersion < 3) {
            database.execSQL(DATABASE_ALTER_ADD_ANIVERSARIO);
        }



    }

}
