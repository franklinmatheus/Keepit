/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaProgramadaLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaProgramada;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named(value = "controladorDespesaProgramada")
@RequestScoped
public class ControladorDespesaProgramada {

    @EJB(beanName = "despesaProgramadaDAO", beanInterface = DespesaProgramadaLocalDAO.class)
    private DespesaProgramadaLocalDAO despesaProgramadaDAO;
    
    private DespesaProgramada despesaProgramada;

    public void criarDespesaProgramada() {
        this.despesaProgramadaDAO.create(despesaProgramada);
    }
    
    public DespesaProgramada getDespesaProgramada() {
        return despesaProgramada;
    }

    public void setDespesaProgramada(DespesaProgramada despesaProgramada) {
        this.despesaProgramada = despesaProgramada;
    }
    
}
