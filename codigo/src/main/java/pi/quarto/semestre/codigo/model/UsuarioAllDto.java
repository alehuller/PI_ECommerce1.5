package pi.quarto.semestre.codigo.model;

public class UsuarioAllDto {
    private Long id;
    private String email;
    private String senha;
    private String grupo;
    private String nome;
    private String statuss;
    private String cpf;
    
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
