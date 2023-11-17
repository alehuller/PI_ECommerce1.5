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
import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.model.EnderecoEntrega;
import pi.quarto.semestre.codigo.model.ProdutoAllDto;

public class CarrinhoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

	public void inserir(Long codigo, String nome, String valor) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("INSERT INTO carrinho (produtoId, quantidade, nome, valor, valorTotal) VALUES (?, ?, ?, ?, ?)")) {
            ps.setLong(1, codigo);
            ps.setLong(2, 1);
            ps.setString(3, nome);
            ps.setString(4, valor);
            valor = valor.replace(",", ".");
            double valorDouble = Double.parseDouble(valor);
            ps.setDouble(5, 1 * valorDouble);
            ps.execute();
    }
}

    public boolean varredura(Long codigo) throws SQLException {
    String query = "SELECT COUNT(*) FROM carrinho WHERE produtoId = ?";
    
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement ps = con.prepareStatement(query)) {
        ps.setLong(1, codigo);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Se count for maior que 0, o produto já está no carrinho
            }
        }
    }
    
    return false; // Se ocorrer algum erro, assume-se que o produto não está no carrinho
}

public Long pegarQuantidade(Long codigo) throws SQLException {
    String query = "SELECT quantidade FROM carrinho WHERE produtoId = ?";
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement ps = con.prepareStatement(query)) {
        ps.setLong(1, codigo);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("quantidade");
            }
        }
    }

    return 0L; // Se não encontrou o produto, retorna 0 ou outro valor padrão, dependendo da sua lógica
}


    public void modificarQuantidade(Long codigo, Long quantidade) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho set quantidade = ? where produtoId = ?")) {
            ps.setLong(1, quantidade + 1);
            ps.setLong(2, codigo);
            ps.execute();
        }
    }

    public List<CarrinhoAllDto> findCarrinhos() throws SQLException {
        List<CarrinhoAllDto> carrinhos = new ArrayList<>();
    
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM carrinho WHERE quantidade > 0")) {
    
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CarrinhoAllDto carrinho = new CarrinhoAllDto();

                    carrinho.setId(rs.getLong("id"));
                    carrinho.setProdutoId(rs.getString("produtoId"));
                    carrinho.setQuantidade(rs.getLong("quantidade"));
                    carrinho.setNome(rs.getString("nome"));
                    carrinho.setValor(rs.getString("valor"));
                    carrinho.setValorTotal(rs.getString("valorTotal"));
                    carrinho.setPrecoTotal(rs.getString("precoTotal"));
                    carrinho.setPrecoCEP(rs.getString("precoCEP"));
                    carrinho.setFormaPagamento(rs.getString("formaPagamento"));
                    carrinho.setNomeCartao(rs.getString("nomeCartao"));
                    carrinho.setNumeroCartao(rs.getString("numeroCartao"));
                    carrinho.setAnoExpiracaoCartao(rs.getString("anoExpiracaoCartao"));
                    carrinho.setCvvCartao(rs.getString("cvvCartao"));
                    carrinho.setParcelaCartao(rs.getString("parcelaCartao"));
    
                    carrinhos.add(carrinho);
                }
            }
        }
    
        return carrinhos;
    }

    public void removerQuantidade(Long produtoId, Long quantidade) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho set quantidade = ? where produtoId = ?")) {
            ps.setLong(1, quantidade - 1);
            ps.setLong(2, produtoId);
            ps.execute();
        }
    }

    public void adicionarQuantidade(Long produtoId, Long quantidade) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho set quantidade = ? where produtoId = ?")) {
            ps.setLong(1, quantidade + 1);
            ps.setLong(2, produtoId);
            ps.execute();
        }
    }

    public Carrinho findById(Long produtoId) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM carrinho WHERE produtoId = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, produtoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Carrinho carrinho = new Carrinho();
                    carrinho.setId(rs.getLong("id"));
                    carrinho.setProdutoId(rs.getString("produtoId"));
                    carrinho.setQuantidade(rs.getLong("quantidade"));
                    carrinho.setNome(rs.getString("nome"));
                    carrinho.setValor(rs.getString("valor"));
                    carrinho.setValorTotal(rs.getString("valorTotal"));
                    carrinho.setPrecoTotal(rs.getString("precoTotal"));
                    carrinho.setPrecoCEP(rs.getString("precoCEP"));
                    carrinho.setFormaPagamento(rs.getString("formaPagamento"));
                    carrinho.setNomeCartao(rs.getString("nomeCartao"));
                    carrinho.setNumeroCartao(rs.getString("numeroCartao"));
                    carrinho.setAnoExpiracaoCartao(rs.getString("anoExpiracaoCartao"));
                    carrinho.setCvvCartao(rs.getString("cvvCartao"));
                    carrinho.setParcelaCartao(rs.getString("parcelaCartao"));
                    return carrinho;
                }
                return null; // Retorna null se não encontrou o usuário com o CPF especificado
            }
        }
        }
    }

    public void atualizarValorTotal(Long quantidade, String valor, String id) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho set valorTotal = ? where produtoId = ?")) {
            valor = valor.replace(",", ".");
            double valorDouble = Double.parseDouble(valor);
            double total = quantidade * valorDouble;
            ps.setDouble(1, total);
            ps.setString(2, id);
            ps.execute();
        }
    }

    public void removerDoCarrinho(Long produtoId) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("DELETE FROM carrinho WHERE produtoId = ? AND quantidade = 0;")) {
            ps.setLong(1, produtoId);
            ps.execute();
        }
    }

    public void atualizarValorTotalDoRemover(Long quantidade, String valor, Long produtoId) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho set valorTotal = ? where produtoId = ?")) {
            valor = valor.replace(",", ".");
            double valorDouble = Double.parseDouble(valor);
            double total = quantidade * valorDouble;
            System.out.println(total);
            ps.setDouble(1, total);
            ps.setLong(2, produtoId);
            ps.execute();
        }
    }

    public void quantidadeAtualizada(Long produtoId) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("Select * from carrinho where = produtoId = ?")) {
            ps.setLong(1, produtoId);
            ps.execute();
        }
    }

    public Carrinho pegarTotal() throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM carrinho WHERE id = (SELECT MIN(id) FROM carrinho);";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Carrinho carrinho = new Carrinho();
                    carrinho.setId(rs.getLong("id"));
                    carrinho.setProdutoId(rs.getString("produtoId"));
                    carrinho.setQuantidade(rs.getLong("quantidade"));
                    carrinho.setNome(rs.getString("nome"));
                    carrinho.setValor(rs.getString("valor"));
                    carrinho.setValorTotal(rs.getString("valorTotal"));
                    carrinho.setPrecoTotal(rs.getString("precoTotal"));
                    carrinho.setPrecoCEP(rs.getString("precoCEP"));
                    carrinho.setFormaPagamento(rs.getString("formaPagamento"));
                    carrinho.setNomeCartao(rs.getString("nomeCartao"));
                    carrinho.setNumeroCartao(rs.getString("numeroCartao"));
                    carrinho.setAnoExpiracaoCartao(rs.getString("anoExpiracaoCartao"));
                    carrinho.setCvvCartao(rs.getString("cvvCartao"));
                    carrinho.setParcelaCartao(rs.getString("parcelaCartao"));
                    return carrinho;
                }
                return null; // Retorna null se não encontrou o usuário com o CPF especificado
            }
        }
        }
    }

    public void atualizarTotal() throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho SET precoTotal = (SELECT SUM(valorTotal) FROM carrinho) + (SELECT precoCEP FROM carrinho WHERE id = (SELECT MIN(id) FROM carrinho));")) {
            ps.execute();
        }
    }

    public void atualizarCep1() throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho SET precoCEP = ? WHERE id = (SELECT MIN(id) FROM carrinho);")) {
            ps.setDouble(1, 5.00);
            ps.execute();
        }
    }

    public void atualizarCep2() throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho SET precoCEP = ? WHERE id = (SELECT MIN(id) FROM carrinho);")) {
            ps.setDouble(1, 10.00);
            ps.execute();
        }
    }

    public void atualizarCep3() throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho SET precoCEP = ? WHERE id = (SELECT MIN(id) FROM carrinho);")) {
            ps.setDouble(1, 15.00);
            ps.execute();
        }
    }

    public void removerProduto(String produtoId) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("DELETE FROM carrinho WHERE produtoId = ?;")) {
            ps.setString(1, produtoId);
            ps.execute();
        }
    }

    public void inserirPagamento(Carrinho carrinho) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE carrinho SET formaPagamento = ?, nomeCartao = ?, numeroCartao = ?, anoExpiracaoCartao = ?, cvvCartao = ?, parcelaCartao = ? WHERE id = (SELECT MIN(id) FROM carrinho);")) {
            ps.setString(1, carrinho.getFormaPagamento());
            ps.setString(2, carrinho.getNomeCartao());
            ps.setString(3, carrinho.getNumeroCartao());
            ps.setString(4, carrinho.getAnoExpiracaoCartao());
            ps.setString(5, carrinho.getCvvCartao());
            ps.setString(6, carrinho.getParcelaCartao());
            ps.execute();
        }
    }

    public Long pegarTotalCarrinho() throws SQLException {
        String query = "SELECT COUNT(*) AS quantidade FROM carrinho;";
    Long quantidade = 0L;

    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            quantidade = rs.getLong("quantidade");
        }
    }

    return quantidade;
}

    public void removerTudo() throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("DELETE FROM carrinho;")) {
            ps.execute();
        }
    }
}
