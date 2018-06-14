/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaComumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaComum;
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
@Named(value = "controladorDespesaComum")
@RequestScoped
public class ControladorDespesaComum {
 
    private DespesaComum despesaComum;
    
    @EJB(beanName = "despesaComumDAO", beanInterface = DespesaComumLocalDAO.class)
    private DespesaComumLocalDAO despesaComumDAO;
    
    @Inject
    private ControladorLogin controladorLogin;

    public void criarDespesaComum() {
        this.despesaComum.setUsuario(controladorLogin.getUsuario());
        this.despesaComumDAO.create(despesaComum);
        this.initObject();
    }
    
    public void removerDespesaComum(DespesaComum despesaComum) {
        this.despesaComumDAO.remove(despesaComum);
    }
    
    public void atualizarDespesaComum() {
        this.despesaComumDAO.edit(despesaComum);
    }
    
    public DespesaComum getDespesaComum() {
        return despesaComum;
    }

    public void setDespesaComum(DespesaComum despesaComum) {
        this.despesaComum = despesaComum;
    }
    
    public List<DespesaComum> getDespesasComuns() {
        return despesaComumDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }
    
    @PostConstruct
    private void initObject() {
        this.despesaComum = new DespesaComum();
    }
}
