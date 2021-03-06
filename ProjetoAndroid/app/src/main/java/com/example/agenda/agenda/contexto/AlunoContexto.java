package com.example.agenda.agenda.contexto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.agenda.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoContexto extends SQLiteOpenHelper {

    SQLiteDatabase DB = getWritableDatabase();

    public AlunoContexto(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL,  " +
                "endereco TEXT," +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL," +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
        }
    }
    public void insere(Aluno aluno){
        ContentValues dados = CarregarDadosAluno(aluno);
        DB.insert("Alunos",null,dados);
    }

    @NonNull
    private ContentValues CarregarDadosAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome",aluno.getNome());
        dados.put("endereco",aluno.getEndereco());
        dados.put("telefone",aluno.getTelefone());
        dados.put("site",aluno.getSite());
        dados.put("nota",aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> listaAlunos() {
        String sql = "SELECT * FROM Alunos;";
        Cursor c = DB.rawQuery(sql,null);
        List<Aluno> list = new ArrayList<Aluno>();
        while (c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            list.add(aluno);
        }
        c.close();
        return list;
    }

    public void remove(Aluno aluno) {
        String[] par = {aluno.getId().toString()};
        DB.delete("Alunos","id = ?",par);
    }

    public void Edit(Aluno aluno) {
        ContentValues dados = CarregarDadosAluno(aluno);
        String[] par = {aluno.getId().toString()};
        DB.update("Alunos",dados,"id = ?",par);
    }
    public boolean EhAluno(String telefone){
        Cursor c = DB.rawQuery("SELECT * FROM Alunos WHERE telefone = ?", new String[]{telefone});
        int resultado = c.getCount();
        c.close();
        return  resultado > 0;
    }
}
