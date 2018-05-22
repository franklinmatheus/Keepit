/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author franklin
 */
@Entity
@Table(name = "familia")
public class Familia implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    
    /**
     * Nome da família (e.g, sobrenome da família).
     */
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "renda_mensal")
    private double renda_mensal;
    
    @OneToMany(mappedBy = "familia", fetch = FetchType.EAGER)
    private List<Votacao> votacoes;
    
    @OneToOne(mappedBy = "familia", fetch = FetchType.EAGER)
    private Gerenciador gerenciador;
    
    @OneToMany(mappedBy = "familia", fetch = FetchType.EAGER)
    private List<Contribuinte> contribuintes;
    
    @OneToMany(mappedBy = "familia", fetch = FetchType.EAGER)
    private List<Dependente> dependentes;
}
