package br.com.caelum.fj59.carangos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.fj59.carangos.CarangosApplication;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate {

    private static final String ESTADO_ATUAL = "ESTADO_ATUAL";
    private ListView listView;
    private PublicacaoAdapter adapter;
    private EstadoMainActivity estado;
    private EventoPublicacoesRecebidas evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.estado = EstadoMainActivity.INICIO;

        this.evento = EventoPublicacoesRecebidas.registraObservador(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);

        MenuItem compras = menu.add("Compras");
        compras.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        String acaoCustomizada = getResources().getString(R.string.action_compra);

        Intent intent = new Intent(acaoCustomizada);

        compras.setIntent(intent);

        return true;
    }

    @Override
    public void lidaComRetorno(List<Publicacao> retorno) {
        CarangosApplication application = (CarangosApplication) getApplication();
        List<Publicacao> p = application.getPublicacoes();

        p.clear();
        p.addAll(retorno);
        this.estado = EstadoMainActivity.PRIMEIRAS_PUBLICACOES_RECEBIDAS;
        this.estado.executa(this);
    }

    @Override
    public void lidaComErro(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "Erro ao buscar dados", Toast.LENGTH_LONG).show();
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estadoMainActivity) {
        this.estado = estadoMainActivity;
        this.estado.executa(this);
    }

    public void buscaPublicacoes() {
        new BuscaMaisPublicacoesTask(this.getCarangosApplication()).execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("Salvando Estado...");
        outState.putSerializable(ESTADO_ATUAL, this.estado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MyLog.i("Restaurando Estado...");
        this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.i("Executando Estado: " + this.estado);
        this.estado.executa(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.evento.desregistra(getCarangosApplication());
    }
}
