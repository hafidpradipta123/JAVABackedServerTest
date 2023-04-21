package com.getarrays.server.repo;

import com.getarrays.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<Server, Long> { //what class do we want to repo and what is the prim key
    Server findByIpAddress(String ipAddress); //extends JPA Repository



}
