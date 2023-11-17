package pi.quarto.semestre.codigo.util;

import java.sql.SQLException;
import java.util.List;

import pi.quarto.semestre.codigo.dao.ImagensDAO;
import pi.quarto.semestre.codigo.model.ImagensAllDto;

public class testeImagem {
    public static void main(String[] args) {
        ImagensDAO imagensDAO = new ImagensDAO();// Substitua ImagemDao pelo nome da sua classe DAO real

        try {
            List<ImagensAllDto> imagens = imagensDAO.findImagensByProdutoId(1); // Substitua 1 pelo produto_id desejado

            for (ImagensAllDto imagem : imagens) {
                System.out.println("ID: " + imagem.getId());
                System.out.println("Nome do Arquivo: " + imagem.getNomeArquivo());
                // Adicione mais campos aqui conforme necessário

                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
