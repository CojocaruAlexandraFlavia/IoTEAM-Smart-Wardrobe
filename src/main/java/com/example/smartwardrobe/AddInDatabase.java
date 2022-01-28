//package com.example.smartwardrobe;
//
//import com.example.smartwardrobe.enums.EyeColor;
//import com.example.smartwardrobe.enums.Gender;
//import com.example.smartwardrobe.enums.HairColor;
//import com.example.smartwardrobe.model.User;
//import com.example.smartwardrobe.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//
//@Component
//public class AddInDatabase implements ApplicationListener<ContextRefreshedEvent> {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private boolean added= false;
//
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if(!added){
//            User user = new User();
//            user.setPassword(passwordEncoder.encode("user1"));
//            user.setUsername("user1");
//            user.setAge(15);
//            user.setGender(Gender.FEMALE);
//            user.setHeight(1.75);
//            user.setEyeColor(EyeColor.BLUE);
//            user.setHairColor(HairColor.BLONDE);
//            user.setWeight(65.5);
//            userRepository.save(user);
//            added = true;
//        }
//    }
//}
