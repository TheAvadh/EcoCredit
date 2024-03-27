package com.group1.ecocredit;

import com.group1.ecocredit.dto.UpdateProfileRequest;
import com.group1.ecocredit.dto.UpdateProfileResponse;
import com.group1.ecocredit.dto.UserDetailsResponse;
import com.group1.ecocredit.models.Address;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.implementations.UserServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.group1.ecocredit.dto.UpdateProfileResponse.ResponseType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UpdateProfileRequest updateProfileRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setFirstName("Chandler");
        user.setLastName("Bing");
        user.setEmail("chandler.bing@gmail.com");
        user.setPhoneNumber("1234567890");

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("City");
        address.setProvince("Province");
        address.setPostalCode("12345");
        address.setCountry("Country");
        user.setAddress(address);

        updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setId("1");
        updateProfileRequest.setFirstName("Geerthana");
        updateProfileRequest.setLastName("Kanagalingame");
        updateProfileRequest.setEmail("geerthu@gmail.com");
        updateProfileRequest.setPhoneNumber("9876543210");

        UpdateProfileRequest.AddressDTO addressDTO = new UpdateProfileRequest.AddressDTO();
        addressDTO.setStreet("Barrington Street");
        addressDTO.setCity("Halifax");
        addressDTO.setProvince("Nova Scotia");
        addressDTO.setPostalCode("54321");
        addressDTO.setCountry("Canada");
        updateProfileRequest.setAddress(addressDTO);
    }

    @Test
    void testLoadUserByUsername(){

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(user.getEmail());

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.userDetailsService().loadUserByUsername(user.getEmail());
        });
    }

    @Test
    void testUpdateProfileSuccess() throws MessagingException {

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        UpdateProfileResponse response = userService.updateProfile(updateProfileRequest);

        assertEquals(SUCCESS, response.getResponse());
        assertEquals("Geerthana", user.getFirstName());
        assertEquals("Kanagalingame", user.getLastName());
        assertEquals("geerthu@gmail.com", user.getEmail());
        assertEquals("9876543210", user.getPhoneNumber());

        Address updatedAddress = user.getAddress();
        assertNotNull(updatedAddress);
        assertEquals("Barrington Street", updatedAddress.getStreet());
        assertEquals("Halifax", updatedAddress.getCity());
        assertEquals("Nova Scotia", updatedAddress.getProvince());
        assertEquals("54321", updatedAddress.getPostalCode());
        assertEquals("Canada", updatedAddress.getCountry());

        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendProfileUpdateEmail(any(User.class));
    }

    @Test
    void testUpdateProfileUserNotFound() throws MessagingException {

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        UpdateProfileResponse response = userService.updateProfile(updateProfileRequest);

        assertEquals(USER_NOT_FOUND, response.getResponse());
        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendProfileUpdateEmail(any(User.class));

    }

    @Test
    void testUpdateProfileThrowsException() throws MessagingException {

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        Mockito.doThrow(new MessagingException()).when(emailService).sendProfileUpdateEmail(any(User.class));

        UpdateProfileResponse response = userService.updateProfile(updateProfileRequest);

        assertEquals(INTERNAL_SERVER_ERROR, response.getResponse());
        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendProfileUpdateEmail(any(User.class));
    }


    @Test
    void testGetUserByIdSuccess() {

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        UserDetailsResponse userDetails = userService.getUserById(1);

        assertNotNull(userDetails);
        assertEquals(1, userDetails.getId());
        assertEquals("Chandler", userDetails.getFirstName());
        assertEquals("Bing", userDetails.getLastName());
        assertEquals("chandler.bing@gmail.com", userDetails.getEmail());
        assertEquals("1234567890", userDetails.getPhoneNumber());

        Address userAddress = userDetails.getAddress();
        assertNotNull(userAddress);
        assertEquals("123 Main St", userAddress.getStreet());
        assertEquals("City", userAddress.getCity());
        assertEquals("Province", userAddress.getProvince());
        assertEquals("12345", userAddress.getPostalCode());
        assertEquals("Country", userAddress.getCountry());

        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetUserByIdUserNotFound() {

        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        UserDetailsResponse userDetails = userService.getUserById(1);

        assertNull(userDetails);
        verify(userRepository, times(1)).findById(anyInt());
    }
}
