package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.CarrinhoDAO;
import pi.quarto.semestre.codigo.model.Carrinho;
import pi.quarto.semestre.codigo.model.CarrinhoAllDto;

@Controller
public class ControllerCarrinhoClienteNaoLogado {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("carrinhoClienteNaoLogado")
    public String carrinhoClienteNaoLogado(Model model) throws SQLException {
        CarrinhoDAO carrinhoRepository = new CarrinhoDAO();
        Carrinho carrinho = carrinhoRepository.pegarTotal();
        List<CarrinhoAllDto> carrinhos = carrinhoRepository.findCarrinhos();
        model.addAttribute("carrinhos", carrinhos);
        model.addAttribute("carrinho", carrinho);
        return "/carrinhoClienteNaoLogado";
    }

    @PostMapping("/removerQuantidade")
    public String removerQuantidade(@RequestParam Long produtoId, @RequestParam String valor, @RequestParam Long quantidade, Model model) throws SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        carrinhoDAO.removerQuantidade(produtoId, quantidade);
        Long quantidadeAtualizada = carrinhoDAO.pegarQuantidade(produtoId);
        carrinhoDAO.atualizarValorTotalDoRemover(quantidadeAtualizada, valor, produtoId);
        carrinhoDAO.atualizarTotal();
        carrinhoDAO.removerDoCarrinho(produtoId);
        return "redirect:/carrinhoClienteNaoLogado";
    }

    @PostMapping("/adicionarQuantidade")
    public String adicionarQuantidade(@RequestParam Long produtoId, @RequestParam String valor, @RequestParam Long quantidade, Model model) throws SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        carrinhoDAO.adicionarQuantidade(produtoId, quantidade);
        Long quantidadeAtualizada = carrinhoDAO.pegarQuantidade(produtoId);
        carrinhoDAO.atualizarValorTotalDoRemover(quantidadeAtualizada, valor, produtoId);
        carrinhoDAO.atualizarTotal();
        return "redirect:/carrinhoClienteNaoLogado";
    }

    @PostMapping("/cep1")
    public String cep1(@RequestParam String preco) throws SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        carrinhoDAO.atualizarCep1();
        carrinhoDAO.atualizarTotal();
        return "redirect:/carrinhoClienteNaoLogado";
    }

    @PostMapping("/cep2")
    public String cep2(@RequestParam String preco) throws SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        carrinhoDAO.atualizarCep2();
        carrinhoDAO.atualizarTotal();
        return "redirect:/carrinhoClienteNaoLogado";
    }

    @PostMapping("/cep3")
    public String cep3(@RequestParam String preco) throws SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        carrinhoDAO.atualizarCep3();
        carrinhoDAO.atualizarTotal();
        return "redirect:/carrinhoClienteNaoLogado";
    }

    @PostMapping("/removerProduto")
    public String removerProduto(@RequestParam String produtoId) throws SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        carrinhoDAO.removerProduto(produtoId);
        carrinhoDAO.atualizarTotal();
        return "redirect:/carrinhoClienteNaoLogado";
    }
}
