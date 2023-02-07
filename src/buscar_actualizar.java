import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class buscar_actualizar {
    private JButton buscarButton;
    private JButton borrarButton;
    private JTextField ingresarID;
    private JTextField ingresarNombre;
    private JTextField ingresarCorreo;
    private JTextField ingresarCelular;
    private JButton cancelarButton;
    private JButton actualizarButton;
    private JPanel buscar_actualizar;
    Statement ps;


    public buscar_actualizar() {

        ingresarNombre.setEnabled(false);
        ingresarCorreo.setEnabled(false);
        ingresarCelular.setEnabled(false);
        actualizarButton.setEnabled(false);
        borrarButton.setEnabled(false);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarID.setText(null);
                ingresarNombre.setText(null);
                ingresarCorreo.setText(null);
                ingresarCelular.setText(null);
            }
        });


        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarNombre.setEnabled(true);
                ingresarCorreo.setEnabled(true);
                ingresarCelular.setEnabled(true);
                actualizarButton.setEnabled(true);
                borrarButton.setEnabled(true);

                Connection con;
                try {
                    con = getConection();
                    ps= con.createStatement();
                    ResultSet rs;
                    rs=ps.executeQuery("select * from base2.usuario where id= " + ingresarID.getText()+";");

                    while (rs.next()){
                        ingresarNombre.setText(rs.getString("Nombre"));
                        ingresarCorreo.setText(rs.getString("Celular"));
                        ingresarCelular.setText(rs.getString("Correo"));

                    }


                    con.close();//importante!!!!
                }catch (HeadlessException | SQLException f){
                    System.err.println(f);
                }
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] arg) {
        JFrame frame1 = new JFrame("Registro");
        buscar_actualizar f1 = new buscar_actualizar();

        frame1.setContentPane(f1.buscar_actualizar);
        frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame1.pack();
        frame1.setSize(300, 300);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
    }

    /// conxion con sql
    public static Connection getConection() {
        Connection con = null;
        String base = "base2"; //Nombre de la base de datos
        String url = "jdbc:mysql://localhost:3306/" + base; //Direccion, puerto y nombre de la Base de Datos
        String user = "root"; //Usuario de Acceso a MySQL
        String password = "root1"; //Password del usuario

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return con;
    }
}
