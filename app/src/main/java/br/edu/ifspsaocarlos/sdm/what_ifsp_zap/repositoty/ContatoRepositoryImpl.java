package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty;

import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.data.SQLiteHelper;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;


public class ContatoRepositoryImpl implements ContatoRepository {

    private Context context;

    private SQLiteDatabase database;

    private SQLiteHelper dbHelper;

    public ContatoRepositoryImpl(Context context) {

        this.context = context;

        this.dbHelper = new SQLiteHelper(context);

    }

    @Override
    public List<Contato> buscaTodosContatos() {

        database = dbHelper.getReadableDatabase();

        List<Contato> contacts = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelper.DATABASE_CONTATOS,
                new String[]{SQLiteHelper.KEY_ID, SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_NICKNAME, SQLiteHelper.KEY_LAST_MESSAGE_ID},
                null, null, null, null, SQLiteHelper.KEY_NAME);

        if (cursor != null) {

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                Contato contato = new Contato();
                contato.setId(cursor.getInt(0));
                contato.setNomeCompleto(cursor.getString(1));
                contato.setApelido(cursor.getString(2));

                contacts.add(contato);
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();
        return contacts;
    }


    @Override
    public void updateContact(Contato c) {

        database = dbHelper.getWritableDatabase();

        ContentValues updateValues = new ContentValues();

        updateValues.put(SQLiteHelper.KEY_NAME, c.getNomeCompleto());

        updateValues.put(SQLiteHelper.KEY_NICKNAME, c.getApelido());

        database.update(SQLiteHelper.DATABASE_CONTATOS, updateValues, SQLiteHelper.KEY_ID + "=" + c.getId(), null);

        database.close();

    }


    @Override
    public void createContact(Contato c) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNomeCompleto());
        values.put(SQLiteHelper.KEY_ID, c.getId());
        values.put(SQLiteHelper.KEY_NICKNAME, c.getApelido());
        database.insert(SQLiteHelper.DATABASE_CONTATOS, null, values);
        database.close();
    }

    @Override
    public Contato buscaPorId(Integer id) {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(SQLiteHelper.DATABASE_CONTATOS, new String[]{
                        SQLiteHelper.KEY_ID, SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_NICKNAME, SQLiteHelper.KEY_LAST_MESSAGE_ID},
                SQLiteHelper.KEY_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);

        Contato contato = new Contato();
        if (cursor != null && cursor.moveToFirst()) {
            contato.setId(Integer.valueOf(cursor.getString(0)));
            contato.setNomeCompleto(cursor.getString(1));
            contato.setApelido(cursor.getString(2));
        }

        cursor.close();
        database.close();
        return contato;
    }

    @Override
    public void delete(Contato contato) {
        database=dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_CONTATOS, SQLiteHelper.KEY_ID + "=" + contato.getId(), null);
        database.close();
    }
}
