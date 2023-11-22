package com.ilyes.clients.services;


import com.ilyes.clients.dto.ClientDTO;
import com.ilyes.clients.dto.RegionDTO;
import com.ilyes.clients.entities.Region;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RegionService {

    List<RegionDTO> getAllRegions();

    Optional<RegionDTO> getRegionById(Long id);

    Region createRegion(Region region);
    Region updateRegion(Region region);

    void deleteRegion(Long id);

    List<ClientDTO> getClientsById(Long id);

    Page<RegionDTO> getAllRegionsParPage(long page, long size);

    RegionDTO convertEntityToDto(Region t);
    Region convertDtoToEntity(RegionDTO typeDto);
}
