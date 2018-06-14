/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaComum;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author franklin
 */
@Stateless(name = "despesaComumDAO")
public class DespesaComumDAO extends AbstractDAO<DespesaComum> implements DespesaComumLocalDAO {

    @PersistenceContext(unitName = "KeepitPU")
    private EntityManager entityManager;
    
    public DespesaComumDAO() {
        super(DespesaComum.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public List<DespesaComum> findByLoggedUser(long idUser) {
        Query query = entityManager.createQuery("SELECT dc FROM DespesaComum dc WHERE dc.usuario.id = :id");
        query.setParameter("id", idUser);
        List<DespesaComum> result = query.getResultList();
        return result;
    }

    @Override
    protected void validate(DespesaComum entity) throws BusinessException {
        if(entity.getValor() < 0)
            throw new BusinessException("Valor negativo inválido!");
        else if(entity.getDiaDoMes() < 1 || entity.getDiaDoMes() > 30)
            throw new BusinessException("Dia inválido");
    }
}
