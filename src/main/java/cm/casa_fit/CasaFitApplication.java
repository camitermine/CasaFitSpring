package cm.casa_fit;

import cm.casa_fit.modelo.Cliente;
import cm.casa_fit.servicio.ClienteServicio;
import cm.casa_fit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CasaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(CasaFitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion");
		//levantar la fabrica de spring
		SpringApplication.run(CasaFitApplication.class, args);
		logger.info("Aplicacion finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp() {
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir) {
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}

	}

	private int mostrarMenu(Scanner consola){
		logger.info("""
				\n*** Aplicacion Casa Fit ***
				1. Listar Clientes
				2. Buscar Cliente
				3. Agregar Cliente
				4. Modificar Cliente
				5. Eliminar Cliente
				6. Salir
				Elije una opcion:\s""");
		return Integer.parseInt(consola.nextLine());
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion){
		var salir = false;
		switch(opcion){
			case 1 -> {
				logger.info(nl + "--- Listado de Clientes ---" + nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
			}
			case 2 -> {
				logger.info(nl + "--- Buscar Cliente por Id---" + nl);
				logger.info("Ingrese el id del cliente que quiere encontrar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null) {
					logger.info("Cliente encontrado: " + cliente + nl);
				}
				else
					logger.info("Cliente no encontrado: " + cliente + nl);
			}
			case 3 -> {
				logger.info(nl + "--- Agregar Cliente ---" + nl);
				logger.info("Nombre: ");
				var nombreCliente = consola.nextLine();
				logger.info("Apellido: ");
				var apellidoCliente = consola.nextLine();
				logger.info("Membresia: ");
				var membresiaCliente = Integer.parseInt(consola.nextLine());
				var cliente = new Cliente();
				cliente.setNombre(nombreCliente);
				cliente.setApellido(apellidoCliente);
				cliente.setMembresia(membresiaCliente);
				clienteServicio.guardarCliente(cliente);
				logger.info("Cliente agregado: " + cliente + nl);
			}
			case 4 -> {
				logger.info(nl + "--- Modificar Cliente ---" + nl);
				logger.info("Ingrese el id del cliente que desea modificar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null) {
					logger.info("Nombre: ");
					var nombre = consola.nextLine();
					logger.info("Apellido: ");
					var apellido = consola.nextLine();
					logger.info("Membresia: ");
					var membresia = Integer.parseInt(consola.nextLine());
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);
					clienteServicio.guardarCliente(cliente);
					logger.info("Cliente modificado: " + cliente + nl);
				}
				else
					logger.info("Cliente no encontrado: " + cliente + nl);
			}
			case 5 -> {
				logger.info(nl + "--- Eliminar Cliente ---" + nl);
				logger.info("Ingrese el id del cliente que desea eliminar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				var cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null) {
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente eliminado: " + cliente + nl);
				}
				else
					logger.info("Cliente no encontrado: " + cliente + nl);
			}
			case 6 -> {
				logger.info(nl + "Hasta pronto!" + nl);
				salir = true;
			}
			default -> logger.info("Opcion NO reconocida: " + opcion + nl);
		}
		return salir;
	}
}
