package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.PresentacionDao;
import com.example.entities.Presentacion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresentacionServiceImpl  implements PresentacionService{

    private final PresentacionDao presentacionDao;


    @Override
    public List<Presentacion> findAll() {
       
        return presentacionDao.findAll();
    }

    @Override
    @Transactional
    public void save(Presentacion presentacion) {
      
        presentacionDao.save(presentacion);
    }

    @Override
    @Transactional
    public Presentacion findById(int id) {
     return presentacionDao.findById(id).get();
    }
    
}
