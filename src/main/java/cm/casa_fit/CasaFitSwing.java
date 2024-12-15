package cm.casa_fit;

import com.formdev.flatlaf.FlatDarculaLaf;

import cm.casa_fit.gui.CasaFitForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javax.swing.*;


@SpringBootApplication
public class CasaFitSwing {
    public static void main(String[] args) {
        //instanciar la fabrica de spring
        FlatDarculaLaf.setup();
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(CasaFitSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);
        // Crear un objeto de Swing
        SwingUtilities.invokeLater(()-> {
            CasaFitForma casaFitForma = contextoSpring.getBean(CasaFitForma.class);
            casaFitForma.setVisible(true);
        });
    }
}
