package org.phonestoremanager.repositories;

import java.util.ArrayList;

public interface IRepository<T> {
    public ArrayList<T> selectAll();

    public ArrayList<T> selectDetailById();
}
