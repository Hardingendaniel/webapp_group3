package no.ntnu.webappgroup03.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import no.ntnu.webappgroup03.dto.HotelDto;
import no.ntnu.webappgroup03.model.Hotel;
import no.ntnu.webappgroup03.model.User;
import no.ntnu.webappgroup03.service.AccessUserService;
import no.ntnu.webappgroup03.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API Controller for hotel collection.
 * Code adapted from ...
 */
@CrossOrigin
@RestController
public class HotelController {
  @Autowired
  private HotelService hotelService;
  @Autowired
  private AccessUserService userService ;

  /**
   * Get all hotels.
   * HTTP GET to /hotels
   *
   * @return List of all hotels currently stored in the collection
   */
  @GetMapping("/api/hotels")
  public Iterable<Hotel> getAll() {
    return hotelService.getAll();
  }

  /**
   * Get a specific hotel.
   *
   * @param id ID` of the hotel to be returned
   * @return Hotel with the given ID or status 404 (Not Found)
   */
  @GetMapping("/api/hotels/{id}")
  public ResponseEntity<?> getOne(@PathVariable int id) {
    ResponseEntity<?> response;
    Optional<Hotel> hotel = this.hotelService.getOne(id);
    if (hotel.isPresent()) {
      HotelDto hotelDto = new HotelDto(hotel.get().getId(), hotel.get().getHotelName(),
          hotel.get().getDescription(), hotel.get().getLocation(), hotel.get().getRoomTypes(),
          hotel.get().getPrice());
      response = new ResponseEntity<>(hotelDto, HttpStatus.OK);
    } else {
      response = new ResponseEntity<>("Hotel with id: " + id +" not found",
          HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Update hotel details information.
   *
   * @param id for which hotel to update
   * @return HTTP 200 OK or error code with error message
   */
  @PutMapping("/api/hotels/{id}")
  public ResponseEntity<String> updateHotel(@PathVariable int id,
      @RequestBody HotelDto hotelData) {
    ResponseEntity<String> response;
    Optional<Hotel> hotel = this.hotelService.getOne(id);
    User sessionUser = this.userService.getSessionUser();
    if (sessionUser != null && sessionUser.isAdmin()) {
      if (hotelData != null && hotel.isPresent()) {
        if (this.hotelService.updateHotel(hotel.get(), hotelData)) {
          response = new ResponseEntity<>("", HttpStatus.OK);
        } else  {
          response = new ResponseEntity<>("Could not update Hotel data",
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
      } else {
        response = new ResponseEntity<>("Profile data not supplied", HttpStatus.BAD_REQUEST);
      }

    } else if (sessionUser == null) {
      response = new ResponseEntity<>("Hotel data accessible only to authenticated users",
          HttpStatus.UNAUTHORIZED);
    } else {
      response = new ResponseEntity<>("Hotel data for other users not accessible",
          HttpStatus.FORBIDDEN);
    }
    return response;
  }

  /**
   * Get all hotels
   *
   * @return return all the hotels
   */
  public List<Hotel> getAllHotels() {
    return hotelService.findAllHotels();
  }

}
