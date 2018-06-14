/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.DespesaProgramada;
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
@Stateless(name = "despesaProgramadaDAO")
public class DespesaProgramadaDAO extends AbstractDAO<DespesaProgramada> implements DespesaProgramadaLocalDAO {

    @PersistenceContext(unitName = "KeepitPU")
    private EntityManager entityManager;

    public DespesaProgramadaDAO() {
        super(DespesaProgramada.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public List<DespesaProgramada> findByLoggedUser(long idUser) {
        Query query = entityManager.createQuery("SELECT dp FROM DespesaProgramada dp WHERE dp.usuario.id = :id");
        query.setParameter("id", idUser);
        List<DespesaProgramada> result = query.getResultList();
        return result;
    }

    @Override
    protected void validate(DespesaProgramada entity) throws BusinessException {
        Date hoje = new Date();
        if (entity.getValor() < 0) {
            throw new BusinessException("Valor negativo inválido!");
        } else if (entity.getData().compareTo(hoje) < 0) {
            throw new BusinessException("Data inválido");
        }
    }
}
