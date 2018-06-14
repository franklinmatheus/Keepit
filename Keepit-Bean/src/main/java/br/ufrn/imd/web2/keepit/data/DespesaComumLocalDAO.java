/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaComum;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franklin
 */
@Local
public interface DespesaComumLocalDAO {

    void create(DespesaComum despesaComum) throws BusinessException;

    void edit(DespesaComum despesaComum);

    void remove(DespesaComum despesaComum);

    DespesaComum find(Object id);

    List<DespesaComum> findAll();
    
    List<DespesaComum> findByLoggedUser(long idUser);

    List<DespesaComum> findRange(int[] range);

    int count();
    
}
