package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;

/**
 * Created by agomes on 07/07/16.
 */
public interface ContatoRepository {

    public List<Contato> buscaTodosContatos();

    public void updateContact(Contato contato);

    public void createContact(Contato contato);

    public Contato buscaPorId(Integer id);

    void delete(Contato contato);
}
