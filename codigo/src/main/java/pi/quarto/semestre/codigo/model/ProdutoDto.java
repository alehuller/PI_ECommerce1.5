package pi.quarto.semestre.codigo.model;

import jakarta.annotation.ManagedBean;

@ManagedBean
public class ProdutoDto {
    private long codigo;
    private String nome;
    private String quantidade;
    private String valor;
    private String statuss;
    private String avaliacao;
    private String descricao;
    private String imagens;
    private String imagemPrincipal;

    public ProdutoDto(long codigo, String nome, String quantidade, String valor, String statuss, String avaliacao,
            String descricao, String imagens, String imagemPrincipal) {
        this.codigo = codigo;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.statuss = statuss;
        this.avaliacao = avaliacao;
        this.descricao = descricao;
        this.imagens = imagens;
        this.imagemPrincipal = imagemPrincipal;
    }

    public ProdutoDto() {
    }
    
    public long getCodigo() {
        return codigo;
    }
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String getStatuss() {
        return statuss;
    }
    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }
    public String getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getImagens() {
        return imagens;
    }
    public void setImagens(String imagens) {
        this.imagens = imagens;
    }
    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

}
