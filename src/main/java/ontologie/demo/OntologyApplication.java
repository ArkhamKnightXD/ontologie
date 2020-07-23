package ontologie.demo;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.FileReader;

@SpringBootApplication
public class OntologyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(OntologyApplication.class, args);
	}


//Este codigo se ejecuta al inicio del programa y funciona como comprobacion
	@Override
	public void run(String... strings) {

		// Nombre del archivo
		String fileName = "gym_semantic.owl";

		try {

			//Creacion del file en el que se manejara el archivo
			File file = new File(fileName);
			FileReader reader = new FileReader(file);

			//Llamado del ontmodel que es utilizado para crear modelos mediante apache jena
			OntModel model = ModelFactory
					.createOntologyModel(OntModelSpec.OWL_DL_MEM);

			//Lectura del modelo y al final se imprime en pantalla
			model.read(reader,null);
			model.write(System.out,"RDF/XML-ABBREV");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
