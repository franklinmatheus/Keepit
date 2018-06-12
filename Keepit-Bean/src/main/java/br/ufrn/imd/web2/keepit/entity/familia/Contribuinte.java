/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.entity.familia;

import br.ufrn.imd.web2.keepit.entity.Usuario;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author franklin
 */
@Entity
@Table(name = "contribuinte")
public class Contribuinte extends Usuario {
    /**
     * Define o peso do voto do contribuinte nas votações familiares.
     */
    public static final int PESO = 2;
    
    /**
     * Define o valor que o contribuinte costuma receber.
     */
    @Column(name = "salario")
    private double salario;
    
    @Column(name = "salario_fixo")
    private boolean salario_fixo;
    
    /**
     * Define a data que o salário costuma entrar.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "data")
    private Date data;
    
    @Column(name = "data_fixa")
    private boolean data_fixa;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "familia_id", nullable = false)
    private Familia familia;

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isSalario_fixo() {
        return salario_fixo;
    }

    public void setSalario_fixo(boolean salario_fixo) {
        this.salario_fixo = salario_fixo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isData_fixa() {
        return data_fixa;
    }

    public void setData_fixa(boolean data_fixa) {
        this.data_fixa = data_fixa;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    
    
}
