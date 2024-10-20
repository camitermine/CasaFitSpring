package cm.casa_fit.repositorio;

import cm.casa_fit.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {
}
