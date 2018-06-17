/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaComumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaComum;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.Calendar;
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
@Named(value = "controladorDespesaComum")
@RequestScoped
public class ControladorDespesaComum {

    private DespesaComum despesaComum;

    @EJB(beanName = "despesaComumDAO", beanInterface = DespesaComumLocalDAO.class)
    private DespesaComumLocalDAO despesaComumDAO;

    @Inject
    private ControladorLogin controladorLogin;

    @Inject
    private ControladorUsuario controladorUsuario;

    public DespesaComum getDespesaComum() {
        return despesaComum;
    }

    public void setDespesaComum(DespesaComum despesaComum) {
        this.despesaComum = despesaComum;
    }

    public void criarDespesaComum() {
        this.despesaComum.setUsuario(controladorLogin.getUsuario());
        this.despesaComum.setData(new Date());
        try {
            this.despesaComumDAO.create(despesaComum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa comum " + despesaComum.getTitulo() + " adicionada!", "Sucesso!"));
            if (this.estaAtrasada(despesaComum) && this.despesaComum.isAutomatica()) {
                this.atualizarDespesaComum(despesaComum);
            }
        } catch (BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Falha!"));
        }
        this.initObject();
    }

    public void removerDespesaComum(DespesaComum despesaComum) {
        this.despesaComumDAO.remove(despesaComum);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa comum " + despesaComum.getTitulo() + " removida!", "Sucesso!"));
    }

    public void editarDespesaComum(DespesaComum despesaComum) {
        this.despesaComumDAO.edit(despesaComum);
    }

    public List<DespesaComum> getDespesasComuns() {
        return despesaComumDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }

    public boolean estaAtrasada(DespesaComum despesaComum) {
        Date hoje = new Date();
        Calendar calendario = Calendar.getInstance();
        if (despesaComum.getDiaDoMes() < calendario.get(Calendar.DAY_OF_MONTH)) {
            if (despesaComum.getUltimaAtualizacao() == null) {
                if (despesaComum.getData().compareTo(hoje) > 0) {
                    return true;
                }
            } else if (despesaComum.getUltimaAtualizacao().compareTo(hoje) > 0) {
                return true;
            }
        } else if (despesaComum.getDiaDoMes() == calendario.get(Calendar.DAY_OF_MONTH)) {
            if (despesaComum.getUltimaAtualizacao() == null || despesaComum.getUltimaAtualizacao().compareTo(hoje) > 0) {
                return true;
            }
        }
        return false;
    }

    public void atualizarDespesaComum(DespesaComum despesaComum) {
        Date hoje = new Date();
        this.controladorLogin.getUsuario().setSaldo(this.controladorLogin.getUsuario().getSaldo() - despesaComum.getValor());
        this.controladorUsuario.editarUsuario(controladorLogin.getUsuario());
        despesaComum.setUltimaAtualizacao(hoje);
        this.editarDespesaComum(despesaComum);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Despesa comum " + despesaComum.getTitulo() + " e saldo atualizados!", "Sucesso!"));
    }

    public void checarDespesasComuns() {
        List<DespesaComum> despesas = this.getDespesasComuns();
        int quantidade = 0;
        int precisamAtualizar = 0;

        for (DespesaComum despesa : despesas) {
            if (estaAtrasada(despesa)) {
                if (despesa.isAutomatica()) {
                    atualizarDespesaComum(despesa);
                    quantidade++;
                } else {
                    precisamAtualizar++;
                }
            }
        }

        if (quantidade > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, quantidade + " despesas comuns foram atualizadas!", "Sucesso!"));
        }

        if (precisamAtualizar > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, precisamAtualizar + " despesas comuns precisam ser atualizadas manualmente!", "Aviso!"));
        }
    }

    @PostConstruct
    private void initObject() {
        this.despesaComum = new DespesaComum();
    }
}
