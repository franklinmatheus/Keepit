/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaEstimada;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author franklin
 */
@Stateless(name = "despesaEstimadaDAO")
public class DespesaEstimadaDAO extends AbstractDAO<DespesaEstimada> implements DespesaEstimadaLocalDAO {
    
    @PersistenceContext(unitName = "KeepitPU")
    private EntityManager entityManager;
    
    public DespesaEstimadaDAO() {
        super(DespesaEstimada.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
}
