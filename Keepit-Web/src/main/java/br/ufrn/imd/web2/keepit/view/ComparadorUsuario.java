/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.ufrn.imd.web2.keepit.view;

import br.ufrn.imd.web2.keepit.data.UsuarioLocalDAO;
import br.ufrn.imd.web2.keepit.entity.Despesa;
import br.ufrn.imd.web2.keepit.entity.Receita;
import br.ufrn.imd.web2.keepit.entity.Usuario;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
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
    
    private LineChartModel lineModel;// = new LineChartModel();
    
    @PostConstruct
    public void init(){
        preencherListaDeUsuariosDaMesmaClasseSocial();
        createLineModel();
    }
    
    public LineChartModel getLineModel() {
        return lineModel;
    }
    
    private void createLineModel() {
        lineModel = new LineChartModel();
        lineModel.addSeries(initDespesaLinearModel());
//        lineModel.addSeries(initReceitaLinearModel());
        lineModel.setTitle("Despesas de usuarios nos ultimos 30 dias");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("R$ ");
        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Dia");
    }
    
    private LineChartSeries initDespesaLinearModel() {
//        LineChartModel model = new LineChartModel();
        
        LineChartSeries seriesDespesa = new LineChartSeries();
        seriesDespesa.setLabel("Despesas de Usuários");
        
//        LineChartSeries seriesReceita = new LineChartSeries();
//        seriesReceita.setLabel("Receitas de Usuários");
        
        for(Usuario u : usuarios){
            List<Despesa> despesas = u.getDespesasUltimos30DiasAPartirDe(new Date());
//            List<Receita> receitas = u.getReceitasUltimos30DiasAPartirDe(new Date());
            for(Despesa d : despesas){
                Calendar cal = Calendar.getInstance();
                cal.setTime(d.getData());
                int day = cal.get(Calendar.DAY_OF_MONTH);
                seriesDespesa.set(day, d.getValor());
            }
//            for(Receita r : receitas){
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(r.getData());
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//                seriesReceita.set(day, r.getValor());
//            }
        }
        
//        model.addSeries(seriesDespesa);
//        model.addSeries(seriesReceita);
        
        return seriesDespesa;
    }
    
    private LineChartSeries initReceitaLinearModel() {
        LineChartModel model = new LineChartModel();
        
//        LineChartSeries seriesDespesa = new LineChartSeries();
//        seriesDespesa.setLabel("Despesas de Usuários");
        
        LineChartSeries seriesReceita = new LineChartSeries();
        seriesReceita.setLabel("Receitas de Usuários");
        
        for(Usuario u : usuarios){
//            List<Despesa> despesas = u.getDespesasUltimos30DiasAPartirDe(new Date());
            List<Receita> receitas = u.getReceitasUltimos30DiasAPartirDe(new Date());
//            for(Despesa d : despesas){
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(d.getData());
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//                seriesDespesa.set(day, d.getValor());
//            }
            for(Receita r : receitas){
                Calendar cal = Calendar.getInstance();
                cal.setTime(r.getData());
                int day = cal.get(Calendar.DAY_OF_MONTH);
                seriesReceita.set(day, r.getValor());
            }
        }
        
//        model.addSeries(seriesDespesa);
//        model.addSeries(seriesReceita);
        
        return seriesReceita;
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
