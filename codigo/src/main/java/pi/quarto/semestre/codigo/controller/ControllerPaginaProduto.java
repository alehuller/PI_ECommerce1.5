package pi.quarto.semestre.codigo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pi.quarto.semestre.codigo.dao.CarrinhoDAO;
import pi.quarto.semestre.codigo.dao.ImagensDAO;
import pi.quarto.semestre.codigo.dao.ProdutoDAO;
import pi.quarto.semestre.codigo.model.Carrinho;
import pi.quarto.semestre.codigo.model.Imagens;
import pi.quarto.semestre.codigo.model.ImagensAllDto;
import pi.quarto.semestre.codigo.model.Produto;
import pi.quarto.semestre.codigo.model.ProdutoAllDto;

@Controller
public class ControllerPaginaProduto {
    
    @Autowired
    private ProdutoDAO produtoDAO;

    @PostMapping("/detalhesProduto")
    public String mostrarDetalhesProduto(@RequestParam Long codigo, Model model) throws SQLException {
        ImagensDAO imagensDAO = new ImagensDAO();
        List<ImagensAllDto> imagens1 = imagensDAO.findImagensByProdutoId(codigo);
        Produto produto = produtoDAO.findByCodigo(codigo); // Implemente o método findByCodigo no seu serviço
        model.addAttribute("produto", produto);
        model.addAttribute("imagens1", imagens1);
        for (ImagensAllDto imagem : imagens1) {
            System.out.println("ID: " + imagem.getId());
            System.out.println("Nome do Arquivo: " + imagem.getNomeArquivo());
            System.out.println("Principal:" + imagem.getPrincipal());
            // Adicione mais campos aqui conforme necessário

            System.out.println();
        }
        return "paginaProduto"; // Nome da página de detalhes do produto
    }

    @PostMapping("/adicionarCarrinho")
    public String adicionarNoCarrinho(@RequestParam Long codigo, @RequestParam String nome, @RequestParam String valor, Model model) throws SQLException {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        Boolean verificacao = carrinhoDAO.varredura(codigo); 

        if(verificacao) {
            Long quantidade = carrinhoDAO.pegarQuantidade(codigo);
            carrinhoDAO.modificarQuantidade(codigo, quantidade);
            Carrinho carrinho = carrinhoDAO.findById(codigo);
            carrinhoDAO.atualizarValorTotal(carrinho.getQuantidade(), carrinho.getValor(), carrinho.getProdutoId());
            carrinhoDAO.atualizarTotal();
            return "redirect:/carrinhoClienteNaoLogado";
        }
        else {
            carrinhoDAO.inserir(codigo, nome, valor);
            carrinhoDAO.atualizarTotal();
            return "redirect:/carrinhoClienteNaoLogado";
        }
    }
}
