/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaEstimadaLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaEstimada;
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
@Named(value = "controladorDespesaEstimada")
@RequestScoped
public class ControladorDespesaEstimada {
    
    private DespesaEstimada despesaEstimada;
    
    @EJB(beanName = "despesaEstimadaDAO", beanInterface = DespesaEstimadaLocalDAO.class)
    private DespesaEstimadaLocalDAO despesaEstimadaDAO;
    
    @Inject
    private ControladorLogin controladorLogin;

    public void criarDespesaEstimada() {
        this.despesaEstimada.setUsuario(controladorLogin.getUsuario());
        try {
            this.despesaEstimadaDAO.create(despesaEstimada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa estimada adicionada!", "Sucesso!"));
        } catch(BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), "Falha!"));
        }
        this.initObject();
    }
    
    public void removerDespesaEstimada(DespesaEstimada despesaEstimada) {
        this.despesaEstimadaDAO.remove(despesaEstimada);
    }
    
    public void atualizarDespesaEstimada(DespesaEstimada despesaEstimada) {
        this.despesaEstimadaDAO.edit(despesaEstimada);
    }
    
    public DespesaEstimada getDespesaEstimada() {
        return despesaEstimada;
    }

    public void setDespesaEstimada(DespesaEstimada despesaEstimada) {
        this.despesaEstimada = despesaEstimada;
    }
    
    public List<DespesaEstimada> getDespesasEstimadas() {
        return despesaEstimadaDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }
    
    @PostConstruct
    private void initObject() {
        this.despesaEstimada = new DespesaEstimada();
    }
}
