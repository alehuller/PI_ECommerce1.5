package pi.quarto.semestre.codigo.model;

public class ClienteAllDto {
    private long id;
    private String email;
    private String senha;
    private String cpf;
    private String nome;
    private String dataNascimento;
    private String genero;
    private String enderecoPrincipal;
    
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
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getEnderecoPrincipal() {
        return enderecoPrincipal;
    }
    public void setEnderecoPrincipal(String enderecoPrincipal) {
        this.enderecoPrincipal = enderecoPrincipal;
    }

}
