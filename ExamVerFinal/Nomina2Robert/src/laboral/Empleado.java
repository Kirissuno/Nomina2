package laboral;

/**
 * 
 * @author Robert
 * @version 2.0
 * @since 08/05/2019
 *
 */

public class Empleado extends Persona {
	
	private int categoria;
	public int anyos;
	
	/**
	 * Constructor simple
	 * @param nombre del empleado
	 * @param dni del empleado
	 * @param sexo del epleado
	 * @throws Exception tira excepción si el sexo del empleado no es M o F
	 * 
	 */

	public Empleado(String nombre, String dni, char sexo) throws DatosNoCorrectosException {
		super(nombre, dni, sexo);
		categoria = 1;
		anyos = 0;
	}
	
	/**
	 * Constructor completo
	 * @param nombre del empleado
	 * @param dni del empleado
	 * @param sexo del epleado
	 * @param anyos del cliente
	 * @param categoria del cliente
	 * @throws DatosNoCorrectosException tira excepción si el sexo del empleado no es M o F, 
	 * si su categoría es incorrecta o si los años no son positivos
	 * 
	 */

	public Empleado(String nombre, String dni, char sexo, int categoria, int anyos) throws DatosNoCorrectosException {
		super(nombre, dni, sexo);
		if(categoria >= 1 && categoria <= 10) {
			this.categoria = categoria;
		}else {
			throw new DatosNoCorrectosException("Categoria incorrecta");
		}
		if(anyos >= 0 ) {
			this.anyos = anyos;
		}else {
			throw new DatosNoCorrectosException("Los anyos deben ser positivos");
		}
		
	}
	
	/**
	 * Setter para la categoria del empleado
	 * @param cat es la categoría insertada para cambiar
	 * @throws DatosNoCorrectosException para controlar si la categoría es correcta
	 */
	
	public void setCategoria (int cat) throws DatosNoCorrectosException  {
		if(categoria >= 1 && categoria <= 10) {
			this.categoria = cat;
		}else {
			throw new DatosNoCorrectosException("Categoria incorrecta");
		}
	}
	
	/**
	 * Getter para recoger la categoria
	 * @return
	 */
	
	public int getCategoria () {
		return categoria;
	}
	
	/**
	 * Metodo para incrementar en 1 anyo de epleado
	 */
	public void incAnyo() {
		this.anyos++;
	}
	
	/**
	 * Metodo equivalente a toString
	 */

	public String imprime() {
		return nombre+" "+dni+" "+sexo+" "+categoria+" "+anyos;
	}
	
	

}
