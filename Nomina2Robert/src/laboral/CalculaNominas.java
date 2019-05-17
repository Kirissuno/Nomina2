package laboral;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CalculaNominas {
	public static void main(String[] args) throws IOException {		
		try {
			Conexion con = new Conexion();

			List<Empleado> empl = new ArrayList<Empleado>();
			
			File empleados = new File("empleados.txt");
			
			Scanner lecturaempleados = new Scanner(empleados);
			
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
			
			lecturaempleados.close();
			
			File sueldos = new File("sueldos.dat");
			FileWriter fw = new FileWriter(sueldos, false);
			BufferedWriter bwr = new BufferedWriter(fw);
			
			for(Empleado emp : empl) {
				con.insertaempleado(emp);
				con.insertasueldo(emp);
				bwr.write(emp.dni + " " + Nomina.sueldo(emp));
				bwr.write("\n");
			}
			
			bwr.close();
			fw.close();
			
			
			
			/*
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
			
			
			
			File backup = new File("backup.txt");
			FileWriter fwrb = new FileWriter(backup, false);
			BufferedWriter bwrb = new BufferedWriter(fwrb);
			
			bwrb.write(con.selectall());
			
			bwrb.close();
			fwrb.close();
			
			con.altaEmpleados(new File("EmpleadosNuevos.txt"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	static private String escribe(Empleado emp) {
		return emp.imprime() + " " + Nomina.sueldo(emp);
	}

}
