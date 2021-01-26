package com.banger.bangerapi.Service;

import com.banger.bangerapi.Exception.CustomException;
import com.banger.bangerapi.Models.Authority;
import com.banger.bangerapi.Models.AuthorityType;
import com.banger.bangerapi.Models.Booking;
import com.banger.bangerapi.Models.DTO.DashboardDetailsDTO;
import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Repository.BookingRepository;
import com.banger.bangerapi.Repository.UserRepository;
import com.banger.bangerapi.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${C:\\Users\\SHAAKIRA\\Desktop\\EIRLS\\Banger\\bangerapi\\src\\main\\webapp\\resources\\Image}")
    public String uploadDir;


    @Autowired
    public UserService() {

    }

    @Autowired
    private AuthorityService authorityService;


    public ResponseEntity<?> googleLogin(User users) {
        if (userRepository.existsByUserName(users.getUserName())) {
            return  null;
        }
        if (userRepository.existsByEmail(users.getEmail())) {
            return null;
        } else {
            try {
                User user = new User();
                user.setEmail(users.getEmail());
                user.setUserName(users.getUserName());
                user.setCustomerName(users.getCustomerName());
                user.setPassword(bCryptPasswordEncoder.encode("default"));


                Set<Authority> mappedAuthorities = new HashSet<>();

                Authority authority = authorityService.getRoleByName(AuthorityType.ROLE_CUSTOMER);
                mappedAuthorities.add(authority);
                user.setAuthorities(mappedAuthorities);


                userRepository.save(user);

                //create a new user account after the checking
                return ResponseEntity.ok("User Registered Successfully");
            } catch (Exception e) {
                throw  new CustomException ("Error Response" + e,HttpStatus.BAD_REQUEST);

            }
        }
    }

    public ResponseEntity<?> registerUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new CustomException("Username is Already taken", HttpStatus.BAD_REQUEST);
        }
       else if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException("User Email is Already Taken!", HttpStatus.BAD_REQUEST);
        }
        else if(user.getPassword().length()<6){
            throw new CustomException("Password should have 6 or more characters", HttpStatus.BAD_REQUEST);
        }
        else {
            User userObject = new User(user.getCustomerName(),
                    user.getUserName(),
                    bCryptPasswordEncoder.encode(user.getPassword()),
                    user.getEmail()
            );
            Set<Authority> mappedAuthorities = new HashSet<>();
            Authority authority = authorityService.getRoleByName(AuthorityType.ROLE_CUSTOMER);
            mappedAuthorities.add(authority);
            userObject.setAuthorities(mappedAuthorities);
            userRepository.save(userObject);
            return  ResponseEntity.ok(true);
        }

    }

    public User getUser(String username) {
        User user = userRepository.findByUserName(username);
        return user;
    }
    public User getUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public ResponseEntity<String> updateUser(User user, String username) {
        User existingUser = userRepository.findByUserName(username);
        existingUser.setCustomerName(user.getCustomerName());
        existingUser.setEmail(user.getEmail());
        userRepository.save(existingUser);
        return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> updateDocuments(MultipartFile[] files, String username) {
        User existingUser = userRepository.findByUserName(username);
        for (int i = 0; i < files.length; i++) {
            upload(files[i]);
        }
        existingUser.setLicenseImage(files[0].getOriginalFilename());
        existingUser.setUtilityImage(files[1].getOriginalFilename());

        userRepository.save(existingUser);
        return new ResponseEntity<>("User Documents Updated Successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> updatePassword(User user, String username) {
        User existingUser = userRepository.findByUserName(username);
        existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(existingUser);
        return new ResponseEntity<>("Password Updated Successfully", HttpStatus.OK);
    }

    public List<User> getActiveUsers() {
        return userRepository.findActiveUsers();
    }

    @Transactional
    public ResponseEntity<String> blacklistUser(int id) {
        userRepository.blacklist(id);
        return new ResponseEntity<>("user blacklisted", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> activateUser(int id) {
        userRepository.activate(id);
        return new ResponseEntity<>("user activated", HttpStatus.OK);
    }

    public List<User> getBlacklistedUsers() {
        return userRepository.findBlacklistUsers();
    }

    public void upload(MultipartFile file) {
        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {

        }

    }

    public DashboardDetailsDTO getDashBoardDetails() {
        double confirmed=0;
        double collected=0;
        List<Booking> confirmedBooking=bookingRepository.newBookings();
        for (Booking b:
            confirmedBooking ) {
            confirmed=confirmed+Double.parseDouble(b.getTotal());
        }
        List<Booking> collectedBooking=bookingRepository.ongoingBookings();
        for (Booking b:
                collectedBooking ) {
            collected=collected+Double.parseDouble(b.getTotal());
        }
        double total=collected+confirmed;
        DashboardDetailsDTO dto = new DashboardDetailsDTO();
        int newBooking = bookingRepository.newBookings().size();
        dto.setNewBookingCount(newBooking);
        dto.setVehicleCount(vehicleRepository.findAll().size());
        dto.setCustomersCount(userRepository.findActiveUsers().size());
        dto.setAvailableVehicleCount(vehicleRepository.availableVehicle().size());
        dto.setOnRentCount(vehicleRepository.unAvailableVehicle().size());
        dto.setEstimation(total);
        return dto;
    }
    public ResponseEntity<String> deleteUser(String username) {
        User user=userRepository.findByUserName(username);
        if(user!=null){
            List<Booking> booking=bookingRepository.findByUser(user);
            for (Booking b:booking) {
                if(b.getStatus().equals("confirmed")||b.getStatus().equals("collected")){
                    throw  new CustomException("Cannot delete user. User has bookings onGoing  or up coming. to delete the user cancel the orders",HttpStatus.BAD_REQUEST);
                }
            }
           userRepository.delete(user);
        }
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
