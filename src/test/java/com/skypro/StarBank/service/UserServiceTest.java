package com.skypro.StarBank.service;

import com.skypro.StarBank.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserIdByName_whenUserExists_shouldReturnUUID() {
        // Подготовка
        UUID expectedId = UUID.randomUUID();
        when(userRepository.getUserIdByName("john")).thenReturn(Optional.of(expectedId));

        // Выполнение
        Optional<UUID> result = userService.getUserIdByName("john");

        // Проверка
        assertTrue(result.isPresent());
        assertEquals(expectedId, result.get());
        verify(userRepository, times(1)).getUserIdByName("john");
    }

    @Test
    void getUserIdByName_whenUserNotFound_shouldReturnEmptyOptional() {
        // Подготовка
        when(userRepository.getUserIdByName("unknown")).thenReturn(Optional.empty());

        // Выполнение
        Optional<UUID> result = userService.getUserIdByName("unknown");

        // Проверка
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).getUserIdByName("unknown");
    }

    @Test
    void getFullName_whenUserExists_shouldReturnFullName() {
        // Подготовка
        when(userRepository.getFullName("john")).thenReturn(Optional.of("John Doe"));

        // Выполнение
        Optional<String> result = userService.getFullName("john");

        // Проверка
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get());
        verify(userRepository, times(1)).getFullName("john");
    }

    @Test
    void getFullName_whenUserNotFound_shouldReturnEmptyOptional() {
        // Подготовка
        when(userRepository.getFullName("unknown")).thenReturn(Optional.empty());

        // Выполнение
        Optional<String> result = userService.getFullName("unknown");

        // Проверка
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).getFullName("unknown");
    }
}
