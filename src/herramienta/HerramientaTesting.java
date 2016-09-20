package herramienta;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class HerramientaTesting {

	private JFrame frmHerramientaTesting;
	
	private JTextField lineasCodTot;
	private JTextField lineasCodComent;
	private JTextField porcentLineas;
	private JTextField complejidadC;
	private JTextField fanIn;
	private JTextField fanOut;
	private JTextField HLongitud;
	private JTextField HVolumen;
	
	private JMenuItem mntmElegirCarpeta;
	
	private JList ArchivoList;
	private DefaultListModel  listModel;
	private DefaultListModel claseModel;
	private DefaultListModel metodoModel;
	private JList ClaseList ;
	private JList MetodoList ;
	private JTextArea CodigoText;
	
	//Analizar codigo
	private int cantLineas=0;
	private int cantComent=0;
	private int cantComentCod=0;
	private int cantCod=0;
	private int contFn=0;
    private int cantVar=0;
	private String showfn;
    private int numeroCiclomatico=0;
    private ArrayList<String> listaFn;
    private File archivo;
    String nombreArchivo;
    private String nombreClase;
    private Halstead hal;
	
    //Halstead
    private File archivoOperandos=null;
    private ArrayList<Operador> listaOp;
    private int N,n,n1=0,n2=0,N1=0,N2=0;
    private double volumen,esfuerzo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HerramientaTesting window = new HerramientaTesting();
					window.frmHerramientaTesting.setVisible(true);
				} catch (Exception e5) {
					e5.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HerramientaTesting() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHerramientaTesting = new JFrame();
		frmHerramientaTesting.setResizable(false);
		frmHerramientaTesting.setTitle("Herramienta Testing");
		frmHerramientaTesting.setBounds(100, 100, 777, 709);
		frmHerramientaTesting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHerramientaTesting.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 881, 21);
		frmHerramientaTesting.getContentPane().add(menuBar);
		
		JMenu mnMenuBar = new JMenu("An\u00E1lisis");
		menuBar.add(mnMenuBar);
		
		mntmElegirCarpeta = new JMenuItem("Elegir carpeta ... ");
		
		mntmElegirCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArchivoList.clearSelection();
				JFileChooser  chooser = new JFileChooser ();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //PERMITE SELECCIONAR SOLO DIRECTORIOS
				
				// NAHU: Con esto permite elegir archivos, pero al elegir un .java no muestra nada por pantalla
//				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				//chooser.setCurrentDirectory(File.listRoots()[0]);
				int i=0,cont=0;
				
				FileFilter filtro=new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						String arch=pathname.toString();
						if(arch.endsWith(".java"))
							return true;
						else
							return false;
						// TODO Auto-generated method stub
					}
				};
				
				if ((chooser.showOpenDialog(mntmElegirCarpeta))!=JFileChooser .APPROVE_OPTION) 
					return; // Si no elige un archivo termina el método.
				
				File seleccion=chooser.getSelectedFile();
				File [] ficheros = seleccion.listFiles();
				claseModel.removeAllElements();
				listModel.removeAllElements();
				while(ficheros.length > i)
				{
					if(filtro.accept(ficheros[i]))
					{
						listModel.addElement(ficheros[i]);
						cont++;
					}
						
					i++;
				}

				if(cont == 0)	
				{
					listModel.addElement("Esta carpeta no contiene archivos .java");
				}

			}
		});
		
		mnMenuBar.add(mntmElegirCarpeta);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		mnMenuBar.add(mntmSalir);
		
		JLabel lblSeleccioneUnArchivo = new JLabel("Seleccione un archivo de la lista:");
		lblSeleccioneUnArchivo.setForeground(new Color(0, 100, 0));
		lblSeleccioneUnArchivo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSeleccioneUnArchivo.setBounds(10, 32, 382, 21);
		frmHerramientaTesting.getContentPane().add(lblSeleccioneUnArchivo);
		JScrollPane scrollPaneSeleccioneUnArchivo = new JScrollPane();
		scrollPaneSeleccioneUnArchivo.setBounds(10, 64, 540, 136);
		frmHerramientaTesting.getContentPane().add(scrollPaneSeleccioneUnArchivo);
		ArchivoList = new JList(listModel);
		scrollPaneSeleccioneUnArchivo.setViewportView(ArchivoList);
		
		ArchivoList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) { //CON ESTA ACCION SE VA A SELECCIONAR UN ARCHIVO DE LA LISTA
			
				try
				{
					nombreArchivo=ArchivoList.getSelectedValue().toString();
					if(!nombreArchivo.equals("Esta carpeta no contiene archivos .java"))
					{				
						String cad=nombreArchivo.substring(nombreArchivo.lastIndexOf("\\")+1, nombreArchivo.length()-5);
						claseModel.removeAllElements();
						metodoModel.removeAllElements();
						//claseModel.addElement(cad);
						analizarCodigo();		//Realiza el analisis completo del código
					}
				}catch(Exception e)
				{
					
				}
			}
		});
		
		ArchivoList.setBorder(new LineBorder(new Color(0, 0, 0)));
		ArchivoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblSeleccioneUnaClase = new JLabel("Seleccione una clase de la lista:");
		lblSeleccioneUnaClase.setForeground(new Color(0, 100, 0));
		lblSeleccioneUnaClase.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSeleccioneUnaClase.setBounds(10, 211, 210, 21);
		frmHerramientaTesting.getContentPane().add(lblSeleccioneUnaClase);
		JScrollPane scrollPaneSeleccioneUnaClase = new JScrollPane();
		scrollPaneSeleccioneUnaClase.setBounds(10, 243, 258, 152);
		frmHerramientaTesting.getContentPane().add(scrollPaneSeleccioneUnaClase);
		ClaseList = new JList(claseModel);
		ClaseList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {//con esta funcion se selecciona una clase de la lista
				FileReader fr = null;
				BufferedReader br = null;
				String linea = new String();
				metodoModel.removeAllElements();
				
				try {
					fr = new FileReader(nombreArchivo);
				} catch (FileNotFoundException e5) {
					e5.printStackTrace();
				}
				 br = new BufferedReader(fr);
				 
				try
				{
					
					linea=br.readLine();
					while(linea!=null)
					{	
						obtenerFunciones(linea);
						linea=br.readLine();
					}
					
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				finally{
					try{
						if(null != fr)
							fr.close();
					}
					catch(Exception e2)
					{
						e2.printStackTrace();
					}
				}
				
			}
		});
		scrollPaneSeleccioneUnaClase.setViewportView(ClaseList);
		ClaseList.setBorder(new LineBorder(new Color(0, 0, 0)));
		ClaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblSeleccioneUnMetodo = new JLabel("Seleccione un metodo de la lista:");
		lblSeleccioneUnMetodo.setForeground(new Color(0, 100, 0));
		lblSeleccioneUnMetodo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSeleccioneUnMetodo.setBounds(292, 211, 210, 21);
		frmHerramientaTesting.getContentPane().add(lblSeleccioneUnMetodo);
		
		
		JScrollPane scrollPaneSeleccioneUnMetodo = new JScrollPane();
		scrollPaneSeleccioneUnMetodo.setBounds(292, 243, 258, 152);
		frmHerramientaTesting.getContentPane().add(scrollPaneSeleccioneUnMetodo);
		MetodoList = new JList(metodoModel);
		MetodoList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {//con esta acción se selecciona un método de la lista
				  analizarFuncion(MetodoList.getSelectedValue().toString());
				
			}
		});
		scrollPaneSeleccioneUnMetodo.setViewportView(MetodoList);
		MetodoList.setBorder(new LineBorder(new Color(0, 0, 0)));
		MetodoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblCodigoDelMetodo = new JLabel("Codigo del metodo seleccionado:");
		lblCodigoDelMetodo.setForeground(new Color(0, 100, 0));
		lblCodigoDelMetodo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCodigoDelMetodo.setBounds(10, 410, 451, 21);
		frmHerramientaTesting.getContentPane().add(lblCodigoDelMetodo);
		
		JScrollPane scrollPaneCodigoDelMetodo = new JScrollPane();
		scrollPaneCodigoDelMetodo.setBounds(10, 435, 760, 244);
		frmHerramientaTesting.getContentPane().add(scrollPaneCodigoDelMetodo);
		
		CodigoText = new JTextArea();
		CodigoText.setBorder(new LineBorder(new Color(0, 0, 0)));
		CodigoText.setEditable(false);
		scrollPaneCodigoDelMetodo.setViewportView(CodigoText);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Analisis del metodo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 100, 0)));
		layeredPane.setBounds(560, 32, 210, 399);
		frmHerramientaTesting.getContentPane().add(layeredPane);
		
		JLabel lblLineasDeCodigo = new JLabel("Lineas de codigo totales:");
		lblLineasDeCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasDeCodigo.setBounds(10, 24, 190, 14);
		layeredPane.add(lblLineasDeCodigo);
		
		lineasCodTot = new JTextField();
		lineasCodTot.setHorizontalAlignment(SwingConstants.CENTER);
		lineasCodTot.setBorder(new EmptyBorder(0, 0, 0, 0));
		lineasCodTot.setEditable(false);
		lineasCodTot.setForeground(new Color(0, 100, 0));
		lineasCodTot.setFont(new Font("Tahoma", Font.BOLD, 12));
		lineasCodTot.setDisabledTextColor(new Color(0, 100, 0));
		lineasCodTot.setBounds(66, 38, 86, 20);
		layeredPane.add(lineasCodTot);
		lineasCodTot.setColumns(10);
		
		JLabel lblLineasDeCodigoComentadas = new JLabel("Lineas de codigo comentadas");
		lblLineasDeCodigoComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblLineasDeCodigoComentadas.setBounds(10, 69, 190, 14);
		layeredPane.add(lblLineasDeCodigoComentadas);
		
		lineasCodComent = new JTextField();
		lineasCodComent.setHorizontalAlignment(SwingConstants.CENTER);
		lineasCodComent.setBorder(new EmptyBorder(0, 0, 0, 0));
		lineasCodComent.setEditable(false);
		lineasCodComent.setForeground(new Color(0, 100, 0));
		lineasCodComent.setFont(new Font("Tahoma", Font.BOLD, 12));
		lineasCodComent.setDisabledTextColor(new Color(0, 100, 0));
		lineasCodComent.setColumns(10);
		lineasCodComent.setBounds(66, 83, 86, 20);
		layeredPane.add(lineasCodComent);
		
		JLabel lblPorcentajeDeLineas = new JLabel("Porcentaje de lineas de codigo ");
		lblPorcentajeDeLineas.setToolTipText("");
		lblPorcentajeDeLineas.setHorizontalAlignment(SwingConstants.CENTER);
		lblPorcentajeDeLineas.setBounds(10, 116, 190, 14);
		layeredPane.add(lblPorcentajeDeLineas);
		
		JLabel lblComentadas = new JLabel("comentadas");
		lblComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblComentadas.setBounds(10, 128, 190, 14);
		layeredPane.add(lblComentadas);
		
		porcentLineas = new JTextField();
		porcentLineas.setHorizontalAlignment(SwingConstants.CENTER);
		porcentLineas.setBorder(new EmptyBorder(0, 0, 0, 0));
		porcentLineas.setEditable(false);
		porcentLineas.setForeground(new Color(0, 100, 0));
		porcentLineas.setFont(new Font("Tahoma", Font.BOLD, 12));
		porcentLineas.setDisabledTextColor(new Color(0, 100, 0));
		porcentLineas.setColumns(10);
		porcentLineas.setBounds(66, 141, 86, 20);
		layeredPane.add(porcentLineas);
		
		JLabel lblComplejidadCiclomtica = new JLabel("Complejidad Ciclom\u00E1tica");
		lblComplejidadCiclomtica.setHorizontalAlignment(SwingConstants.CENTER);
		lblComplejidadCiclomtica.setBounds(10, 172, 190, 14);
		layeredPane.add(lblComplejidadCiclomtica);
		
		complejidadC = new JTextField();
		complejidadC.setHorizontalAlignment(SwingConstants.CENTER);
		complejidadC.setBorder(new EmptyBorder(0, 0, 0, 0));
		complejidadC.setEditable(false);
		complejidadC.setForeground(new Color(0, 100, 0));
		complejidadC.setFont(new Font("Tahoma", Font.BOLD, 12));
		complejidadC.setDisabledTextColor(new Color(0, 100, 0));
		complejidadC.setColumns(10);
		complejidadC.setBounds(66, 186, 86, 20);
		layeredPane.add(complejidadC);
		
		JLabel lblFanIn = new JLabel("Fan In");
		lblFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanIn.setBounds(10, 217, 190, 14);
		layeredPane.add(lblFanIn);
		
		fanIn = new JTextField();
		fanIn.setHorizontalAlignment(SwingConstants.CENTER);
		fanIn.setBorder(new EmptyBorder(0, 0, 0, 0));
		fanIn.setEditable(false);
		fanIn.setForeground(new Color(0, 100, 0));
		fanIn.setFont(new Font("Tahoma", Font.BOLD, 12));
		fanIn.setDisabledTextColor(new Color(0, 100, 0));
		fanIn.setColumns(10);
		fanIn.setBounds(66, 231, 86, 20);
		layeredPane.add(fanIn);
		
		JLabel lblFanOut = new JLabel("Fan Out");
		lblFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblFanOut.setBounds(10, 263, 190, 14);
		layeredPane.add(lblFanOut);
		
		fanOut = new JTextField();
		fanOut.setHorizontalAlignment(SwingConstants.CENTER);
		fanOut.setBorder(new EmptyBorder(0, 0, 0, 0));
		fanOut.setEditable(false);
		fanOut.setForeground(new Color(0, 100, 0));
		fanOut.setFont(new Font("Tahoma", Font.BOLD, 12));
		fanOut.setDisabledTextColor(new Color(0, 100, 0));
		fanOut.setColumns(10);
		fanOut.setBounds(66, 277, 86, 20);
		layeredPane.add(fanOut);
		
		JLabel lblHalsteadLongitud = new JLabel("Halstead longitud");
		lblHalsteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		lblHalsteadLongitud.setBounds(10, 310, 190, 14);
		layeredPane.add(lblHalsteadLongitud);
		
		HLongitud = new JTextField();
		HLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		HLongitud.setBorder(new EmptyBorder(0, 0, 0, 0));
		HLongitud.setEditable(false);
		HLongitud.setForeground(new Color(0, 100, 0));
		HLongitud.setFont(new Font("Tahoma", Font.BOLD, 12));
		HLongitud.setDisabledTextColor(new Color(0, 100, 0));
		HLongitud.setColumns(10);
		HLongitud.setBounds(66, 324, 86, 20);
		layeredPane.add(HLongitud);
		
		JLabel lblHalsteadVolumen = new JLabel("Halstead volumen");
		lblHalsteadVolumen.setBounds(10, 356, 190, 14);
		layeredPane.add(lblHalsteadVolumen);
		lblHalsteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		
		HVolumen = new JTextField();
		HVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		HVolumen.setBorder(new EmptyBorder(0, 0, 0, 0));
		HVolumen.setEditable(false);
		HVolumen.setForeground(new Color(0, 100, 0));
		HVolumen.setFont(new Font("Tahoma", Font.BOLD, 12));
		HVolumen.setDisabledTextColor(new Color(0, 100, 0));
		HVolumen.setBounds(66, 370, 86, 20);
		layeredPane.add(HVolumen);
		HVolumen.setColumns(10);
		
		listModel = new DefaultListModel();
		
		
		claseModel=new DefaultListModel();
		
		metodoModel = new DefaultListModel();
	}
	
	public void analizarCodigo() {//esta función analiza tódo el código del programa
		
		FileReader fr = null;
		BufferedReader br = null;
	    listaFn = new ArrayList<String>();
		CodigoText.setText(null);
		cantLineas=0;
		cantComent=0;
		cantComentCod=0;
		cantCod=0;
		contFn=0;
	    cantVar=0;
		numeroCiclomatico=0;
		int cont =0;
		 try {
			fr = new FileReader(nombreArchivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 br = new BufferedReader(fr);
		 
		 try {
			 String linea = new String();
			 String lineaLimpia = new String();
			 String comentario = new String();
			 showfn=new String();
			 
			 linea = br.readLine();
			 while(linea != null){
				 
				 CodigoText.setText(CodigoText.getText() + linea + "\n");
				 lineaLimpia = linea.trim();
//				 CodigoText.setText(CodigoText.getText() + lineaLimpia + "\n");
				 obtenerClases(linea);
				 if(! lineaLimpia.equals("")){
					 cantLineas++;
					 
					 if((linea.contains("if") || linea.contains("while") || linea.contains("for")) && linea.contains("(") && linea.contains(")") && 
                                            !linea.contains("private") && !linea.contains("public") && !linea.contains(";")){

						 //CodigoText.append("TestA: " + linea + "\n");
						 if(linea.contains("&&")){
							//CodigoText.append("TestB: " + linea + "\n");
							 cont = 1 + (linea.length() - linea.replace("&&", "").length()) / 2;
							 if(linea.contains("||"))
								 cont += 1 + (linea.length() - linea.replace("||", "").length()) / 2;
							 numeroCiclomatico += cont; 
						 }
						 else{
							 //CodigoText.append("TestC: " + linea + "\n");
							 if(linea.contains("||")){
								cont = 1 + (linea.length() - linea.replace("||", "").length()) / 2;
						 		numeroCiclomatico += cont;
							 }
							 else
								numeroCiclomatico++;
						 }
					 }
					//Evaluo si tiene algún tipo de comentario
					 if(linea.contains("/*")){
						 cantComent++;
						 while(!linea.contains("*/")){
							 linea = br.readLine();
							 cantComent++;
							 cantLineas++;
						 }
						 
					 }
					 else
						 if(linea.contains("//"))		
							 cantComent++;
					 //obtenerFunciones(linea);
				 }
				 else
					 cantLineas++;
				
             
				 linea = br.readLine();
	                         
			}
	                 
	         numeroCiclomatico++;
			 cantCod = cantLineas - cantComent;
			 
			 lineasCodComent.setText(Integer.toString(cantComent));
			 lineasCodTot.setText(Integer.toString(cantCod));
			 complejidadC.setText(Integer.toString(numeroCiclomatico));
			 porcentLineas.setText(Integer.toString((cantComent*100)/cantLineas)+"%");
			 //numeroCiclomatico=0;
			 Halstead();
			        
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (br!=null)
				try {
	                		fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	                }
			 
			
		}
	
	public void obtenerFunciones(String linea){ //esta función encuentra los métodos de la clase
		String campos[],campos2[],campos3[];
		try{
		if ( (linea.contains("private") || linea.contains("public") || linea.contains("protected") ) && linea.contains("{") && !linea.contains("class") && !linea.contains("run") && !linea.contains("actionPerformed") ){
			contFn++;
			String aux1 = linea.trim();
			campos = aux1.split("\\{");
			String aux = campos[0];
            if(!linea.contains("()")){
                campos2 = aux.split("\\(");
                campos3 = campos2[0].split(" ");
                //CodigoText.append("\t"+"Test: " + campos3[campos3.length-1]+ "\n");
                listaFn.add(campos3[campos3.length-1]);
                //showfn = showfn.concat(campos3[campos3.length-1]+"\n");
                //cb_Funciones.addItem(campos3[campos3.length-1]);
                metodoModel.addElement(campos3[campos3.length-1]+"\n");	//ver
            }
            else{
            	campos2 = aux.split("\\(");
            	campos3 = campos2[0].split(" ");
                listaFn.add(campos3[campos3.length-1]);
                //showfn = showfn.concat(campos3[campos3.length-1]+"\n");
                metodoModel.addElement(campos3[campos3.length-1]+"\n");	//ver
                //cb_Funciones.addItem(campos3[campos3.length-1]);
            }
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void obtenerClases(String linea){ //encuentra las clases en el código		
		String campos[];
		try{
		if (linea.contains("class")){
            campos = linea.split(" ");
            int i=0;
            while(!campos[i].equals("class")){
                i++;
            }
            //Reemplazo
            //nombreClase = campos[i+1];
            //por
            claseModel.addElement(campos[i+1]);
        }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void analizarFuncion(String item){ //será llamada al seleccionar un método y hará todo el análisis sólo del método seleccionado mostrandolo por pantalla
        int fanin=0;
        int fanout=0;
        int contLlave=1;
        int cont =0;
        int numeroCiclo=0;
        String aux = item.trim();
        String linea = new String();
        FileReader fr = null;
        BufferedReader br = null;
        String campos[];
        
        cantLineas=0;
		cantComent=0;
		cantComentCod=0;
		cantCod=0;
		contFn=0;
	    cantVar=0;
		numeroCiclomatico=0;
		CodigoText.setText(null);
       try{
    	   		archivoOperandos = new File("operandos.txt");
	            fr = null;
	            br = null;
	            listaOp = new ArrayList<Operador>();
	           
	            fr = new FileReader(archivoOperandos);
	            br = new BufferedReader(fr);
	            linea = new String();
	            Operador aux1;
	            linea=br.readLine();
	            while(linea != null){
	                aux1 = new Operador(linea);
	                listaOp.add(aux1);
	                linea = br.readLine();
	            }
	            fr.close();
	            br.close();   
       }catch (IOException e) {
			e.printStackTrace();
       }
        
	 try {
			fr = new FileReader(nombreArchivo);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		 br = new BufferedReader(fr);
		
	 try {
		
         linea = br.readLine();
		 while(linea !=null && contLlave!=0 ){
			
			 		//CodigoText.append("\t"+"Test: 222" + aux + "222\n");
                     if (linea.contains(aux) && !linea.contains("private") && !linea.contains("public") && !linea.contains("protected") && linea.contains("(")){
                            fanin++;
                          
                     }
                 
                     if (linea.contains(aux.toString()) && (linea.contains("private") || linea.contains("public") || linea.contains("protected"))){
                    	 CodigoText.setText(CodigoText.getText() + linea + "\n");
                    	 //contLlave++;
                          linea = br.readLine();
                           while(contLlave!=0 && linea !=null){
                        	   
                        	   CodigoText.setText(CodigoText.getText() + linea + "\n");
                                for (String variable : listaFn) {
                                	//CodigoText.append("\t"+"Tes1t: " + variable + "\n");
                                    if(linea.contains(variable.toString()) && linea.contains("(")){
                                        if(linea.contains(aux+".")){
                                            if(!variable.equals(aux))
                                                fanout++;
                                        }
                                        else
                                            fanout++;
                                    }
                                }
                                
	                           //   if(!linea.equals("")){
	           					 cantLineas++;
	           					 
	           					 if((linea.contains("if ")|| linea.contains("if(") || linea.contains("while") || linea.contains("for")) && linea.contains("(") && linea.contains(")") && 
	                                                       !linea.contains("private") && !linea.contains("public") && !linea.contains(";")){
	
	
	           						 if(linea.contains("&&")){
	           							 cont = 1 + (linea.length() - linea.replace("&&", "").length()) / 2;
	           							 if(linea.contains("||"))
	           								 cont += 1 + (linea.length() - linea.replace("||", "").length()) / 2;
	           							 numeroCiclo += cont;
	           							
	           						 }
	           						 else
	           							 if(linea.contains("||")){
	           								cont = 1 + (linea.length() - linea.replace("||", "").length()) / 2;
	           						 		numeroCiclo += cont;
	           						 	CodigoText.setText(CodigoText.getText() + "Chris + ciclo: "+ Integer.toString(numeroCiclo) + "Cont: "+ Integer.toString(cont) + "\n");
	           							 }
	           							 else{
	           								numeroCiclo++;
	           								CodigoText.setText(CodigoText.getText() + "Chris + ciclo: "+ Integer.toString(numeroCiclo) + "Cont: "+ Integer.toString(cont) + "\n");
	           							}
	           					 }
	           					//Evaluo si tiene algún tipo de comentario
	           					 if(linea.contains("/*")){
	           						 cantComent++;
	           						 while(!linea.contains("*/")){
	           							 linea = br.readLine();
	           							CodigoText.setText(CodigoText.getText() + linea + "\n");
	           							 cantComent++;
	           							 cantLineas++;
	           						 }
	           						 
	           					 }
	           					 else
	           						 if(linea.contains("//"))		
	           							 cantComent++;
	           					 //obtenerFunciones(linea);
	           					
	           					for (Operador operador : listaOp) {
	            	                   if(operador.getNombre().equals("+") || operador.getNombre().equals("|") || 
	            	                           operador.getNombre().equals("*") || operador.getNombre().equals("+=")
	            	                           || operador.getNombre().equals("*=")){
	            	                               
	            	                                campos=linea.split("\\"+operador.getNombre());	            	                   
	            	                   }
	            	                   else{
	            	                       if(operador.getNombre().equals("++"))
	            	                           campos=linea.split("\\+\\+");
	            	                       else {
	            	                           if(operador.getNombre().equals("||")) {
	            	                              campos=linea.split("\\|\\|"); 
	            	                               
	            	                               }
	            	                           else
	            	                                campos=linea.split(operador.getNombre());     
	            	                       }
	                	              }
	                	                   operador.contar(campos.length-1);	                    	                      
	                	            }
	               					 
	               				 //}
	               				 //else
	               				//	 cantLineas++;
	               				
	                            
	               			//	 linea = br.readLine();
	               			//	CodigoText.setText(CodigoText.getText() + linea + "\n");
	               	                         	               				               	                 	               	       
	               	            ////////////////////////////////////
                                
                           if(linea.contains("{"))
                              contLlave++;
                           if(linea.contains("}"))
                              contLlave--;
                           //if(contLlave!=0)
                        	   //CodigoText.setText(CodigoText.getText() + "Llaves: "+ contLlave + "\n");
                           linea = br.readLine();
                           
                     }                     
                 }
					 linea = br.readLine();
		 	}         
		 	for (Operador operator : listaOp) {
	             
		         if(operator.getContador()!=0){
		             
		             n1++;
		             N1+=operator.getContador();
		         }
	             
	         
	         }
	         N2=10;
	         n2=12;        
	         N = N1+N2;
	         HLongitud.setText(Integer.toString(N));
	         n = n1+n2;
	         volumen = N*(Math.log(n)/Math.log(2));
	         HVolumen.setText(Integer.toString(n));
	         N=N2=N1=n=n1=n2=0;
	        
	         numeroCiclo++;
			 cantCod = cantLineas - cantComent;
			 
			 lineasCodComent.setText(Integer.toString(cantComent));
			 lineasCodTot.setText(Integer.toString(cantCod));
			 complejidadC.setText(Integer.toString(numeroCiclo));
			 porcentLineas.setText(Integer.toString((cantComent*100)/cantLineas)+"%");
			 
			 fanIn.setText(Integer.toString(fanin));
			 fanOut.setText(Integer.toString(fanout));
                 
                
	}catch (IOException e1) {
		e1.printStackTrace();
	}finally{
		if(br!=null)
			try {
                		fr.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
  		
		}
	}
	
	public void Halstead() throws IOException{	// realiza el analisis de Halstead en todo el archivo con los operandos del archivo "Operandos.txt"
        try {
            archivoOperandos = new File("operandos.txt");
            FileReader fr = null;
            BufferedReader br = null;
            listaOp = new ArrayList<Operador>();
           
            fr = new FileReader(archivoOperandos);
            br = new BufferedReader(fr);
            String linea = new String();
            Operador aux;
            linea=br.readLine();
            while(linea != null){
                aux = new Operador(linea);
                listaOp.add(aux);
                linea = br.readLine();
            }
            fr.close();
            br.close();        
            ////////////////////////////////////
            fr = null;
            br = null;
                       
            fr = new FileReader(nombreArchivo);
            br = new BufferedReader(fr);
            
            linea=br.readLine();
            String campos[];
            while(linea != null){
                for (Operador operador : listaOp) {
                   if(operador.getNombre().equals("+") || operador.getNombre().equals("|") || 
                           operador.getNombre().equals("*") || operador.getNombre().equals("+=")
                           || operador.getNombre().equals("*=")){
                               
                                campos=linea.split("\\"+operador.getNombre());
                   
                   }
                   else{
                       if(operador.getNombre().equals("++"))
                           campos=linea.split("\\+\\+");
                       else {
                           if(operador.getNombre().equals("||")) {
                              campos=linea.split("\\|\\|"); 
                               
                               }
                           else
                                campos=linea.split(operador.getNombre());     
                       }
              }
                   operador.contar(campos.length-1);
                      
            }
             linea = br.readLine();        
            
      }
         for (Operador operator : listaOp) {
             
	         if(operator.getContador()!=0){
	             
	             n1++;
	             N1+=operator.getContador();
	         }
             
         
         }
         N2=20;
         n2=12;        
         N = N1+N2;
         HLongitud.setText(Integer.toString(N));
         n = n1+n2;
         volumen = N*(Math.log(n)/Math.log(2));
         HVolumen.setText(Integer.toString(n));
         
         N=N2=N1=n=n1=n2=0;
       
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Halstead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
