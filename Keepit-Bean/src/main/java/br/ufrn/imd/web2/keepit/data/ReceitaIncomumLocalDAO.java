/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.ReceitaIncomum;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ailson F. dos Santos
 */
@Local
public interface ReceitaIncomumLocalDAO {

    void create(ReceitaIncomum receitaIncomum);

    void edit(ReceitaIncomum receitaIncomum);

    void remove(ReceitaIncomum receitaIncomum);

    ReceitaIncomum find(Object id);

    List<ReceitaIncomum> findAll();

    List<ReceitaIncomum> findRange(int[] range);

    int count();
    
}
