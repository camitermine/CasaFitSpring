package cm.casa_fit.servicio;

import cm.casa_fit.modelo.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IClienteServicio {
    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Integer idCliente);

    public void guardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);

}
