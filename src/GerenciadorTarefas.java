
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;



public class GerenciadorTarefas implements OperacoesCRUD<Tarefas> {
    private List<Tarefas> listaTarefas;
    BancoDeDados bancodedados = new BancoDeDados("jdbc:postgresql://motty.db.elephantsql.com/qbxnpisz","qbxnpisz","wy3xBTbG1KVo6o-Yw9yVEllxImAUIa_c");
    public GerenciadorTarefas() {
        this.listaTarefas = new ArrayList<>();
        bancodedados.carregarTarefasDoBanco(listaTarefas);
    }
    
    public List<Tarefas> getListaTarefas(){
     return listaTarefas;
    }
    

    @Override
    public void adicionar(Tarefas tarefa) {
        listaTarefas.add(tarefa);
        bancodedados.adicionar(tarefa);
    }

    @Override
    public void listar() {
        listarTarefasComFiltros();
    }

    @Override
    public void editar(int indice, Tarefas novaTarefa) {
        if (indice >= 0 && indice < listaTarefas.size()) {
            listaTarefas.set(indice, novaTarefa);
            bancodedados.editar(indice, novaTarefa);
            System.out.println("Tarefa editada com sucesso!");
        } else {
            System.out.println("Índice inválido. Não foi possível editar a tarefa.");
        }
    }

 @Override   
 public void removerTarefa(int indice) {
     if (indice >= 0 && indice < listaTarefas.size()) {
         listaTarefas.remove(indice);
         bancodedados.removerTarefa(indice);
         System.out.println("Tarefa removida com sucesso!");
     } else {
         System.out.println("Índice inválido. Não foi possível remover a tarefa.");
     }
 } 

 private void listarTarefasComFiltros() {
     Scanner scanner = new Scanner(System.in);

     System.out.println("\n=== Opções de Filtro ===");
     System.out.println("1. Filtrar por Data de Início");
     System.out.println("2. Filtrar por Data de Conclusão");
     System.out.println("3. Filtrar por Status (Concluídas/Não Concluídas)");
     System.out.println("4. Listar Todas as Tarefas");
     System.out.print("Escolha uma opção: ");

     int escolha = scanner.nextInt();
     scanner.nextLine();

     switch (escolha) {
         case 1:
             System.out.print("Digite a data de início desejada (formato: dd/MM/yyyy): ");
             String dataInicio = scanner.nextLine();
             filtrarPorDataInicio(dataInicio);
             break;
         case 2:
             System.out.print("Digite a data de conclusão desejada (formato: dd/MM/yyyy): ");
             String dataConclusao = scanner.nextLine();
             filtrarPorDataConclusao(dataConclusao);
             break;
         case 3:
             System.out.print("Digite o status desejado (1 para concluídas, 0 para não concluídas): ");
             int status = scanner.nextInt();
             filtrarPorStatus(status == 1);
             break;
         case 4:
             exibirTodasTarefas();
             break;
         default:
             System.out.println("Opção inválida. Listando todas as tarefas.");
             exibirTodasTarefas();
     }
     scanner. close();
 }
 
 private void filtrarPorDataInicio(String dataInicio) {
     System.out.println("\n=== Tarefas filtradas por Data de Início ===");

     for (Tarefas tarefa : listaTarefas) {
         if (tarefa.getDatainicio().equals(dataInicio)) {
             exibirDetalhesTarefa(tarefa);
         }
     }
 }

 
 private void filtrarPorDataConclusao(String dataConclusao) {
     System.out.println("\n=== Tarefas filtradas por Data de Conclusão ===");

     for (Tarefas tarefa : listaTarefas) {
         if (tarefa.getDataconclusao().equals(dataConclusao)) {
             exibirDetalhesTarefa(tarefa);
         }
     }
 }
 
 private void filtrarPorStatus(boolean status) {
     System.out.println("\n=== Tarefas filtradas por Status ===");

     for (Tarefas tarefa : listaTarefas) {
         if (tarefa.getStatus() == status) {
             exibirDetalhesTarefa(tarefa);
         }
     }
 }

 private void exibirDetalhesTarefa(Tarefas tarefa) {
     System.out.println(tarefa);
     System.out.println("------------------------");
 }
 
 
     private void exibirTodasTarefas() {
         System.out.println("\n=== Lista de Todas as Tarefas ===");
         for (Tarefas tarefa : listaTarefas) {
             System.out.println(tarefa);
             System.out.println("------------------------");
         }
 }
}

