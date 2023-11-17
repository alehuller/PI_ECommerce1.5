const modal = document.querySelector('.modal-container');
const tbody = document.querySelector('tbody');
const sNome = document.querySelector('#m-nome');
const sCpf = document.querySelector('#m-cpf');
const sEmail = document.querySelector('#m-email');
const sStatus = document.querySelector('#m-status');
const sGrupo = document.querySelector('#m-grupo');
const sSenha = document.querySelector('#m-senha');
const sSenhaConfirm = document.querySelector('#m-senhaConfirm');
//variaveis que indicam atributos de um item
const btnSalvar = document.querySelector('#btnSalvar');

let itens; //variavel que armazena os itens
let id; //armazena o index para edicao

const itemArray = [];

function validarEmail(email) { //funcao para validar o cadastro (se nao houver cadastro com outro email)
  //criar um array com os emails e verificar eles
  let emailRepetido = "";
  for(let y = 0; y <= itemArray.length; y++) {
    if(email == itemArray[y]) {
      emailRepetido = email;
    }
    else {
      emailRepetido = " ";
    }
  }

  if(emailRepetido == email) {
    return false;
  }
  else {
    return true;
  }
}

function contaCpf(strCpf) { //funcao para validar o CPF
  var soma;
  var resto;
  soma = 0;
  
  if (strCpf == "000000000") return false;

  for (i = 1; i <= 9; i++) soma = soma + parseInt(strCpf.substring(i-1, i)) * (11-i);
  resto = (soma * 10) % 11;

  if ((resto == 10) || (resto == 11)) resto = 0;
  if (resto != parseInt(strCpf.substring(9, 10))) return false;

  soma = 0;
  for (i = 1; i <= 10; i++) soma = soma + parseInt(strCpf.substring(i - 1, i)) * (12 - i);
  resto = (soma * 10) % 11;

  if((resto == 10) || (resto == 11)) resto = 0;
  if(resto != parseInt(strCpf.substring(10, 11))) return false;
  return true;
}

function openModal(edit = false, index = 0) { //funcao onde aparece a tela de inclusao. tudo false para nao aparecer na tela a parte do incluir.
    modal.classList.add('active') //a modal eh ativada para ficar visivel em tela
  
    modal.onclick = e => { //para cada clique fora da classe modal de inclusao, a classe eh removida para nao aparecer mais na tela.
      if (e.target.className.indexOf('modal-container') !== -1) {
        modal.classList.remove('active')
      }
    }

    if (edit) { //valores que sao editados na inclusao, cada novo com seu index
        sNome.value = itens[index].nome
        sSenha.value = itens[index].senha
        sSenhaConfirm.value = itens[index].sSenhaConfirm
        sCpf.value = itens[index].cpf
        sEmail.value = itens[index].email
        sStatus.value = itens[index].status
        sGrupo.value = itens[index].grupo
        id = index
    } else { //valores nao preenchidos
      sNome.value = ''
      sSenha.value = ''
      sSenhaConfirm.value = ''
      sCpf.value = ''
      sEmail.value = ''
      sStatus.value = ''
      sGrupo.value = ''
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
      <td>${item.nome}</td>
      <td>${item.cpf}</td>
      <td>${item.email}</td>
      <td>${item.status} <input id="alterarStatus" type="button" onclick="alterar()" value="Alterar" /></td>
      <td>${item.grupo}</td>
      <td class="acao">
        <button onclick="editItem(${index})"><i class='bx bx-edit' ></i></button> 
      </td>
    `
    tbody.appendChild(tr)
}

btnSalvar.onclick = e => { //botao de salvar os dados da inclusao

  if (sNome.value == '' || sSenha.value == '' || sSenhaConfirm.value == '' || sCpf.value == '' || sEmail.value == '' || sGrupo.value == '' || sStatus.value == '') { //valores do item
    return
  }
  
  e.preventDefault();

  if(sSenha.value === sSenhaConfirm.value && contaCpf(sCpf.value) && validarEmail(sEmail.value)) {
    if (id !== undefined) { //atribui os parametros passados para suas variaveis e id
    itens[id].nome = sNome.value
    itens[id].senha = sSenha.value
    itens[id].senhaConfirm = sSenhaConfirm.value
    itens[id].cpf = sCpf.value
    itens[id].email = sEmail.value
    itens[id].grupo = sGrupo.value
    itens[id].status = sStatus.value
    for(let varFor = 0; varFor <= itemArray.length; i++) {
      itemArray[i] = sStatus.value;
    }
    } else {
      itens.push({'nome': sNome.value, 'senha': sSenha.value, 'senhaConfirm': sSenhaConfirm.value, 'cpf': sCpf.value, 'email': sEmail.value, 'grupo': sGrupo.value, 'status': sStatus.value})
    }
  
    setItensBD() //seta os novos itens no banco de dados
  
    modal.classList.remove('active') //remove a tela de inclusao
    loadItens() //carrega novamente com o novo item criado
    id = undefined
  }
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
      if(sStatus.value) {
        sStatus.value = "Inativo";
      }
      else {
        sStatus.value = "Ativo";
      }
    }
}

document.addEventListener("DOMContentLoaded", function() {
  const editButtons = document.querySelectorAll(".edit-button");
  const modal = document.querySelector(".modal");
  const modalForm = modal.querySelector("form");
  
  editButtons.forEach(button => {
      button.addEventListener("click", function() {
          const row = this.closest("tr");
          const cells = row.querySelectorAll("td");
          
          const nome = cells[0].textContent;
          const cpf = cells[1].textContent;
          const email = cells[2].textContent;
          const status = cells[3].textContent;
          const grupo = cells[4].textContent;
          
          modalForm.elements["m-nome"].value = nome;
          modalForm.elements["m-cpf"].value = cpf;
          modalForm.elements["m-email"].value = email;
          modalForm.elements["m-status"].value = status;
          modalForm.elements["m-grupo"].value = grupo;
          
          modal.style.display = "block";
      });
  });
});

  
/*const getItensBD = () => JSON.parse(localStorage.getItem('dbfunc')) ?? [] //funcao que pega o item no banco local. o JSON.parse torna uma informacao em formato JSON em um objeto JavaScript
const setItensBD = () => localStorage.setItem('dbfunc', JSON.stringify(itens)) //seta os itens no banco local em JSON form
  
loadItens()*/