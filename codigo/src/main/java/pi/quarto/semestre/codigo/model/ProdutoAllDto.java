package pi.quarto.semestre.codigo.model;

public class ProdutoAllDto {
    private Long codigo;
    private String nome;
    private String quantidade;
    private String valor;
    private String statuss;
    private String avaliacao;
    private String descricao;
    private String imagens;
    private String imagemPrincipal;
    
    public String getImagemPrincipal() {
        return imagemPrincipal;
    }
    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
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
}
