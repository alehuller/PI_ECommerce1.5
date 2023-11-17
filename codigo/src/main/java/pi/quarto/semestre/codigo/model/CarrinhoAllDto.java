package pi.quarto.semestre.codigo.model;

public class CarrinhoAllDto {
    private long id;
    private String produtoId;
    private long quantidade;
    private String nome;
    private String valor;
    private String valorTotal;
    private String precoTotal;
    private String precoCEP;
    private String formaPagamento;
    private String nomeCartao;
    private String numeroCartao;
    private String anoExpiracaoCartao;
    private String cvvCartao;
    private String parcelaCartao;
    
    public String getPrecoTotal() {
        return precoTotal;
    }
    public void setPrecoTotal(String precoTotal) {
        this.precoTotal = precoTotal;
    }
    public String getPrecoCEP() {
        return precoCEP;
    }
    public void setPrecoCEP(String precoCEP) {
        this.precoCEP = precoCEP;
    }
    public String getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }
    public long getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
    
    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getAnoExpiracaoCartao() {
        return anoExpiracaoCartao;
    }

    public void setAnoExpiracaoCartao(String anoExpiracaoCartao) {
        this.anoExpiracaoCartao = anoExpiracaoCartao;
    }

    public String getCvvCartao() {
        return cvvCartao;
    }

    public void setCvvCartao(String cvvCartao) {
        this.cvvCartao = cvvCartao;
    }

    public String getParcelaCartao() {
        return parcelaCartao;
    }

    public void setParcelaCartao(String parcelaCartao) {
        this.parcelaCartao = parcelaCartao;
    }
}
