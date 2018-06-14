/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaEstimada;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    @Override
    public List<DespesaEstimada> findByLoggedUser(long idUser) {
        Query query = entityManager.createQuery("SELECT de FROM DespesaEstimada de WHERE de.usuario.id = :id");
        query.setParameter("id", idUser);
        List<DespesaEstimada> result = query.getResultList();
        return result;
    }
}
