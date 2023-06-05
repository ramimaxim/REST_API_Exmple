package com.example.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

    // el metodo siguiente no tiene nada que ver con la api que estamos desarrollando es solo para comprender el formato json.

@GetMapping
    public List<String> nombres() {

        List<String> nombres = new ArrayList<>();
        String[] arrayNombres = {
            "Salwa", "Rachida", "Yolanda", "Patricia", "Maria Jose", "Pili" 
        };


nombres = Arrays.asList(arrayNombres);
    
   return nombres; 
}
}
