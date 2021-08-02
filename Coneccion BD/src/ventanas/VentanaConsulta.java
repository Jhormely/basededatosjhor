package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import conexion.Conexion;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import vo.PersonaVo;
import dao.PersonaDao;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class VentanaConsulta extends JFrame implements ActionListener {

	private JLabel labelTitulo;
	JTable miTabla1;
	JScrollPane mibarra1;
	private JButton botonAct;
	private JTextField casilla1;//ingreso de parametros
	private JTextField casilla2;
	private JTextField casilla3;
	private JTextField casilla4;
	private JTextField casilla5;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaConsulta() {
		getContentPane().setBackground(Color.PINK);
		getContentPane().setForeground(Color.BLACK);
		setSize(528, 505);
		setTitle("UNAM : Componentes JTable");
		setLocationRelativeTo(null);
		setResizable(false);
		
		inicializaComponentes();
		construirTabla();
	}

	private void construirTabla() {
		String titulos[]={ "Codigo", "Nombre", "Edad", "Profesión","Telefono" };//PARAMETROS 
		String informacion[][]=obtenerMatriz();
		
		miTabla1=new JTable(informacion,titulos);
		miTabla1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                Conexion con = new Conexion();
                try {
                    int fila = miTabla1.getSelectedRow();
                    int id = Integer.parseInt(miTabla1.getValueAt(fila, 0).toString());
                    ResultSet rs;
                    Connection conexion = con.getConnection();// realizamos la conexion 
                    PreparedStatement ps = ((Connection) conexion).prepareStatement("SELECT nombre,edad,profesion,telefono FROM persona WHERE id=?");
                    ps.setInt(1, id);
                    rs= ps.executeQuery();
                    
                    while (rs.next()) {
                        casilla1.setText(String.valueOf(id));// codigo id
                        casilla2.setText(rs.getString("nombre"));
                        casilla4.setText(rs.getString("edad"));
                        casilla3.setText(rs.getString("profesion"));
                        casilla5.setText(rs.getString("telefono"));
                    }
                } catch(SQLException e2) {
                    JOptionPane.showMessageDialog(null, e.toString());
                }
			}
		});
		mibarra1.setViewportView(miTabla1);
		
	}

	private String[][] obtenerMatriz() {
		
		PersonaDao miPersonaDao=new PersonaDao();
		ArrayList<PersonaVo>miLista=miPersonaDao.buscarUsuariosConMatriz();
		
		String matrizInfo[][]=new String[miLista.size()][5];
		
		for (int i = 0; i < miLista.size(); i++) {
			matrizInfo[i][0]=miLista.get(i).getIdPersona()+"";
			matrizInfo[i][1]=miLista.get(i).getNombrePersona()+"";
			matrizInfo[i][2]=miLista.get(i).getProfesionPersona()+"";
			matrizInfo[i][3]=miLista.get(i).getEdadPersona()+"";
			matrizInfo[i][4]=miLista.get(i).getTelefonoPersona()+"";
		}
			
		return matrizInfo;
	}

	private void inicializaComponentes() {
		getContentPane().setLayout(null);

		labelTitulo = new JLabel();
		labelTitulo.setForeground(Color.GRAY);
		labelTitulo.setBounds(64, 11, 400, 30);
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setText("CONSULTA DE PERSONAS");
		labelTitulo.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD | Font.ITALIC, 22));
		getContentPane().add(labelTitulo);
		
		mibarra1=new JScrollPane();
		mibarra1.setBounds(10, 70,498,130);
		getContentPane().add(mibarra1);
		
		botonAct = new JButton("Actualizar");
		botonAct.setFont(new Font("Tahoma", Font.BOLD, 12));
		botonAct.setBounds(401, 219, 105, 23);
		getContentPane().add(botonAct);
		
		JButton botonBorrar = new JButton("Borrar");
		botonBorrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		botonBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                Conexion con = new Conexion();
                int id = Integer.parseInt(casilla1.getText());
                
                try {
                    Connection conexion = con.getConnection();// realiza una conexion 
                    PreparedStatement ps = ((Connection) conexion).prepareStatement("DELETE FROM persona WHERE id=?");//pregunta sobre eliminar 
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                } catch(SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1.toString());
                    
                }
			}
		});
		botonBorrar.setBounds(159, 221, 105, 21);
		getContentPane().add(botonBorrar);
		
		JButton BotonIns = new JButton("Insertar");
		BotonIns.setFont(new Font("Tahoma", Font.BOLD, 12));
		BotonIns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Conexion con = new Conexion();
                int id = Integer.parseInt(casilla1.getText());
                String nombre = casilla2.getText();
                String profesion = casilla3.getText();
                int edad = Integer.parseInt(casilla4.getText());
                int telefono = Integer.parseInt(casilla5.getText());
                
                try {
                    Connection conexion = con.getConnection();
                    PreparedStatement ps = ((Connection) conexion).prepareStatement("INSERT INTO persona (id,nombre,edad,profesion,telefono) VALUES (?,?,?,?,?)");//parametros ya ingresados
                    ps.setInt(1, id);
                    ps.setString(2, nombre);
                    ps.setInt(3, edad);
                    ps.setString(4, profesion);
                    ps.setInt(5, telefono);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro guardado");
                } catch(SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1.toString());
                    
                }
			}
		});
		BotonIns.setBounds(293, 221, 89, 23);
		getContentPane().add(BotonIns);
		
		JLabel lblNewLabel = new JLabel("Codigo: ");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(46, 259, 105, 17);
		getContentPane().add(lblNewLabel);
		
		casilla1 = new JTextField();
		casilla1.setBounds(199, 259, 288, 20);
		getContentPane().add(casilla1);
		casilla1.setColumns(10);
		
		casilla2 = new JTextField();
		casilla2.setColumns(10);
		casilla2.setBounds(199, 296, 288, 20);
		getContentPane().add(casilla2);
		
		casilla3 = new JTextField();
		casilla3.setColumns(10);
		casilla3.setBounds(199, 338, 288, 20);
		getContentPane().add(casilla3);
		
		casilla4 = new JTextField();
		casilla4.setColumns(10);
		casilla4.setBounds(199, 381, 288, 20);
		getContentPane().add(casilla4);
		
		casilla5 = new JTextField();
		casilla5.setColumns(10);
		casilla5.setBounds(199, 428, 288, 20);
		getContentPane().add(casilla5);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setForeground(Color.DARK_GRAY);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNombre.setBounds(46, 296, 105, 17);
		getContentPane().add(lblNombre);
		
		JLabel lblProfesion = new JLabel("Profesion: ");
		lblProfesion.setForeground(Color.DARK_GRAY);
		lblProfesion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProfesion.setBounds(46, 338, 105, 17);
		getContentPane().add(lblProfesion);
		
		JLabel lblEdad = new JLabel("Edad: ");
		lblEdad.setForeground(Color.DARK_GRAY);
		lblEdad.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEdad.setBounds(46, 381, 105, 17);
		getContentPane().add(lblEdad);
		
		JLabel lblTelefono = new JLabel("Telefono: ");
		lblTelefono.setForeground(Color.DARK_GRAY);
		lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTelefono.setBounds(46, 428, 105, 17);
		getContentPane().add(lblTelefono);
		
		JButton btnNewButton = new JButton("Limpiar");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				casilla1.setText("");
				casilla2.setText("");
				casilla3.setText("");
				casilla4.setText("");
				casilla5.setText("");
			}
		});
		btnNewButton.setBounds(46, 219, 89, 23);
		getContentPane().add(btnNewButton);
		botonAct.addActionListener(this);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==botonAct) {
			construirTabla();
		}
	}
}