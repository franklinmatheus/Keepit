/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaComum;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author franklin
 */
@Stateless(name = "despesaComumDAO")
public class DespesaComumDAO extends AbstractDAO<DespesaComum> implements DespesaComumLocalDAO {

    @PersistenceContext(name = "KeepitPU")
    private EntityManager entityManager;
    
    public DespesaComumDAO() {
        super(DespesaComum.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    
}
