package com.example.agenda.agenda;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.agenda.agenda.contexto.AlunoContexto;
import com.example.agenda.agenda.converter.AlunoConverter;
import com.example.agenda.agenda.model.Aluno;

import java.util.List;

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {
    private Context context;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... objects) {
        AlunoContexto ctx = new AlunoContexto(context);
        List<Aluno> alunos = ctx.listaAlunos();
        ctx.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJson(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
