/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaIncomum;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franklin
 */
@Local
public interface DespesaIncomumLocalDAO {
    
    void create(DespesaIncomum despesaIncomum) throws BusinessException;

    void edit(DespesaIncomum despesaIncomum);

    void remove(DespesaIncomum despesaIncomum);

    DespesaIncomum find(Object id);

    List<DespesaIncomum> findAll();
    
    List<DespesaIncomum> findByLoggedUser(long idUser);

    List<DespesaIncomum> findRange(int[] range);

    int count();
    
}
