package pi.quarto.semestre.codigo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.model.Usuario;

@Repository
public class ClienteDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public boolean validarCliente(String email, String senha) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT * FROM cliente WHERE email = ? and senha = ?";
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, senha);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean findByEmail(String email) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM cliente WHERE email = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next(); // Retorna true se encontrou o usuário com o email especificado, ou false se não encontrou
                }
            }
        }
    }

    public void inserir(Cliente cliente) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        var ps = con.prepareStatement("INSERT INTO cliente (id, email, senha, cpf, nome, dataNascimento, genero) VALUES (?, ?, ?, ?, ?, ?, ?)");
        ps.setLong(1, cliente.getId());
        ps.setString(2, cliente.getEmail());
        ps.setString(3, cliente.getSenha());
        ps.setString(4, cliente.getCpf());
        ps.setString(5, cliente.getNome());
        ps.setString(6, cliente.getDataNascimento());
        ps.setString(7, cliente.getGenero());

        ps.execute();

        con.close();
    }

    public Long pegarClienteId() throws SQLException {
        Long clienteId = null;
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT MAX(id) AS max_id FROM cliente";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        clienteId = rs.getLong("max_id");
                    }
                }
            }
        }
        return clienteId;
    }

    public Cliente findByEmailDados(String loggedInUserEmail) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM cliente WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, loggedInUserEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getLong("id"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setDataNascimento(rs.getString("dataNascimento"));
                    cliente.setGenero(rs.getString("genero"));
                    cliente.setEnderecoPrincipal(rs.getString("enderecoPrincipal"));
                    return cliente;
                }
                return null; // Retorna null se não encontrou o usuário com o CPF especificado
            }
        }
        }
    }

    public void editar(String email, Cliente cliente) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        var ps = con.prepareStatement("UPDATE cliente SET nome = ?, senha = ?, dataNascimento = ?, genero = ? WHERE email = ?");
        ps.setString(1, cliente.getNome());
        ps.setString(2, cliente.getSenha());
        ps.setString(3, cliente.getDataNascimento());
        ps.setString(4, cliente.getGenero());
        ps.setString(5, email);

        ps.execute();

		con.close();
    }

    public void enderecoPrincipal(String email,Long id) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE cliente set enderecoPrincipal = ? where email = ?;")) {
            ps.setLong(1, id);
            ps.setString(2, email);
            ps.execute();
        }
    }


}
