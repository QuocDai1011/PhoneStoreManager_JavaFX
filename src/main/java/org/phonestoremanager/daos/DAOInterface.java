package org.phonestoremanager.daos;

import java.util.ArrayList;

public interface DAOInterface<T> {
    public ArrayList<T> selectAll();
}
