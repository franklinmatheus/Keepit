/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaProgramada;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author franklin
 */
@Stateless(name = "despesaProgramadaDAO")
public class DespesaProgramadaDAO extends AbstractDAO<DespesaProgramada> implements DespesaProgramadaLocalDAO {

    @PersistenceContext(name = "KeepitPU")
    private EntityManager entityManager;
    
    public DespesaProgramadaDAO() {
        super(DespesaProgramada.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    
}
