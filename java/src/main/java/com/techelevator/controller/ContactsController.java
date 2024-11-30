package com.techelevator.controller;

import com.techelevator.Services.S3Service;
import com.techelevator.dao.ContactsDao;
import com.techelevator.dao.ProfilePictureDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Contacts;
import com.techelevator.model.ProfilePicture;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin
public class ContactsController {

    private final ContactsDao contactsDao;
    private final S3Service s3Service;
    @Autowired
    private final UserDao userDao;

    public ContactsController(ContactsDao contactsDao, UserDao userDao, S3Service s3Service){
        this.contactsDao = contactsDao;
        this.userDao = userDao;
        this.s3Service = s3Service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/new-contact",consumes = {"multipart/form-data"})
    public void createContact(Principal principal, @ModelAttribute Contacts contacts,
                              @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture){
        System.out.println("Received contact: " + contacts);
        System.out.println("Principal: " + (principal != null ? principal.getName() : "No principal"));

        if(principal != null ) {
            System.out.println(principal.getName());
            User user = userDao.getUserByUsername(principal.getName());

            if (user.getAuthorities().contains("ROLE_USER") && !user.getAuthorities().contains("ROLE_ADMIN")) {
                int contactCount = contactsDao.getContactCountForUser(user.getId());
                if (contactCount >= 4) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact Limit reached");
                }
            }

            int contactId = contactsDao.createContact(user, contacts);
            contacts.setContactId(contactId);

            if(profilePicture != null && !profilePicture.isEmpty()){
                System.out.println(principal.getName());
                String profilePictureUrl = s3Service.uploadFile("profile-images/"+contactId,profilePicture);
                //update contact with profile picture URL
                contacts.setProfilePictureUrl(profilePictureUrl);
                contactsDao.updateProfilePictureUrl(contactId, profilePictureUrl);
            }
        }
        else{
            System.out.println("User is not logged in");
        }
    }
    @GetMapping( path ="/contacts-list/{userId}")
    public List<Contacts> getContactsForCurrentUser(@PathVariable int userId){
        return contactsDao.getContactsForCurrentUser(userId);
    }
//    @RequestMapping(path ="/update-contact/{contactId}", method = RequestMethod.PUT)
//    public void updateContact(@RequestBody Contacts contacts, @PathVariable int contactId){
//        contactsDao.updateContact(contactId,contacts);
//    }
//    original below
//    @PutMapping(path="/update-contact/{contactId}", consumes = {"multipart/form-data"})
//    public void updateContact(@PathVariable int contactId, @ModelAttribute Contacts contacts,
//                              @RequestPart(value="profilePicture", required=false) MultipartFile profilePicture){
//
//        contactsDao.updateContact(contactId, contacts);
//        System.out.println("Updating contact: " + contacts);
//        System.out.println("Received profile picture: " + profilePicture);
//
//        if(profilePicture !=null && !profilePicture.isEmpty()){
//            String profilePictureUrl = s3Service.uploadFile("profile-images/"+contactId,profilePicture);
//            contacts.setProfilePictureUrl(profilePictureUrl);
//            contactsDao.updateContact(contactId, contacts);
//
//        }
//    }
@PutMapping(path = "/update-contact/{contactId}", consumes = {"multipart/form-data"})
public void updateContact(
        @PathVariable int contactId,
        Principal principal,
        @ModelAttribute Contacts contacts,
        @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {


    System.out.println("Updating contact: " + contacts);
    System.out.println("Contact ID: " + contacts.getContactId());
    System.out.println("User ID: " + contacts.getUserId());
    System.out.println("Name: " + contacts.getName());
    System.out.println("Email: " + contacts.getEmail());
    System.out.println("Profile Picture: " + (profilePicture != null ? profilePicture.getOriginalFilename() : "None"));
    // Ensure contactId is set
    User user = userDao.getUserByUsername(principal.getName());
//    contacts.setContactId(contactId);
    contacts.setUserId(user.getId());
    // Update contact in database


    // Handle profile picture upload
//    if (profilePicture != null && !profilePicture.isEmpty()) {
//        String profilePictureUrl = s3Service.uploadFile("profile-images/" + contactId, profilePicture);
//        contactsDao.updateProfilePictureUrl(contactId, profilePictureUrl);
//    }
    if (profilePicture == null || profilePicture.isEmpty()) {
        String existingProfilePictureUrl = contactsDao.getProfilePictureUrl(contactId);
        contacts.setProfilePictureUrl(existingProfilePictureUrl);
    } else {
        // Upload new profile picture and set the URL
        String profilePictureUrl = s3Service.uploadFile("profile-images/" + contactId, profilePicture);
        contacts.setProfilePictureUrl(profilePictureUrl);
        contactsDao.updateProfilePictureUrl(contactId, profilePictureUrl);
    }
    contactsDao.updateContact(contactId, contacts);

    System.out.println("Received contact: " + contacts);
    System.out.println("Received profile picture: " + (profilePicture != null ? profilePicture.getOriginalFilename() : "None"));
}

    @RequestMapping(path="/delete-contact/{contactId}", method = RequestMethod.DELETE)
    public void deleteContact(@PathVariable int contactId){
        contactsDao.deleteContact(contactId);
    }


}
