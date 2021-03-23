import java.sql.*;
//разбор занятия № 2 (уровень 3)
public class Main {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement pstmt;  //предварит запрс

    private static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");  //инициализация драйвера
            connection = DriverManager.getConnection("jdbc:sqlite:main.db"); //путь к базе для драйвера
            statement = connection.createStatement(); //инициализация для создания запроса к бд
            //результат стейтмента либо резалт-таблица, либо int - сколько записей обработано
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connect();
        try {
            //переменной типа resultset присваиваем результат запроса
//            ResultSet rs = statement.executeQuery("SELECT * FROM students" );
//            while (rs.next()){
//                System.out.println(rs.getInt(1)+" "+rs.getString("name")+" "+rs.getInt(3));
//            }
//            ResultSetMetaData rsmd = rs.getMetaData(); //результат запроса о структуре таблица БД
//            for (int i=1;i<=rsmd.getColumnCount();i++) //индекс в результате начинается с 1
//            {
//                System.out.println(rsmd.getColumnName(i)+" битовая маска "+rsmd.getColumnType(i));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

//        try {
            //добавление новой записи в БД, переменной присвоится количество добавленных записей
            //int res = statement.executeUpdate("INSERT INTO students (name, score) VALUES ('Bill', 60)" );
            // УНИЧТОЖЕНИЕ ТАБЛИЦЫ
            //int res = statement.executeUpdate("DROP TABLE IF EXIST students" );
            //создание таблицы с указанными полями 2 варианта
            //statement.executeUpdate("CREATE TABLE students (" + " id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT," + " score INTEGER)");
            //statement.execute("CREATE TABLE students (" + " id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT," + " score INTEGER)");
//            connection.setAutoCommit(false);  //отключение автосохранения БД на время выполнения операций
//            for (int i = 0; i < 1000; i++) {
//                stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('test_name', 100)");
//            }
//            connection.setAutoCommit(true); //включение автосохранения по завершении операций

//добавление записей через предзапрос, добавление предзапроса в пачку, выполнение пачки

//            connection.setAutoCommit(false);
//            pstmt = connection.prepareStatement("INSERT INTO students (name, score) VALUES (?, ?);");
//            for (int i = 0; i < 100; i++) {
//                pstmt.setString(1, "Bob" + i);
//                pstmt.setInt(2, i);
//                pstmt.addBatch();
//            }
//            pstmt.executeBatch();
//            connection.setAutoCommit(true);

            connection.setAutoCommit(false);

            statement.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob11', 1110);");
            //установка точки отката
            Savepoint sp = connection.setSavepoint();

            try {
                statement.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob12', 1120);");
            //    connection.commit();
            } catch (Exception e) {
                //если при добавлении выпало исключение - откатить до сейвпоинта
                connection.rollback(sp);
            }
           // connection.rollback(sp);
            //иначе добавить еще запись
            statement.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob13', 1130);");
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }

    }
}
