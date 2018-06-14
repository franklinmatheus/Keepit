/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaIncomumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaIncomum;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named(value = "controladorDespesaIncomum")
@RequestScoped
public class ControladorDespesaIncomum {
    
    @EJB(beanName = "despesaIncomumDAO", beanInterface = DespesaIncomumLocalDAO.class)
    private DespesaIncomumLocalDAO despesaIncomumDAO;
    
    private DespesaIncomum despesaIncomum;

    public void criarDespesaIncomum() {
        this.despesaIncomumDAO.create(despesaIncomum);
    }
    
    public DespesaIncomum getDespesaIncomum() {
        return despesaIncomum;
    }

    public void setDespesaIncomum(DespesaIncomum despesaIncomum) {
        this.despesaIncomum = despesaIncomum;
    }
    
}
