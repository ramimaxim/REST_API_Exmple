package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Producto;

public interface ProductoDao extends JpaRepository<Producto, Integer> {
    /**
     * vamos a necesitar tres metodos personalizados
     * que traigan la pñresentacion en una consulta, que es mas eficeinte,
     * que primero traer el producto y luego una subconsulta para la presentacion
     * que es lo que por defecto ocurriria por esta establecido al fetch type lazy
     * 1. recuperará los productos paginados, es decir de 10 en 10, de 20 en 20 etc
     * 
     * 2. otro que recupera los productos ordenados, sin paginacion
     * 
     * 3. dado el id de un producto recupera el producto con su presentacion correspondiente
     * para ello vamos a utilizar el lenguaje HQL(hybernate query language), es muy similar a SQL, pero los se consulta 
     * son las entidades, no las tablas.
     * y no se utiliza consultas de SQL nativo porque no sopòrtan la paginacion y el ordenamiento.
     */


     // el metodo siguiente ademas de paginacion tambien ofrece ordenamiento
     @Query(value = "select p from Producto p left join fetch p.presentacion",
     countQuery = "select count(p) from Producto p left join p.presentacion")
     public Page<Producto> findAll(Pageable pageable);


     @Query(value = "select p from Producto p left join fetch p.presentacion")
     public List<Producto>findAll(Sort sort);

     @Query(value = "select p from Producto p left join fetch p.presentacion where p.id = :id")
     public Producto findById(int id);
    
}
