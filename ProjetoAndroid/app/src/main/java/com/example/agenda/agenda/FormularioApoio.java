package com.example.agenda.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.agenda.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class FormularioApoio {
    private final EditText campoNome;
    private final EditText campoEnd;
    private final EditText campoTel;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;
    public List<String> Erros = new ArrayList<>();

    private Aluno aluno;

    public FormularioApoio(FormularioActivity activity){
        campoNome = activity.findViewById(R.id.formularioNome);
        campoEnd = activity.findViewById(R.id.formularioEndereco);
        campoTel = activity.findViewById(R.id.formularioTelefone);
        campoSite = activity.findViewById(R.id.formularioSite);
        campoNota = activity.findViewById(R.id.formularioNota);
        campoFoto = activity.findViewById(R.id.formularioFoto);
        aluno = new Aluno();
    }
    public String getErros(){

        return TextUtils.join("\n",Erros);
    }

    public Aluno pegarAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEnd.getText().toString());
        aluno.setTelefone(campoTel.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());
        return aluno;
    }

    public void CarregaDados(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEnd.setText(aluno.getEndereco());
        campoTel.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap,350,350,true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
    public boolean validarCampos(){
        Erros.clear();

        if (aluno.getNome().isEmpty()){
            Erros.add("Informe o Nome");
        }
        if (aluno.getTelefone().isEmpty()){
            Erros.add("Informe o Telefone");
        }
        if (aluno.getEndereco().isEmpty()){
            Erros.add("Informe o Endereço");
        }
        return (Erros.size() <= 0);
    }
}
