package estoqueA3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClasseConexao {
    public static Connection getcon() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String unicode = "useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
            return DriverManager.getConnection("jdbc:mysql://localhost/estoque?" + unicode, "root", "");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Couldn't connect!");
            throw new RuntimeException(ex);
        }
    }

    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
