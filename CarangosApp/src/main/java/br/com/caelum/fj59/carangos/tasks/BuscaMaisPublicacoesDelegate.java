package br.com.caelum.fj59.carangos.tasks;

import java.util.List;

import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by android5372 on 10/10/15.
 */
public interface BuscaMaisPublicacoesDelegate {

    void lidaComRetorno(List<Publicacao> retorno);

    void lidaComErro(Exception e);

}
