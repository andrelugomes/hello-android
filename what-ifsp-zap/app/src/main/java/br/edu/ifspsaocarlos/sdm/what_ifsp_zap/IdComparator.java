package br.edu.ifspsaocarlos.sdm.what_ifsp_zap;

import java.util.Comparator;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Mensagem;

/**
 * Created by agomes on 07/07/16.
 */
public class IdComparator implements Comparator<Mensagem> {
    @Override
    public int compare(Mensagem m1, Mensagem m2) {
        return Integer.compare(m1.getId(), m2.getId());
    }
}
