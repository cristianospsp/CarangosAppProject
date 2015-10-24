package br.com.caelum.fj59.carangos;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.RegistraAparelhoTask;

/**
 * Created by android5372 on 10/10/15.
 */
public class CarangosApplication extends Application {

    private static final String ID_DO_REGISTRO = "idDoRegistro";
    private static final String REGISTRADO = "registradoNoGcm";
    private List<Publicacao> publicacoes = new ArrayList<>();
    private List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?,?,?>>();
    private SharedPreferences preferences;

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (AsyncTask t : this.tasks) {
            t.cancel(true);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("configs", Activity.MODE_PRIVATE);
        registraNoGCM();
    }

    public void registraNoGCM() {
        if (!usuarioRegistrado()){
            new RegistraAparelhoTask(this).execute();
        }else {
            MyLog.i("Aparelho jah cadastrado! Sei id é: " + preferences.getString(ID_DO_REGISTRO, null));
        }
    }

    private boolean usuarioRegistrado() {
        return preferences.getBoolean(REGISTRADO, false);
    }

    public void registra(AsyncTask<?,?,?> task) {
        tasks.add(task);
    }

    public void desregistra(AsyncTask<?,?,?> task) {
        MyLog.i("CarangosApplication - desregistra....");
        tasks.remove(task);
    }

    public List<Publicacao> getPublicacoes() {
        return publicacoes;
    }

    public void lidaComRespostaDoRegistroNoServidor(String registro) {
        if (registro != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REGISTRADO, true);
            editor.putString(ID_DO_REGISTRO, registro);
            editor.commit();
        }
    }
}
