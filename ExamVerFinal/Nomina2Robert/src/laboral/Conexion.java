package laboral;

/**
 * @author Robert
 * @version 2.0
 * @since 17/05/2019
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Conexion {

	/**
	 * Constructor vacio
	 */
	public Conexion() {
		
	}

	/**
	 * Metodo que me devuelve todos los empleados de la base de datos
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String selectall() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		String vuelta = "";
		String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
		connection = DriverManager.getConnection(url,"exam", "exam");
		Statement stat = connection.createStatement();
		ResultSet query = stat.executeQuery("select * from empleado");
		ResultSetMetaData rsmd = query.getMetaData();
		int columnas = rsmd.getColumnCount();
		
		while(query.next()) {
			for (int i = 1; i <= columnas; i++) {
		           String columna = query.getString(i);
		           vuelta += columna + " " + "\t";
		       }
			vuelta += "\n";
		}
		connection.close();
		return vuelta;
	}
	
	/**
	 * Metodo que me saca el salario del cliente especificado mediante dni
	 * @param dni
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int selectsalario(String dni) throws ClassNotFoundException, SQLException {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection;
			String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
			connection = DriverManager.getConnection(url,"exam", "exam");
			Statement stat = connection.createStatement();
			String query = "select sueldo from nomina where dni = '"+dni+"'";
			ResultSet resultado = stat.executeQuery(query);
			
			int cantidad = 0;
			
			while(resultado.next()) {
				cantidad += resultado.getInt("sueldo");
			}
				
			connection.close();
			return cantidad;
		}
	
	/**
	 * Metodo para recalcular sueldo a todos los empleados
	 * @param dni
	 * @throws ClassNotFoundException
	 * @throws DatosNoCorrectosException
	 */
	public void recalcularsueldo(String dni) throws ClassNotFoundException, DatosNoCorrectosException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		try {
			String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
			connection = DriverManager.getConnection(url,"exam", "exam");
			Statement stat = connection.createStatement();
			String query = "UPDATE nomina set sueldo = "+Nomina.sueldo(sacaemp(dni))+" where dni like \'"+dni+"\'";
			stat.executeUpdate(query);
			
			stat.close();
			connection.close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para modificar datos de algun empleado
	 * @param nombre
	 * @param sexo
	 * @param categoria
	 * @param anyos
	 * @param dni
	 * @throws ClassNotFoundException
	 */
	public void modificaDatos(String nombre, String sexo, Integer categoria, Integer anyos, String dni) throws ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		try {
			String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
			connection = DriverManager.getConnection(url,"exam", "exam");
			Statement stat = connection.createStatement();
			String query = "UPDATE empleado set nombre = \'"+nombre+"\', sexo = \'"+sexo+"\', categoria = "+categoria+", anyos = "+anyos+" where dni like \'"+dni+"\'";
			stat.executeUpdate(query);
			
			stat.close();
			connection.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para sacar un empleado completo de la base de datos pasandole su dni
	 * @param dni
	 * @return
	 * @throws DatosNoCorrectosException
	 */
	public Empleado sacaemp(String dni) throws DatosNoCorrectosException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Connection connection;
		Empleado emp = null;
		try {
			String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
			connection = DriverManager.getConnection(url,"exam", "exam");
			Statement stat = connection.createStatement();
			String query = "Select * from empleado where dni like \'"+dni+"\'";
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()) {
				emp = new Empleado(rs.getString("nombre"), 
						rs.getString("dni"), 
						rs.getString("sexo").trim().charAt(0), 
						rs.getInt("categoria"), 
						rs.getInt("anyos"));
			}
			
			stat.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}
	
	/**
	 * Metodo necesario para recalcular el sueldo a todos los empleados
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<String> dnisueldo() throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
		connection = DriverManager.getConnection(url,"exam", "exam");
		
		List<String> lista = new ArrayList<>();
		Statement stat = connection.createStatement();
		String query = "select dni,sueldo from nomina";
		
		ResultSet resultado = stat.executeQuery(query);
				
		while(resultado.next()) {
			String campos = resultado.getString("dni") + " " + resultado.getInt("sueldo");
			lista.add(campos);
		}
			
		connection.close();
		return lista;
	}
	
	/**
	 * Metodo para dar de alta a todos los empleados de un fichero
	 * @param fichero
	 * @throws FileNotFoundException
	 * @throws DatosNoCorrectosException
	 * @throws ClassNotFoundException
	 */
	public void altaEmpleados(File fichero) throws FileNotFoundException, DatosNoCorrectosException, ClassNotFoundException {
		Scanner lecturaempleados = new Scanner(fichero);
		List<Empleado> empl = new ArrayList<Empleado>();
		
		while(lecturaempleados.hasNext()) {
			String [] linea = lecturaempleados.nextLine().split(",");
			String nombre = linea[0].toString().trim();
			String dni = linea[1].toString().trim();
			char sexo = linea[2].trim().charAt(0);
			if(!lecturaempleados.hasNext()) {
				empl.add(new Empleado(nombre, dni, sexo));
			}else {
				empl.add(new Empleado(nombre, dni, sexo, Integer.parseInt(linea[3].toString().trim()), Integer.parseInt(linea[4].toString().trim())));
			}
		}
		
		for(Empleado emp : empl) {
			insertaempleado(emp);
			insertasueldo(emp);
		}
		
		lecturaempleados.close();
	}
	
	/**
	 * Metodo para insertar un empleado, sin su salario
	 * @param emp
	 * @throws ClassNotFoundException
	 */
	public void insertaempleado(Empleado emp) throws ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		try {
			String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
			connection = DriverManager.getConnection(url,"exam", "exam");
			Statement stat = connection.createStatement();
			String query = "INSERT INTO EMPLEADO VALUES (\'"+emp.dni+"\', \'"+emp.nombre+"\', \'"+emp.sexo+"\',"+emp.getCategoria()+","+emp.anyos+")";
			stat.executeUpdate(query);
			
			stat.close();
			connection.close();
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para crearle el sueldo a un empleado
	 * @param emp
	 * @throws ClassNotFoundException
	 */
	public void insertasueldo(Empleado emp) throws ClassNotFoundException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		try {
			String url = "jdbc:oracle:thin:@192.168.0.21:1521:xe";
			connection = DriverManager.getConnection(url,"exam", "exam");
			Statement stat = connection.createStatement();
			
			String query = "INSERT INTO nomina VALUES (\'"+emp.dni+"\', "+Nomina.sueldo(emp)+")";
			
			stat.executeUpdate(query);
			
			stat.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
