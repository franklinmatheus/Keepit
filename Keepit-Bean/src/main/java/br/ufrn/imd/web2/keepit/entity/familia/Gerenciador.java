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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author franklin
 */
@Entity
@Table(name = "gerenciador")
public class Gerenciador extends Usuario {
    /**
     * Define o peso do voto do gerenciador nas votações familiares.
     */
    public static final int PESO = 3;
    
    @Column(name = "salario")
    private double salario;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data")
    private Date data;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "familia_id", nullable = false)
    private Familia familia;

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
}
