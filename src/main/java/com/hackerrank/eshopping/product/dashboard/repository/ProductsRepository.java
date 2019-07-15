package com.hackerrank.eshopping.product.dashboard.repository;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity addProduct(Product product) {
        List<Product> products = jdbcTemplate.query("select id,name,category,retail_price," +
                "discounted_price,availability from products where id=" + product.getId(), (rs,rowNumber) -> {
            try
            {   return productMapping(rs);
            }catch(SQLException e){
                throw new RuntimeException("SQLException: ",e);
            }
        });

        if(products.size()<1){
            String sqlStatements[] = {
                    "insert into products(id, name, category, retail_price, discounted_price, availability) " +
                            "values('" + product.getId() + "','" + product.getName() + "','" + product.getCategory() +
                            "'," + product.getRetail_price() + "," + product.getDiscounted_price() +
                            "," + product.getAvailability() + ")",
            };

            Arrays.asList(sqlStatements).stream().forEach(sql -> {
                System.out.println(sql);
                jdbcTemplate.execute(sql);
            });

            return new ResponseEntity(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity updateProductById(Product product, Long product_id) {
        List<Product> products = jdbcTemplate.query("select id,name,category,retail_price," +
                "discounted_price,availability from products where id=" + product_id, (rs,rowNumber) -> {
            try
            {   return productMapping(rs);
            }catch(SQLException e){
                throw new RuntimeException("SQLException: ",e);
            }
        });

        if(products.size()<1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        else{
            String sqlStatements[] = {
                    "update products set retail_price=" + product.getRetail_price() + ", discounted_price=" +
                    product.getDiscounted_price() + ", availability=" + product.getAvailability() + " where id=" +
                    product_id,
            };

            Arrays.asList(sqlStatements).stream().forEach(sql -> {
                System.out.println(sql);
                jdbcTemplate.execute(sql);
            });

            return new ResponseEntity(HttpStatus.OK);
        }
    }

    public List<Product> getProductById(Long product_id){
        return jdbcTemplate.query("select id,name,category,retail_price," +
                "discounted_price,availability from products where id=" + product_id, (rs,rowNumber) -> {
                    try
                    {   return productMapping(rs);
                    }catch(SQLException e){
                        throw new RuntimeException("SQLException: ",e);
                    }
                });
    }

    public List<Product> getProductByCategory(String category){
        return jdbcTemplate.query("select id,name,category,retail_price," +
                "discounted_price,availability from products where category='" + category + "'", (rs,rowNumber) -> {
            try
            {   return productMapping(rs);
            }catch(SQLException e){
                throw new RuntimeException("SQLException: ",e);
            }
        });
    }

    public List<Product> getProductByCategoryAndAvailability(String category, Boolean availability) {
        return jdbcTemplate.query("select id,name,category,retail_price," +
                "discounted_price,availability from products where category='" + category + "' and availability=" +
                availability, (rs,rowNumber) -> {
                    try
                    {   return productMapping(rs);
                    }catch(SQLException e){
                        throw new RuntimeException("SQLException: ",e);
                    }
                });
    }

    public List<Product> getProducts() {
        return jdbcTemplate.query("select id,name,category,retail_price," +
                "discounted_price,availability from products ", (rs,rowNumber) -> {
                    try
                    {   return productMapping(rs);
                    }catch(SQLException e){
                        throw new RuntimeException("SQLException: ",e);
                    }
                });
    }

    private Product productMapping(ResultSet rs) throws SQLException {
        Product product = new Product();

        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setCategory(rs.getString("category"));
        product.setRetail_price(rs.getDouble("retail_price"));
        product.setDiscounted_price(rs.getDouble("discounted_price"));
        product.setAvailability(rs.getBoolean("availability"));

        return product;
    }
}
