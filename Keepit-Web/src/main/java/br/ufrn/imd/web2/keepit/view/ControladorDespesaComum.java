/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.DespesaComumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.DespesaComum;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named(value = "controladorDespesaComum")
@RequestScoped
public class ControladorDespesaComum {
 
    @EJB(beanName = "despesaComumDAO", beanInterface = DespesaComumLocalDAO.class)
    private DespesaComumLocalDAO despesaComumDAO;
    
    private DespesaComum despesaComum;

    public void criarDespesaComum() {
        this.despesaComumDAO.create(despesaComum);
    }
    
    public DespesaComum getDespesaComum() {
        return despesaComum;
    }

    public void setDespesaComum(DespesaComum despesaComum) {
        this.despesaComum = despesaComum;
    }
}
