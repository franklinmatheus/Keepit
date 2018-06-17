/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaEstimadaLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaEstimada;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.Date;
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

    @Inject
    private ControladorUsuario controladorUsuario;

    public DespesaEstimada getDespesaEstimada() {
        return despesaEstimada;
    }

    public void setDespesaEstimada(DespesaEstimada despesaEstimada) {
        this.despesaEstimada = despesaEstimada;
    }

    public void criarDespesaEstimada() {
        this.despesaEstimada.setUsuario(controladorLogin.getUsuario());
        try {
            this.despesaEstimadaDAO.create(despesaEstimada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa estimada " + despesaEstimada.getTitulo() + " adicionada!", "Sucesso!"));
            this.controladorLogin.getUsuario().setSaldo(this.controladorLogin.getUsuario().getSaldo() - despesaEstimada.getValor());
            this.controladorUsuario.editarUsuario(this.controladorLogin.getUsuario());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Saldo atualizado!", "Falha!"));
        } catch (BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Falha!"));
        }
        this.initObject();
    }

    public void removerDespesaEstimada(DespesaEstimada despesaEstimada) {
        this.despesaEstimadaDAO.remove(despesaEstimada);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa estimada " + despesaEstimada.getTitulo() + " removida!", "Falha!"));
    }

    public void atualizarDespesaEstimada(DespesaEstimada despesaEstimada) {
        this.despesaEstimadaDAO.edit(despesaEstimada);
    }

    public List<DespesaEstimada> getDespesasEstimadas() {
        return despesaEstimadaDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }

    public boolean estaAtrasada(DespesaEstimada despesaEstimada) {
        Date hoje = new Date();
        if (!despesaEstimada.isConfirmada() && despesaEstimada.getData().compareTo(hoje) <= 0) {
            return true;
        }
        return false;
    }

    public void confirmarDespesaEstimada(DespesaEstimada despesaEstimada) {
        despesaEstimada.setConfirmada(true);
        this.despesaEstimadaDAO.edit(despesaEstimada);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa estimada " + despesaEstimada.getTitulo() + " confirmada!", "Falha!"));
    }

    public void cancelarDespesaEstimada(DespesaEstimada despesaEstimada) {
        this.despesaEstimadaDAO.remove(despesaEstimada);
        this.controladorLogin.getUsuario().setSaldo(this.controladorLogin.getUsuario().getSaldo() + despesaEstimada.getValor());
        this.controladorUsuario.editarUsuario(this.controladorLogin.getUsuario());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa estimada " + despesaEstimada.getTitulo() + " removida e seu saldo foi atualizado!", "Falha!"));
    }

    public void checarDespesasEstimadas() {
        List<DespesaEstimada> despesas = this.getDespesasEstimadas();
        int quantidade = 0;

        for (DespesaEstimada despesa : despesas) {
            if (this.estaAtrasada(despesa)) {
                quantidade++;
            }
        }
        if (quantidade > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você precisa confirmar " + quantidade + " despesas estimadas!", "Sucesso!"));
        }
    }

    public double gastoEstimado() {
        List<DespesaEstimada> despesas = this.getDespesasEstimadas();
        double total = 0;

        for (DespesaEstimada despesa : despesas) {
            total += despesa.getValor();
        }
        return total;
    }

    @PostConstruct
    private void initObject() {
        this.despesaEstimada = new DespesaEstimada();
    }
}
