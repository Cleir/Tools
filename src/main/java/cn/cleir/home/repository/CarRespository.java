package cn.cleir.home.repository;

import cn.cleir.home.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRespository extends JpaRepository<Car, Integer> {
}
