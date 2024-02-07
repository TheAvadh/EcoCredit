package com.group1.ecocredit.services.implementations;


import com.group1.ecocredit.dto.UpdateProfileRequest;
import com.group1.ecocredit.dto.UpdateProfileResponse;
import com.group1.ecocredit.models.Address;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.repositories.UserRepository;
import com.group1.ecocredit.services.EmailService;
import com.group1.ecocredit.services.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.group1.ecocredit.dto.UpdateProfileRequest;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

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

        if (user != null) {
            user.setFirstname(updateProfileRequest.getFirstname());
            user.setLastname(updateProfileRequest.getLastname());
            user.setEmail(updateProfileRequest.getEmail());
            user.setPhone_number(updateProfileRequest.getPhone_number());
            UpdateProfileRequest.AddressDTO addressDTO = updateProfileRequest.getAddress();
            if (addressDTO != null) {
                Address userAddress = user.getAddress();

                if (userAddress == null) {
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

            try {
                emailService.sendProfileUpdateNotification(user);
                updateProfileResponse.setResponse("User profile updated. Email sent successfully");
            } catch (MessagingException e) {
                e.printStackTrace();
                updateProfileResponse.setResponse("User profile updated. Email sending failed: " + e.getMessage());
            }
        } else {
            updateProfileResponse.setResponse("User not found");
        }
        return updateProfileResponse;
    }
}
