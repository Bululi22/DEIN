package conexionBD;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.TimeZone;

public class Propiedades {
	private static Properties props=new Properties();
	static{
        try(FileInputStream input = new FileInputStream("configuration.properties")){
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValor(String clave) {
        String valor = props.getProperty(clave);
        if(valor!=null) {
            return valor;
        }
        throw new RuntimeException("La clave solicitada en configuration.properties no está disponible");
    }

    
}
