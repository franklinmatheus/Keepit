/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.imd.web2.keepit.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author franklin
 */
@Entity
@Table(name = "votacao")
public class Votacao implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    
    @OneToOne(mappedBy = "votacao", fetch = FetchType.EAGER)
    private Solicitacao solicitacao;
}
