/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.ufrn.imd.web2.keepit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author franklin
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    
    @Column(name = "nome")
    private String nome;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    
    @Column(name = "saldo")
    private double saldo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "classe_social")
    private ClasseSocial classe_social;
    
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Despesa> despesas = new ArrayList<>();
    
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Receita> receitas = new ArrayList<>();
    
    public Usuario() { }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Date getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public ClasseSocial getClasse_social() {
        return classe_social;
    }
    
    public void setClasse_social(ClasseSocial classe_social) {
        this.classe_social = classe_social;
    }
    
    public List<Despesa> getDespesas() {
        return despesas;
    }
    
    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }
    
    public List<Receita> getReceitas() {
        return receitas;
    }
        
    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }
    
    public List<Object> getUltimos30DiasAPartirDe(Class classe, Date date) {
        List<Object> ultimas = new ArrayList<>();
        if(Despesa.class.equals(classe.getClass())){
            for(Despesa d : despesas){
                int result = date.compareTo(d.getData());
                if(result <=30 && result >= 0){
                    ultimas.add(d);
                }
            }
        } else if(Receita.class.equals(classe.getClass())){
            for(Receita r : receitas){
                int result = date.compareTo(r.getData());
                if(result <=30 && result >= 0){
                    ultimas.add(r);
                }
            }
        }
        return ultimas;
    }
        
    public List<Despesa> getDespesasUltimos30DiasAPartirDe(Date date) {
        List<Despesa> ultimasDespesas = new ArrayList<>();
        for(Despesa d : despesas){
            int result = date.compareTo(d.getData());
            if(result <=30 && result >= 0){
                ultimasDespesas.add(d);
            }
        }
        return ultimasDespesas;
    }
    
    public List<Receita> getReceitasUltimos30DiasAPartirDe(Date date) {
        List<Receita> ultimasReceitas = new ArrayList<>();
        for(Receita r : receitas){
            int result = date.compareTo(r.getData());
            if(result <=30 && result >= 0){
                ultimasReceitas.add(r);
            }
        }
        return ultimasReceitas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
