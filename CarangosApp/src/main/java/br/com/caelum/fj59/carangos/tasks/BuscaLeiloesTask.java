package br.com.caelum.fj59.carangos.tasks;

import android.app.Application;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android5372 on 24/10/15.
 */
public class BuscaLeiloesTask extends TimerTask {

    private CustonHandler handler;
    private Calendar horarioUltimaBusca;
    private Application application;

    public BuscaLeiloesTask(CustonHandler handler, Calendar horarioUltimaBusca, Application application) {
        this.handler = handler;
        this.horarioUltimaBusca = horarioUltimaBusca;
        this.application = application;
    }

    @Override
    public void run() {
        MyLog.i("Efetuando nova busca em: " + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime()));
        WebClient webClient = new WebClient("leilao/leilaoid54635/" + new SimpleDateFormat("ddMMyyHHmmss").format(horarioUltimaBusca.getTime()), application);
        String json = webClient.get();
        MyLog.i("Lances recebidos: " + json);

        Message message = handler.obtainMessage();

        message.obj = json;

        handler.sendMessage(message);

        horarioUltimaBusca = Calendar.getInstance();
    }

    public void executa() {

        Timer timer = new Timer();
        timer.schedule(this, 0, 30*1000);

    }

}
