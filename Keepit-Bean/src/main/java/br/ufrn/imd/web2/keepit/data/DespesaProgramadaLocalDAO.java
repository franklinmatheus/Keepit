/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaProgramada;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franklin
 */
@Local
public interface DespesaProgramadaLocalDAO {
    
    void create(DespesaProgramada despesaProgramada);

    void edit(DespesaProgramada despesaProgramada);

    void remove(DespesaProgramada despesaProgramada);

    DespesaProgramada find(Object id);

    List<DespesaProgramada> findAll();

    List<DespesaProgramada> findRange(int[] range);

    int count();
    
}
