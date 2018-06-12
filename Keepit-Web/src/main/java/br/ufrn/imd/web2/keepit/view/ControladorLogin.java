/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.entity.Usuario;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author franklin
 */
@Named(value = "controladorLogin")
@SessionScoped
public class ControladorLogin implements Serializable {
    
    @Inject
    private HttpSession session;

    public void login(Usuario usuario) {
        this.setUsuario(usuario);
    }
    
    public void logout() {
        session.removeAttribute("logado");
        session.invalidate();
    }
    
    public Usuario getUsuario() {
        return (Usuario) this.session.getAttribute("logado");
    }

    public void setUsuario(Usuario usuario) {
        this.session.setAttribute("logado", usuario);
    }
    
}
