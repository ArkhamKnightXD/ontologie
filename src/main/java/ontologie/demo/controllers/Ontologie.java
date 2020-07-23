package ontologie.demo.controllers;

import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Los restcontroller en springboot funcionan de tal forma que las funciones que sean
// puestas aqui retornaran por defecto un JSON
@RestController
public class Ontologie {

    @Autowired
    private OntologyService ontologyService;

    final String fileName = "gym_semantic.owl";


    @RequestMapping(value = "/ontologies",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getontologies() {

        List<JSONObject> list=new ArrayList();

        Iterator ontologiesIter = ontologyService.readOntologyFileAndReturn(fileName).listOntologies();

        while (ontologiesIter.hasNext()) {

            Ontology ontology = (Ontology) ontologiesIter.next();
            JSONObject obj = new JSONObject();
            obj.put("name",ontology.getLocalName());
            obj.put("uri",ontology.getURI());

            list.add(obj);

            }

            return list;
    }


    @RequestMapping(value = "/classesList",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getClasses() {

        List<JSONObject> list=new ArrayList();


        // Creamos un file para poder leer nuestro archivo de ontologia gym_semantic.owl

        Iterator classIter = ontologyService.readOntologyFileAndReturn(fileName).listClasses();

        //mientras haya un siguiente
        while (classIter.hasNext()) {

            OntClass ontClass = (OntClass) classIter.next();
            JSONObject obj = new JSONObject();

            //Al objeto json mediante un hashmap le agregamos los campos name y uri
            obj.put("name",ontClass.getLocalName());
            obj.put("uri",ontClass.getURI());

            list.add(obj);
            }

            return list;
    }


    @RequestMapping(value = "/subClasses",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getSubClasses(@RequestParam("classname") String className) {

        List<JSONObject> list=new ArrayList();

        String classURI = "http://www.semanticweb.org/opendev/ontologies/2017/10/untitled-ontology-8#".concat(className);
        System.out.println(classURI);
        OntClass personne =  ontologyService.readOntologyFileAndReturn(fileName).getOntClass(classURI );

        Iterator subIter = personne.listSubClasses();

        while (subIter.hasNext()) {

            OntClass sub = (OntClass) subIter.next();
            JSONObject obj = new JSONObject();
            obj.put("URI",sub.getURI());

            list.add(obj);
        }

        return list;
    }


    @RequestMapping(value = "/Individus",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getIndividus() {

        List<JSONObject> list=new ArrayList();


        Iterator individus = ontologyService.readOntologyFileAndReturn(fileName).listIndividuals();

        while (individus.hasNext()) {

            Individual   sub = (Individual) individus.next();
            JSONObject obj = new JSONObject();
            obj.put("name",sub.getLocalName());
            obj.put("uri",sub.getURI());

            list.add(obj);
            }

        return list;
    }


    @RequestMapping(value = "/superClasses",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getSuperClasses(@RequestParam("classname") String className) {

        List<JSONObject> list=new ArrayList();

        String classURI = "http://www.semanticweb.org/opendev/ontologies/2017/10/untitled-ontology-8#".concat(className);
        System.out.println(classURI);
        OntClass personne = ontologyService.readOntologyFileAndReturn(fileName).getOntClass(classURI );
        Iterator subIter = personne.listSuperClasses();

        while (subIter.hasNext()) {

            OntClass sub = (OntClass) subIter.next();
            JSONObject obj = new JSONObject();
            obj.put("URI",sub.getURI());

            list.add(obj);
        }

        return list;
    }


    @RequestMapping(value = "/getClasProperty",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getClasProperty(@RequestParam("classname") String className) {

        List<JSONObject> list=new ArrayList();


        String classURI = "http://www.semanticweb.org/opendev/ontologies/2017/10/untitled-ontology-8#".concat(className);
        OntClass ontClass = ontologyService.readOntologyFileAndReturn(fileName).getOntClass(classURI );
        Iterator subIter = ontClass.listDeclaredProperties();

        while (subIter.hasNext()) {

            OntProperty property = (OntProperty) subIter.next();
            JSONObject obj = new JSONObject();
            obj.put("propertyName",property.getLocalName());

            obj.put("propertyType",property.getRDFType().getLocalName());

            if(property.getDomain()!=null)
                obj.put("domain", property.getDomain().getLocalName());
            if(property.getRange()!=null)
                obj.put("range",property.getRange().getLocalName());

            list.add(obj);
        }

        return list;
    }


    @RequestMapping(value = "/equivClasses",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getequivClasses(@RequestParam("classname") String className) {

        List<JSONObject> list=new ArrayList();

        String classURI = "http://www.semanticweb.org/opendev/ontologies/2017/10/untitled-ontology-8#".concat(className);
        System.out.println(classURI);
        OntClass personne = ontologyService.readOntologyFileAndReturn(fileName).getOntClass(classURI );
        Iterator subIter = personne.listEquivalentClasses();

        while (subIter.hasNext()) {

            OntClass sub = (OntClass) subIter.next();
            JSONObject obj = new JSONObject();
            obj.put("URI",sub.getURI());
            list.add(obj);
        }

        return list;
    }


    @RequestMapping(value = "/Instances",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getInstancesClasses(@RequestParam("classname") String className) {

        List<JSONObject> list=new ArrayList();

        String classURI = "http://www.semanticweb.org/opendev/ontologies/2017/10/untitled-ontology-8#".concat(className);
        System.out.println(classURI);
        OntClass personne = ontologyService.readOntologyFileAndReturn(fileName).getOntClass(classURI );
        Iterator subIter = personne.listInstances();

        while (subIter.hasNext()) {

            Individual   sub = (Individual) subIter.next();
            JSONObject obj = new JSONObject();
            obj.put("name",sub.getLocalName());
            obj.put("uri",sub.getURI());

            list.add(obj);
        }

        return list;
    }


    @RequestMapping(value = "/isHierarchyRoot",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> isHirarchieroot(@RequestParam("classname") String className) {

        List<JSONObject> list=new ArrayList();


        String classURI = "http://www.semanticweb.org/opendev/ontologies/2017/10/untitled-ontology-8#".concat(className);
        System.out.println(classURI);
        OntClass personne = ontologyService.readOntologyFileAndReturn(fileName).getOntClass(classURI );

        if (personne != null){
            JSONObject obj = new JSONObject();
            if (personne.isHierarchyRoot()){
                obj.put("isroot","true");
            }else {
                obj.put("isroot","false");
            }

            list.add(obj);
        }

        return list;
    }


    @RequestMapping(value = "/query",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> query() {

        List<JSONObject> list=new ArrayList();

            //Aqui utilizo sparql
        String sprql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "select * {?x ?y ?z}";
        Query query = QueryFactory.create(sprql);
        QueryExecution qe = QueryExecutionFactory.create(query, ontologyService.readOntologyFileAndReturn(fileName));
        ResultSet resultSet = qe.execSelect();
        int x=0;

        while (resultSet.hasNext()) {

            x++;
            JSONObject obj = new JSONObject();
            QuerySolution solution = resultSet.nextSolution();
            System.out.println(solution.get("x").toString());
            obj.put("subject",solution.get("x").toString());
            obj.put("property",solution.get("y").toString());
            obj.put("object",solution.get("z").toString());

            list.add(obj);
        }

        System.out.println(x);

        return list;
    }
}
