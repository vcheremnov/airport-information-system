package airport.controllers;

import airport.dtos.AbstractDto;
import airport.services.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractController
        <DTO extends AbstractDto<ID>,
        ID extends Serializable> {

    protected abstract Service<DTO, ID> getService();

    @GetMapping("/count")
    public ResponseEntity<Long> countAll() {
        Long entitiesNumber = getService().countAll();
        return ResponseEntity.ok(entitiesNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(
            @PathVariable ID id
    ) {
        DTO dto = getService().getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DTO>> getAll(
            Pageable pageable
    ) {
        Page<DTO> dtoPage = getService().getAll(pageable);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/collection")
    public ResponseEntity<Collection<DTO>> getAllById(
            @RequestParam("id") Collection<ID> idCollection
    ) {
        Collection<DTO> dtoCollection = getService().getAllById(idCollection);
        return ResponseEntity.ok(dtoCollection);
    }

    @PostMapping
    public ResponseEntity<DTO> create(
            @RequestBody DTO dto
    ) {
        DTO createdDto = getService().create(dto);
        return ResponseEntity.ok(createdDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> save(
            @PathVariable ID id,
            @RequestBody DTO dto
    ) {
        DTO updatedDto = getService().save(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/collection")
    public ResponseEntity<Collection<DTO>> saveAll(
            @RequestBody Collection<DTO> dtoCollection
    ) {
        Collection<DTO> updatedDtoCollection = getService().saveAll(dtoCollection);
        return ResponseEntity.ok(updatedDtoCollection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable ID id
    ) {
        getService().deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/collection")
    public ResponseEntity<?> deleteAllById(
            @RequestParam("id") Collection<ID> idCollection
    ) {
        getService().deleteAllById(idCollection);
        return ResponseEntity.ok().build();
    }

}
