package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.PedidoDAO;
import pi.quarto.semestre.codigo.dao.ProdutoDAO;
import pi.quarto.semestre.codigo.dao.ProdutosPedidoDAO;
import pi.quarto.semestre.codigo.model.Pedido;
import pi.quarto.semestre.codigo.model.ProdutosPedido;
import pi.quarto.semestre.codigo.model.ProdutosPedidoAllDto;

@Controller
public class ControllerDetalhePedido {

    @PostMapping("/detalhesCompra")
    public String mostrarDetalhesCompra(@RequestParam Long id, Model model) throws SQLException {
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = pedidoDAO.findByCodigo(id);

        ProdutosPedidoDAO produtosPedidoDAO = new ProdutosPedidoDAO();
        List<ProdutosPedidoAllDto> produtosPedidos = produtosPedidoDAO.findByCodigoId(id);


        model.addAttribute("pedido", pedido);
        model.addAttribute("produtosPedidos", produtosPedidos);
        return "paginaDetalhePedido";
    }
}
