package pi.quarto.semestre.codigo.model;

import jakarta.annotation.ManagedBean;

@ManagedBean
public class UsuarioDto {
    private long id;
    private String email;
    private String senha;
    private String grupo;
    private String nome;
    private String statuss;
    private String cpf;

    public UsuarioDto(long id, String email, String senha, String grupo, String nome, String statuss, String cpf) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.grupo = grupo;
        this.nome = nome;
        this.statuss = statuss;
        this.cpf = cpf;
    }

    public UsuarioDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
