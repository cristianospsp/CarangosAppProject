package br.com.caelum.fj59.carangos.tasks;

import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import java.util.Collections;
import java.util.List;

import br.com.caelum.fj59.carangos.converter.LanceConverter;
import br.com.caelum.fj59.carangos.modelo.Lance;

/**
 * Created by android5372 on 24/10/15.
 */
public class CustonHandler extends Handler {

    private ArrayAdapter<Lance> adapter;
    private List<Lance> lancesAteOMomento;

    public CustonHandler(ArrayAdapter<Lance> adapter, List<Lance> lancesAteOMomento){
        this.adapter = adapter;
        this.lancesAteOMomento = lancesAteOMomento;
    }

    @Override
    public void handleMessage(Message msg) {
        String json = (String) msg.obj;
        List<Lance> novosLances = new LanceConverter().converte(json);

        lancesAteOMomento.addAll(novosLances);
        adapter.notifyDataSetChanged();

        Collections.sort(lancesAteOMomento);
    }
}
