package nhom18.com.restful.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

import nhom18.com.restful.service.LoaiSanPhamService;
import nhom18.com.restful.service.ServiceResult;
import nhom18.com.restful.entities.LoaiSanPham;
import nhom18.com.restful.entities.LoaiSanPham;

@RestController
@RequestMapping("loaisanpham")
@CrossOrigin("*")
public class LoaiSanPhamController {
	@Autowired
	private LoaiSanPhamService loaiSanPhamService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "tenLoaiSanPham", required = false) String tenLoaiSanPham,
							     @RequestParam(value = "laDanhMucCha", required = false) Boolean laDanhMucCha,
							     @RequestParam(value = "loaiSanPhamPID", required = false) Integer loaiSanPhamPID,
							     @RequestParam(value = "loaiSanPhamID", required = false) Integer loaiSanPhamID){

        return ResponseEntity.ok(loaiSanPhamService.findByProperty(tenLoaiSanPham, laDanhMucCha, loaiSanPhamPID, loaiSanPhamID));
	}
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody LoaiSanPham LoaiSanPham) {
        return ResponseEntity.ok(loaiSanPhamService.save(LoaiSanPham));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<LoaiSanPham>> findById(@PathVariable int id) {
        if (loaiSanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(loaiSanPhamService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoaiSanPham> update(@PathVariable int id, @Valid @RequestBody LoaiSanPham loaiSanPham) {
        if (loaiSanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(loaiSanPhamService.save(loaiSanPham));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (loaiSanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        loaiSanPhamService.delete(id);
        return ResponseEntity.ok().build();
    }
}
