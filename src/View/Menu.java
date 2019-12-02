package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Label;

import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import thinning.Main;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Point;

public class Menu extends JFrame {
	private JDialog dialogo = new JDialog(this,"resultados");
	private JPanel contentPane;
	private JTextField tFile;
	private JPanel panel_1;
	private JLabel imagen;
	private Main main = new Main();
	private JLabel imagen2;
	private JLabel result;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 809, 697);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Inicio");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Resultados");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmString = new JMenuItem("String");
		mntmString.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				ventana();
			}
		});
		mnNewMenu_1.add(mntmString);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 0, 0)));
		panel.setBounds(10, 32, 773, 82);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ingrese Imagen:");
		lblNewLabel.setBounds(10, 11, 168, 31);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 17));

		tFile = new JTextField();
		tFile.setBounds(167, 17, 479, 25);
		panel.add(tFile);
		tFile.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscador();
			}
		});
		btnBuscar.setBounds(674, 16, 89, 27);
		panel.add(btnBuscar);

		JButton btnSubir = new JButton("Subir");
		
		btnSubir.setBounds(366, 53, 89, 25);
		panel.add(btnSubir);
		
		 panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 128)));
		panel_1.setBounds(10, 125, 350, 500);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		imagen = new JLabel();
		panel_1.add(imagen);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 128)));
		panel_2.setBounds(433, 125, 350, 500);
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		imagen2 = new JLabel();
		panel_2.add(imagen2, BorderLayout.CENTER);
		
		lblNewLabel_1 = new JLabel(new ImageIcon("flecha.png"));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				imagen2.setIcon(new ImageIcon(""));

				main.Otsu(tFile.getText());
				main.stentiford();
				main.sobel();
				imagen2.setIcon(new ImageIcon(main.stentiford()));
				
			}
		});
		lblNewLabel_1.setBounds(359, 286, 75, 64);
		contentPane.add(lblNewLabel_1);
		
		btnSubir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subirImage();
			}
		});
		dialogo.setLocation(new Point(500, 500));
//		dialogo.setSize(new Dimension(400, 400));
		result = new JLabel("Resultado es:");
		dialogo.setContentPane(result);
		dialogo.pack();
		dialogo.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				result.setText("");
				dialogo.setVisible(false);
			}
		
			public void windowClosed(WindowEvent e) {
				dialogo.setVisible(false);
			}
		});
	}
	private void ventana() {
		result.setText(result +"\n"+main.tesseract());
		dialogo.setVisible(true);

	}
	public void subirImage() {
		imagen.setIcon(new ImageIcon(tFile.getText()));
	}
	public void buscador() {
		JFileChooser fc = new JFileChooser();
		// Mostrar la ventana para abrir archivo y recoger la respuesta
		// En el parámetro del showOpenDialog se indica la ventana
		// al que estará asociado. Con el valor this se asocia a la
		// ventana que la abre.
		fc.setFileFilter(new FileNameExtensionFilter("JPG y PNG","jpg","png"));
		int respuesta = fc.showOpenDialog(this);
		// Comprobar si se ha pulsado Aceptar
		if (respuesta == JFileChooser.APPROVE_OPTION) {
			// Crear un objeto File con el archivo elegido
			File archivoElegido = fc.getSelectedFile();
			// Mostrar el nombre del archvivo en un campo de texto
			tFile.setText(archivoElegido.getPath());
		}
	}
}
