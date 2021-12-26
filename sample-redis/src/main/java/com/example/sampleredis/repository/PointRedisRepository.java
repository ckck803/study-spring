package com.example.sampleredis.repository;

import com.example.sampleredis.domain.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRedisRepository extends CrudRepository<Point, String> {
}
