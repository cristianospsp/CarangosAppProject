package br.com.caelum.fj59.carangos.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.modelo.Lance;
import br.com.caelum.fj59.carangos.tasks.BuscaLeiloesTask;
import br.com.caelum.fj59.carangos.tasks.CustonHandler;

/**
 * Created by android5372 on 24/10/15.
 */
public class LeiaoActivity extends ActionBarActivity {

    private List<Lance> lancesAteOMomento = new ArrayList<Lance>();
    private Calendar horarioUltimaBusca = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leilao);

        ListView lancesList = (ListView)findViewById(R.id.lances_list);

        ArrayAdapter<Lance> adapter = new ArrayAdapter<Lance>(LeiaoActivity.this, android.R.layout.simple_list_item_1,
                lancesAteOMomento);

        lancesList.setAdapter(adapter);

        CustonHandler handler = new CustonHandler(adapter, lancesAteOMomento);

        new BuscaLeiloesTask(handler, horarioUltimaBusca, this.getApplication()).executa();

    }
}
