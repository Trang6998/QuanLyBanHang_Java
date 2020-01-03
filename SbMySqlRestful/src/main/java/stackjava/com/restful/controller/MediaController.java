package stackjava.com.restful.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stackjava.com.restful.entities.Media;
import stackjava.com.restful.service.MediaService;

@RestController
@RequestMapping("media")
@CrossOrigin("*")
public class MediaController {

	@Autowired
	private MediaService mediaService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "dichVuID", required = false) Integer dichVuID,
							     @RequestParam(value = "sanPhamID", required = false) Integer sanPhamID){

        return ResponseEntity.ok(mediaService.findByProperty(dichVuID, sanPhamID));
	}
    @PutMapping("/{id}")
    public ResponseEntity<Media> update(@PathVariable int id, @Valid @RequestBody Media media) {
        if (mediaService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(mediaService.save(media));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (mediaService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        mediaService.delete(id);
        return ResponseEntity.ok().build();
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Media>> findById(@PathVariable int id) {
        if (mediaService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mediaService.findById(id));
    }
}
