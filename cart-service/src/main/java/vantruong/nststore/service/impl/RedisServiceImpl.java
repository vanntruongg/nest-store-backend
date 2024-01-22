package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vantruong.nststore.entity.CartItem;
import vantruong.nststore.service.RedisService;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, CartItem> redisTemplate;

  @Override
  public void set(String key, CartItem value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public CartItem get(String key) {
    return redisTemplate.opsForValue().get(key);
  }
}
