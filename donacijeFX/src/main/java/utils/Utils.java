package utils;

import Exceptions.UserIsNotRegisteredException;
import Exceptions.UserIsRegisteredException;
import Exceptions.WrongPasswordException;
import com.example.donacijefx.HelloController;
import domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Utils extends Exception{

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private static final String DONOR_USERS = "dat/donorusers.txt";
    private static final String ALL_CHANGES = "dat/allchanges.dat";

    private static final String ORGANIZATION_USERS = "dat/organizationusers.txt";
    private static final String ALL_USER_NAMES = "dat/allusernames.txt";
    private static final String CURRENTLY_ACTIVE_DONOR_USER = "dat/currentlyactivedonoruser.txt";

    private static final String CURRENTLY_ACTIVE_ORGANIZATION_USER = "dat/currentlyactiveorganizationuser.txt";



    public static void saveDonorUser(List<Donor> donorList){
        File donorUsersFile = new File(DONOR_USERS);
        try(PrintWriter pw = new PrintWriter(donorUsersFile)){
            for(Donor donor: donorList){
                pw.println(donor.getUserId());
                pw.println(donor.getUserName());
                pw.println(donor.getPasswordHashCode());
                pw.println(donor.getDonorName());
                pw.println(donor.getDonorLastname());

                DonorInfo newInfo = donor.getInfo();
                pw.println(newInfo.getCity());
                pw.println(newInfo.getPostalCode());
                pw.println(newInfo.getStreet());
                pw.println(newInfo.getHouseNumber());
                pw.println(newInfo.getEmail());
                pw.println(newInfo.getPhoneNumber());
            }
        }catch (IOException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    public static List<Donor> getDonorUsersFromFile() {
        List<Donor> donorUsers = new ArrayList<>();
        File donorUsersFile = new File(DONOR_USERS);

        try (BufferedReader reader = new BufferedReader(new FileReader(donorUsersFile))) {

            String linija;
            while ((linija = reader.readLine()) != null) {
                int id = Integer.parseInt(linija);
                String userName = reader.readLine();
                String passwordHashCode = reader.readLine();
                String donorName = reader.readLine();
                String donorLastName = reader.readLine();
                String city = reader.readLine();
                int postalCode = Integer.parseInt(reader.readLine());
                String street = reader.readLine();
                int houseNumber = Integer.parseInt(reader.readLine());
                String email = reader.readLine();
                int phoneNumber = Integer.parseInt(reader.readLine());

                DonorInfo newInfo = new DonorInfo(city, street, postalCode, houseNumber, email, phoneNumber);
                Donor newDonor = new Donor(userName, passwordHashCode, donorName, donorLastName, newInfo, id);
                donorUsers.add(newDonor);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return donorUsers;
    }

    public static int getNextDonorUserId(){
        List<Donor> donorList = getDonorUsersFromFile();
        int rez = 0;
        if(!(donorList.isEmpty())){
            int lastDonorId = donorList.stream().map(i -> i.getUserId()).max((i1,i2)->i1.compareTo(i2)).get().intValue();
            rez = lastDonorId + 1;
        }else{
            rez = 1;
        }
        return rez;
    }

    public static boolean isDonorRegistered(String userName) {
        boolean boolValue = false;
        List<Donor> donorList= getDonorUsersFromFile();
        for(Donor donor : donorList){
            if(donor.getDonorUserName().equals(userName)){
                boolValue = true;
                break;
            }
        }
        return boolValue;
    }


    public static void saveDonorToActiveUserFile(Donor donor){
        File itemsFIle = new File(CURRENTLY_ACTIVE_DONOR_USER);
        itemsFIle.delete();
        try(PrintWriter pw = new PrintWriter(itemsFIle)){
            pw.println(donor.getUserId());
            pw.println(donor.getUserName());
            pw.println(donor.getPasswordHashCode());
            pw.println(donor.getDonorName());
            pw.println(donor.getDonorLastname());

            DonorInfo newInfo = donor.getInfo();
            pw.println(newInfo.getCity());
            pw.println(newInfo.getPostalCode());
            pw.println(newInfo.getStreet());
            pw.println(newInfo.getHouseNumber());
            pw.println(newInfo.getEmail());
            pw.println(newInfo.getPhoneNumber());
        }catch (IOException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }
    public static Donor getActiveDonorUser(){
        Donor currentlyActiveDonor = null;
        File categoryFile = new File(CURRENTLY_ACTIVE_DONOR_USER);
        try (BufferedReader reader = new BufferedReader(new FileReader(categoryFile))) {
            int id = Integer.parseInt(reader.readLine());
            String userName = reader.readLine();
            String passwordHashCode = reader.readLine();
            String donorName = reader.readLine();
            String donorLastName = reader.readLine();
            String city = reader.readLine();
            int postalCode = Integer.parseInt(reader.readLine());
            String street = reader.readLine();
            int houseNumber = Integer.parseInt(reader.readLine());
            String email = reader.readLine();
            int phoneNumber = Integer.parseInt(reader.readLine());

            DonorInfo newInfo = new DonorInfo(city,street,postalCode,houseNumber,email,phoneNumber);
            currentlyActiveDonor = new Donor(userName,passwordHashCode,donorName,donorLastName,newInfo,id);

        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return currentlyActiveDonor;
    }
    public static int getActiveDonorUserId(){
        Donor activeDonor = getActiveDonorUser();
        return activeDonor.getUserId();
    }

    public static boolean checkDonorsPassword(String userName,String password)throws WrongPasswordException{
        boolean boolValue = false;
        List<Donor> donorList= getDonorUsersFromFile();
        String passwordHashCode = String.valueOf(password.hashCode());
        for(Donor donor : donorList){
            if(donor.getDonorUserName().equals(userName) && donor.getDonorPasswordHashCode().equals(passwordHashCode)){
                boolValue = true;
                break;
            }
        }
        if(!boolValue){
            throw new WrongPasswordException("You entered the wrong password, try again!");
        }
        return boolValue;
    }
    public static Donor getDonor(String userName,String password){
        List<Donor> donorList= getDonorUsersFromFile();
        Donor wantedDonor = null;
        String passwordHashCode = String.valueOf(password.hashCode());
        for(Donor donor : donorList){
            if(donor.getDonorUserName().equals(userName) && donor.getDonorPasswordHashCode().equals(passwordHashCode)){
                wantedDonor = donor;
                break;
            }
        }
        return wantedDonor;
    }
    public static Donor getDonorById(int donorId){
        List<Donor> donorList= getDonorUsersFromFile();
        Donor wantedDonor = null;
        for(Donor donor : donorList){
            if(donor.getUserId() == donorId){
                wantedDonor = donor;
            }
        }
        return wantedDonor;
    }
    public static int getDonorId(String donorName){
        List<Donor> donorList= getDonorUsersFromFile();
        int id = 0;
        for(Donor donor : donorList){
            if(donor.getDonorName().equals(donorName)){
                id = donor.getUserId();
            }
        }
        return id;
    }

    public static void saveOrganizationUser(List<Organization> organizationsList){
        File organizationUsersFile = new File(ORGANIZATION_USERS);
        try(PrintWriter pw = new PrintWriter(organizationUsersFile)){
            for(Organization organization: organizationsList){
                pw.println(organization.getUserId());
                pw.println(organization.getUserName());
                pw.println(organization.getPasswordHashCode());
                pw.println(organization.getEmployerIdentificationNumber().EIN());
                pw.println(organization.getOrganizationInfo().getState());
                pw.println(organization.getOrganizationInfo().getCity());
                pw.println(organization.getOrganizationInfo().getStreet());
                pw.println(organization.getOrganizationInfo().getPostalCode());
                pw.println(organization.getOrganizationInfo().getEmail());
                pw.println(organization.getSocialMediaLinks().getFacebook());
                pw.println(organization.getSocialMediaLinks().getInstagram());
                pw.println(organization.getSocialMediaLinks().getTwitter());
                pw.println(organization.getEstablishmentInfo().getFounderName());

                LocalDate date = organization.getEstablishmentInfo().getFoundationDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = date.format(formatter);

                pw.println(formattedDate);
                pw.println(organization.getEstablishmentInfo().getIndustry());
            }
        }catch (IOException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }


   public static List<Organization> getOrganizationUsersFromFile(){
        List<Organization> organizationList = new ArrayList<>();
        File organizationUsersFile = new File(ORGANIZATION_USERS);

        try (BufferedReader reader = new BufferedReader(new FileReader(organizationUsersFile))) {
            String linija;
            while ((linija = reader.readLine()) != null) {
                int id = Integer.parseInt(linija);
                String userName = reader.readLine();
                String passwordHashCode = reader.readLine();
                int EIN = Integer.parseInt(reader.readLine());
                String state = reader.readLine();
                String city = reader.readLine();
                String street = reader.readLine();
                int postalCode = Integer.parseInt(reader.readLine());
                String email = reader.readLine();
                String facebook = reader.readLine();
                String instagram = reader.readLine();
                String twitter = reader.readLine();
                String founderName = reader.readLine();
                String date = reader.readLine();
                String industry = reader.readLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(date, formatter);
                EmployerIdentificationNumber employerIdentificationNumber = new EmployerIdentificationNumber(EIN);
                OrganizationInfo organizationInfo = new OrganizationInfo(city,street,postalCode,state,email);
                SocialMediaLinks socialMediaLinks = new SocialMediaLinks(facebook,instagram,twitter);
                EstablishmentInfo establishmentInfo = new EstablishmentInfo(founderName,localDate,industry);
                Organization organization = new Organization(userName,passwordHashCode,employerIdentificationNumber,organizationInfo,socialMediaLinks,establishmentInfo,id);
                organizationList.add(organization);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
       return organizationList;
    }

    public static int getNextOrganizaitonUserId(){
        List<Organization> organizationList = getOrganizationUsersFromFile();
        int rez = 0;
        if(!(organizationList.isEmpty())){
            int lastOrganizationUserId = organizationList.stream().map(i -> i.getUserId()).max((i1,i2)->i1.compareTo(i2)).get().intValue();
            rez = lastOrganizationUserId + 1;
        }else{
            rez = 1;
        }
        return rez;
    }

    public static boolean isOrganizationRegistered(String userName) {
        boolean boolValue = false;
        List<Organization> organizationList= getOrganizationUsersFromFile();
        for(Organization organization : organizationList){
            if(organization.getUserName().equals(userName)){
                boolValue = true;
                break;
            }
        }
        return boolValue;
    }

    public static void saveOrganizationToActiveUserFile(Organization organization){
        File organizationUsersFile = new File(CURRENTLY_ACTIVE_ORGANIZATION_USER);
        organizationUsersFile.delete();
        try(PrintWriter pw = new PrintWriter(organizationUsersFile)){
            pw.println(organization.getUserId());
            pw.println(organization.getUserName());
            pw.println(organization.getPasswordHashCode());
            pw.println(organization.getEmployerIdentificationNumber().EIN());
            pw.println(organization.getOrganizationInfo().getState());
            pw.println(organization.getOrganizationInfo().getCity());
            pw.println(organization.getOrganizationInfo().getStreet());
            pw.println(organization.getOrganizationInfo().getPostalCode());
            pw.println(organization.getOrganizationInfo().getEmail());
            pw.println(organization.getSocialMediaLinks().getFacebook());
            pw.println(organization.getSocialMediaLinks().getInstagram());
            pw.println(organization.getSocialMediaLinks().getTwitter());
            pw.println(organization.getEstablishmentInfo().getFounderName());

            LocalDate date = organization.getEstablishmentInfo().getFoundationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = date.format(formatter);

            pw.println(formattedDate);
            pw.println(organization.getEstablishmentInfo().getIndustry());
        }catch (IOException ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    public static Organization getActiveOrganizationUser(){
        Organization currentlyActiveOrganization = null;
        File organizationUsersFile = new File(CURRENTLY_ACTIVE_ORGANIZATION_USER);

        try (BufferedReader reader = new BufferedReader(new FileReader(organizationUsersFile))) {
            int id = Integer.parseInt(reader.readLine());
            String userName = reader.readLine();
            String passwordHashCode= reader.readLine();
            int EIN = Integer.parseInt(reader.readLine());
            String state = reader.readLine();
            String city = reader.readLine();
            String street = reader.readLine();
            int postalCode = Integer.parseInt(reader.readLine());
            String email = reader.readLine();
            String facebook = reader.readLine();
            String instagram = reader.readLine();
            String twitter = reader.readLine();
            String founderName = reader.readLine();
            String date = reader.readLine();
            String industry = reader.readLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            EmployerIdentificationNumber employerIdentificationNumber = new EmployerIdentificationNumber(EIN);
            OrganizationInfo organizationInfo = new OrganizationInfo(city,street,postalCode,state,email);
            SocialMediaLinks socialMediaLinks = new SocialMediaLinks(facebook,instagram,twitter);
            EstablishmentInfo establishmentInfo = new EstablishmentInfo(founderName,localDate,industry);
            currentlyActiveOrganization = new Organization(userName,passwordHashCode,employerIdentificationNumber,organizationInfo,socialMediaLinks,establishmentInfo,id);

        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
        return currentlyActiveOrganization;
    }

    public int getActiveOrganizationUserId(){
        Organization activeOrganization = getActiveOrganizationUser();
        return activeOrganization.getUserId();
    }

    public static boolean checkOrganizationUserPassword(String userName,String password) {
        boolean boolValue = false;
        List<Organization> organizationList = getOrganizationUsersFromFile();
        String passwordHashCode = String.valueOf(password.hashCode());
        for(Organization organization: organizationList){
            if(organization.getUserName().equals(userName) && organization.getPasswordHashCode().equals(passwordHashCode)){
                boolValue = true;
                break;
            }
        }
        if(!boolValue){
            throw new WrongPasswordException("You entered the wrong password, try again!");
        }
        return boolValue;
    }

    public static Organization getOrganization(String userName,String password){
        List<Organization> organizationList= getOrganizationUsersFromFile();
        Organization wantedOrganization = null;
        String passwordHashCode = String.valueOf(password.hashCode());
        for(Organization organization : organizationList){
            if(organization.getUserName().equals(userName) && organization.getPasswordHashCode().equals(passwordHashCode)){
                wantedOrganization = organization;
                break;
            }
        }
        return wantedOrganization;
    }
    public static void isOrganizationRegistered_Login(String username) throws UserIsNotRegisteredException{
        boolean boolValue = isOrganizationRegistered(username);
        if(!boolValue){
            throw new UserIsNotRegisteredException("Username is not registered!");
        }
    }


    public static void isOrganizationRegistered_Register(String username) throws  UserIsRegisteredException{
        boolean boolValue = isOrganizationRegistered(username);
        if(boolValue){
            throw new UserIsRegisteredException("The selected username is already registered!");
        }
    }
    public static void isDonorRegistered_Login(String username) throws UserIsNotRegisteredException{
        boolean boolValue = isDonorRegistered(username);
        if(!boolValue){
            throw new UserIsNotRegisteredException("Username is not registered!");
        }
    }
    public static void isDonorRegistered_Register(String username) throws UserIsRegisteredException{
        boolean boolValue = isDonorRegistered(username);
        if(boolValue){
            throw new UserIsRegisteredException("The selected username is already registered!");
        }
    }

    public static void saveChanges(NoteTheChange<String> noteTheChange){
        List<NoteTheChange<String>> changes = Utils.getAllChanges();
        changes.add(noteTheChange);
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ALL_CHANGES))){
            for(NoteTheChange<String> change : changes){
                out.writeObject(change);
            }
            out.close();
        } catch (IOException ex){
            logger.info(ex.getMessage());
        }

    }

    public static void clearActiveDonorUserFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CURRENTLY_ACTIVE_DONOR_USER));
            writer.write("");
            writer.close();
        } catch (IOException ex) {
            logger.info(ex.getMessage());
        }
    }
    public static void clearActiveOrganizationUserFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CURRENTLY_ACTIVE_ORGANIZATION_USER));
            writer.write("");
            writer.close();
        } catch (IOException ex) {
            logger.info(ex.getMessage());
        }
    }

    public static NoteTheChange<String> getFirstChange() {
        NoteTheChange<String> object = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ALL_CHANGES))) {
            object = (NoteTheChange<String>) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            logger.info(ex.getMessage());
        }
        return object;
    }

    public static List<NoteTheChange<String>> getAllChanges() {
        List<NoteTheChange<String>> changesList = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ALL_CHANGES))) {
            while (true) {
                try {
                    Object obj = in.readObject();
                    if (obj instanceof NoteTheChange) {
                        changesList.add((NoteTheChange<String>) obj);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            logger.info(ex.getMessage());
        } catch (IOException ex) {
            logger.info(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            logger.info(ex.getMessage());
        }
        return changesList;
    }







}
