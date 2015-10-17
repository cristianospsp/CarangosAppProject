package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import br.com.caelum.fj59.carangos.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android5372 on 17/10/15.
 */
public class RegistraAparelhoTask extends AsyncTask<Void, Void, String> {
   
    private CarangosApplication app;

    public RegistraAparelhoTask(CarangosApplication application) {
        this.app = application;
    }

    @Override
    protected String doInBackground(Void... params) {
        String registrationId = null;

        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.app);
            gcm.register(Constantes.GCM_SERVER_ID);
            MyLog.i("Aparelho registrado com id: " + registrationId);

            String email = InformacoesDoUsuario.getEmail(this.app);

            String url = "device/register/"+email+"/"+registrationId;
            WebClient webClient = new WebClient(url);
            webClient.post();

        } catch (Exception e) {
            MyLog.i("Problema no registro do aparelho no server!" + e.getMessage());
        }
        return registrationId;
    }

    @Override
    protected void onPostExecute(String result) {
        this.app.lidaComRespostaDoRegistroNoServidor(result);
    }
}
