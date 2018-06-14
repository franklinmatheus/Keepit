/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.ReceitaComumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.ReceitaComum;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named(value = "controladorReceitaComum")
@RequestScoped
public class ControladorReceitaComum {
 
    private ReceitaComum receitaComum;
    
    @EJB(beanName = "receitaComumDAO", beanInterface = ReceitaComumLocalDAO.class)
    private ReceitaComumLocalDAO receitaComumDAO;
    
    @Inject
    private ControladorLogin controladorLogin;
    
    public void criarReceitaComum(){
        this.receitaComum.setUsuario(controladorLogin.getUsuario());
        receitaComumDAO.create(receitaComum);
        this.initObject();
    }
    
    public void removerReceitaComum(ReceitaComum receitaComum) {
        this.receitaComumDAO.remove(receitaComum);
    }
    
    public void atualizarReceitaComum() {
        this.receitaComumDAO.edit(receitaComum);
    }
    
    public ReceitaComum getReceitaComum() {
        return receitaComum;
    }

    public void setReceitaComum(ReceitaComum receitaComum) {
        this.receitaComum = receitaComum;
    }
    
    public List<ReceitaComum> getReceitasComuns() {
        return receitaComumDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }
    
    @PostConstruct
    private void initObject() {
        this.receitaComum = new ReceitaComum();
    }
}
