package bll.validators;

import model.Product;

/**
 * Check if the amount is greater than 0 and return -1 otherwise.
 */
public class AmountValidator  implements Validator<Product>{

    public int validate(Product p){
        if(p.getQuantity() < 0){
            return -1;
        }
        else
            return 0;
    }
}
