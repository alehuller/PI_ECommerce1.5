package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pi.quarto.semestre.codigo.dao.PedidoDAO;
import pi.quarto.semestre.codigo.model.Pedido;

@Controller
@RequestMapping("/paginaResumoPedido")
public class ControllerResumoPedido {
    
    @GetMapping
    public String init(final Model model) throws SQLException {
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = pedidoDAO.pegarPedido();
        model.addAttribute("pedido", pedido);
        return "paginaResumoPedido";
    }
}
