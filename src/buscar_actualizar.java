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
    PreparedStatement pss;


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
                        ingresarCorreo.setText(rs.getString("Correo"));
                        ingresarCelular.setText(rs.getString("Celular"));
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
                Connection con;

                try{
                    con = getConection();
                    pss = con.prepareStatement("UPDATE usuario SET nombre = ?, celular = ?, correo = ? WHERE id ="+ingresarID.getText() );

                        pss.setString(1, ingresarNombre.getText());
                        pss.setString(2, ingresarCelular.getText());
                        pss.setString(3, ingresarCorreo.getText());

                    System.out.println(ps);
                    int res = pss.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Persona modificada correctamente");
                    }else{
                        JOptionPane.showMessageDialog(null, "Persona no modificada");
                    }
                    con.close();

                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
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
