package laboral;

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

	
	
	public Conexion() {
		
	}

	public String selectall() throws ClassNotFoundException, SQLException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		String vuelta = "";
		String url = "jdbc:oracle:thin:@172.16.8.133:1521:xe";
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
	
	public void altaEmpleados(File fichero) throws FileNotFoundException, DatosNoCorrectosException, ClassNotFoundException {
		Scanner lecturaempleados = new Scanner(fichero);
		List<Empleado> empl = new ArrayList<Empleado>();
		
		while(lecturaempleados.hasNext()) {
			String [] linea = lecturaempleados.nextLine().split(",");
			String nombre = linea[0].toString().trim();
			String dni = linea[1].toString().trim();
			char sexo = linea[2].trim().charAt(0);
			if(!lecturaempleados.hasNext()) {
				Empleado empleado = new Empleado(nombre, dni, sexo);
				empl.add(empleado);
			}else {
				Empleado empleado = new Empleado(nombre, dni, sexo, Integer.parseInt(linea[3].toString().trim()), Integer.parseInt(linea[4].toString().trim()) );
				empl.add(empleado);
			}
		}
		
		for(Empleado emp : empl) {
			insertaempleado(emp);
			insertasueldo(emp);
		}
	}
	
	public void insertaempleado(Empleado emp) throws ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		try {
			String url = "jdbc:oracle:thin:@172.16.8.133:1521:xe";
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
	
	public void insertasueldo(Empleado emp) throws ClassNotFoundException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection;
		try {
			String url = "jdbc:oracle:thin:@172.16.8.133:1521:xe";
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
