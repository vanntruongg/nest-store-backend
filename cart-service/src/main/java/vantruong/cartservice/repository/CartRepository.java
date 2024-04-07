package vantruong.cartservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vantruong.cartservice.entity.CartItem;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<CartItem, String> {
}
