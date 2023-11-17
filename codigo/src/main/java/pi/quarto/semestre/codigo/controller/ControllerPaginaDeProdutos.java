package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.ProdutoDAO;
import pi.quarto.semestre.codigo.dao.UsuarioDAO;
import pi.quarto.semestre.codigo.model.Produto;
import pi.quarto.semestre.codigo.model.ProdutoAllDto;
import pi.quarto.semestre.codigo.model.Usuario;
import pi.quarto.semestre.codigo.model.UsuarioAllDto;

@Controller
public class ControllerPaginaDeProdutos {

    @Autowired
    private ProdutoDAO produtoDAO;
    
    @GetMapping("/produtos")
    public String listarProdutos(@RequestParam(defaultValue = "1") int page, Model model) throws SQLException {
        int pageSize = 10; // Número de produtos por página
        List<ProdutoAllDto> produtos = produtoDAO.findAll(page, pageSize);
        int totalProducts = produtoDAO.countAll();
        
        model.addAttribute("produtos", produtos);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalProducts / pageSize));

        return "paginaDeProdutos";
    }

    @PostMapping("/mudarStatusProduto")
    public String mudarStatusProduto(@RequestParam Long codigo, HttpSession session) throws SQLException {
        String userType = (String) session.getAttribute("userType");
        Produto produto = produtoDAO.findByCodigo(codigo);
        
        if (!"Estoquista".equals(userType)) {
        if (produto.getStatuss().equals("Ativo")) {
            produto.setStatuss("Inativo");
            produtoDAO.mudarParaInativo(produto);
        } else if (produto.getStatuss().equals("Inativo")) {
            produto.setStatuss("Ativo");
            produtoDAO.mudarParaAtivo(produto);
        }
        }
        
        return "redirect:/produtos";
    }

    @GetMapping("/pesquisarProduto")
    public String pesquisarUsuarios(@RequestParam("username") String username, Model model) throws SQLException {
        ProdutoDAO produtoRepository = new ProdutoDAO();
        List<ProdutoAllDto> produtos = produtoRepository.findAllByNome(username);
        model.addAttribute("produtos", produtos);
        return "/paginaDeProdutos";
    }
}
