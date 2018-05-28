/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.ReceitaIncomum;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ailson F. dos Santos
 */
@Stateless
public class ReceitaIncomumDAO extends AbstractDAO<ReceitaIncomum> implements ReceitaIncomumLocalDAO {

    @PersistenceContext(unitName = "KeepitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceitaIncomumDAO() {
        super(ReceitaIncomum.class);
    }
    
}
