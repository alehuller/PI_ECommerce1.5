package pi.quarto.semestre.codigo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pi.quarto.semestre.codigo.model.ImagensAllDto;
import pi.quarto.semestre.codigo.model.ProdutoAllDto;
import pi.quarto.semestre.codigo.model.ProdutosPedidoAllDto;

public class ProdutosPedidoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void inserir(Long quantidade) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("INSERT INTO produtosPedido (produtoId, produtoNome, produtoQuantidade, valor, valorTotal, pedidoId) SELECT produtoId, nome, quantidade, valor, valorTotal, (SELECT MAX(id) FROM pedido) FROM carrinho;")) {
            ps.execute();
    }
    }

    public List<ProdutosPedidoAllDto> findByCodigoId(Long id) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM produtospedido WHERE pedidoId = ?;";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {

                List<ProdutosPedidoAllDto> produtos = new ArrayList<>();

                while (rs.next()) {
                    ProdutosPedidoAllDto produto = new ProdutosPedidoAllDto();
                    produto.setProdutoNome(rs.getString("produtoNome"));
                    produto.setProdutoQuantidade(rs.getLong("produtoQuantidade"));
                    produto.setValor(rs.getString("valor"));
                    produto.setValorTotal(rs.getString("valorTotal"));

                    produtos.add(produto);
                }

                return produtos;
            }
        }
    }
    }
    
}
