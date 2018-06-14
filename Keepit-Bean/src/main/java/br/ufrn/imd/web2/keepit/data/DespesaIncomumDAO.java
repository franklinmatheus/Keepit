/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaIncomum;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author franklin
 */
@Stateless(name = "despesaIncomumDAO")
public class DespesaIncomumDAO extends AbstractDAO<DespesaIncomum> implements DespesaIncomumLocalDAO {

    @PersistenceContext(unitName = "KeepitPU")
    private EntityManager entityManager;
    
    public DespesaIncomumDAO() {
        super(DespesaIncomum.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public List<DespesaIncomum> findByLoggedUser(long idUser) {
        Query query = entityManager.createQuery("SELECT di FROM DespesaIncomum di WHERE di.usuario.id = :id");
        query.setParameter("id", idUser);
        List<DespesaIncomum> result = query.getResultList();
        return result;
    }
    
}
