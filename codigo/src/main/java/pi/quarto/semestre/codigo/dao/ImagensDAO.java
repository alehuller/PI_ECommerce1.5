package pi.quarto.semestre.codigo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import pi.quarto.semestre.codigo.model.Imagens;
import pi.quarto.semestre.codigo.model.ImagensAllDto;
import pi.quarto.semestre.codigo.model.Produto;
import pi.quarto.semestre.codigo.model.ProdutoAllDto;

@Repository
public class ImagensDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public List<ImagensAllDto> findAll() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM imagenss;";
            try (Statement stm = con.createStatement();
                 ResultSet rs = stm.executeQuery(sql)) {
                
                List<ImagensAllDto> listAll = new ArrayList<ImagensAllDto>();

                while (rs.next()) {
                    ImagensAllDto n = new ImagensAllDto();
                    n.setId(rs.getLong("id"));
                    n.setNomeArquivo(rs.getString("nome_arquivo"));
                    n.setProdutoID(rs.getLong("produto_id"));
                    n.setPrincipal(rs.getString("principal"));
                    listAll.add(n);
                }
                return listAll;
            }
        }
    }

    public List<ImagensAllDto> findImagensByProdutoId(long produtoID) throws SQLException {
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM imagenss WHERE produto_id = ?;";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setLong(1, produtoID);
            try (ResultSet rs = stm.executeQuery()) {

                List<ImagensAllDto> imagens = new ArrayList<>();

                while (rs.next()) {
                    ImagensAllDto imagem = new ImagensAllDto();
                    imagem.setId(rs.getLong("id"));
                    imagem.setNomeArquivo(rs.getString("nome_arquivo"));
                    // Se você quiser mapear outros campos da tabela imagens, adicione-os aqui

                    imagens.add(imagem);
                }

                return imagens;
            }
        }
    }
}

    public void inserir(Imagens imagens) throws SQLException {
		var con = DriverManager.getConnection(URL, USER, PASSWORD);

		var ps = con.prepareStatement("INSERT INTO imagenss (nome_arquivo, produto_id) VALUES (?, ?)");
        ps.setString(1, "images/" + imagens.getNomeArquivo());
		ps.setLong(2, imagens.getProdutoID());
		
		ps.execute();

		con.close();
	}

    public void removerPorProdutoId(Long produtoId) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement("DELETE FROM imagenss WHERE produto_id = ?")) {
    
            ps.setLong(1, produtoId);
            ps.execute();
        }
    }
    
    public void imagemPrincipalSql(Long produtoId) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE imagenss SET principal = 'esse' WHERE id = ?;")) {
            ps.setLong(1, produtoId);
            ps.execute();
        }
    }

    public String pegarNome(Long produtoId) throws SQLException {
        String nomeArquivo = null;
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement("SELECT nome_arquivo FROM imagenss WHERE id = ?")) {
            ps.setLong(1, produtoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nomeArquivo = rs.getString("nome_arquivo");
                }
            }
        }
        return nomeArquivo;
    }

    public Long pegarCodigo(Long produtoId) throws SQLException {
        Long codigo = (long) -1;
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement("SELECT produto_id FROM imagenss WHERE id = ?;")) {
            ps.setLong(1, produtoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    codigo = rs.getLong("produto_id");
                }
            }
        }
        return codigo;
    }
    
    public void removerImagemPrincipal(Long codigo) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement("UPDATE produto SET imagemPrincipal = NULL WHERE codigo = ?")) {
            ps.setLong(1, codigo);
            ps.executeUpdate();
        }
    }
    
}
