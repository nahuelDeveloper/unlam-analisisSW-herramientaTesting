package principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Window.Type;

public class TrianguloFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldA;
	private JTextField textFieldB;
	private JTextField textFieldC;
	private JLabel lblSalida;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrianguloFrame frame = new TrianguloFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TrianguloFrame() {
		setResizable(false);
		setTitle("Verificador de Tri\u00E1ngulo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 276, 291);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Lado A");
		lblNewLabel.setBounds(61, 29, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Lado B");
		lblNewLabel_1.setBounds(61, 67, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Lado C");
		lblNewLabel_2.setBounds(61, 103, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		textFieldA = new JTextField();
		textFieldA.setToolTipText("Ingrese un lado del tri\u00E1ngulo (mayor que cero)");
		textFieldA.setBounds(117, 26, 86, 20);
		contentPane.add(textFieldA);
		textFieldA.setColumns(10);
		
		
		textFieldB = new JTextField();
		textFieldB.setToolTipText("Ingrese un lado del tri\u00E1ngulo (mayor que cero)");
		textFieldB.setBounds(117, 64, 86, 20);
		contentPane.add(textFieldB);
		textFieldB.setColumns(10);
		
		textFieldC = new JTextField();
		textFieldC.setToolTipText("Ingrese un lado del tri\u00E1ngulo (mayor que cero)");
		textFieldC.setBounds(117, 100, 86, 20);
		contentPane.add(textFieldC);
		textFieldC.setColumns(2);
		
		lblSalida = new JLabel("");
		lblSalida.setBounds(61, 199, 157, 30);
		contentPane.add(lblSalida);
		
		
		JButton btnVerificar = new JButton("Verificar");
		btnVerificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblSalida.setText("");
				
				
				//Verifico que los textField hayan sido completados
				if(textFieldA.getText().equals("") || textFieldB.getText().equals("") || textFieldC.getText().equals("")){
					
					JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
					return;
				}
				
			
				
				int ladoA,ladoB,ladoC;
		try {
			
			//Hago el pasaje de String a int
				ladoA = Integer.parseInt(textFieldA.getText());
				ladoB = Integer.parseInt(textFieldB.getText());
				ladoC = Integer.parseInt(textFieldC.getText());
				
				//Verifico que los valores sean mayores que cero
				if(ladoA<=0 || ladoB <=0 || ladoC<=0){
					
					JOptionPane.showMessageDialog(null, "Lados deben ser mayores que cero");
					return;
				}
				
				//Verifico que se cumplan las siguientes condiciones de un triángulo:
				// Cada lado debe ser menor que la suma de los otros dos y mayor que la diferencia entre los otros dos
				if((ladoA > ladoB + ladoC) || (ladoB > ladoA + ladoC) || (ladoC > ladoA + ladoB)){
					
					JOptionPane.showMessageDialog(null, "No se puede formar triángulo");
					return;
				}
				
				if((ladoA < ladoB - ladoC) || (ladoB < ladoA - ladoC) || (ladoC < ladoA - ladoB)){
					
					JOptionPane.showMessageDialog(null, "No se puede formar triángulo");
					return;
				}
			
		
				//Verifico qué tipo de triángulo es.
				if(ladoA == ladoB && ladoA==ladoC){
					
					lblSalida.setText("Triángulo Equilátero");
				}
				else if(ladoA != ladoB && ladoB!=ladoC && ladoA!=ladoC){
					
					lblSalida.setText("Triángulo Escaleno");
				}
					else {
						
						lblSalida.setText("Triángulo Isósceles");
					}
				
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Lado posee valor inesperado. \n" +
					"							 Verifique que no se haya ingresado: letras, números decimales, o números elevados");
			lblSalida.setText("");
			System.out.println(e2.toString());
		}
			}
		
		
		});
		btnVerificar.setBounds(90, 151, 89, 23);
		contentPane.add(btnVerificar);
	}
}
