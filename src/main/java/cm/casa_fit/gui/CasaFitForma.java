package cm.casa_fit.gui;

import cm.casa_fit.modelo.Cliente;
import cm.casa_fit.servicio.ClienteServicio;
import cm.casa_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class CasaFitForma extends JFrame {
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;
    private Integer idCliente;

    @Autowired
    public CasaFitForma(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> guardarCliente());
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        eliminarButton.addActionListener(e -> eliminarCliente());
        limpiarButton.addActionListener(e -> limpiarFormulario());
    }

    private void iniciarForma() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String [] cabeceras = {"Id", "Nombre", "Apellido", "Membresia"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceras);
        this.clientesTabla = new JTable(this.tablaModeloClientes);
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarClientes();
    }

    private void listarClientes() {
        this.tablaModeloClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
           Object[] renglonCliente = {
                   cliente.getId(),
                   cliente.getNombre(),
                   cliente.getApellido(),
                   cliente.getMembresia()
           };
           this.tablaModeloClientes.addRow(renglonCliente);
        });
    }

    private void guardarCliente(){
        if(nombreTexto.getText().equals("")){
            mostrarMensaje("Proporciona un nombre");
            nombreTexto.requestFocusInWindow();
            return;
        }
        if(membresiaTexto.getText().equals("")){
            mostrarMensaje("Proporciona membresia");
            membresiaTexto.requestFocusInWindow();
            return;
        }

        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var membresia = Integer.parseInt(membresiaTexto.getText());
        var cliente = new Cliente(this.idCliente, nombre, apellido, membresia);
        this.clienteServicio.guardarCliente(cliente); //insertar / modificar
        if(this.idCliente == null)
            mostrarMensaje("Se agrego el nuevo Cliente");
        else
            mostrarMensaje("Se actualizo el Cliente");
        limpiarFormulario();
        listarClientes();
    }

    private void cargarClienteSeleccionado() {
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){//-1 significa que no se selecciono ningun registro
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon, 1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon, 3).toString();
            this.membresiaTexto.setText(membresia);
        }
    }

    private void eliminarCliente(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){
            var idClienteStr = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(idClienteStr);
            var cliente = new Cliente();
            cliente.setId(idCliente);
            clienteServicio.eliminarCliente(cliente);
            mostrarMensaje("Cliente con id " + this.idCliente + " eliminado");
            limpiarFormulario();
            listarClientes();

        }
        else
            mostrarMensaje("Debe seleccionar un cliente a eliminar");
    }

    private void limpiarFormulario() {
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
        this.idCliente = null;
        this.clientesTabla.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
