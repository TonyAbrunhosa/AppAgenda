package com.example.agenda.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.agenda.agenda.contexto.AlunoContexto;
import com.example.agenda.agenda.model.Aluno;

import java.io.File;

public class FormularioActivity extends AppCompatActivity {

    public static final int codigo_foto = 561;
    private FormularioApoio Apoio;
    AlunoContexto contexto;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Apoio = new FormularioApoio(this);
        final Intent intent = getIntent();
        Aluno aluno = (Aluno)intent.getSerializableExtra("aluno");
        if (aluno != null){
            Apoio.CarregaDados(aluno);
        }

        Button botaoFoto = findViewById(R.id.formBotaoFoto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(FormularioActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider", arquivoFoto));
                startActivityForResult(intentCamera, codigo_foto);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == codigo_foto){
                Apoio.carregaImagem(caminhoFoto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = Apoio.pegarAluno();
                contexto = new AlunoContexto(this);
                if (Apoio.validarCampos()){
                    if (aluno.getId() != null){
                        contexto.Edit(aluno);
                    }else {
                        contexto.insere(aluno);
                    }
                    Toast.makeText(FormularioActivity.this,"Aluno "+ aluno.getNome() + " salvo com sucesso!",Toast.LENGTH_LONG).show();
                    finish();
                }else  {
                    Toast.makeText(FormularioActivity.this,Apoio.getErros(),Toast.LENGTH_LONG).show();
                }
                contexto.close();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
