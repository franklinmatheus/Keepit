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
        lineModel = new LineChartModel();
        lineModel.addSeries(initDespesaLinearModel());
        lineModel.addSeries(initReceitaLinearModel());
//        lineModel.addSeries(initUserDespesasLinearModel());
//        lineModel.addSeries(initUserReceitasLinearModel());
        lineModel.setTitle("Comparação com outros usuarios nos últimos 30 dias");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("R$ ");
        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setLabel("Dia");
    }
    
    private LineChartSeries initDespesaLinearModel() {
        LineChartSeries seriesDespesa = new LineChartSeries();
        seriesDespesa.setLabel("Despesas de todos os Usuários");
        
        for(Usuario u : usuarios){
            List<Despesa> despesas = u.getDespesasUltimos30DiasAPartirDe(new Date());
            for(Despesa d : despesas){
                Calendar cal = Calendar.getInstance();
                cal.setTime(d.getData());
                int day = cal.get(Calendar.DAY_OF_MONTH);
                seriesDespesa.set(day, d.getValor());
            }
        }
        
        return seriesDespesa;
    }
    
    private LineChartSeries initReceitaLinearModel() {
        LineChartModel model = new LineChartModel();
        
        LineChartSeries seriesReceita = new LineChartSeries();
        seriesReceita.setLabel("Receitas de todos os Usuários");
        
        for(Usuario u : usuarios){
            List<Receita> receitas = u.getReceitasUltimos30DiasAPartirDe(new Date());
            for(Receita r : receitas){
                Calendar cal = Calendar.getInstance();
                cal.setTime(r.getData());
                int day = cal.get(Calendar.DAY_OF_MONTH);
                seriesReceita.set(day, r.getValor());
            }
        }
        
        return seriesReceita;
    }

    private LineChartSeries initUserReceitasLinearModel() {
        LineChartModel model = new LineChartModel();
        
        LineChartSeries seriesReceita = new LineChartSeries();
        seriesReceita.setLabel("Suas receitas");
        
        List<Receita> receitas = controladorLogin.getUsuario().getReceitasUltimos30DiasAPartirDe(new Date());
        for(Receita r : receitas){
            Calendar cal = Calendar.getInstance();
            cal.setTime(r.getData());
            int day = cal.get(Calendar.DAY_OF_MONTH);
            seriesReceita.set(day, r.getValor());
        }
        
        return seriesReceita;
    }
    
    private LineChartSeries initUserDespesasLinearModel() {
        LineChartModel model = new LineChartModel();
        
        LineChartSeries seriesDespesa = new LineChartSeries();
        seriesDespesa.setLabel("Suas despesas");
        
        List<Despesa> despesas = controladorLogin.getUsuario().getDespesasUltimos30DiasAPartirDe(new Date());
            for(Despesa d : despesas){
                Calendar cal = Calendar.getInstance();
                cal.setTime(d.getData());
                int day = cal.get(Calendar.DAY_OF_MONTH);
                seriesDespesa.set(day, d.getValor());
            }
        
        return seriesDespesa;
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
