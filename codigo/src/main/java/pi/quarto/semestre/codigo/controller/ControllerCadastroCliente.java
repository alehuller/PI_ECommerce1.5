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

import pi.quarto.semestre.codigo.dao.ClienteDAO;
import pi.quarto.semestre.codigo.dao.EnderecoEntregaDAO;
import pi.quarto.semestre.codigo.dao.EnderecoFaturamentoDAO;
import pi.quarto.semestre.codigo.dao.UsuarioDAO;
import pi.quarto.semestre.codigo.model.Cliente;
import pi.quarto.semestre.codigo.model.EnderecoFaturamento;
import pi.quarto.semestre.codigo.util.CpfValidacao;
import pi.quarto.semestre.codigo.util.Cripto;

@Controller
@RequestMapping("/paginaCadastroClienteLoja")
public class ControllerCadastroCliente {
    
    @GetMapping
    public String init(final Model model) {
        model.addAttribute("cliente", new Cliente());
        return "paginaCadastroClienteLoja";
    }

    @PostMapping
    public ModelAndView result(@ModelAttribute Cliente cliente) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        CpfValidacao cpfValidacao = new CpfValidacao();
        boolean cpfValidado = cpfValidacao.isCPF(cliente.getCpf());

        if (!cpfValidado) {
            ModelAndView errorModelAndView = new ModelAndView("paginaCadastroClienteLoja");
            errorModelAndView.addObject("cpfError", "CPF invalido");
            return errorModelAndView;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        if (clienteDAO.findByEmail(cliente.getEmail())) {
            ModelAndView errorModelAndView = new ModelAndView("paginaCadastroClienteLoja");
            errorModelAndView.addObject("emailError", "Email ja esta em uso");
            return errorModelAndView;
        }

        Cripto cripto = new Cripto();
        String senhaCriptografada = cripto.crip(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);

        clienteDAO.inserir(cliente);
        Long idDoCliente = clienteDAO.pegarClienteId();

        EnderecoEntregaDAO enderecoEntregaDAO = new EnderecoEntregaDAO();
        enderecoEntregaDAO.criar(idDoCliente);
        EnderecoFaturamentoDAO enderecoFaturamentoDAO = new EnderecoFaturamentoDAO();
        enderecoFaturamentoDAO.criar(idDoCliente);

        ModelAndView successModelAndView = new ModelAndView("redirect:/paginaEscolhaDeEnderecoCadastro");
        return successModelAndView;
    }
}
