package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service;

/**
 * Serviço para acessar as informações do usuário
 */
public class UserService {

    private static UserService instance;

    private Integer id;

    private  UserService(){
    }

    public static UserService getInstance(){
        if(instance == null)
            instance = new UserService();
        return instance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
