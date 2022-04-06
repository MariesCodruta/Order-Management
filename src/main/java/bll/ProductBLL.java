package bll;

import bll.validators.AmountValidator;
import bll.validators.Validator;
import model.Product;

import java.util.ArrayList;
import java.util.List;

/*
 * Checks before inserting a product if all its fields are valid.
 * @param product product to be checked if valid
 * @return
 */

public class ProductBLL {
    private List<Validator<Product>> validators;

    public ProductBLL(){
        validators = new ArrayList<Validator<Product>>();
        validators.add(new AmountValidator());
    }

    public int insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            if(v.validate(product) < 0)
                return -1;
        }
        return 0;
    }
}
