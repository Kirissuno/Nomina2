package laboral;

/**
 * 
 * @author Robert
 * @version 2.0
 * @since 08/05/2019
 *
 */

public class Nomina {
	
	private static final int SUELDO_BASE[] = { 50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000};
	
	/**
	 * 
	 * @param emp es el epleado que deseamos ver su sueldo
	 * @return nos devuelve el sueldo
	 */
	
	public static int sueldo (Empleado emp) {
		return SUELDO_BASE[emp.getCategoria()-1] + 5000*emp.anyos;
	}

}
