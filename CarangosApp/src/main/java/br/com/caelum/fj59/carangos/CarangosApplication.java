package br.com.caelum.fj59.carangos;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by android5372 on 10/10/15.
 */
public class CarangosApplication extends Application {

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
            editor.putBoolean("REGISTRADO", true);
            editor.putString("ID_DO_REGISTRO", registro);
            editor.commit();
        }
    }
}
