package pi.quarto.semestre.codigo.model;

import jakarta.annotation.ManagedBean;

@ManagedBean
public class ImagensDto {
    private long id;
    private String nomeArquivo;
    private long produtoID;
    private String principal;

    public ImagensDto(long id, String nomeArquivo, long produtoID, String principal) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.produtoID = produtoID;
        this.principal = principal;
    }

    public ImagensDto() {
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    public long getProdutoID() {
        return produtoID;
    }
    public void setProdutoID(long produtoID) {
        this.produtoID = produtoID;
    }
    public String getPrincipal() {
        return principal;
    }
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
