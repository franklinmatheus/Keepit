/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.ReceitaIncomum;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ailson F. dos Santos
 */
@Stateless(name = "receitaIncomumDAO")
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

    @Override
    public List<ReceitaIncomum> findByLoggedUser(long idUser) {
        Query query = em.createQuery("SELECT ri FROM ReceitaIncomum ri WHERE ri.usuario.id = :id");
        query.setParameter("id", idUser);
        List<ReceitaIncomum> result = query.getResultList();
        return result;
    }

    @Override
    protected void validate(ReceitaIncomum entity) throws BusinessException {
        Date hoje = new Date();
        if (entity.getData().compareTo(hoje) > 0) {
            throw new BusinessException("Data inválida!");
        } else if (entity.getValor() <= 0) {
            throw new BusinessException("Valor inválido!");
        }
    }

}
