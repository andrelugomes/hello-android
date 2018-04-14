package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.data.SQLiteHelper;

/**
 *
 * Essa tabela armazena o id da ultima mensagem recebida
 */
public class LastMessageRepositoryImpl implements LastMessageRepository{

    private Context context;

    private SQLiteDatabase database;

    private SQLiteHelper dbHelper;

    public LastMessageRepositoryImpl(Context context) {

        this.context = context;

        this.dbHelper = new SQLiteHelper(context);

    }

    @Override
    public Integer getLastMessage(Integer remetente, Integer destinatario) {
        database = dbHelper.getReadableDatabase();

        String[] columns_to_return = new String[]{SQLiteHelper.KEY_LM_ID_MSG};
        String where = String.format("%s = ? AND %s = ?", SQLiteHelper.KEY_LM_REMETENTE, SQLiteHelper.KEY_LM_DESTINATARIO);
        String[] where_args =  new String[]{String.valueOf(remetente), String.valueOf(destinatario)};

        Cursor cursor = database.query(SQLiteHelper.DATABASE_CREATE_LM_TABLE,
            columns_to_return, where, where_args, null, null, null);

        return cursor != null && cursor.moveToFirst() ?cursor.getInt(0) : -1 ;
    }


    @Override
    public void insertOrUpdate(Integer remetente, Integer destinatario, Integer id_last_message) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_LM_REMETENTE, remetente);
        values.put(SQLiteHelper.KEY_LM_DESTINATARIO, destinatario);
        values.put(SQLiteHelper.KEY_LM_ID_MSG, id_last_message);

        database.replace(SQLiteHelper.DATABASE_CREATE_LM_TABLE, null ,values);
        database.close();
    }
}


