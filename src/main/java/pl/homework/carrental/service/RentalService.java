package pl.homework.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.homework.carrental.model.Car;
import pl.homework.carrental.model.Rental;
import pl.homework.carrental.repository.RentalRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    CarService carService;
    public void rentACar (Car car){
        Rental rental = new Rental();

        rental.setCar(car);
        rental.setStartRentalDate(Date.valueOf(LocalDate.now()));
        rentalRepository.save(rental);
    }

    public void returnACar (Rental rental){
        rental.setEndRentalDate(Date.valueOf(LocalDate.now()));

        rentalRepository.save(rental);
    }
    public void deleteRental (Rental rental){
        rentalRepository.delete(rental);
    }

    public Optional<Rental> getRentalById (long id) {
        return rentalRepository.findById(id);
    }

    public List<Rental> getAllRentalsInProgress(){
        return rentalRepository.findByEndRentalDateIsNull();
    }

    public List<Car> getAllAvailableCars(){
        List<Rental> takenCars = rentalRepository.findByEndRentalDateIsNull();
        ArrayList<Car> availableCars = new ArrayList<>();

        Iterable<Car> carsIterable = carService.getAllCars();
        List<Car> allCars = new ArrayList<>();
        carsIterable.forEach(allCars::add);

        for (Rental rental: takenCars) {
            if (rental.getEndRentalDate() == null) {
                Optional <Car> car = carService.getCarById(rental.getCar().getId());
                car.ifPresent(availableCars::add);
            }
        }
        for (Car car: availableCars){
            allCars.remove(car);
        }
    return allCars;
    }
}
