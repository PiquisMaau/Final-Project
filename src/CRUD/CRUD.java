/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import java.util.List;

/**
 *
 * @author Sebas
 */
public abstract class CRUD<T> {

    public abstract boolean create(T obj);

    public abstract T read(int id);

    public abstract boolean update(T obj);

    public abstract boolean delete(int id);

    public abstract List<T> listAll();
}


   
