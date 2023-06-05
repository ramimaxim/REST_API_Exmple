package com.example.controllers;




import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * la anotacion @restcontroller automaticamente hace que los metodos devuelvan/acepten datos en formato JSON
 * (Java script OPbject Notation), que anteriormente los datos entre el cliente y el servidor se intercambien en formato
 * xml pero por la facilidad de convertir un dato a un aobjeto de javascript ys viceversa, el formato
 * de intercambio JSON ha triunfado, porque el mismo esfuerzo que hay que realizar para un objeto de javascript es el que hay qye  hacer
 * para crear un JSON. ademas un json puede tener array y un xml no.
 */

@RestController
@RequestMapping("/productos")
public class ProoductoController {

   


}
