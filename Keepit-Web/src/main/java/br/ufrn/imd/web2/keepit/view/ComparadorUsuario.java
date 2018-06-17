/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.UsuarioDAO;
import br.ufrn.imd.web2.keepit.data.UsuarioLocalDAO;
import br.ufrn.imd.web2.keepit.entity.Despesa;
import br.ufrn.imd.web2.keepit.entity.Receita;
import br.ufrn.imd.web2.keepit.entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Ailson F. dos Santos <ailsonforte@hotmail.com>
 */
@Named(value = "comparadorUsuario")
@RequestScoped
public class ComparadorUsuario {
    
    private ArrayList<Usuario> usuarios;
    
    @EJB(beanInterface = UsuarioLocalDAO.class, name = "usuarioDAO")
    private UsuarioLocalDAO usuarioLocalDAO;
    
    @Inject
    private ControladorLogin controladorLogin;
    
    private Usuario usuarioSessao;
    
    private LineChartModel lineModel;
    
    @PostConstruct
    public void init(){
        preencherListaDeUsuariosDaMesmaClasseSocial();
        createLineModel();
    }
    
    public LineChartModel getLineModel() {
        return lineModel;
    }
    
    private void createLineModel() {
        lineModel = initLinearModel();
        lineModel.setTitle("Despesas de usuarios nos ultimos 30 dias");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("R$");
    }
    
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
        
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Despesas de Usuários");
        
        for(Usuario u : usuarios){
            for(Despesa d : u.getDespesasUltimos30DiasAPartirDe(new Date())){
                series1.set(d.getData().compareTo(new Date()), d.getValor());
            }
        }
        model.addSeries(series1);
        
        return model;
    }
    
    private void preencherListaDeUsuariosDaMesmaClasseSocial(){
        this.usuarios = new ArrayList<>();
        usuarioSessao = controladorLogin.getUsuario();
        for(Usuario u : usuarioLocalDAO.findAll()){
            if(usuarioSessao.getClasse_social() == u.getClasse_social() && !usuarios.contains(u)){
                usuarios.add(u);
            }
        }
    }
    
    public double mediaDaReceitasDeTodosUsuariosNosUltimos30Dias(){
        double receitaMedia = 0.0;
        long counter = 0;
        for(Usuario u : usuarios){
            for(Receita r : u.getReceitasUltimos30DiasAPartirDe(new Date())){
                receitaMedia += r.getValor();
                counter++;
            }
        }
        if(counter != 0) receitaMedia /= counter;
        return receitaMedia;
    }
    
    public double mediaDasDespesasDeTodosUsuariosNosUltimos30Dias(){
        double despesaMedia = 0.0;
        long counter = 0;
        for(Usuario u : usuarios){
            for(Despesa d : u.getDespesasUltimos30DiasAPartirDe(new Date())){
                despesaMedia += d.getValor();
                counter++;
            }
        }
        if(counter != 0) despesaMedia /= counter;
        return despesaMedia;
    }
}
