package com.example.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Producto;
import com.example.services.ProductoService;

import ch.qos.logback.core.pattern.parser.FormattingNode;
import jakarta.validation.Valid;

/**
 * la anotacion @restcontroller automaticamente hace que los metodos
 * devuelvan/acepten datos en formato JSON
 * (Java script OPbject Notation), que anteriormente los datos entre el cliente
 * y el servidor se intercambien en formato
 * xml pero por la facilidad de convertir un dato a un aobjeto de javascript ys
 * viceversa, el formato
 * de intercambio JSON ha triunfado, porque el mismo esfuerzo que hay que
 * realizar para un objeto de javascript es el que hay qye hacer
 * para crear un JSON. ademas un json puede tener array y un xml no.
 */

@RestController
@RequestMapping("/productos")
public class ProoductoController {
    @Autowired

    private ProductoService productoService;

    /**
     * 
     * El siguiente metodo recupera los productos con paginacion, o no, respondiendo
     * a una peticion del siguiente tipo
     * 
     * que seria http://localhost:8080/productos?page=0&size=3
     * 
     */

    @GetMapping

    public ResponseEntity<List<Producto>> findAll(@RequestParam(name = "page", required = false) Integer page,

            @RequestParam(name = "size", required = false) Integer size) {

        List<Producto> productos = new ArrayList<>();

        Sort sortByName = Sort.by("name");

        ResponseEntity<List<Producto>> responseEntity = null;

        // Primero comprobar si se requiere paginacion o no

        if (page != null && size != null) {

            Pageable pageable = PageRequest.of(page, size, sortByName);

            // Se solicita el listado de productos paginados

            try {

                Page<Producto> productosPaginados = productoService.findAll(pageable);

                productos = productosPaginados.getContent();

                responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);

            } catch (Exception e) {

                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }

        } else {

            // Devolver los productos ordenados en cualquier caso

            try {

                productos = productoService.findAll(sortByName);

                responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);

            } catch (Exception e) {

                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }

        }

        return responseEntity;

    }

    // Metodo que persiste un producto en la base de datos

    @PostMapping

    public ResponseEntity<Map<String, Object>> saveProduct(@Valid @RequestBody Producto producto,
            BindingResult results) {

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Comprobar si el producto ha llegado con errores, es decir si está mal formado

        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            // Quiero recorrer los resultados de la validacion y extraer los mensajes por
            // defecto

            for (ObjectError objectError : results.getAllErrors()) {

                mensajesError.add(objectError.getDefaultMessage());

            }

            // Hay que devolver como respuesta 3 objetos

            // 1. El producto recibido mal formado

            // 2. Los mensajes de error

            // 3. Estado de la operacion httpStatus

            // Por lo cual necesitamos un Map Interface, un mapa, hablando en castellano

            responseAsMap.put("errores: ", mensajesError);

            responseAsMap.put("Producto: ", producto);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;

        }

        // Si no hay errores persistimos el producto y devolvemos informacion

        try {

            Producto productoPersistido = productoService.save(producto);

            String sucessMessage = "El producto se ha creado exitosamente";

            responseAsMap.put("mensaje: ", sucessMessage);

            responseAsMap.put("producto", productoPersistido);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);

        } catch (DataAccessException e) {

            String errorMessage = "El producto no se pudo persistir y la causa mas probable del error es: "
                    + e.getMostSpecificCause();

            responseAsMap.put("error: ", errorMessage);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }

    // Metodo que actualiza un producto dado el id del mismo

    // Es basicamente igual al de persistir un producto nuevo

    @PutMapping("/{id}")

    public ResponseEntity<Map<String, Object>> updateProduct(@Valid @RequestBody Producto producto,
            BindingResult results,

            @PathVariable(name = "id") Integer idProducto) {

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Comprobar si el producto ha llegado con errores, es decir si está mal formado

        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            // Quiero recorrer los resultados de la validacion y extraer los mensajes por
            // defecto

            for (ObjectError objectError : results.getAllErrors()) {

                mensajesError.add(objectError.getDefaultMessage());

            }

            // Hay que devolver como respuesta 3 objetos

            // 1. El producto recibido mal formado

            // 2. Los mensajes de error

            // 3. Estado de la operacion httpStatus

            // Por lo cual necesitamos un Map Interface, un mapa, hablando en castellano

            responseAsMap.put("errores: ", mensajesError);

            responseAsMap.put("Producto: ", producto);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;

        }

        // Si no hay errores persistimos el producto y devolvemos informacion

        try {

            producto.setId(idProducto);

            Producto productoActualizado = productoService.save(producto);

            String sucessMessage = "El producto se ha actualizado exitosamente";

            responseAsMap.put("mensaje: ", sucessMessage);

            responseAsMap.put("producto", productoActualizado);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

        } catch (DataAccessException e) {

            String errorMessage = "El producto no se pudo actualizar y la causa mas probable del error es: "
                    + e.getMostSpecificCause();

            responseAsMap.put("error: ", errorMessage);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }


    //metodo para recuperar un producto por el id

    //responde a una peticion del tipo http://localhost:8080/productos/1, por ejemplo

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findByIdProducto(@PathVariable(name = "id", required = true) Integer idProducto){

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {
            Producto producto = productoService.findById(idProducto);
            if( producto != null) {
                String successMessage = "se ha encontrado el producto con el Id : " + idProducto;
                responseAsMap.put("mensaje", successMessage);
                responseAsMap.put("producto", producto);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);

            } else {

             String notFoundMessage = "no se ha encontrado el producto con el Id : "   + idProducto;
             responseAsMap.put("mensaje", notFoundMessage);
             responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
           String errorMessage = "Error grave, y la causa mas probable es: " + e.getMostSpecificCause();
           responseAsMap.put("Error Grave", errorMessage);
           responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }



        return responseEntity;
    }



    //metodo  para eliminar un producto cuyo id se recibe como parametro de la peticion

    @DeleteMapping("/{id}")

    public ResponseEntity<Map<String, Object>> deleteProducto(@PathVariable(name = "id") Integer idProducto) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> respnseAsMap = new HashMap<>();

        try {
            productoService.delete(productoService.findById(idProducto));
            respnseAsMap.put("mensaje", "el producto se ha eliminados correctamente");
            responseEntity = new ResponseEntity<Map<String,Object>>(respnseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            respnseAsMap.put("error grave", "no se ha podido eliminar el producto, debido a : " + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<Map<String,Object>>(respnseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }



        return responseEntity;
    }
}
