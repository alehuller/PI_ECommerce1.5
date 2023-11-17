package pi.quarto.semestre.codigo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import pi.quarto.semestre.codigo.model.EnderecoEntrega;

@Repository
public class EnderecoFaturamentoDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/pi_quarto_semestre";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void criar(long id) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASSWORD);

        var ps = con.prepareStatement("INSERT INTO enderecofaturamento (clienteId) VALUES (?)");
        ps.setLong(1, id);
        ps.execute();

        con.close();
    }

    public void inserir(EnderecoEntrega enderecoEntrega) throws SQLException{
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement("UPDATE enderecofaturamento SET cep = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ? WHERE id = (SELECT MAX(id) FROM enderecofaturamento);")) {
            ps.setString(1, enderecoEntrega.getCep());
            ps.setString(2, enderecoEntrega.getLogradouro());
            ps.setString(3, enderecoEntrega.getNumero());
            ps.setString(4, enderecoEntrega.getComplemento());
            ps.setString(5, enderecoEntrega.getBairro());
            ps.setString(6, enderecoEntrega.getCidade());
            ps.setString(7, enderecoEntrega.getUf());
            ps.execute();
    }
}
}
