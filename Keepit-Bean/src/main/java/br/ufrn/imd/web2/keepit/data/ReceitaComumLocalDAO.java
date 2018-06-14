/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.ReceitaComum;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author franklin
 */
@Local
public interface ReceitaComumLocalDAO {
    
    void create(ReceitaComum receitaComum) throws BusinessException;

    void edit(ReceitaComum receitaComum);

    void remove(ReceitaComum receitaComum);

    ReceitaComum find(Object id);

    List<ReceitaComum> findAll();
    
    List<ReceitaComum> findByLoggedUser(long idUser);

    List<ReceitaComum> findRange(int[] range);

    int count();
    
}
