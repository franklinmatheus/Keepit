/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.UsuarioDAO;
import br.ufrn.imd.web2.keepit.entity.Usuario;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named
@ApplicationScoped
public class ControladorUsuario {
    
    private Usuario usuario;
    
    @EJB
    private UsuarioDAO usuarioDAO;
    
    private void criarUsuario() {
        usuarioDAO.create(usuario);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
