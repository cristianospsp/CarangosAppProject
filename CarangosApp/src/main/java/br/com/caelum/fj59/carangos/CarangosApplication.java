package br.com.caelum.fj59.carangos;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android5372 on 10/10/15.
 */
public class CarangosApplication extends Application {

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
        tasks.remove(task);
    }
}
