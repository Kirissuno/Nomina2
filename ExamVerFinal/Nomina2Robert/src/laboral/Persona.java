package laboral;

/**
 * 
 * @author Robert
 * @version 2.0
 * @since 08/05/2019
 *
 */

public class Persona {

	public String nombre;
	public String dni;
	public char sexo;
	
	/**
	 * Constructor completo
	 * @param nombre
	 * @param dni
	 * @param sexo
	 * @throws DatosNoCorrectosException
	 */
	
	public Persona(String nombre, String dni, char sexo) throws DatosNoCorrectosException {
		this.nombre = nombre;
		this.dni = dni;
		if(sexo == 'F' || sexo == 'M') {
			this.sexo = sexo;
		}else {
			throw new DatosNoCorrectosException("Sexo incorrecto, debe ser F o M");
		}
	}
	
	/**
	 * Constructor simple
	 * @param nombre
	 * @param sexo
	 * @throws DatosNoCorrectosException
	 */
	public Persona(String nombre, char sexo) throws DatosNoCorrectosException {
		this.nombre = nombre;
		if(sexo == 'F' || sexo == 'M') {
			this.sexo = sexo;
		}else {
			throw new DatosNoCorrectosException("Sexo incorrecto, debe ser F o M");
		}
	}

	/**
	 * Setter del dni de la persona
	 * @param dni
	 */
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	/**
	 * Equivalente a toString
	 * @return
	 */
	public String imprime() {
		return nombre+" "+dni;
	}
	
}
