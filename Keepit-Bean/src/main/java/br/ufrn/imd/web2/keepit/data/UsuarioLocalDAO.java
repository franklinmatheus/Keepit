/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.data;

import br.ufrn.imd.web2.keepit.entity.Usuario;
import br.ufrn.imd.web2.keepit.exception.BusinessException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ailson F. dos Santos
 */
@Local
public interface UsuarioLocalDAO {

    void create(Usuario usuario) throws BusinessException;

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
}
