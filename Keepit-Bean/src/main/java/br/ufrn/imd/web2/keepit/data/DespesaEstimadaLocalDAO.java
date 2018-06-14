/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaEstimada;
import br.ufrn.imd.web2.keepit.entity.ReceitaIncomum;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franklin
 */
@Local
public interface DespesaEstimadaLocalDAO {
    
    void create(DespesaEstimada despesaEstimada);

    void edit(DespesaEstimada despesaEstimada);

    void remove(DespesaEstimada despesaEstimada);

    DespesaEstimada find(Object id);

    List<DespesaEstimada> findAll();
    
    List<DespesaEstimada> findByLoggedUser(long idUser);

    List<DespesaEstimada> findRange(int[] range);

    int count();
    
}
