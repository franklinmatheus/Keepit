/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.ReceitaIncomumDAO;
import br.ufrn.imd.web2.keepit.entity.ReceitaIncomum;
import javax.ejb.*;

/**
 *
 * @author Ailson F. dos Santos
 */
@Stateless
public class ControladorReceitaIncomum {
    
    public ReceitaIncomum receitaIncomum;
    
    @EJB
    public ReceitaIncomumDAO receitaIncomumDAO;
    
    public void criarReceitaIncomum(){
        receitaIncomumDAO.create(receitaIncomum);
    }
    
    public boolean verificarValorValido(){
        return receitaIncomum.getValor() >= 0;
    }
}
