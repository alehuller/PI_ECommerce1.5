package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pi.quarto.semestre.codigo.dao.ImagensDAO;
import pi.quarto.semestre.codigo.dao.ProdutoDAO;
import pi.quarto.semestre.codigo.dao.UsuarioDAO;
import pi.quarto.semestre.codigo.model.Imagens;
import pi.quarto.semestre.codigo.model.Produto;
import pi.quarto.semestre.codigo.model.Usuario;
import pi.quarto.semestre.codigo.util.CpfValidacao;
import pi.quarto.semestre.codigo.util.Cripto;

@Controller
@RequestMapping("/paginaCadastroProduto")
public class ControllerCadastroProduto {

    @GetMapping
    public String init(final Model model) {
    	model.addAttribute("produto", new Produto()); 
    	return "paginaCadastroProduto";
    }
    
    @PostMapping
    public ModelAndView result(@ModelAttribute Produto produto) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
    ImagensDAO imagensDAO = new ImagensDAO();
    Imagens imagens = new Imagens();
    ProdutoDAO produtoDAO = new ProdutoDAO();  

    produto.setStatuss("Ativo");
    produtoDAO.inserir(produto);

    Long produtoID = produtoDAO.obterUltimoIDInserido();

    String imagensString = produto.getImagens();
    String[] nomesArquivos = imagensString.split(",");

    for (String nomeArquivo : nomesArquivos) {
        // Adicione "images/" antes de cada nome de arquivo
        String caminhoCompleto = nomeArquivo.trim();
        imagens.setNomeArquivo(caminhoCompleto);
        imagens.setProdutoID(produtoID);
        imagensDAO.inserir(imagens);
    }

    /*imagens.setNomeArquivo(produto.getImagens());
    imagens.setProdutoID(produtoID);
    imagensDAO.inserir(imagens);*/
    
    ModelAndView successModelAndView = new ModelAndView("redirect:/produtos");
    return successModelAndView;
}
}
