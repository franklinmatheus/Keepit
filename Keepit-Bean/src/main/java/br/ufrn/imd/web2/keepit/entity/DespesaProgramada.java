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
@Table(name = "despesa_programada")
public class DespesaProgramada extends Despesa implements Serializable {

    @Column(name = "deferida")
    private boolean deferida;

    public boolean isDeferida() {
        return deferida;
    }

    public void setDeferida(boolean deferida) {
        this.deferida = deferida;
    }
}
