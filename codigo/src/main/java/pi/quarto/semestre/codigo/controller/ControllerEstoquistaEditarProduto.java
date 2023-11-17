package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.ProdutoDAO;
import pi.quarto.semestre.codigo.model.Produto;

@Controller
@RequestMapping("/paginaEstoquistaEditarProduto/{codigo}")
public class ControllerEstoquistaEditarProduto {
    
    private final ProdutoDAO produtoDAO;

    public ControllerEstoquistaEditarProduto(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    @GetMapping
    public String init(@PathVariable Long codigo, Model model) throws SQLException {
        Produto produto = produtoDAO.findByCodigo(codigo);
        model.addAttribute("produto", produto);
        return "paginaEstoquistaEditarProduto";
    }

    @PostMapping
    public String result(@ModelAttribute Produto produto) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        produtoDAO.editarEstoquista(produto);
        return "redirect:/produtos";
    }
}
