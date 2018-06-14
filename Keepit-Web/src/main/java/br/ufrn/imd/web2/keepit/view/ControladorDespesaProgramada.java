/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaProgramadaLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaProgramada;
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
@Named(value = "controladorDespesaProgramada")
@RequestScoped
public class ControladorDespesaProgramada {

    private DespesaProgramada despesaProgramada;
    
    @EJB(beanName = "despesaProgramadaDAO", beanInterface = DespesaProgramadaLocalDAO.class)
    private DespesaProgramadaLocalDAO despesaProgramadaDAO;
    
    @Inject
    private ControladorLogin controladorLogin;

    public void criarDespesaProgramada() {
        this.despesaProgramada.setUsuario(controladorLogin.getUsuario());
        this.despesaProgramadaDAO.create(despesaProgramada);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa programada adicionada!", "Sucesso!"));
        this.initObject();
    }
    
    public void removerDespesaProgramada(DespesaProgramada despesaProgramada) {
        this.despesaProgramadaDAO.remove(despesaProgramada);
    }
    
    public void atualizarDespesaProgramada() {
        this.despesaProgramadaDAO.edit(despesaProgramada);
    }
    
    public DespesaProgramada getDespesaProgramada() {
        return despesaProgramada;
    }

    public void setDespesaProgramada(DespesaProgramada despesaProgramada) {
        this.despesaProgramada = despesaProgramada;
    }
    
    public List<DespesaProgramada> getDespesasProgramadas() {
        return despesaProgramadaDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }
    
    @PostConstruct
    private void initObject() {
        this.despesaProgramada = new DespesaProgramada();
    }
}
