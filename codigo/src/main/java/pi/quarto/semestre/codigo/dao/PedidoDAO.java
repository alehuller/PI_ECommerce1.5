package pi.quarto.semestre.codigo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pi.quarto.semestre.codigo.model.Carrinho;
import pi.quarto.semestre.codigo.model.CarrinhoAllDto;
import pi.quarto.semestre.codigo.model.EnderecoEntregaAllDto;
import pi.quarto.semestre.codigo.model.Pedido;
import pi.quarto.semestre.codigo.model.PedidoAllDto;

public class PedidoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void inserir(String loggedInUserEmail, String string, String string2, String string3, String string4, String string5, String string6, String string7) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("INSERT INTO pedido (clienteEmail, valorTotal, statuss, formaPagamento, frete, cep, logradouro, numero, complemento, bairro, cidade, uf) VALUES (?, (SELECT precoTotal FROM carrinho WHERE id = (SELECT MIN(id) FROM carrinho)), ?, (SELECT formaPagamento FROM carrinho WHERE id = (SELECT MIN(id) FROM carrinho)), (SELECT precoCEP FROM carrinho WHERE id = (SELECT MIN(id) FROM carrinho)), ?, ?, ?, ?, ?, ?, ?);")) {
            ps.setString(1, loggedInUserEmail);
            ps.setString(2, "Aguardando Pagamento");
            ps.setString(3, string);
            ps.setString(4, string2);
            ps.setString(5, string3);
            ps.setString(6, string4);
            ps.setString(7, string5);
            ps.setString(8, string6);
            ps.setString(9, string7);
            ps.execute();
    }
    }

    public Pedido pegarPedido() throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM pedido WHERE dataPedido = (SELECT MAX(dataPedido) FROM pedido);";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getLong("id"));
                    pedido.setClienteEmail(rs.getString("clienteEmail"));
                    pedido.setDataPedido(rs.getString("dataPedido"));
                    pedido.setValorTotal(rs.getString("valorTotal"));
                    pedido.setStatuss(rs.getString("statuss"));
                    pedido.setFormaPagamento(rs.getString("formaPagamento"));
                    return pedido;
                }
                return null; // Retorna null se não encontrou o usuário com o CPF especificado
            }
        }
        }
    }

    public List<PedidoAllDto> findPedidos(String email) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM pedido WHERE clienteEmail = ?;";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {

                List<PedidoAllDto> pedidos = new ArrayList<>();

                while (rs.next()) {
                    PedidoAllDto pedido = new PedidoAllDto();
                    pedido.setId(rs.getLong("id"));
                    pedido.setClienteEmail(rs.getString("clienteEmail"));
                    pedido.setDataPedido(rs.getString("dataPedido"));
                    pedido.setValorTotal(rs.getString("valorTotal"));
                    pedido.setStatuss(rs.getString("statuss"));
                    pedido.setFormaPagamento(rs.getString("formaPagamento"));
                    // Se você quiser mapear outros campos da tabela imagens, adicione-os aqui

                    pedidos.add(pedido);
                }

                return pedidos;
            }
        }
    }
}

    public Pedido findByCodigo(Long id) throws SQLException{
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
    String sql = "SELECT * FROM pedido WHERE id = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setLong(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("id"));
                pedido.setClienteEmail(rs.getString("clienteEmail"));
                pedido.setDataPedido(rs.getString("dataPedido"));
                pedido.setValorTotal(rs.getString("valorTotal"));
                pedido.setStatuss(rs.getString("statuss"));
                pedido.setFormaPagamento(rs.getString("formaPagamento"));
                pedido.setFrete(rs.getString("frete"));
                pedido.setCep(rs.getString("cep"));
                pedido.setLogradouro(rs.getString("logradouro"));
                pedido.setNumero(rs.getString("numero"));
                pedido.setComplemento(rs.getString("complemento"));
                pedido.setBairro(rs.getString("bairro"));
                pedido.setCidade(rs.getString("cidade"));
                pedido.setUf(rs.getString("uf"));
                return pedido;
            }
            return null; // Retorna null se não encontrou o usuário com o CPF especificado
        }
    }
}
    }
}
