package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pi.quarto.semestre.codigo.dao.PedidoDAO;
import pi.quarto.semestre.codigo.model.PedidoAllDto;

@Controller
public class ControllerListaDePedidos {

    @GetMapping("/pedidos")
    public String listarPedidos(Model model) throws SQLException {
        PedidoDAO pedidoDAO = new PedidoDAO();
        List<PedidoAllDto> pedidos = pedidoDAO.findAll();

        model.addAttribute("pedidos", pedidos);
        return "paginaListaDePedidos";
    }

    @PostMapping("/alterarStatus")
    public String mudarStatusPedido(@RequestParam("codigo") Long id, @RequestParam("statusPedido") String statusPedido) throws SQLException {
        PedidoDAO pedidoDAO = new PedidoDAO();
        pedidoDAO.alterarStatus(id, statusPedido);
        return "redirect:/pedidos";
    }
}
