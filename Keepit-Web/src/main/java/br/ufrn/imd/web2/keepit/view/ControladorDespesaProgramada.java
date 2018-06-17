/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaProgramadaLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaProgramada;
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
@Named(value = "controladorDespesaProgramada")
@RequestScoped
public class ControladorDespesaProgramada {

    private DespesaProgramada despesaProgramada;

    @EJB(beanName = "despesaProgramadaDAO", beanInterface = DespesaProgramadaLocalDAO.class)
    private DespesaProgramadaLocalDAO despesaProgramadaDAO;

    @Inject
    private ControladorLogin controladorLogin;

    @Inject
    private ControladorUsuario controladorUsuario;

    public DespesaProgramada getDespesaProgramada() {
        return despesaProgramada;
    }

    public void setDespesaProgramada(DespesaProgramada despesaProgramada) {
        this.despesaProgramada = despesaProgramada;
    }

    public void criarDespesaProgramada() {
        this.despesaProgramada.setUsuario(controladorLogin.getUsuario());
        try {
            this.despesaProgramadaDAO.create(despesaProgramada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa programada adicionada!", "Sucesso!"));
        } catch (BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Falha!"));
        }
        this.initObject();
    }

    public void removerDespesaProgramada(DespesaProgramada despesaProgramada) {
        this.despesaProgramadaDAO.remove(despesaProgramada);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa programada " + despesaProgramada.getTitulo() + " removida!", "Falha!"));
    }

    public void atualizarDespesaProgramada() {
        this.despesaProgramadaDAO.edit(despesaProgramada);
    }

    public List<DespesaProgramada> getDespesasProgramadas() {
        return despesaProgramadaDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }

    public boolean estaAtrasada(DespesaProgramada despesaProgramada) {
        Date hoje = new Date();
        if (!despesaProgramada.isDeferida() && despesaProgramada.getData().compareTo(hoje) <= 0) {
            return true;
        }
        return false;
    }

    public void deferirDespesaProgramada(DespesaProgramada despesaProgramada) {
        despesaProgramada.setDeferida(true);
        this.controladorLogin.getUsuario().setSaldo(this.controladorLogin.getUsuario().getSaldo() - despesaProgramada.getValor());
        this.controladorUsuario.editarUsuario(this.controladorLogin.getUsuario());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa programada " + despesaProgramada.getTitulo() + " deferida e seu saldo foi atualizado!", "Falha!"));
    }

    public void checarDespesasProgramadas() {
        List<DespesaProgramada> despesas = this.getDespesasProgramadas();
        int quantidade = 0;

        for (DespesaProgramada despesa : despesas) {
            if (this.estaAtrasada(despesa)) {
                quantidade++;
            }
        }
        if (quantidade > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você precisa deferir " + quantidade + " despesas programadas!", "Sucesso!"));
        }
    }

    public double gastoProgramado() {
        List<DespesaProgramada> despesas = this.getDespesasProgramadas();
        double total = 0;

        for (DespesaProgramada despesa : despesas) {
            total += despesa.getValor();
        }
        return total;
    }

    @PostConstruct
    private void initObject() {
        this.despesaProgramada = new DespesaProgramada();
    }
}
