package com.example;



import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.example.entities.Presentacion;
import com.example.entities.Producto;
import com.example.services.PresentacionService;
import com.example.services.ProductoService;

@Configuration
public class LoadDataBase {

    @Bean
    public CommandLineRunner sampleData(PresentacionService presentacionService, ProductoService productoService) {

        return args -> {
            presentacionService.save(
                Presentacion.builder()
                .id(1)
                .description("unidad")
                .build()
            );
            presentacionService.save(
                Presentacion.builder()
                .id(2)
                .description("decena")
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(1)
                .name("rezma de papel")
                .price(3.75)
                .stock(10)
                .presentacion(presentacionService.findById(1))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(2)
                .name("cartas")
                .price(1)
                .stock(10)
                .presentacion(presentacionService.findById(2))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(3)
                .name("guitarra de juguete")
                .price(4.5)
                .stock(5)
                .presentacion(presentacionService.findById(1))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(4)
                .name("teclado para computadora")
                .price(15)
                .stock(5)
                .presentacion(presentacionService.findById(1))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(5)
                .name("teclado para laptop")
                .price(40)
                .stock(5)
                .presentacion(presentacionService.findById(1))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(6)
                .name("bocinas bluetooth")
                .price(15)
                .stock(5)
                .presentacion(presentacionService.findById(1))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(7)
                .name("lapices 2b")
                .price(1.50)
                .stock(4)
                .presentacion(presentacionService.findById(2))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(8)
                .name("plumas color azul")
                .price(2)
                .stock(10)
                .presentacion(presentacionService.findById(2))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(9)
                .name("monitor del 15p")
                .price(40)
                .stock(5)
                .presentacion(presentacionService.findById(1))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(10)
                .name("cargador samsung")
                .price(10)
                .stock(10)
                .presentacion(presentacionService.findById(1))
                .build()
            );
            productoService.save(
                Producto.builder()
                .id(11)
                .name("mouse básico")
                .price(5)
                .stock(5)
                .presentacion(presentacionService.findById(1))
                .build()
            );
    };
}
}
    

