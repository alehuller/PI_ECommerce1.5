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

import com.mysql.cj.jdbc.Driver;

import pi.quarto.semestre.codigo.model.Usuario;
import pi.quarto.semestre.codigo.model.UsuarioAllDto;

@Repository
public class UsuarioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public List<UsuarioAllDto> findAll() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM usuario ORDER BY nome;";
            try (Statement stm = con.createStatement();
                 ResultSet rs = stm.executeQuery(sql)) {
                
                List<UsuarioAllDto> listAll = new ArrayList<UsuarioAllDto>();

                while (rs.next()) {
                    UsuarioAllDto n = new UsuarioAllDto();
                    n.setId(rs.getLong("id"));
                    n.setCpf(rs.getString("cpf"));
                    n.setEmail(rs.getString("email"));
                    n.setGrupo(rs.getString("grupo"));
                    n.setNome(rs.getString("nome"));
                    n.setSenha(rs.getString("senha"));
                    n.setStatuss(rs.getString("statuss"));

                    listAll.add(n);
                }

                return listAll;
            }
        }
    }

    public void inserir(Usuario usuario) throws SQLException {
		var con = DriverManager.getConnection(URL, USER, PASSWORD);

		var ps = con.prepareStatement("INSERT INTO usuario (id, email, senha, grupo, nome, statuss, cpf) VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps.setLong(1, usuario.getId());
        ps.setString(2, usuario.getEmail());
        ps.setString(3, usuario.getSenha());
        ps.setString(4, usuario.getGrupo());
		ps.setString(5, usuario.getNome());
        ps.setString(6, usuario.getStatuss());
		ps.setString(7, usuario.getCpf());
		
		ps.execute();

		con.close();
	}

    /*public Usuario findById(Long id) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        var ps = con.prepareStatement("SELECT * FROM usuario WHERE id = ?");
        ps.setLong(1, id);
        ps.execute();
        con.close();
    }*/

    public void editar(Usuario usuario) throws SQLException {
		var con = DriverManager.getConnection(URL, USER, PASSWORD);

		var ps = con.prepareStatement("UPDATE usuario SET senha = ?, nome = ? WHERE cpf = ?");
        ps.setString(1, usuario.getSenha());
		ps.setString(2, usuario.getNome());
        ps.setString(3, usuario.getCpf());
		
		ps.execute();

		con.close();
	}

    public void editarNaoLogado(Usuario usuario) throws SQLException {
		var con = DriverManager.getConnection(URL, USER, PASSWORD);

		var ps = con.prepareStatement("UPDATE usuario SET nome = ?, senha = ?, cpf = ?, grupo = ? WHERE cpf = ?");
        ps.setString(1, usuario.getNome());
        ps.setString(2, usuario.getSenha());
        ps.setString(3, usuario.getCpf());
        ps.setString(4, usuario.getGrupo());
        ps.setString(5, usuario.getCpf());
		
		ps.execute();

		con.close();
	}

    public Usuario findByCpf(String cpf) throws SQLException {
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setGrupo(rs.getString("grupo"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setStatuss(rs.getString("statuss"));
                    return usuario;
                }
                return null; // Retorna null se não encontrou o usuário com o CPF especificado
            }
        }
    }
}

    public boolean validarAdmin(String email, String senha, String grupo, String statuss) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT * FROM usuario WHERE email = ? and senha = ? and grupo = ? and statuss = ?";
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, senha);
        preparedStatement.setString(3, grupo);
        preparedStatement.setString(4, statuss);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void mudarParaInativo(Usuario usuario) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        var ps = con.prepareStatement("UPDATE usuario SET statuss = ? where cpf = ?");
        ps.setString(1, "Inativo");
        ps.setString(2, usuario.getCpf());
        ps.execute();
        con.close();
    }
    
    public void mudarParaAtivo(Usuario usuario) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);
        var ps = con.prepareStatement("UPDATE usuario SET statuss = ? where cpf = ?");
        ps.setString(1, "Ativo");
        ps.setString(2, usuario.getCpf());
        ps.execute();
        con.close();
    }

    public boolean findByEmail(String email) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM usuario WHERE email = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next(); // Retorna true se encontrou o usuário com o email especificado, ou false se não encontrou
                }
            }
        }
    }
    
    public List<UsuarioAllDto> findAllByNome(String nome) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM usuario WHERE nome LIKE ? ORDER BY nome;";
            try (PreparedStatement stm = con.prepareStatement(sql)) {
                stm.setString(1, "%" + nome + "%"); // Correspondência parcial no nome
    
                try (ResultSet rs = stm.executeQuery()) {
                    List<UsuarioAllDto> listAll = new ArrayList<UsuarioAllDto>();
    
                    while (rs.next()) {
                        UsuarioAllDto n = new UsuarioAllDto();
                        n.setId(rs.getLong("id"));
                        n.setCpf(rs.getString("cpf"));
                        n.setEmail(rs.getString("email"));
                        n.setGrupo(rs.getString("grupo"));
                        n.setNome(rs.getString("nome"));
                        n.setSenha(rs.getString("senha"));
                        n.setStatuss(rs.getString("statuss"));
    
                        listAll.add(n);
                    }
    
                    return listAll;
                }
            }
        }
    }
    
}
