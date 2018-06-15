/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaIncomumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaIncomum;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named(value = "controladorDespesaIncomum")
@RequestScoped
public class ControladorDespesaIncomum {
    
    private DespesaIncomum despesaIncomum;
    
    @EJB(beanName = "despesaIncomumDAO", beanInterface = DespesaIncomumLocalDAO.class)
    private DespesaIncomumLocalDAO despesaIncomumDAO;
    
    @Inject
    private ControladorLogin controladorLogin;

    public void criarDespesaIncomum() {
        this.despesaIncomum.setUsuario(controladorLogin.getUsuario());
        try {
            this.despesaIncomumDAO.create(despesaIncomum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa incomum adicionada!", "Sucesso!"));
        } catch(BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Falha!"));
        }
        this.initObject();
    }
    
    public void removerDespesaIncomum(DespesaIncomum despesaIncomum) {
        this.despesaIncomumDAO.remove(despesaIncomum);
    }
    
    public void atualizarDespesaIncomum() {
        this.despesaIncomumDAO.edit(despesaIncomum);
    }
    
    public DespesaIncomum getDespesaIncomum() {
        return despesaIncomum;
    }

    public void setDespesaIncomum(DespesaIncomum despesaIncomum) {
        this.despesaIncomum = despesaIncomum;
    }
    
    public List<DespesaIncomum> getDespesasIncomuns() {
        return despesaIncomumDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }
    
    @PostConstruct
    private void initObject() {
        this.despesaIncomum = new DespesaIncomum();
    }
}
