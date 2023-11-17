package pi.quarto.semestre.codigo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.model.EnderecoEntrega;
import pi.quarto.semestre.codigo.model.EnderecoEntregaAllDto;
import pi.quarto.semestre.codigo.model.ImagensAllDto;
import pi.quarto.semestre.codigo.model.Produto;

@Repository
public class EnderecoEntregaDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void criar(long id) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        var ps = con.prepareStatement("INSERT INTO enderecoentrega (clienteId) VALUES (?)");
        ps.setLong(1, id);
        ps.execute();

        con.close();
    }

    public void inserir(EnderecoEntrega enderecoEntrega) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE enderecoentrega SET cep = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, statuss = ? WHERE id = (SELECT MAX(id) FROM enderecoentrega);")) {
            ps.setString(1, enderecoEntrega.getCep());
            ps.setString(2, enderecoEntrega.getLogradouro());
            ps.setString(3, enderecoEntrega.getNumero());
            ps.setString(4, enderecoEntrega.getComplemento());
            ps.setString(5, enderecoEntrega.getBairro());
            ps.setString(6, enderecoEntrega.getCidade());
            ps.setString(7, enderecoEntrega.getUf());
            ps.setString(8, "Ativo");
            ps.execute();
    }
}

    public void inserirCliente(Long id, EnderecoEntrega enderecoEntrega) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("INSERT INTO enderecoentrega (cep, logradouro, numero, complemento, bairro, cidade, uf, clienteId, statuss) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, enderecoEntrega.getCep());
            ps.setString(2, enderecoEntrega.getLogradouro());
            ps.setString(3, enderecoEntrega.getNumero());
            ps.setString(4, enderecoEntrega.getComplemento());
            ps.setString(5, enderecoEntrega.getBairro());
            ps.setString(6, enderecoEntrega.getCidade());
            ps.setString(7, enderecoEntrega.getUf());
            ps.setLong(8, id);
            ps.setString(9, "Ativo");
            ps.execute();
    }
    }

    public List<EnderecoEntregaAllDto> findEnderecosByClienteId(Long id) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM enderecoEntrega WHERE clienteId = ? and statuss = 'Ativo';";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {

                List<EnderecoEntregaAllDto> enderecos = new ArrayList<>();

                while (rs.next()) {
                    EnderecoEntregaAllDto endereco = new EnderecoEntregaAllDto();
                    endereco.setId(rs.getLong("id"));
                    endereco.setCep(rs.getString("cep"));
                    endereco.setLogradouro(rs.getString("logradouro"));
                    endereco.setNumero(rs.getString("numero"));
                    endereco.setComplemento(rs.getString("complemento"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setUf(rs.getString("uf"));
                    // Se você quiser mapear outros campos da tabela imagens, adicione-os aqui

                    enderecos.add(endereco);
                }

                return enderecos;
            }
        }
    }
    }

    public void inativarEndereco(Long id, String status) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        var ps = con.prepareStatement("UPDATE enderecoentrega set statuss = ? where id = ?");
        ps.setString(1, status);
        ps.setLong(2, id);
        ps.execute();
        con.close();
    }

    public EnderecoEntrega pegarEndereco(String enderecoPrincipal) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM enderecoentrega WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, enderecoPrincipal);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
                    enderecoEntrega.setId(rs.getLong("id"));
                    enderecoEntrega.setCep(rs.getString("cep"));
                    enderecoEntrega.setLogradouro(rs.getString("logradouro"));
                    enderecoEntrega.setNumero(rs.getString("numero"));
                    enderecoEntrega.setComplemento(rs.getString("complemento"));
                    enderecoEntrega.setBairro(rs.getString("bairro"));
                    enderecoEntrega.setCidade(rs.getString("cidade"));
                    enderecoEntrega.setUf(rs.getString("uf"));
                    
                    return enderecoEntrega;
                }
                return null; // Retorna null se não encontrou o usuário com o CPF especificado
            }
        }
    }
    }
}
