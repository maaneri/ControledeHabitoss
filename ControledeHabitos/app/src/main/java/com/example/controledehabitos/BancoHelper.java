package com.example.provaac1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BancoHelper extends SQLiteOpenHelper {

    public BancoHelper(Context context) {
        super(context, "habitos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE habito (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descricao TEXT, feito INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS habito");
        onCreate(db);
    }

    public void inserir(String nome, String descricao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("descricao", descricao);
        cv.put("feito", 0);
        db.insert("habito", null, cv);
    }

    public ArrayList<Habito> listar() {
        ArrayList<Habito> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM habito", null);
        while (c.moveToNext()) {
            lista.add(new Habito(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3) == 1
            ));
        }
        c.close();
        return lista;
    }

    public Habito buscarPorId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM habito WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.moveToFirst()) {
            Habito h = new Habito(
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

    public void atualizar(Habito h) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", h.nome);
        cv.put("descricao", h.descricao);
        cv.put("feito", h.feito ? 1 : 0);
        db.update("habito", cv, "id = ?", new String[]{String.valueOf(h.id)});
    }

    public void excluir(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("habito", "id = ?", new String[]{String.valueOf(id)});
    }
}
