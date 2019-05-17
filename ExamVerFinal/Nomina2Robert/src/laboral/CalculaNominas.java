package laboral;

/**
 * @author Robert
 * @version 2.0
 * @since 17/05/2019
 * Desde windows no puedo ejecutar el Javadoc y eso que tengo el JDK instalado pero bueno.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class CalculaNominas {
	public static void main(String[] args) throws IOException, DatosNoCorrectosException, ClassNotFoundException, SQLException {
		/*
		 * Declaracion de objetos mas utilizados.
		 */
		Conexion con = new Conexion();
		Scanner numeros = new Scanner(System.in);
		Scanner letras = new Scanner(System.in);
		
		/*
		 * Entrada al menu
		 */
		System.out.println("1. Select all \n"
				+ "2. Select salario del empleado \n"
				+ "3. Editar datos \n"
				+ "4. Recalcular sueldo de los empleados \n"
				+ "5. Realizar copia de seguridad en un fichero backup.txt \n"
				+ "6. Cargar fichero empleados.txt \n"
				+ "7. Cargar fichero EmpleadosNuevos.txt \n"
				+ "8. Dar de alta nuevo empleado \n"
				+ "Cualquier otro boton para salir");
		String opcion1 = letras.nextLine();
		
		/*
		 * Menu
		 */
		switch(opcion1) {
			/*
			 * Select * from empleado
			 */
			case "1":
				System.out.println(con.selectall());
				break;
			/*
			 * Select sueldo from empleado where dni = algun empleado
			 */
			case "2":
				System.out.println("Introduce el DNI del empleado a seleccionar");
				String dni = letras.nextLine();
				int cantidad = con.selectsalario(dni);
				if(cantidad == 0) {
					System.out.println("Empleado inexistente");
				}else {
					System.out.println(cantidad+ "€");
				}
				break;
			/*
			 * Edicion de datos
			 */
			case "3":
				System.out.println("Introduce el DNI del empleado a editar");
				String dnieditar = letras.nextLine();
				
				Empleado existe = con.sacaemp(dnieditar);
				
				if(existe != null) {
					System.out.println("Introduce su nombre");
					String nuevonombre = letras.nextLine();
					System.out.println("Introduce su sexo M o F");
					String nuevosexo = letras.nextLine();
					System.out.println("Introduce su categoria");
					Integer nuevacategoria = numeros.nextInt();
					System.out.println("Introduce sus anyos");
					Integer nuevosanyos = numeros.nextInt();
					
					if(nuevacategoria == null || nuevacategoria < 1 || nuevacategoria > 10  || nuevosanyos == null || nuevosanyos < 1 || nuevosexo == null) {
						System.out.println("ERROR");
					}else {
						con.modificaDatos(nuevonombre, nuevosexo, nuevacategoria, nuevosanyos, dnieditar);
						System.out.println("Hecho!");
					}
					
				}else {
					System.out.println("Empleado especificado no existe");
				}
				
				break;
			/*
			 * Recalcular sueldo en bbdd y fichero
			 */
			case "4":
				File sueldos = new File("sueldos.dat");
				FileWriter fw = new FileWriter(sueldos, false);
				BufferedWriter bwr = new BufferedWriter(fw);
				
				for(String campo : con.dnisueldo()) {
					String [] aux = campo.split(" ");
					con.recalcularsueldo(aux[0].trim());
				}
				
				for(String campo : con.dnisueldo()) {
					String [] aux = campo.split(" ");
					bwr.write(aux[0] + " " + aux[1] + "\n");
				}
//				System.out.println(con.sacaemp("X9309413W").imprime());
				
				bwr.close();
				fw.close();
				System.out.println("Hecho!");
				break;
			/*
			 * Copia de seguridad
			 */
			case "5":
				File backup = new File("backup.txt");
				FileWriter fwrb = new FileWriter(backup, false);
				BufferedWriter bwrb = new BufferedWriter(fwrb);
				
				bwrb.write(con.selectall());
				
				bwrb.close();
				fwrb.close();
				System.out.println("Hecho!");
				break;
			/*
			 * Cargar fichero empleados.txt
			 */
			case "6":
				File empleados = new File("empleados.txt");
				con.altaEmpleados(empleados);
				System.out.println("Hecho!");
				break;
			/*
			 * Cargar fichero EmpleadosNuevos.txt
			 */
			case "7":
				con.altaEmpleados(new File("EmpleadosNuevos.txt"));
				System.out.println("Hecho!");
				break;
			/*
			 * Dar de alta empleado a mano campo por campo
			 */
			case "8":
				System.out.println("Introduce el nombre");
				String nombre = letras.nextLine();
				System.out.println("Introduce el dni");
				String dnin = letras.nextLine();
				System.out.println("Introduce el sexo M o F");
				char sexo = letras.nextLine().trim().charAt(0);
				System.out.println("Introduce la categoria");
				Integer categoria = numeros.nextInt();
				System.out.println("Introduce los anyos en la empresa");
				Integer anyos = numeros.nextInt();
				
				if(categoria == null || anyos == null) {
					con.insertaempleado(new Empleado(nombre, dnin, sexo));
					con.insertasueldo(new Empleado(nombre, dnin, sexo));
				}else {
					con.insertaempleado(new Empleado(nombre, dnin, sexo, categoria, anyos));
					con.insertasueldo(new Empleado(nombre, dnin, sexo, categoria, anyos));
				}
				
				System.out.println("Hecho!");
				break;
			default:
				break;
		}	
		/*
		 * Cerramos los scanners
		 */
		numeros.close();
		letras.close();
	}
	
	/**
	 * Metodo de nominas 1
	 */
	static public String escribe(Empleado emp) {
		return emp.imprime() + " " + Nomina.sueldo(emp);
	}

}

/*
 * CODIGO PARA CREAR LAS TABLAS DE LA BASE DE DATOS
 * 
create table empleado(
	    dni varchar2(255) primary key,
	    nombre varchar2(255),
	    sexo CHAR(1),
	    categoria number,
	    anyos number
	    
	);

	create table nomina(
	    dni varchar2(255) primary key constraint fk references empleado,
	    sueldo number
	);
*/	
