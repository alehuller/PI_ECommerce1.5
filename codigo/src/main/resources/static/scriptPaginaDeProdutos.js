const modal = document.querySelector('.modal-container');
const tbody = document.querySelector('tbody');
const sCodigo = document.querySelector('#m-codigo');
const sNome = document.querySelector('#m-nome');
const sDescricao = document.querySelector('#m-descricao');
const sAvaliacao = document.querySelector('#m-avaliacao');
const sPreco = document.querySelector('#m-preco');
const sQuantidade = document.querySelector('#m-quantidade');
const sStatus = document.querySelector('#m-status');
const sLink = document.querySelector('#m-link');
const sImagens = document.querySelector('#m-imagens');
//variaveis que indicam atributos de um item
const btnSalvar = document.querySelector('#btnSalvar');

let itens; //variavel que armazena os itens
let id; //armazena o index para edicao

const itemArray = [];

function openModal(edit = false, index = 0) { //funcao onde aparece a tela de inclusao. tudo false para nao aparecer na tela a parte do incluir.
  modal.classList.add('active') //a modal eh ativada para ficar visivel em tela

  modal.onclick = e => { //para cada clique fora da classe modal de inclusao, a classe eh removida para nao aparecer mais na tela.
    if (e.target.className.indexOf('modal-container') !== -1) {
      modal.classList.remove('active')
    }
  }

  if (edit) { //valores que sao editados na inclusao, cada novo com seu index
    sCodigo.value = itens[index].codigo
    sNome.value = itens[index].nome
    sDescricao.value = itens[index].descricao
    sAvaliacao.value = itens[index].avaliacao
    sPreco.value = itens[index].preco
    sQuantidade.value = itens[index].quantidade
    sStatus.value = itens[index].status
    sLink.value = itens[index].link
    sImagens.value = itens[index].imagens
    id = index
  } else { //valores nao preenchidos
    sCodigo.value = ''
    sNome.value = ''
    sDescricao.value = ''
    sAvaliacao.value = ''
    sPreco.value = ''
    sQuantidade.value = ''
    sStatus.value = ''
    sLink.value = ''
    sImagens.value = ''
  }

}

function editItem(index) { //funcao que chama a funcao openModal

  openModal(true, index)
}

function deleteItem(index) { //funcao para deletar um item. recebe o index do item.
  itens.splice(index, 1) //splice faz com que o index seja removido. o 1 da funcao corresponde a 1 item que sera deletado
  setItensBD() //atualiza o banco sem o item que foi removido
  loadItens() //carrega novamente os dados na tela
}

function insertItem(item, index) { //funcao onde junta e insere os atributos do item para aparecer na tela, juntamente do botao de edicao e deletar item
  let tr = document.createElement('tr')

  tr.innerHTML = `
      <td>${item.codigo}</td>
      <td>${item.nome}</td>
      <td>${item.quantidade}</td>
      <td>${item.preco}</td>
      <td>${item.status} <input id="alterarStatus" type="button" onclick="alterar()" value="Alterar" /></td>
      <td><a href="${item.link}">${item.link}</a></td>
      <td class="acao">
        <button onclick="editItem(${index})"><i class='bx bx-edit' ></i></button> 
      </td>
    `
  tbody.appendChild(tr)
}

btnSalvar.onclick = e => { //botao de salvar os dados da inclusao

  if (sCodigo.value == '' || sNome.value == '' || sDescricao.value == '' || sAvaliacao.value == '' || sPreco.value == '' || sQuantidade.value == '' || sStatus.value == '' || sLink.value == '' || sImagens.value == '') { //valores do item
    return
  }

  e.preventDefault();


  if (id !== undefined) { //atribui os parametros passados para suas variaveis e id
    itens[id].codigo = sCodigo.value
    itens[id].nome = sNome.value
    itens[id].descricao = sDescricao.value
    itens[id].avaliacao = sAvaliacao.value
    itens[id].preco = sPreco.value
    itens[id].quantidade = sQuantidade.value
    itens[id].status = sStatus.value
    itens[id].link = sLink.value
    itens[id].imagens = sImagens.value
    for (let varFor = 0; varFor <= itemArray.length; i++) {
      itemArray[i] = sStatus.value;
    }
  } else {
    itens.push({ 'codigo': sCodigo.value, 'nome': sNome.value, 'descricao': sDescricao.value, 'avaliacao': sAvaliacao.value, 'preco': sPreco.value, 'quantidade': sQuantidade.value, 'status': sStatus.value, 'link': sLink.value, 'imagens': sImagens.value })
  }

  setItensBD() //seta os novos itens no banco de dados

  modal.classList.remove('active') //remove a tela de inclusao
  loadItens() //carrega novamente com o novo item criado
  id = undefined
}

function loadItens() { //funcao para buscar os itens do banco e colocar na tela quando carregada
  itens = getItensBD()
  tbody.innerHTML = ''
  itens.forEach((item, index) => { //for
    insertItem(item, index)
  })

}

const alterarStatus = document.querySelector('#alterarStatus'); //funcao para alterar o status (nao finalizado)
function alterar() {
  alert("Tem certeza que deseja mudar o status?");
  //mudar no itens[id].status 

  alterarStatus.addEventListener("click", mudarStatus);
  function mudarStatus() {
    if (sStatus.value) {
      sStatus.value = "Inativo";
    }
    else {
      sStatus.value = "Ativo";
    }
  }
}

document.getElementById("btnSalvar").addEventListener("click", function(e) {
  e.preventDefault(); 

  var input = document.getElementById("m-imagens");
  var files = input.files;
});

const getItensBD = () => JSON.parse(localStorage.getItem('dbfuncc')) ?? [] //funcao que pega o item no banco local. o JSON.parse torna uma informacao em formato JSON em um objeto JavaScript
const setItensBD = () => localStorage.setItem('dbfuncc', JSON.stringify(itens)) //seta os itens no banco local em JSON form

loadItens()

