package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
    Optional<Product> findById(Long aLong);
    @Override
    List<Product> findAllById(Iterable<Long> longs);
    List<Product> findAllByCategories_Id(Long categoryId);
    List<Product> findAllByCategories_IdIn(List<Long> childIds);

    List<Product> findAllByCategories_IdIn(List<Long> childIds, Sort sortType);

    List<Product> findAllByCategories_Id(Long categoryId, Sort sortType);
    @Query("""
          select p
          from Product p
          where not exists (
              select s.id
              from Stock s
              where s.product = p
                and s.shop.id = :shopId
          )
      """)
    List<Product> findProductsNotInShopStock(@Param("shopId") Long shopId);
}
