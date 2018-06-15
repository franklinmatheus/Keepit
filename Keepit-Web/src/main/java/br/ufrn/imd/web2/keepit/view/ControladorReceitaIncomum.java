/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.ReceitaIncomumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.ReceitaIncomum;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
    @Inject
    private ControladorUsuario controladorUsuario;
    
    public ReceitaIncomum getReceitaIncomum() {
        return receitaIncomum;
    }

    public void setReceitaIncomum(ReceitaIncomum receitaIncomum) {
        this.receitaIncomum = receitaIncomum;
    }
    
    public void criarReceitaIncomum(){
        this.receitaIncomum.setUsuario(controladorLogin.getUsuario());
        this.receitaIncomum.setAtualizada(false);
        try {
            this.receitaIncomumDAO.create(receitaIncomum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receita incomum adicionada!", "Sucesso!"));
            Date hoje = new Date();
            if(this.receitaIncomum.getData().compareTo(hoje) <= 0) {
                this.controladorLogin.getUsuario().setSaldo(this.controladorLogin.getUsuario().getSaldo() + receitaIncomum.getValor());
                this.controladorUsuario.editarUsuario(controladorLogin.getUsuario());
                this.receitaIncomum.setAtualizada(true);
                this.receitaIncomumDAO.edit(receitaIncomum);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saldo atualizado!", "Sucesso!"));
            }
        } catch(BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Falha!"));
        }
        this.initObject();
    }
    
    public void removerReceitaIncomum(ReceitaIncomum receitaIncomum) {
        this.receitaIncomumDAO.remove(receitaIncomum);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receita " + receitaIncomum.getTitulo() + " foi removida!" , "Falha!"));
    }
    
    public void removerDesfazerAlteracoes(ReceitaIncomum receitaIncomum) {
        this.receitaIncomumDAO.remove(receitaIncomum);
        if(receitaIncomum.isAtualizada()) {
            this.controladorLogin.getUsuario().setSaldo(this.controladorLogin.getUsuario().getSaldo() - receitaIncomum.getValor());
            this.controladorUsuario.editarUsuario(controladorLogin.getUsuario());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receita " + receitaIncomum.getTitulo() + " foi removida e seu saldo foi atualizado!" , "Falha!"));
        }
    }
    
    public void editarReceitaIncomum(ReceitaIncomum receitaIncomum) {
        this.receitaIncomumDAO.edit(receitaIncomum);
    }
    
    public List<ReceitaIncomum> getReceitasIncomuns() {
        return receitaIncomumDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }
    
    public void checarReceitasIncomuns() {
        List<ReceitaIncomum> receitas = this.getReceitasIncomuns();
        int quantidade = 0;
        Date hoje = new Date();
        for(ReceitaIncomum receita : receitas) {
            if(!receita.isAtualizada() && receita.getData().compareTo(hoje) <= 0) {
                this.controladorLogin.getUsuario().setSaldo(this.controladorLogin.getUsuario().getSaldo() + receita.getValor());
                this.controladorUsuario.editarUsuario(controladorLogin.getUsuario());
                receita.setAtualizada(true);
                this.editarReceitaIncomum(receita);
                quantidade++;
            }
        }
        if(quantidade > 0)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, quantidade + " receitas incomuns foram atualizadas!", "Sucesso!"));
    }
    
    @PostConstruct
    private void initObject() {
        this.receitaIncomum = new ReceitaIncomum();
    }
}