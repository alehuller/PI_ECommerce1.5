package pi.quarto.semestre.codigo.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pi.quarto.semestre.codigo.dao.UsuarioDAO;
import pi.quarto.semestre.codigo.model.Usuario;
import pi.quarto.semestre.codigo.model.UsuarioAllDto;
import pi.quarto.semestre.codigo.model.UsuarioDto;

@Controller
public class ControllerApp {
    
    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("paginaEscolherLista")
    public String paginaUsuarioBackoffice(Model model) {
        return "/paginaEscolherLista";
    }

    @GetMapping("paginaEscolherListaEstoquista")
    public String paginaEscolherListaEstoquista(Model model) {
        return "/paginaEscolherListaEstoquista";
    }

    @GetMapping("paginaOrganizacao")
    public String paginaOrganizacao(Model model) throws SQLException {
        UsuarioDAO userRepository = new UsuarioDAO();
        List<UsuarioAllDto> usuarios = userRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "/paginaOrganizacao";
    }

    @GetMapping("/pesquisar")
    public String pesquisarUsuarios(@RequestParam("username") String username, Model model) throws SQLException {
        UsuarioDAO userRepository = new UsuarioDAO();
        List<UsuarioAllDto> usuarios = userRepository.findAllByNome(username);
        model.addAttribute("usuarios", usuarios);
        return "/paginaOrganizacao";
    }

    @PostMapping("/mudarStatus")
    public String mudarStatus(@RequestParam String cpf) throws SQLException {
        Usuario usuario = usuarioDAO.findByCpf(cpf);
        
        if (usuario.getStatuss().equals("Ativo")) {
            usuario.setStatuss("Inativo");
            usuarioDAO.mudarParaInativo(usuario);
        } else if (usuario.getStatuss().equals("Inativo")) {
            usuario.setStatuss("Ativo");
            usuarioDAO.mudarParaAtivo(usuario);
        }
        
        return "redirect:/paginaOrganizacao";
    }

    /*@GetMapping("paginaEditarCliente")
    public String paginaEditarCliente(Model model) {
    return "/paginaEditarCliente";
    }*/

    
    /*@GetMapping("paginaEditarCliente/{id}")
    public String paginaEditarCliente(@RequestParam Long id, Model model) {
        Usuario usuario = usuarioDAO.findById(id);
        model.addAttribute("usuario", usuario);
        return "/paginaEditarCliente";*/
}


