/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.UsuarioLocalDAO;
import br.ufrn.imd.web2.keepit.entity.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author franklin
 */
@Named(value = "controladorUsuario")
@RequestScoped
public class ControladorUsuario implements Serializable {
    
    @EJB(beanName = "usuarioDAO", beanInterface = UsuarioLocalDAO.class)
    private UsuarioLocalDAO usuarioDAO;
    
    @Inject
    private ControladorLogin controladorLogin;
    
    private Usuario usuario = new Usuario();
    
    public void criarUsuario() {
        usuarioDAO.create(usuario);
    }
    
    public List<Usuario> listaUsuarios() {
        return usuarioDAO.findAll();
    }
    
    public void login(Usuario usuario) {
        controladorLogin.login(usuario);
    }
    
    public void logout() {
        controladorLogin.logout();
    }
    
    public void remover(Usuario usuario) {
        usuarioDAO.remove(usuario);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
