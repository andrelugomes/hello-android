package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty;


public interface LastMessageRepository {

    public Integer getLastMessage(Integer remetente, Integer destinatario);
    public void insertOrUpdate(Integer remetente, Integer destinatario, Integer id_last_message);
}
