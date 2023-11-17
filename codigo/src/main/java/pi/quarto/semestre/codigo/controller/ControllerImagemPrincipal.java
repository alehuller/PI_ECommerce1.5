package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pi.quarto.semestre.codigo.dao.ImagensDAO;
import pi.quarto.semestre.codigo.dao.ProdutoDAO;
import pi.quarto.semestre.codigo.model.ImagensAllDto;

@Controller
@RequestMapping("/paginaSelecionarImagemPrincipal/{codigo}")
public class ControllerImagemPrincipal {

    private final ImagensDAO imagensDAO;

    public ControllerImagemPrincipal(ImagensDAO imagensDAO) {
        this.imagensDAO = imagensDAO;
    }

    @GetMapping
    public String init(@PathVariable Long codigo, Model model) throws SQLException {
        List<ImagensAllDto> imagens1 = imagensDAO.findImagensByProdutoId(codigo);
        model.addAttribute("imagens1", imagens1);
        for (ImagensAllDto imagem : imagens1) {
            System.out.println("ID: " + imagem.getId());
            System.out.println("Nome do Arquivo: " + imagem.getNomeArquivo());
            System.out.println("Principal:" + imagem.getPrincipal());
            // Adicione mais campos aqui conforme necessário

            System.out.println();
        }
        return "paginaSelecionarImagemPrincipal";
    }

    @PostMapping
    public String result(@RequestParam Long codigo, @RequestParam String nomeArquivo) throws SQLException {
        ImagensDAO imagensDAO = new ImagensDAO();
        imagensDAO.imagemPrincipalSql(codigo);
        String nome = imagensDAO.pegarNome(codigo);
        Long codigoProduto = imagensDAO.pegarCodigo(codigo);
        imagensDAO.removerImagemPrincipal(codigoProduto);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.imagemPrincipalProduto(codigoProduto, nome);

        return "paginaSelecionarImagemPrincipal";
    }
}
