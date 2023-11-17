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

import pi.quarto.semestre.codigo.model.Produto;
import pi.quarto.semestre.codigo.model.ProdutoAllDto;
import pi.quarto.semestre.codigo.model.Usuario;
import pi.quarto.semestre.codigo.model.UsuarioAllDto;

@Repository
public class ProdutoDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public List<ProdutoAllDto> findAll(int page, int pageSize) throws SQLException {
        // Calcule o índice inicial com base no número da página
        int startIndex = (page - 1) * pageSize;
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM produto ORDER BY codigo DESC LIMIT ?, ?;";
            try (PreparedStatement stm = con.prepareStatement(sql)) {
                stm.setInt(1, startIndex);
                stm.setInt(2, pageSize);
                
                try (ResultSet rs = stm.executeQuery()) {
                    List<ProdutoAllDto> listAll = new ArrayList<>();

                    while (rs.next()) {
                        ProdutoAllDto n = new ProdutoAllDto();
                        n.setCodigo(rs.getLong("codigo"));
                        n.setNome(rs.getString("nome"));
                        n.setQuantidade(rs.getString("quantidade"));
                        n.setValor(rs.getString("valor"));
                        n.setStatuss(rs.getString("statuss"));
                        n.setAvaliacao(rs.getString("avaliacao"));
                        n.setDescricao(rs.getString("descricao"));
                        n.setImagens(rs.getString("imagens"));

                        listAll.add(n);
                    }

                    return listAll;
                }
            }
        }
    }

    public List<ProdutoAllDto> findAllLandingPage() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM produto;";
            try (Statement stm = con.createStatement();
                 ResultSet rs = stm.executeQuery(sql)) {
                
                List<ProdutoAllDto> listAll = new ArrayList<ProdutoAllDto>();

                while (rs.next()) {
                    ProdutoAllDto n = new ProdutoAllDto();
                    n.setCodigo(rs.getLong("codigo"));
                    n.setNome(rs.getString("nome"));
                    n.setQuantidade(rs.getString("quantidade"));
                    n.setValor(rs.getString("valor"));
                    n.setStatuss(rs.getString("statuss"));
                    n.setAvaliacao(rs.getString("avaliacao"));
                    n.setDescricao(rs.getString("descricao"));
                    n.setImagens(rs.getString("imagens"));
                    n.setImagemPrincipal(rs.getString("imagemPrincipal"));

                    listAll.add(n);
                }

                return listAll;
            }
        }
    }

    public int countAll() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM produto;";
            try (Statement stm = con.createStatement();
                 ResultSet rs = stm.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    public Produto findByCodigo(Long id) throws SQLException {
       try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM produto WHERE codigo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setCodigo(rs.getLong("codigo"));
                    produto.setNome(rs.getString("nome"));
                    produto.setQuantidade(rs.getString("quantidade"));
                    produto.setValor(rs.getString("valor"));
                    produto.setStatuss(rs.getString("statuss"));
                    produto.setAvaliacao(rs.getString("avaliacao"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setImagens(rs.getString("imagens"));
                    return produto;
                }
                return null; // Retorna null se não encontrou o usuário com o CPF especificado
            }
        }
    }
    }

    public void mudarParaInativo(Produto produto) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        var ps = con.prepareStatement("UPDATE produto SET statuss = ? where codigo = ?");
        ps.setString(1, "Inativo");
        ps.setLong(2, produto.getCodigo());
        ps.execute();
        con.close();
    }
    
    public void mudarParaAtivo(Produto produto) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        var ps = con.prepareStatement("UPDATE produto SET statuss = ? where codigo = ?");
        ps.setString(1, "Ativo");
        ps.setLong(2, produto.getCodigo());
        ps.execute();
        con.close();
    }

    public List<ProdutoAllDto> findAllByNome(String nome) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM produto WHERE nome LIKE ? ORDER BY nome;";
            try (PreparedStatement stm = con.prepareStatement(sql)) {
                stm.setString(1, "%" + nome + "%"); // Correspondência parcial no nome
    
                try (ResultSet rs = stm.executeQuery()) {
                    List<ProdutoAllDto> listAll = new ArrayList<ProdutoAllDto>();
    
                    while (rs.next()) {
                        ProdutoAllDto n = new ProdutoAllDto();
                        n.setCodigo(rs.getLong("codigo"));
                        n.setNome(rs.getString("nome"));
                        n.setQuantidade(rs.getString("quantidade"));
                        n.setValor(rs.getString("valor"));
                        n.setStatuss(rs.getString("statuss"));
                        n.setAvaliacao(rs.getString("avaliacao"));
                        n.setDescricao(rs.getString("descricao"));
                        n.setImagens(rs.getString("imagens"));

                    listAll.add(n);
                    }
    
                    return listAll;
                }
            }
        }
    }

    public void inserir(Produto produto) throws SQLException {
		var con = DriverManager.getConnection(URL, USER, PASSWORD);

		var ps = con.prepareStatement("INSERT INTO produto (codigo, nome, quantidade, valor, statuss, avaliacao, descricao, imagens) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		ps.setLong(1, produto.getCodigo());
        ps.setString(2, produto.getNome());
        ps.setString(3, produto.getQuantidade());
        ps.setString(4, produto.getValor());
		ps.setString(5, produto.getStatuss());
        ps.setString(6, produto.getAvaliacao());
		ps.setString(7, produto.getDescricao());
        ps.setString(8, "images/" + produto.getImagens());
		
		ps.execute();

		con.close();
	}

    public void editar(Produto produto) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

		var ps = con.prepareStatement("UPDATE produto SET nome = ?, quantidade = ?, valor = ?, avaliacao = ?, descricao = ?, imagens = ? WHERE codigo = ?");
        ps.setString(1, produto.getNome());
        ps.setString(2, produto.getQuantidade());
		ps.setString(3, produto.getValor());
        ps.setString(4, produto.getAvaliacao());
        ps.setString(5, produto.getDescricao());
        ps.setString(6, produto.getImagens());
        ps.setLong(7, produto.getCodigo());
		
		ps.execute();

		con.close();
    }

    public void editarEstoquista(Produto produto) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

		var ps = con.prepareStatement("UPDATE produto SET quantidade = ? WHERE codigo = ?");
        ps.setString(1, produto.getQuantidade());
        ps.setLong(2, produto.getCodigo());
		
		ps.execute();

		con.close();
    }

    public List<ProdutoAllDto> findPaginatedProdutosByNome(String nome, int page, int pageSize) throws SQLException {
        int offset = (page - 1) * pageSize;
    
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM produto WHERE nome LIKE ? ORDER BY codigo DESC LIMIT ?, ?;";
            try (PreparedStatement stm = con.prepareStatement(sql)) {
                stm.setString(1, "%" + nome + "%");
                stm.setInt(2, offset);
                stm.setInt(3, pageSize);
                try (ResultSet rs = stm.executeQuery()) {
                    List<ProdutoAllDto> listAll = new ArrayList<>();
    
                    while (rs.next()) {
                        ProdutoAllDto n = new ProdutoAllDto();
                        n.setCodigo(rs.getLong("codigo"));
                        n.setNome(rs.getString("nome"));
                        n.setQuantidade(rs.getString("quantidade"));
                        n.setValor(rs.getString("valor"));
                        n.setStatuss(rs.getString("statuss"));
                        n.setAvaliacao(rs.getString("avaliacao"));
                        n.setDescricao(rs.getString("descricao"));
                        n.setImagens(rs.getString("imagens"));
    
                        listAll.add(n);
                    }
    
                    return listAll;
                }
            }
        }
    }
    
    public int countProdutosByNome(String nome) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM produto WHERE nome LIKE ?;";
            try (PreparedStatement stm = con.prepareStatement(sql)) {
                stm.setString(1, "%" + nome + "%");
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                    return 0;
                }
            }
        }
    }

    public Long obterUltimoIDInserido() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT MAX(codigo) FROM produto";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getLong(1); // Retorna o valor máximo da coluna 'codigo'
                    }
                    return null; // Retorna null se não encontrou nenhum registro na tabela
                }
            }
        }
    }
    
    public void imagemPrincipalProduto(Long codigo, String nomeImagemPrincipal) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE produto set imagemPrincipal = ? where codigo = ?;")) {
            ps.setString(1, nomeImagemPrincipal);
            ps.setLong(2, codigo);
            ps.execute();
        }
    }

    public List<ProdutoAllDto> findActiveProductsForLandingPage() throws SQLException {
        List<ProdutoAllDto> produtos = new ArrayList<>();
    
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM produto WHERE quantidade > 0 AND statuss = 'ativo'")) {
    
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdutoAllDto produto = new ProdutoAllDto();
                    produto.setCodigo(rs.getLong("codigo"));
                    produto.setNome(rs.getString("nome"));
                    produto.setQuantidade(rs.getString("quantidade"));
                    produto.setValor(rs.getString("valor"));
                    produto.setStatuss(rs.getString("statuss"));
                    produto.setAvaliacao(rs.getString("avaliacao"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setImagens(rs.getString("imagens"));
                    produto.setImagemPrincipal(rs.getString("imagemPrincipal"));
    
                    produtos.add(produto);
                }
            }
        }
    
        return produtos;
    }
    
    
    /*public Produto findByCodigo(Long id) throws SQLException {
       try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM produto WHERE codigo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setCodigo(rs.getLong("codigo"));
                    produto.setNome(rs.getString("nome"));
                    produto.setQuantidade(rs.getString("quantidade"));
                    produto.setValor(rs.getString("valor"));
                    produto.setStatuss(rs.getString("statuss"));
                    produto.setAvaliacao(rs.getString("avaliacao"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setImagens(rs.getString("imagens"));
                    return produto;
                }
                return null; // Retorna null se não encontrou o usuário com o CPF especificado
            }
        }
    }
    }*/

}
