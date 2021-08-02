package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import conexion.Conexion;
import vo.PersonaVo;

public class PersonaDao {

	public ArrayList<PersonaVo> buscarUsuariosConMatriz() {

		Conexion conex = new Conexion();
		ArrayList<PersonaVo> miLista = new ArrayList<PersonaVo>();
		PersonaVo persona;
		try {
			Statement estatuto = conex.getConnection().createStatement();
			ResultSet rs = estatuto.executeQuery("SELECT * FROM persona ");

			while (rs.next()) {
				persona = new PersonaVo();
				persona.setIdPersona(Integer.parseInt(rs.getString("id")));
				persona.setNombrePersona(rs.getString("nombre"));
				persona.setEdadPersona(Integer.parseInt(rs.getString("edad")));
				persona.setProfesionPersona(rs.getString("profesion"));
				persona.setTelefonoPersona(Integer.parseInt(rs.getString("telefono")));
				miLista.add(persona);
			}
			rs.close();
			estatuto.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error al consultar", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
		return miLista;
	}
}