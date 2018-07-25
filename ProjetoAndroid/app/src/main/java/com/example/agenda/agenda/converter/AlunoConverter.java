package com.example.agenda.agenda.converter;

import com.example.agenda.agenda.model.Aluno;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

public class AlunoConverter {
    public String converteParaJson(List<Aluno> alunos) {

        try {
            JSONStringer js = new JSONStringer();
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunos){
                js.object()
                .key("id").value(aluno.getId())
                .key("nome").value(aluno.getNome())
                .key("telefone").value(aluno.getTelefone())
                .key("endereco").value(aluno.getEndereco())
                .key("site").value(aluno.getSite())
                .key("nota").value(aluno.getNota())
                .endObject();
            }
            return js.endArray().endObject().endArray().endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
