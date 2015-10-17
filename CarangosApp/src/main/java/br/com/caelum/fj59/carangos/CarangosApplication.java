package br.com.caelum.fj59.carangos;

import android.app.Application;
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
}
