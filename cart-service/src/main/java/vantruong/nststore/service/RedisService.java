package vantruong.nststore.service;

import vantruong.nststore.entity.CartItem;

public interface RedisService {
  void set(String key, CartItem value);
  CartItem get(String key);
}
