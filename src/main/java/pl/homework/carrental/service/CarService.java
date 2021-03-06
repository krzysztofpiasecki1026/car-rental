package pl.homework.carrental.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;


    public void saveCar(Car car){
        carRepository.save(car);
    }

    public Optional<Car> getCarById(Long id){
        return carRepository.findById(id);
    }

    public void deleteCar(Car car){
        carRepository.delete(car);
    }

    public List<Car> getAllAvailableCars(){
        return carRepository.findByIsAvailableIsTrue();
    }

    public void deleteAllCars(){
        carRepository.deleteAll();
    }

    public Iterable<Car> getAllCars(){
        return carRepository.findAll();
    }
}
