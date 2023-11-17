package pi.quarto.semestre.codigo.model;

public class ImagensAllDto {
    private Long id;
    private String nomeArquivo;
    private Long produtoID;
    private String principal;
    
    public String getPrincipal() {
        return principal;
    }
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    public Long getProdutoID() {
        return produtoID;
    }
    public void setProdutoID(Long produtoID) {
        this.produtoID = produtoID;
    }
}
