/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.Usuario;
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
@Stateless(name = "usuarioDAO")
public class UsuarioDAO extends AbstractDAO<Usuario> implements UsuarioLocalDAO {

    @PersistenceContext(unitName = "KeepitPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioDAO() {
        super(Usuario.class);
    }

    @Override
    protected void validate(Usuario entity) throws BusinessException {
        Date hoje = new Date();
        
        if(entity.getDataNascimento().compareTo(hoje) > 0) 
            throw new BusinessException("Data inválida!");
        else {
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :nome");
            query.setParameter("nome", entity.getNome());
            List<Usuario> result = query.getResultList();
            if(!result.isEmpty())
                throw new BusinessException("Nome do usuário já existe!");
        }
    }
    
}
