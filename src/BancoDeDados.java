
import java.util.List;
import java.sql.*;

public class BancoDeDados implements OperacoesCRUD<Tarefas> {

    private final String url;
    private final String usuario;
    private final String senha;

    public BancoDeDados(String url, String usuario, String senha) {
        this.url = "jdbc:postgresql://motty.db.elephantsql.com/qbxnpisz";
        this.usuario = "qbxnpisz";
        this.senha = "wy3xBTbG1KVo6o-Yw9yVEllxImAUIa_c";
    }

    @Override 
    public void adicionar(Tarefas tarefa) {
     
        String sql = "INSERT INTO tarefas (titulo, descricao, data_inicio, data_conclusao_prevista, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tarefa.getTitulo());
            statement.setString(2, tarefa.getDescricao());
            statement.setString(3, tarefa.getDatainicio());
            statement.setString(4, tarefa.getDataconclusao());
            statement.setBoolean(5, tarefa.getStatus());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void listar() {
            System.out.println("\n=== Lista de Tarefas do Banco de Dados ");
            String sql = "SELECT * FROM tarefas";
             try (Connection connection = DriverManager.getConnection(url, usuario, senha);
                  Statement statement = connection.createStatement();
                  ResultSet resultSet = statement.executeQuery(sql)
             ){

                while(resultSet.next()){
                    String titulo = resultSet.getString("titulo");
                    String descricao = resultSet.getString("descricao");
                    String datainicio = resultSet.getString("datainicio");
                    String dataconclusao = resultSet.getString("dataconclusao");
                    boolean status = resultSet.getInt("status") == 1;
                    Tarefas tarefa = new Tarefas (titulo, descricao,datainicio,dataconclusao);
                    tarefa.setStatus(status);
                    System.out.println(tarefa);
                    System.out.println("------------------");
                }
             } catch (SQLException e) {
                e.printStackTrace();
             }
    }

    @Override
    public void editar(int indice, Tarefas tarefa) {
     
        String sql = "UPDATE tarefas SET titulo=?, descricao=?, data_inicio=?, data_conclusao_prevista=?, status=? " +
                "WHERE id=?";

        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tarefa.getTitulo());
            statement.setString(2, tarefa.getDescricao());
            statement.setString(3, tarefa.getDatainicio());
            statement.setString(4, tarefa.getDataconclusao());
            statement.setBoolean(5, tarefa.getStatus());
            statement.setInt(6, indice + 1); // Índices no banco começam em 1

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removerTarefa(int indice) {
    
        String sql = "DELETE FROM tarefas WHERE id=?";

             try (Connection connection = DriverManager.getConnection(url, usuario, senha);
                  PreparedStatement statement = connection.prepareStatement(sql)) {

                 statement.setInt(1,indice+1);

                 int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0){
                        System.out.println("Tarefa removida do banco de dados com sucesso");       
                    } else {
                        System.out.println("Nenhuma tarefa encontrada para ser removida.");
                    }

             } catch (SQLException e) {
                 e.printStackTrace();
             }
                
    }
    public  List<Tarefas> carregarTarefasDoBanco( List<Tarefas> listaTarefas) {
       
        try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
            String sql = "SELECT * FROM tarefas"; // Consulta SQL para selecionar todas as tarefas
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Cria uma nova instância de Tarefas com os dados do ResultSet
                    Tarefas tarefa = new Tarefas(
                            resultSet.getString("titulo"),
                            resultSet.getString("descricao"),
                            resultSet.getString("data_inicio"),
                            resultSet.getString("data_conclusao")
                    );
                    tarefa.setStatus(resultSet.getBoolean("status")); // Define o status da tarefa
                    listaTarefas.add(tarefa); // Adiciona a tarefa à lista
                }
            }
        } catch (SQLException e) { // Captura exceções de SQL
           
            e.printStackTrace();
            // Se ocorrer uma exceção, a lista retornada será vazia 
            
            throw new RuntimeException("Erro ao carregar tarefas do banco de dados", e);
        }
        
        return listaTarefas; // Retorna a lista de tarefas (pode ser vazia se ocorrer uma exceção)
    }

    
}
