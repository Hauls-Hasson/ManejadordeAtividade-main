import java.util.Scanner;


public class App {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            GerenciadorTarefas gerenciador = new GerenciadorTarefas();

            while (true) {
                exibirMenu();
                int escolha = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                switch (escolha) {
                    case 1:
                        criarTarefa(scanner, gerenciador);
                        break;
                    case 2:
                        listarTarefas(gerenciador);
                        break;
                    case 3:
                        modificarTarefa(scanner, gerenciador);
                        break;
                    case 4:
                        excluirTarefa(scanner, gerenciador);
                        break;
                    case 5:
                        System.out.println("Saindo do programa.");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

        private static void exibirMenu() {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Criar Tarefa");
            System.out.println("2. Listar Tarefas");
            System.out.println("3. Modificar Tarefa");
            System.out.println("4. Excluir Tarefa");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
        }

        private static void criarTarefa(Scanner scanner, GerenciadorTarefas gerenciador) {
            System.out.print("Digite o título da tarefa: ");
            String titulo = scanner.nextLine();

            System.out.print("Digite a descrição da tarefa: ");
            String descricao = scanner.nextLine();

            System.out.print("Digite a data de início da tarefa: ");
            String datainicio = scanner.nextLine();

            System.out.print("Digite a data de conclusão prevista da tarefa: ");
            String dataconclusao = scanner.nextLine();

            Tarefas novaTarefa = new Tarefas(titulo, descricao, datainicio, dataconclusao);
            gerenciador.adicionar(novaTarefa);

            System.out.println("Tarefa criada com sucesso!");
        }

        private static void listarTarefas(GerenciadorTarefas gerenciador) {
            System.out.println("\n=== Lista de Tarefas ===");
            gerenciador.listar();
        }

        private static void modificarTarefa(Scanner scanner, GerenciadorTarefas gerenciador) {
            System.out.print("Digite o índice da tarefa que deseja modificar: ");
            int indice = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            if (indice >= 0 && indice < gerenciador.getListaTarefas().size()) {
                Tarefas tarefaAtual = gerenciador.getListaTarefas().get(indice);

                System.out.print("Digite o novo título da tarefa: ");
                String novoTitulo = scanner.nextLine();

                System.out.print("Digite a nova descrição da tarefa: ");
                String novaDescricao = scanner.nextLine();

                System.out.print("Digite a nova data de início da tarefa: ");
                String novaDataInicio = scanner.nextLine();

                System.out.print("Digite a nova data de conclusão prevista da tarefa: ");
                String novaDataConclusaoPrevista = scanner.nextLine();

                Tarefas novaTarefa = new Tarefas(novoTitulo, novaDescricao, novaDataInicio, novaDataConclusaoPrevista);
                gerenciador.editar(indice, novaTarefa);

                System.out.println("Tarefa modificada com sucesso!");
            } else {
                System.out.println("Índice inválido. Não foi possível modificar a tarefa.");
            }
        }

        private static void excluirTarefa(Scanner scanner, GerenciadorTarefas gerenciador) {
            System.out.print("Digite o índice da tarefa que deseja excluir: ");
            int indice = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            gerenciador.removerTarefa(indice);

            System.out.println("Tarefa removida com sucesso!");
        }
    }



