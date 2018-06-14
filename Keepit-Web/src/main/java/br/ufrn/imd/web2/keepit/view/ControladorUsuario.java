/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.UsuarioLocalDAO;
import br.ufrn.imd.web2.keepit.entity.ClasseSocial;
import br.ufrn.imd.web2.keepit.entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
    private Usuario usuario;
    
    public void criarUsuario() {
        usuarioDAO.create(usuario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado!", "Sucesso!"));
        this.initObject();
    }
    
    public List<Usuario> listaUsuarios() {
        return usuarioDAO.findAll();
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
    
    public List<String> getListaClasseSocial() {
        List<String> classes = new ArrayList<>();
        classes.add((ClasseSocial.BAIXA).toString());
        classes.add((ClasseSocial.MEDIA).toString());
        classes.add((ClasseSocial.ALTA).toString());
        return classes;
    }
    
    @PostConstruct
    private void initObject() {
        this.usuario = new Usuario();
    }
}
