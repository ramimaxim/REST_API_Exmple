package com.example.controllers;




import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Producto;
import com.example.services.ProductoService;

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

    @Autowired
    private ProductoService productoService;

   /**
    * el siguiente metodo recupera los productos con paginacion o no.
    respondiendo a una peticion del siguiente tipo 
    http://localhost:8080/productos?page=0&size=3
    */

    @GetMapping
    public ResponseEntity<List<Producto>> findAll(@RequestParam(name = "page", required = false) Integer page,
    @RequestParam(name = "size", required = false) Integer size) {
        List<Producto> productos = new ArrayList<>();

        Sort sortByName = Sort.by("name");
        ResponseEntity<List<Producto>> responseEntity = null;
        //primero comprobar si se requiere paginacion, o no
        if(page != null && size != null) {

            Pageable pageable = PageRequest.of(page, size, sortByName);
            try {
                  //se solicita el listado de productos paginados.
            Page<Producto> productosPaginados = productoService.findAll(pageable); // page es un contenedor, no te devuelve el contenido hasta que se ponga la segunda linea

            productos = productosPaginados.getContent();
            responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
                
            } catch (Exception e) {
               responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
          

        }else {
            // si no hubiera paginacion, ni size. devolver los productos ordenados en cualquier caso
       try {
           productos = productoService.findAll(sortByName);
           responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
    
          } catch (Exception e) {

            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
          }
        

        return responseEntity;
    }


}
