import java.sql.*;

public class BaseQuery {
    public static Connection connection;
    public static Statement st;
    //подключение (вложенный метод)
    public static void  connect() {
        try {
            Class.forName("org.sqlite.JDBC");  //инициализация драйвера
            connection = DriverManager.getConnection("jdbc:sqlite:main.db"); //путь к базе для драйвера
            st = connection.createStatement(); //инициализация для создания запроса к бд
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    //отключение (вложенный метод)
    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //создание таблицы
    public static void createTableIfNotExist (String qw){
        connect();
        try {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS "+ qw);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnect();
    }
    public static void addData(String data){
        connect();
        try {
            st.executeUpdate("INSERT INTO staff "+data);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnect();
    }
    public static void deleteStaffOlderThan(Integer age){
        connect();
        try {
            st.executeUpdate("DELETE FROM staff WHERE Age>="+age);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnect();
    }
    public static void selectStaffOlderThan(Integer age){
        connect();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM staff WHERE Age>="+age);
            while (rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3));
                System.out.println("Задолбало!!! 422 Unprocessable Entity - Validation Failed [PullRequest; null]custom: No commits between homework_3 and homework_3");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnect();
    }
}
