package org.phonestoremanager.daos;

import org.phonestoremanager.models.ProductDetailModel;

import java.util.ArrayList;

public class ProductDetailDAO implements DAOInterface<ProductDetailModel> {
    public static ProductDetailDAO getInstance() {
        return new ProductDetailDAO();
    }

    @Override
    public ArrayList<ProductDetailModel> selectAll() {


        return null;
    }
}
