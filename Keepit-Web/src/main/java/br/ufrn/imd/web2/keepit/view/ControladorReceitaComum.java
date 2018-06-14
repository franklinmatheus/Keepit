/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.ReceitaComumLocalDAO;
import br.ufrn.imd.web2.keepit.entity.ReceitaComum;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named(value = "controladorReceitaComum")
@RequestScoped
public class ControladorReceitaComum {
 
    private ReceitaComum receitaComum = new ReceitaComum();
    
    @EJB(beanName = "receitaComumDAO", beanInterface = ReceitaComumLocalDAO.class)
    private ReceitaComumLocalDAO receitaComumDAO;

    public ReceitaComum getReceitaComum() {
        return receitaComum;
    }

    public void setReceitaComum(ReceitaComum receitaComum) {
        this.receitaComum = receitaComum;
    }
    
    public void criarReceitaComum(){
        receitaComumDAO.create(receitaComum);
    }
    
}
