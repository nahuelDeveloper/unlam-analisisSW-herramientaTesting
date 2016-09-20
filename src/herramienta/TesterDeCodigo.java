package herramienta;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class TesterDeCodigo extends javax.swing.JFrame {

    private int cantLineas=0;
	private int cantComent=0;
	private int cantComentCod=0;
	private int cantCod=0;
	private int contFn=0;
        private int cantVar=0;
	private String showfn;
        private int numeroCiclomatico=0;
        private ArrayList<String> listaFn;
        private File nombreArchivo;
        private String nombreClase;
        private Halstead hal;
       
    public TesterDeCodigo() {
        
        initComponents();
    }
    
	
    public void analizarCodigo() {
		
	FileReader fr = null;
	BufferedReader br = null;
        listaFn = new ArrayList<String>();
	
	 nombreArchivo = fchooser.getSelectedFile();
         File arch = nombreArchivo;
	 try {
		fr = new FileReader(arch);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	 br = new BufferedReader(fr);
	 
	 try {
		 String linea = new String();
		 String comentario = new String();
		 showfn=new String();
		 
		 linea = br.readLine();
		 while(linea != null){
			 if(linea.contains("//")){														//Si es comentario
				 comentario=linea.substring(0,2);
				 
				 if(comentario.equals("//"))
					 cantComent++;
				 else{
					 if(linea.contains("if") && linea.contains("(") && linea.contains(")") && 
                                            !linea.contains("private") && !linea.contains("public") && !linea.contains(";"))
                                                numeroCiclomatico++;
                                         cantComentCod++;
				 	 obtenerFunciones(linea);
				 	}
				 			 
				 cantLineas++;
			 }
			 else{																			//Si no es comentario
				 if(! linea.equals("")){
					 cantLineas++;
                                         if(linea.contains("if") && linea.contains("(") && linea.contains(")") && 
                                            !linea.contains("private") && !linea.contains("public") && !linea.contains(";"))
                                                numeroCiclomatico++;
					 obtenerFunciones(linea);
				 }
				
             }
			 linea = br.readLine();
                         
		}
                 
                 numeroCiclomatico++;
		 cantCod = cantLineas - cantComent;
                 txta_salida.append("La cantidad de lineas comentadas es de: " + cantComent + "\n\n");
                 txta_salida.append("La cantidad de lineas con comentarios y codigo es de: " + cantComentCod + "\n\n");
                 txta_salida.append("La cantidad de lineas es de: " + cantLineas + "\n\n");
                 txta_salida.append("La cantidad de lineas con codigo es de: " + cantCod + "\n\n");
                 txta_salida.append("El porcentaje de lineas comentadas es: " + (cantComent*100)/cantLineas +"%"+ "\n\n");
                 txta_salida.append("El Numero Ciclomatico del codigo es: " + numeroCiclomatico + "\n\n");
                 txta_salida.append("La cantidad de funciones es: " + contFn + "\n\n");
                 txta_salida.append("Las funciones de este codigo son: " + "\n\n" + showfn);
                 
               
                 
                
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		if(br!=null)
			try {
                		fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
                }
		 
		
	}
	
	public void obtenerFunciones(String linea){
		String campos[],campos2[],campos3[];
		
		if( (linea.contains("private") || linea.contains("public") || linea.contains("protected") ) && linea.contains("{") && !linea.contains("class") ){
			contFn++;
			campos = linea.split("\\{");
                        String aux = campos[0];
                        if(!linea.contains("()")){
                            campos2 = aux.split("\\(");
                            campos3 = campos2[0].split(" ");
                            listaFn.add(campos3[campos3.length-1]);
                            showfn = showfn.concat(campos3[campos3.length-1]+"\n");
                            cb_Funciones.addItem(campos3[campos3.length-1]);
                        }
                        else{
                            campos3 = aux.split(" ");
                            listaFn.add(campos3[campos3.length-1]);
                            showfn = showfn.concat(campos3[campos3.length-1]+"\n");
                            cb_Funciones.addItem(campos3[campos3.length-1]);
                        }
			
                        
                        
		}
                if(linea.contains("class")){
                    campos = linea.split(" ");
                    int i=0;
                    while(!campos[i].equals("class")){
                        i++;
                    }
                    nombreClase = campos[i+1];
                    
                }
	}
        
 public void analizarFuncion(String item){
        int fanin=0;
        int fanout=0;
        int contLlave=0;
     
        FileReader fr = null;
	BufferedReader br = null;
        
	
	 nombreArchivo = fchooser.getSelectedFile();
         File arch = nombreArchivo;
	 try {
		fr = new FileReader(arch);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	 br = new BufferedReader(fr);
	 
	 try {
		 String linea = new String();
                 linea = br.readLine();
		 while(linea !=null){
                     if(linea.contains(item) && !linea.contains("private") && !linea.contains("public") && !linea.contains("protected") && linea.contains("(")){
                            fanin++;
                     }
                 
                     if(linea.contains(item) && (linea.contains("private") || linea.contains("public") || linea.contains("protected"))){
                          contLlave++;
                          linea = br.readLine();
                           while(contLlave!=0){
                                for (String variable : listaFn) {
                                    if(linea.contains(variable) && linea.contains("(")){
                                        if(linea.contains(nombreClase+".")){
                                            if(!variable.equals(nombreClase))
                                                fanout++;
                                        }
                                        else
                                            fanout++;
                                    }
                           }
                           if(linea.contains("{"))
                              contLlave++;
                           if(linea.contains("}"))
                              contLlave--;
                           if(contLlave!=0)
                               linea = br.readLine();
                           }
                     }
                     
                      linea = br.readLine();
                 }
                 
           txt_fanin.setText(Integer.toString(fanin));
           txt_fanout.setText(Integer.toString(fanout));
                 
                
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		if(br!=null)
			try {
                		fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
                }
		 
		
	}
         
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fchooser = new javax.swing.JFileChooser();
        lab_Titulo = new javax.swing.JLabel();
        bt_buscar = new javax.swing.JButton();
        txt_archivo = new javax.swing.JTextField();
        bt_analizar = new javax.swing.JButton();
        jScrollPane_principal = new javax.swing.JScrollPane();
        txta_salida = new javax.swing.JTextArea();
        cb_Funciones = new javax.swing.JComboBox();
        lb_fanout = new javax.swing.JLabel();
        lb_fanin = new javax.swing.JLabel();
        txt_fanout = new javax.swing.JTextField();
        txt_fanin = new javax.swing.JTextField();
        bt_halstead = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lab_Titulo.setText("Tester de Código fuente");

        bt_buscar.setText("Buscar Archivo");
        bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_buscarActionPerformed(evt);
            }
        });

        txt_archivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_archivoActionPerformed(evt);
            }
        });

        bt_analizar.setText("Analizar");
        bt_analizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_analizarActionPerformed(evt);
            }
        });

        txta_salida.setColumns(20);
        txta_salida.setRows(5);
        jScrollPane_principal.setViewportView(txta_salida);

        cb_Funciones.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cb_Funciones.setToolTipText("");
        cb_Funciones.setName("Seleccione una funcion..."); // NOI18N
        cb_Funciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_FuncionesActionPerformed(evt);
            }
        });

        lb_fanout.setText("FAN OUT");

        lb_fanin.setText("FAN IN");

        txt_fanin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_faninActionPerformed(evt);
            }
        });

        bt_halstead.setText("Halstead");
        bt_halstead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_halsteadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txt_archivo, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(bt_analizar)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bt_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cb_Funciones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_fanout)
                            .addComponent(lb_fanin)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(bt_halstead)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_fanout, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                                    .addComponent(txt_fanin))))
                        .addGap(37, 37, 37))))
            .addGroup(layout.createSequentialGroup()
                .addGap(301, 301, 301)
                .addComponent(lab_Titulo)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lab_Titulo)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_archivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_analizar)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cb_Funciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(lb_fanin)
                        .addGap(30, 30, 30)
                        .addComponent(txt_fanin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(lb_fanout)
                        .addGap(26, 26, 26)
                        .addComponent(txt_fanout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_halstead)
                        .addGap(39, 39, 39))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public File getNombreArchivo() {
        return nombreArchivo;
    }

private void bt_analizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_analizarActionPerformed
    this.analizarCodigo();


}//GEN-LAST:event_bt_analizarActionPerformed

private void bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_buscarActionPerformed
    this.fchooser.showOpenDialog(this);
    txt_archivo.setText(fchooser.getSelectedFile().getPath());
    

}//GEN-LAST:event_bt_buscarActionPerformed

private void txt_archivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_archivoActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txt_archivoActionPerformed

    private void cb_FuncionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_FuncionesActionPerformed
       
        analizarFuncion(this.cb_Funciones.getSelectedItem().toString());
    }//GEN-LAST:event_cb_FuncionesActionPerformed

    private void txt_faninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_faninActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_faninActionPerformed

    private void bt_halsteadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_halsteadActionPerformed
        try {
            hal=new Halstead(this);
        } catch (IOException ex) {
            Logger.getLogger(TesterDeCodigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        hal.setVisible(true);
        
    }//GEN-LAST:event_bt_halsteadActionPerformed

    
    public static void main(String args[]) {
            
        
        TesterDeCodigo t1 = new TesterDeCodigo();
         try {
	            JFrame.setDefaultLookAndFeelDecorated(true);
	            JDialog.setDefaultLookAndFeelDecorated(true);
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TesterDeCodigo().setVisible(true);
            }
        });
        
        
    
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_analizar;
    private javax.swing.JButton bt_buscar;
    private javax.swing.JButton bt_halstead;
    private javax.swing.JComboBox cb_Funciones;
    private javax.swing.JFileChooser fchooser;
    private javax.swing.JScrollPane jScrollPane_principal;
    private javax.swing.JLabel lab_Titulo;
    private javax.swing.JLabel lb_fanin;
    private javax.swing.JLabel lb_fanout;
    private javax.swing.JTextField txt_archivo;
    private javax.swing.JTextField txt_fanin;
    private javax.swing.JTextField txt_fanout;
    private javax.swing.JTextArea txta_salida;
    // End of variables declaration//GEN-END:variables
}

