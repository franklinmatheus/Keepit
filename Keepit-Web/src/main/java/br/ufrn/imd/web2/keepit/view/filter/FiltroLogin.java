/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.view.filter;

import br.ufrn.imd.web2.keepit.view.ControladorLogin;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author franklin
 */
public class FiltroLogin implements Filter {

    @Inject
    private ControladorLogin controladorLogin;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURL().toString();
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
        if(controladorLogin.getUsuario() == null)
            response.sendRedirect(request.getServletContext().getContextPath() + "/login.xhtml");
        else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
}
