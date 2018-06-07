/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.entity;

import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author franklin
 */
public class Dependente extends Usuario {
    /**
     * Define o peso do voto do dependente nas votações familiares.
     */
    public static final int PESO = 1;
    
    @OneToMany(mappedBy = "dependente", fetch = FetchType.EAGER)
    private List<AuxilioDependencia> auxilios;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "familia_id", nullable = false)
    private Familia familia;

    public List<AuxilioDependencia> getAuxilios() {
        return auxilios;
    }

    public void setAuxilios(List<AuxilioDependencia> auxilios) {
        this.auxilios = auxilios;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
}
