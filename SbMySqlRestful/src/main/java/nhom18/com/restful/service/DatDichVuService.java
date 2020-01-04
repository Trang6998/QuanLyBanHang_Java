package nhom18.com.restful.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nhom18.com.restful.entities.DatDichVu;
import nhom18.com.restful.repository.DatDichVuRepository;

@Service
public class DatDichVuService {
	@Autowired
	private DatDichVuRepository datDichVuRepository;
	
	public Iterable<DatDichVu> findAll(){
		return datDichVuRepository.findAll();
	}
	public List<DatDichVu> findByProperty(String tenDichVu, Date tuNgay, Date denNgay, Integer dichVuID, Integer userID){
		return datDichVuRepository.findByProperty(tenDichVu, tuNgay, denNgay, dichVuID, userID);
	}
	public Optional<DatDichVu> findById(int id){
		return datDichVuRepository.findById(id);
	}
    public DatDichVu save(DatDichVu dv) {
        return datDichVuRepository.save(dv);
    }
    public void delete(int id) {
    	datDichVuRepository.deleteById(id);
    }
}
