package laboral;

/**
 * 
 * @author Robert
 * @version 2.0
 * @since 08/05/2019
 *
 */

public class DatosNoCorrectosException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de nuestros errores
	 * @param message
	 */

	public DatosNoCorrectosException(String message) {
        super(message);
    }

}
