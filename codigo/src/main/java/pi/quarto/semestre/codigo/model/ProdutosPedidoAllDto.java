package pi.quarto.semestre.codigo.model;

public class ProdutosPedidoAllDto {
    private long id;
    private long pedidoId;
    private String produtoNome;
    private long produtoQuantidade;
    private long produtoId;
    private String valor;
    private String valorTotal;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getPedidoId() {
        return pedidoId;
    }
    public void setPedidoId(long pedidoId) {
        this.pedidoId = pedidoId;
    }
    public String getProdutoNome() {
        return produtoNome;
    }
    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }
    public long getProdutoQuantidade() {
        return produtoQuantidade;
    }
    public void setProdutoQuantidade(long produtoQuantidade) {
        this.produtoQuantidade = produtoQuantidade;
    }
    public long getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(long produtoId) {
        this.produtoId = produtoId;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }
}
