package nhom18.com.restful.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import nhom18.com.restful.entities.ThuocTinh;

import nhom18.com.restful.service.ThuocTinhService;
@RestController
@RequestMapping("thuoctinh")
@CrossOrigin("*")
public class ThuocTinhController {
	@Autowired
	private ThuocTinhService thuocTinhService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "tenThuocTinh", required = false) String tenThuocTinh){	
    	if(tenThuocTinh!=null) {
    		return ResponseEntity.ok(thuocTinhService.findByName(tenThuocTinh));
    	}
        return ResponseEntity.ok(thuocTinhService.findAll());
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ThuocTinh>> findById(@PathVariable int id) {
        if (thuocTinhService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(thuocTinhService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ThuocTinh tt) {
        return ResponseEntity.ok(thuocTinhService.save(tt));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ThuocTinh> update(@PathVariable int id, @Valid @RequestBody ThuocTinh tt) {
        if (thuocTinhService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(thuocTinhService.save(tt));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity<ThuocTinh> delete(@PathVariable Integer id) {
        if (thuocTinhService.findById(id) == null) {
        	
            ResponseEntity.badRequest().build();
        }
        thuocTinhService.delete(id);
        return ResponseEntity.ok().build();
    }
}
