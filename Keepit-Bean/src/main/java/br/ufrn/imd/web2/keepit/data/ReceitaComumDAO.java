/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.ReceitaComum;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author franklin
 */
@Stateless(name = "receitaComumDAO")
public class ReceitaComumDAO extends AbstractDAO<ReceitaComum> implements ReceitaComumLocalDAO {
    
    @PersistenceContext(unitName = "KeepitPU")
    private EntityManager entityManager;
    
    public ReceitaComumDAO() {
        super(ReceitaComum.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public List<ReceitaComum> findByLoggedUser(long idUser) {
        Query query = entityManager.createQuery("SELECT rc FROM ReceitaComum rc WHERE rc.usuario.id = :id");
        query.setParameter("id", idUser);
        List<ReceitaComum> result = query.getResultList();
        return result;
    }
    
}
