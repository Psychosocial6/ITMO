package client.dataInput;

import java.io.*;

/**
 * Класс для чтения данных с консоли.
 * Использует {@link BufferedReader} для более удобного чтения текстовых данных.
 */
public class DataInput implements InputSource {
	
	/**
     * Чтение строки с консоли.
     * 
     * @return строка, введенная пользователем, или {@code null} в случае ошибки.
     */
	private static final BufferedInputStream bis = new BufferedInputStream(System.in);
	@Override
	public String input() {
		try{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int byteRead;
            
            while ((byteRead = bis.read()) != -1) { 
                if (byteRead == '\n') { 
                    break;
                }
                buffer.write(byteRead);
            }
            return buffer.toString().trim();
			
		}
		catch(IOException e ) {
			e.printStackTrace();
			return null;
		}
		
	}
}
