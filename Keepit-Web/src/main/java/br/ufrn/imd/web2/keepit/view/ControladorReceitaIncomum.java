/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.ReceitaIncomumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.ReceitaIncomum;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.*;

/**
 *
 * @author Ailson F. dos Santos
 */
@Named(value = "controladorReceitaIncomum")
@RequestScoped
public class ControladorReceitaIncomum {
    
    private ReceitaIncomum receitaIncomum;
    
    @EJB(beanName = "receitaIncomumDAO", beanInterface = ReceitaIncomumLocalDAO.class)
    private ReceitaIncomumLocalDAO receitaIncomumDAO;

    @Inject
    private ControladorLogin controladorLogin;
    
    public ReceitaIncomum getReceitaIncomum() {
        return receitaIncomum;
    }

    public void setReceitaIncomum(ReceitaIncomum receitaIncomum) {
        this.receitaIncomum = receitaIncomum;
    }
    
    public void criarReceitaIncomum(){
        this.receitaIncomum.setUsuario(controladorLogin.getUsuario());
        receitaIncomumDAO.create(receitaIncomum);
        this.initObject();
    }
    
    public void removerReceitaIncomum(ReceitaIncomum receitaIncomum) {
        this.receitaIncomumDAO.remove(receitaIncomum);
    }
    
    public List<ReceitaIncomum> getReceitasIncomuns() {
        return receitaIncomumDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }
    
    @PostConstruct
    private void initObject() {
        this.receitaIncomum = new ReceitaIncomum();
    }
}