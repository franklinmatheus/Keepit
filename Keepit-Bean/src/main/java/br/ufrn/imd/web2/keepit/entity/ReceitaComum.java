/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author franklin
 */
@Entity
@Table(name = "receita_comum")
public class ReceitaComum extends Receita implements Serializable {
    
    /**
     * Define se a receita pode ser adicionada todo mês de forma automática.
     */
    @Column(name = "automatica")
    private boolean automatica;

    @Column(name = "dia_do_mes")
    private int diaDoMes;

    public int getDiaDoMes() {
        return diaDoMes;
    }

    public void setDiaDoMes(int diaDoMes) {
        this.diaDoMes = diaDoMes;
    }

    public boolean isAutomatica() {
        return automatica;
    }

    public void setAutomatica(boolean automatica) {
        this.automatica = automatica;
    }
}
