package com.example.mimi.daul.controller;

import com.example.mimi.daul.entity.Category;
import com.example.mimi.daul.entity.EquipmentEntity;
import com.example.mimi.daul.entity.ResponseEquipmentDTO;
import com.example.mimi.daul.entity.Status;
import com.example.mimi.daul.service.EquipmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentRestController {
    private final EquipmentServiceImpl equipmentService;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody EquipmentEntity equipmentEntity) {
        equipmentService.insert(
                equipmentEntity.getSerialNum(),
                equipmentEntity.getStatus().name(),
                equipmentEntity.getCategory().name());
        return ResponseEntity.status(HttpStatus.CREATED).body("su");
    }

//    @PostMapping("/findByMgmNum")
//    public ResponseEntity<EquipmentEntity> findByMgmNum(@RequestParam("mgmNum") String mgmNum) {
//        EquipmentEntity equipment = equipmentService.findByMgmNum(mgmNum);
//
//        return ResponseEntity.ok(equipment);
//    }
    @GetMapping("/findbymgmnum/{mgmnum}")
    public ResponseEquipmentDTO findByMgmNum(@PathVariable String mgmnum) {
        EquipmentEntity equipment = equipmentService.findByMgmNum(mgmnum);
        ResponseEquipmentDTO responseEquipmentDTO = new ResponseEquipmentDTO(equipment.getMgmNum(),equipment.getSerialNum(), equipment.getStatus(),equipment.getCategory());
        return responseEquipmentDTO;}

//    @PostMapping("/findByMgmNum")
//    public ResponseEntity<EquipmentEntity> findByMgmNum(@RequestParam("mgmNum") String mgmNum) {
//        EquipmentEntity equipment = equipmentService.findByMgmNum(mgmNum);
//
//        return ResponseEntity.ok(equipment);
//    }


  /*
   @PostMapping("/update")
    public void update(@RequestParam Long mgmNum, @RequestParam Status str1, @RequestParam Category str2) {
        Status status = Status.valueOf(str1.name());
        Category category = Category.valueOf(str2.name());
        EquipmentEntity equipment = equipmentService.findByMgmNum(mgmNum);
        equipmentService.update(new EquipmentEntity(mgmNum, equipment.getSerialNum(), status, category));
    }
*/
/*    @PostMapping("/updateStatus")
    public void update(@RequestParam("mgmNum") String mgmNum, @RequestParam("status") Status status) {
        Long longNum = Long.parseLong(mgmNum);
        Status status1 = Status.valueOf(status.name());
        equipmentService.updateStatus(new ResponseEquipmentDTO(longNum, status1));

    }*/

    @PostMapping("/updateStatus")
    public void update(@RequestBody ResponseEquipmentDTO dto) {
        Long longNum = dto.getMgmNum();
        Status status1 = dto.getStatus();
        equipmentService.updateStatus(new ResponseEquipmentDTO(longNum, status1));
    }

    @PostMapping("/updateSerial")
    public void updateSerial(@RequestParam("mgmNum") String mgmNum, @RequestParam("serialNum") String serialNum) {
        Long longNum = Long.parseLong(mgmNum);
        equipmentService.updateSerial(new ResponseEquipmentDTO(longNum, serialNum));
    }


    @PostMapping("/delete")
    public void delete(@RequestParam("mgmNum") String mgmNum) {
        EquipmentEntity delete = equipmentService.findByMgmNum(mgmNum);
        equipmentService.delete(mgmNum);

    }


    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        List<EquipmentEntity> equipmentList = equipmentService.findAll();
        return ResponseEntity.ok(equipmentList);
    }

    @GetMapping("/findStatus")
    public ResponseEntity<List<EquipmentEntity>> findStatus(
            @RequestParam("status") String status,
            @RequestParam("category") String category) {
        List<EquipmentEntity> equipmentList = equipmentService.findstcok(status, category);
        return ResponseEntity.ok(equipmentList);
    }
    @GetMapping("/findStock")
    public int findStock(
            @RequestParam("status") String status,
            @RequestParam("category") String category) {
        int stock = equipmentService.findstock(status, category);
        return stock;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EquipmentEntity>> filterByCategoryAndStatus(
            @RequestParam("category") String category,
            @RequestParam("status") String status) {
        Category categoryEnum = Category.valueOf(category);
        Status statusEnum = Status.valueOf(status);

        List<EquipmentEntity> equipmentList = equipmentService.findFilter(categoryEnum, statusEnum);
        return ResponseEntity.ok(equipmentList);
    }


}
