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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pi.quarto.semestre.codigo.dao.UsuarioDAO;
import pi.quarto.semestre.codigo.model.Usuario;
import pi.quarto.semestre.codigo.util.Cripto;

@Controller
@RequestMapping("/paginaEditarClienteNaoLogado/{cpf}")
public class ControllerEditarClienteNaoLogado {

    private final UsuarioDAO usuarioDAO;

    public ControllerEditarClienteNaoLogado(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @GetMapping
    public String init(@PathVariable String cpf, Model model) throws SQLException {
        Usuario usuario = usuarioDAO.findByCpf(cpf); // Implemente o método findByCpf no seu DAO
        model.addAttribute("usuario", usuario);
        return "paginaEditarClienteNaoLogado";
    }

    @PostMapping
    public String result(@ModelAttribute Usuario usuario) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
        Cripto cripto = new Cripto();
        String senhaCriptografada = cripto.crip(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        usuarioDAO.editarNaoLogado(usuario); // Implemente o método editar no seu DAO
        return "redirect:/paginaOrganizacao";
    }
}
