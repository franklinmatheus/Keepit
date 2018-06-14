/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaEstimadaLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaEstimada;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named(value = "controladorDespesaEstimada")
@RequestScoped
public class ControladorDespesaEstimada {
    
    @EJB(beanName = "despesaEstimadaDAO", beanInterface = DespesaEstimadaLocalDAO.class)
    private DespesaEstimadaLocalDAO despesaEstimadaDAO;
    
    private DespesaEstimada despesaEstimada;

    public void criarDespesaEstimada() {
        this.despesaEstimadaDAO.create(despesaEstimada);
    }
    
    public DespesaEstimada getDespesaEstimada() {
        return despesaEstimada;
    }

    public void setDespesaEstimada(DespesaEstimada despesaEstimada) {
        this.despesaEstimada = despesaEstimada;
    }
    
}
