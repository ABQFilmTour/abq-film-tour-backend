package edu.cnm.deepdive.abq_film_tour_backend.controller;

import edu.cnm.deepdive.abq_film_tour_backend.model.dao.FilmLocationRepository;
import edu.cnm.deepdive.abq_film_tour_backend.model.entity.FilmLocation;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(FilmLocation.class)
@RequestMapping("/film_locations")
public class FilmLocationController {

  private FilmLocationRepository filmLocationRepository;

  @Autowired
  public FilmLocationController(FilmLocationRepository filmLocationRepository) {
    this.filmLocationRepository = filmLocationRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<FilmLocation> list() { return filmLocationRepository.findAllByOrderByIdAsc();}

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FilmLocation> post(@RequestBody FilmLocation filmLocation) {
    filmLocationRepository.save(filmLocation);
    return ResponseEntity.created(filmLocation.getHref()).body(filmLocation);
  }

  @GetMapping(value = "{filmLocationId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public FilmLocation get(@PathVariable("filmLocationId") UUID filmLocationId){
    return filmLocationRepository.findById(filmLocationId).get();
  }

  @DeleteMapping(value = "{filmLocationId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("filmLocationId") UUID filmLocationId) {
    filmLocationRepository.deleteById(filmLocationId);
  }

  @PatchMapping(value = "{filmLocationId}")
  public void patch(@PathVariable("filmLocationId") UUID filmLocationId) {
//TODO patch
 }
 //comment hello

}