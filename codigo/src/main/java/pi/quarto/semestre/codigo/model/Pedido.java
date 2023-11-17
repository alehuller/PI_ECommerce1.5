package pi.quarto.semestre.codigo.model;

public class Pedido {
    private long id;
    private String clienteEmail;
    private String dataPedido;
    private String valorTotal;
    private String statuss;
    private String produtos;
    private String formaPagamento;
    private String frete;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    public Pedido(long id, String clienteEmail, String dataPedido, String valorTotal, String statuss, String produtos,
            String formaPagamento, String frete, String cep, String logradouro, String numero, String complemento,
            String bairro, String cidade, String uf) {
        this.id = id;
        this.clienteEmail = clienteEmail;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
        this.statuss = statuss;
        this.produtos = produtos;
        this.formaPagamento = formaPagamento;
        this.frete = frete;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Pedido() {
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getClienteEmail() {
        return clienteEmail;
    }
    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }
    public String getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }
    public String getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getStatuss() {
        return statuss;
    }
    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }
    public String getProdutos() {
        return produtos;
    }
    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }
    public String getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public String getFrete() {
        return frete;
    }

    public void setFrete(String frete) {
        this.frete = frete;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }


}
