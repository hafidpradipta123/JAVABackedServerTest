package com.getarrays.server.service.implementation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.getarrays.server.enumeration.Status;
import com.getarrays.server.model.Server;
import com.getarrays.server.repo.ServerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ServerServiceImplTest {
    @Mock
    private ServerRepo serverRepo;

    @InjectMocks
    private ServerServiceImpl serverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Arrange
        Server server = new Server();
        when(serverRepo.save(server)).thenReturn(server);

        // Act
        Server result = serverService.create(server);

        // Assert
        assertEquals(server, result);
        verify(serverRepo, times(1)).save(server);
    }

    @Test
    void testPing() throws IOException {
        // Arrange
        String ipAddress = "192.168.1.1";
        Server server = new Server();
        server.setIpAddress(ipAddress);
        InetAddress address = mock(InetAddress.class);
        when(address.isReachable(10000)).thenReturn(true);
        when(serverRepo.findByIpAddress(ipAddress)).thenReturn(server);
        when(InetAddress.getByName(ipAddress)).thenReturn(address);
        when(serverRepo.save(server)).thenReturn(server);

        // Act
        Server result = serverService.ping(ipAddress);

        // Assert
        assertEquals(Status.SERVER_UP, result.getStatus());
        verify(serverRepo, times(1)).findByIpAddress(ipAddress);
        verify(serverRepo, times(1)).save(server);
    }

    @Test
    void testList() {

    }

    @Test
    void testGet() {
        // Arrange
        Long id = 1L;
        Server server = new Server();
        when(serverRepo.findById(id)).thenReturn(java.util.Optional.of(server));

        // Act
        Server result = serverService.get(id);

        // Assert
        assertEquals(server, result);
        verify(serverRepo, times(1)).findById(id);
    }

    @Test
    void testUpdate() {
        // Arrange
        Server server = new Server();
        when(serverRepo.save(server)).thenReturn(server);

        // Act
        Server result = serverService.update(server);

        // Assert
        assertEquals(server, result);
        verify(serverRepo, times(1)).save(server);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;

        // Act
        Boolean result = serverService.delete(id);

        // Assert
        assertEquals(null, result);
        verify(serverRepo, times(1)).deleteById(id);
    }
}