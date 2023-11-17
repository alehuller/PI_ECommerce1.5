package pi.quarto.semestre.codigo.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.CarrinhoDAO;
import pi.quarto.semestre.codigo.dao.ClienteDAO;
import pi.quarto.semestre.codigo.dao.EnderecoEntregaDAO;
import pi.quarto.semestre.codigo.dao.PedidoDAO;
import pi.quarto.semestre.codigo.dao.ProdutosPedidoDAO;
import pi.quarto.semestre.codigo.model.Carrinho;
import pi.quarto.semestre.codigo.model.CarrinhoAllDto;
import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.model.EnderecoEntrega;
import pi.quarto.semestre.codigo.model.ProdutosPedido;

@Controller
@RequestMapping("/paginaResumoCompra")
public class ControllerResumoCompra {
    
    private HttpServletRequest request;

    public ControllerResumoCompra(HttpServletRequest request) {
        this.request = request;
    }

    @GetMapping
    public String init(final Model model) throws SQLException {
        CarrinhoDAO carrinhoRepository = new CarrinhoDAO();
        Carrinho carrinho = carrinhoRepository.pegarTotal();
        List<CarrinhoAllDto> carrinhos = carrinhoRepository.findCarrinhos();

        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.findByEmailDados(loggedInUserEmail);
        EnderecoEntregaDAO enderecoEntregaDAO = new EnderecoEntregaDAO();
        EnderecoEntrega enderecoEntrega = enderecoEntregaDAO.pegarEndereco(cliente.getEnderecoPrincipal());


        model.addAttribute("carrinhos", carrinhos);
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("enderecoEntrega", enderecoEntrega);
        return "paginaResumoCompra";
    }

    @PostMapping
    public ModelAndView result() throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        HttpSession session = request.getSession();
        String loggedInUserEmail = (String) session.getAttribute("loggedInUser");
        CarrinhoDAO carrinhoRepository = new CarrinhoDAO();
        Carrinho carrinho = carrinhoRepository.pegarTotal();

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.findByEmailDados(loggedInUserEmail);
        EnderecoEntregaDAO enderecoEntregaDAO = new EnderecoEntregaDAO();
        EnderecoEntrega enderecoEntrega = enderecoEntregaDAO.pegarEndereco(cliente.getEnderecoPrincipal());

        PedidoDAO pedidoDAO = new PedidoDAO();
        pedidoDAO.inserir(loggedInUserEmail, enderecoEntrega.getCep(), enderecoEntrega.getLogradouro(), enderecoEntrega.getNumero(), enderecoEntrega.getComplemento(), enderecoEntrega.getBairro(), enderecoEntrega.getCidade(), enderecoEntrega.getUf());

        Long quantidade = carrinhoRepository.pegarTotalCarrinho();
        ProdutosPedidoDAO produtosPedidoDAO = new ProdutosPedidoDAO();
        produtosPedidoDAO.inserir(quantidade);

        carrinhoRepository.removerTudo();

        ModelAndView successModelAndView = new ModelAndView("redirect:/paginaResumoPedido");
        return successModelAndView;
    }
}
