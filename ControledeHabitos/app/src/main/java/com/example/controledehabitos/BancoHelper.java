package com.example.controledehabitos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "habitos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE habitos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descricao TEXT, feitoHoje INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS habitos");
        onCreate(db);
    }

    public void inserir(String nome, String descricao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("descricao", descricao);
        cv.put("feitoHoje", 0);
        db.insert("habitos", null, cv);
    }

    public ArrayList<Habits> listar() {
        ArrayList<Habits> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM habitos", null);
        while (c.moveToNext()) {
            lista.add(new Habits(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3) == 1
            ));
        }
        c.close();
        return lista;
    }

    public Habits buscarPorId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM habitos WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            Habits h = new Habits(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3) == 1
            );
            c.close();
            return h;
        }
        c.close();
        return null;
    }

    public void atualizar(Habits h) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", h.getNome());
        cv.put("descricao", h.getDescricao());
        cv.put("feitoHoje", h.isFeitoHoje() ? 1 : 0);
        db.update("habitos", cv, "id = ?", new String[]{String.valueOf(h.getId())});
    }

    public void excluir(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("habitos", "id = ?", new String[]{String.valueOf(id)});
    }
}
