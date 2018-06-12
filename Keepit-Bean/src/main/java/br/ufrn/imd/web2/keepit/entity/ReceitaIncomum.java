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
@Table(name = "receira_incomum")
public class ReceitaIncomum extends Receita implements Serializable {
    
    /**
     * Motivo da receita.
     */
    @Column(name = "motivo", nullable = true)
    private String motivo;
    
    /**
     * Emissor da receita.
     */
    @Column(name = "emissor", nullable = true)
    private String emissor;

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }
    
    public void pegarInformacoes(){
        
    }
    
    public void adicionarParcelaPosterior(){
    
    }
}
