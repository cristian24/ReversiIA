package ia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class Archivo {
	
	private String ruta;
	private static final String RUTA_DEFAULT = "src/ia/default.txt";
	private File archivo;
	
	public Archivo()
	{
		setRuta(RUTA_DEFAULT);
	}
	
	public String getRuta(){
		return ruta;
	}
	
	public void setRuta(String ruta)
	{
		this.ruta = ruta;
	}
	
	/**
	 * Retorna un archivo de texto valido, si este es null
	 * se pasa el archivo de texto por defecto valido.
	 * @return
	 */
	public File getFile(){
		if(archivo == null)
		{
			archivo = new File(getRuta());
		}
		return archivo;
	}
	
	public void setFile(File file){
		archivo = file;
	}
	
	public String obtenerTurno()
	{
		File archivo = getFile();		
        BufferedReader buffer;
        String linea = null;
		try {
			buffer = new BufferedReader(new FileReader(archivo));			
			linea = buffer.readLine();	        
	        buffer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(linea == null)
			linea = "1";
		return linea;
	}

	/**
	 * Convierte un archivo de texto valido, en una matriz
	 * @return Array bidimencional (tablero)
	 * @throws IOException
	 */
	public int[][] convertMatriz() throws IOException {
		
		File archivo = getFile();		
		int[][] matriz = new int[8][8];
        BufferedReader buffer = new BufferedReader(new FileReader(archivo));
        int Dimencion = 0;
        String Linea = buffer.readLine();
        int contador  = 0;
        while(Linea != null && Dimencion < 8)
        {
        	contador++;
        	if(contador > 1)
        	{
        		String[] split = Linea.split(" ");            
                for(int x=0; x < 8; x++)
                {
                	String contenido = split[x];            	
                	if(contenido.equals("0") || contenido.equals("1") || contenido.equals("2"))
                	{
                		matriz[x][Dimencion] = Integer.valueOf(contenido);
                	}else
                	{
                		matriz[x][Dimencion] = Integer.valueOf("0");
                	}
                }
                Dimencion++;
                       		
        	}
        	Linea = buffer.readLine();            
        }
        buffer.close();
        return matriz;
	}
		
	/**
	 * Crea y muestra una ventana para realizar la busqueda y seleccion de un archivo.
	 */
	public String elegirArchivo(){
		String nombreArchivo = "default.txt";
		JFileChooser file = new JFileChooser();
		file.setCurrentDirectory(new java.io.File("src/ia"));
		file.setDialogTitle("Elige un estado de tablero valido");
		file.setFileSelectionMode(JFileChooser.FILES_ONLY);
		file.setAcceptAllFileFilterUsed(false);
		file.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return null;
			}
			
			@Override
			public boolean accept(File f) {
				if (f.getName().toLowerCase().endsWith("txt"))
				{
					return true;
				}
				return false;
			}
		});

		if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			setFile(file.getSelectedFile());
			nombreArchivo = file.getSelectedFile().getName();
		} else {
			JOptionPane.showMessageDialog(null, "No se eligio ningun archivo valido, se jugará entonces con el archivo por defecto", "Advertencia", 2, null);
		}
		
		return nombreArchivo;
	}

}