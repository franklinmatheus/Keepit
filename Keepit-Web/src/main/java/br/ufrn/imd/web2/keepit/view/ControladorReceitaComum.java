/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.ReceitaComumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.ReceitaComum;
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
@Named(value = "controladorReceitaComum")
@RequestScoped
public class ControladorReceitaComum {

    private ReceitaComum receitaComum;

    @EJB(beanName = "receitaComumDAO", beanInterface = ReceitaComumLocalDAO.class)
    private ReceitaComumLocalDAO receitaComumDAO;

    @Inject
    private ControladorLogin controladorLogin;

    @Inject
    private ControladorUsuario controladorUsuario;

    public ReceitaComum getReceitaComum() {
        return receitaComum;
    }

    public void setReceitaComum(ReceitaComum receitaComum) {
        this.receitaComum = receitaComum;
    }

    public void criarReceitaComum() {
        this.receitaComum.setUsuario(controladorLogin.getUsuario());
        this.receitaComum.setData(new Date());
        try {
            receitaComumDAO.create(receitaComum);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receita comum " + receitaComum.getTitulo() + " adicionada!", "Sucesso!"));
            if (this.estaAtrasada(receitaComum) && this.receitaComum.isAutomatica()) {
                this.atualizarReceitaComum(receitaComum);
            }
        } catch (BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Falha!"));
        }
        this.initObject();
    }

    public void removerReceitaComum(ReceitaComum receitaComum) {
        this.receitaComumDAO.remove(receitaComum);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receita " + receitaComum.getTitulo() + " foi removida!", "Falha!"));
    }

    public void editarReceitaComum(ReceitaComum receitaComum) {
        this.receitaComumDAO.edit(receitaComum);
    }

    public List<ReceitaComum> getReceitasComuns() {
        return receitaComumDAO.findByLoggedUser(controladorLogin.getUsuario().getId());
    }

    public boolean estaAtrasada(ReceitaComum receitaComum) {
        Date hoje = new Date();
        Calendar calendario = Calendar.getInstance();
        if (receitaComum.getDiaDoMes() < calendario.get(Calendar.DAY_OF_MONTH)) {
            if (receitaComum.getUltimaAtualizacao() == null) {
                if (receitaComum.getData().compareTo(hoje) > 0) {
                    return true;
                }
            } else if (receitaComum.getUltimaAtualizacao().compareTo(hoje) > 0) {
                return true;
            }
        } else if (receitaComum.getDiaDoMes() == calendario.get(Calendar.DAY_OF_MONTH)) {
            if (receitaComum.getUltimaAtualizacao() == null || receitaComum.getUltimaAtualizacao().compareTo(hoje) > 0) {
                return true;
            }
        }
        return false;
    }

    public void atualizarReceitaComum(ReceitaComum receitaComum) {
        Date hoje = new Date();
        this.controladorLogin.getUsuario().setSaldo(this.controladorLogin.getUsuario().getSaldo() + receitaComum.getValor());
        this.controladorUsuario.editarUsuario(controladorLogin.getUsuario());
        receitaComum.setUltimaAtualizacao(hoje);
        this.editarReceitaComum(receitaComum);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Receita comum " + receitaComum.getTitulo() + " e saldo atualizados!", "Sucesso!"));
    }

    public void checarReceitasComuns() {
        List<ReceitaComum> receitas = this.getReceitasComuns();
        int quantidade = 0;
        int precisamAtualizar = 0;

        for (ReceitaComum receita : receitas) {
            if (estaAtrasada(receita)) {
                if (receita.isAutomatica()) {
                    atualizarReceitaComum(receita);
                    quantidade++;
                } else {
                    precisamAtualizar++;
                }
            }
        }

        if (quantidade > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, quantidade + " receitas comuns foram atualizadas!", "Sucesso!"));
        }

        if (precisamAtualizar > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, precisamAtualizar + " receitas comuns precisam ser atualizadas manualmente!", "Aviso!"));
        }
    }

    @PostConstruct
    private void initObject() {
        this.receitaComum = new ReceitaComum();
    }
}
