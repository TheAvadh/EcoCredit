package com.group1.ecocredit.services.implementations;


import com.group1.ecocredit.dto.UpdateProfileRequest;
import com.group1.ecocredit.dto.UpdateProfileResponse;
import com.group1.ecocredit.dto.UserDetailsResponse;
import com.group1.ecocredit.enums.Role;
import com.group1.ecocredit.models.Address;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.UserService;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.ConfirmationTokenService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.group1.ecocredit.dto.UpdateProfileResponse.ResponseType.*;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements com.group1.ecocredit.services.UserService {
    private final UserService userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }


    // User profile update.
    @Override
    public UpdateProfileResponse updateProfile(UpdateProfileRequest updateProfileRequest) {
        UpdateProfileResponse updateProfileResponse = new UpdateProfileResponse();

        User user = userRepository.findById(Integer.valueOf(updateProfileRequest.getId())).orElse(null);

        try
        {
            if (user == null)
            {
                updateProfileResponse.setResponse(USER_NOT_FOUND);
            }
            else
            {
                user.setFirstName(updateProfileRequest.getFirstName());
                user.setLastName(updateProfileRequest.getLastName());
                user.setEmail(updateProfileRequest.getEmail());
                user.setPhoneNumber(updateProfileRequest.getPhoneNumber());
                UpdateProfileRequest.AddressDTO addressDTO = updateProfileRequest.getAddress();
                if (addressDTO != null)
                {
                    Address userAddress = user.getAddress();

                    if (userAddress == null)
                    {
                        userAddress = new Address();
                    }

                    userAddress.setStreet(addressDTO.getStreet());
                    userAddress.setCity(addressDTO.getCity());
                    userAddress.setProvince(addressDTO.getProvince());
                    userAddress.setPostalCode(addressDTO.getPostalCode());
                    userAddress.setCountry(addressDTO.getCountry());

                    user.setAddress(userAddress);
                }

                userRepository.save(user);
                emailService.sendProfileUpdateEmail(user);
                updateProfileResponse.setResponse(SUCCESS);
            }
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
            updateProfileResponse.setResponse(INTERNAL_SERVER_ERROR);
        }
        return updateProfileResponse;
    }

    @Override
    public UserDetailsResponse getUserById(Integer userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user != null) {
                UserDetailsResponse userDTO = new UserDetailsResponse();
                userDTO.setId(user.getId());
                userDTO.setFirstName(user.getFirstName());
                userDTO.setLastName(user.getLastName());
                userDTO.setEmail(user.getEmail());
                userDTO.setPhoneNumber(user.getPhoneNumber());
                userDTO.setAddress(user.getAddress());

                return userDTO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByRole(Role role) {
        return userRepository.findByRole(role);
    }
}
