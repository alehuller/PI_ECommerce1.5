package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.ImagensDAO;
import pi.quarto.semestre.codigo.dao.ProdutoDAO;
import pi.quarto.semestre.codigo.model.Imagens;
import pi.quarto.semestre.codigo.model.Produto;

@Controller
@RequestMapping("/paginaAdministradorEditarProduto/{codigo}")
public class ControllerAdministradorEditarProduto {

    private final ProdutoDAO produtoDAO;
    private HttpServletRequest request;

    public ControllerAdministradorEditarProduto(ProdutoDAO produtoDAO, HttpServletRequest request) {
        this.produtoDAO = produtoDAO;
        this.request = request;
    }
    
    @GetMapping
    public String init(@PathVariable Long codigo, Model model) throws SQLException {
        Produto produto = produtoDAO.findByCodigo(codigo);
        HttpSession session = request.getSession();
        String loggedInUserGrupo = (String) session.getAttribute("userType");

        if(loggedInUserGrupo != null && loggedInUserGrupo.equals("Estoquista")) {
            model.addAttribute("produto", produto);
            return "paginaEstoquistaEditarProduto";
        } else {
            model.addAttribute("produto", produto);
            return "paginaAdministradorEditarProduto";
        }
    }

    @PostMapping
public String result(@ModelAttribute Produto produto) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
    HttpSession session = request.getSession();
    String userType = (String) session.getAttribute("userType");
    if ("Estoquista".equals(userType)) {
        // Se o usuário for um estoquista, execute a ação do estoquista
        produtoDAO.editarEstoquista(produto);
    } else if ("Administrador".equals(userType)) {
        // Se o usuário for um administrador, execute a ação do administrador
        produtoDAO.editar(produto);
        ImagensDAO imagensDAO = new ImagensDAO();
        Imagens imagens = new Imagens();

        // Separe a lista de nomes de arquivo por vírgula
        String imagensString = produto.getImagens();
        String[] nomesArquivos = imagensString.split(",");
        imagensDAO.removerPorProdutoId(produto.getCodigo());

        for (String nomeArquivo : nomesArquivos) {
            // Adicione "images/" antes de cada nome de arquivo
            String caminhoCompleto = nomeArquivo.trim();
            imagens.setNomeArquivo(caminhoCompleto);
            imagens.setProdutoID(produto.getCodigo());
            imagensDAO.inserir(imagens);
        }
    }
    return "redirect:/produtos";
}

}
