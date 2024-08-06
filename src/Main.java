import Model.Paciente;

import java.sql.*;

public class Main {
    public static final String CREATE_TABLE_PACIENTES = "CREATE TABLE Pacientes (\n" +
            "    id INT AUTO_INCREMENT PRIMARY KEY," +
            "    nombre VARCHAR(50) NOT NULL," +
            "    apellido VARCHAR(50) NOT NULL," +
            "    domicilio VARCHAR(100)," +
            "    dni INT NOT NULL UNIQUE," +
            "    fecha_de_alta VARCHAR(50)," +
            "    usuario VARCHAR(50) NOT NULL UNIQUE," +
            "    password VARCHAR(255) NOT NULL" +
            ")";
    public static final String INSERT_PACIENTE = "INSERT INTO Pacientes (nombre,apellido,domicilio,dni,fecha_de_alta,usuario,password) VALUES (?,?,?,?,?,?,?)";
    public static final String UPDATE_PACIENTE = "UPDATE Pacientes SET password=? WHERE dni=?";

    public static void main(String[] args) throws Exception {
        Paciente paciente = new Paciente("Marco","Sava","POR AHI 2234",23445679, "20/10/2024", "MarcoSava22", "Messi");
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE_PACIENTES);
            PreparedStatement psInsert = connection.prepareStatement(INSERT_PACIENTE);

            psInsert.setString(1,paciente.getNombre());
            psInsert.setString(2,paciente.getApellido());
            psInsert.setString(3,paciente.getDomicilio());
            psInsert.setInt(4,paciente.getDni());
            psInsert.setString(5,paciente.getFechaDeAlta());
            psInsert.setString(6,paciente.getUsuario());
            psInsert.setString(7,paciente.getPassword());
            psInsert.execute();

            connection.setAutoCommit(false);
            PreparedStatement psUpdate = connection.prepareStatement(UPDATE_PACIENTE);
            psUpdate.setString(1, paciente.setPassword("CONTRA1"));
            psUpdate.setInt(2,paciente.getDni());
            psUpdate.execute();
            int a = 5/0;
            connection.commit();
            connection.setAutoCommit(true);

            String sql = "SELECT * FROM Pacientes";
            Statement stmt = connection.createStatement();
            ResultSet rd = stmt.executeQuery(sql);
            while (rd.next()){
                System.out.println(rd.getInt(1)+ rd.getString(2) + rd.getString(3) + rd.getString(4) + rd.getInt(5) + rd.getString(6) + rd.getString(7) + rd.getString(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }
        Connection connection1 = getConnection();
        String sql2 = "SELECT * FROM Pacientes";
        Statement statement2 = connection1.createStatement();
        ResultSet rd2 = statement2.executeQuery(sql2);
        while (rd2.next()){
            System.out.println(rd2.getInt(1)+ rd2.getString(2) + rd2.getString(3) + rd2.getString(4) + rd2.getInt(5) + rd2.getString(6) + rd2.getString(7) + rd2.getString(8));
        }
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:./pacientesDB", "sa", "sa");
    }
}