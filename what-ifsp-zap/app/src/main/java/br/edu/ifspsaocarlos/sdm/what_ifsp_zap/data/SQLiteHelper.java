package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "what_ifsp_zap.db";
    public static final String DATABASE_CONTATOS = "contatos";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "nome";
    public static final String KEY_NICKNAME = "apelido";
    public static final String KEY_LAST_MESSAGE_ID = "id_mensagem";

    public static final String DATABASE_USUARIO = "usuario";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_NAME = "nome";
    public static final String KEY_USER_NICKNAME = "apelido";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_LAST_MESSAGE = "last_message";
    public static final String KEY_LM_REMETENTE = "remetente";
    public static final String KEY_LM_DESTINATARIO = "destinatario"; //PK
    public static final String KEY_LM_ID_MSG = "message_id";
    public static final int DATABASE_LM_VERSION = 1;

    public static final String DATABASE_CREATE_CONTACTS_TABLE = "CREATE TABLE " + DATABASE_CONTATOS + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_NICKNAME + " TEXT NOT NULL, " +
            KEY_LAST_MESSAGE_ID +" INTEGER);";

    public static final String DATABASE_CREATE_USER_TABLE = "CREATE TABLE " + DATABASE_USUARIO + " (" +
            KEY_USER_ID + " INTEGER NOT NULL, " +
            KEY_USER_NAME + " TEXT NOT NULL, " +
            KEY_USER_NICKNAME + " TEXT NOT NULL);";

    public static final String DATABASE_CREATE_LM_TABLE = "CREATE TABLE " + DATABASE_LAST_MESSAGE + " (" +
            KEY_LM_REMETENTE + " INTEGER NOT NULL, " +
            KEY_LM_DESTINATARIO + " INTEGER NOT NULL, " +
            KEY_LM_ID_MSG + " INTEGER NOT NULL, " +
            "PRIMARY KEY (remetente, destinatario) );";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_CONTACTS_TABLE);
        database.execSQL(DATABASE_CREATE_USER_TABLE);
        database.execSQL(DATABASE_CREATE_LM_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }

}
