package database;

import com.example.donacijefx.HelloController;
import domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class Database {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final static String DATABASE_FILE = "conf/database.properties";

    private static synchronized Connection connectToDatabase() throws SQLException, IOException {
        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE));
        String urlBazePodataka = svojstva.getProperty("databaseUrl");
        String korisnickoIme = svojstva.getProperty("username");
        String lozinka = svojstva.getProperty("password");
        Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme,lozinka);
        return veza;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to the database is closed.");
            } catch (SQLException e) {
                System.err.println("Error while closing connection: " + e.getMessage());
            }
        }
    }

    public static void saveDonation(Donation donation){
        try(Connection connection = connectToDatabase()){
            String insertItem = "INSERT INTO DONATION(AMOUNT,CREATION_DATE,DONOR_ID,FUNDED_PROJECT_ID) VALUES(?,?,?,?);";
            PreparedStatement pstmt = connection.prepareStatement(insertItem);
            pstmt.setDouble(1,donation.getDonationAmount());
            pstmt.setString(2,donation.getDonationDateAndTime());
            pstmt.setInt(3,donation.whatIsDonorId());
            pstmt.setInt(4,donation.whatIsFundedProjectId());
            pstmt.execute();

        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }
    public static void saveDonor(Donor donor){
        try(Connection connection = connectToDatabase()){
            String insertItem = "INSERT INTO DONOR(ID,NAME,LAST_NAME,CITY,EMAIL,PHONE_NUMBER) VALUES(?,?,?,?,?,?);";
            PreparedStatement pstmt = connection.prepareStatement(insertItem);
            pstmt.setInt(1,donor.getUserId());
            pstmt.setString(2,donor.getDonorName());
            pstmt.setString(3,donor.getDonorLastname());
            pstmt.setString(4,donor.getInfo().getCity());
            pstmt.setString(5,donor.getInfo().getEmail());
            pstmt.setString(6,String.valueOf(donor.getInfo().getPhoneNumber()));
            pstmt.execute();

        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }
    public static void saveMessage(String donationMessage,int projectId){
        try(Connection connection = connectToDatabase()){
            String insertItem = "INSERT INTO MESSAGE(PROJECT_ID,MESSAGE_TEXT) VALUES(?,?);";
            PreparedStatement pstmt = connection.prepareStatement(insertItem);
            pstmt.setInt(1,projectId);
            pstmt.setString(2,donationMessage);
            pstmt.execute();

        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }
    public static void saveOrganization(Organization organization){
        try(Connection connection = connectToDatabase()){
            String insertItem = "INSERT INTO ORGANIZATION(ID,EIN,NAME,EMAIL,CITY) VALUES(?,?,?,?,?);";
            PreparedStatement pstmt = connection.prepareStatement(insertItem);
            pstmt.setInt(1,organization.getUserId());
            pstmt.setInt(2,organization.getEmployerIdentificationNumber().EIN());
            pstmt.setString(3,organization.getUserName());
            pstmt.setString(4,organization.getOrganizationInfo().getEmail());
            pstmt.setString(5,organization.getOrganizationInfo().getCity());
            pstmt.execute();
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }

    public static List<Donation> getDonationsByDonorId(int checkDonorId){
        List<Donation> donationList = new ArrayList<>();

        try(Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM DONATION";
            Statement stmt = connection.createStatement();
            stmt.execute((sqlQuery));
            ResultSet rs = stmt.getResultSet();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while(rs.next()){
                int id = rs.getInt("ID");
                double donationAmount = rs.getDouble("AMOUNT");
                String creationDate = rs.getString("CREATION_DATE");
                LocalDateTime localDateTime = LocalDateTime.parse(creationDate,dateTimeFormatter);
                int donorId = rs.getInt("DONOR_ID");
                int fundedProjectId = rs.getInt("FUNDED_PROJECT_ID");
                if(donorId == checkDonorId){
                    donationList.add(new Donation(id,donationAmount,localDateTime,donorId,fundedProjectId));
                }
            }
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);

        }

        return donationList;
    }
    public static List<Donation> getDonationsByFundingProjectId(int checkFundingProjectId){
        List<Donation> donationList = new ArrayList<>();

        try(Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM DONATION";
            Statement stmt = connection.createStatement();
            stmt.execute((sqlQuery));
            ResultSet rs = stmt.getResultSet();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while(rs.next()){
                int id = rs.getInt("ID");
                double donationAmount = rs.getDouble("AMOUNT");
                String creationDate = rs.getString("CREATION_DATE");
                LocalDateTime localDateTime = LocalDateTime.parse(creationDate,dateTimeFormatter);
                int donorId = rs.getInt("DONOR_ID");
                int fundedProjectId = rs.getInt("FUNDED_PROJECT_ID");
                if(checkFundingProjectId ==fundedProjectId ){
                    donationList.add(new Donation(id,donationAmount,localDateTime,donorId,fundedProjectId));
                }
            }
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }

        return donationList;
    }

    public static List<Donation> getAllDonations(){
        List<Donation> donationList = new ArrayList<>();

        try(Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM DONATION";
            Statement stmt = connection.createStatement();
            stmt.execute((sqlQuery));
            ResultSet rs = stmt.getResultSet();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while(rs.next()){
                int id = rs.getInt("ID");
                double donationAmount = rs.getDouble("AMOUNT");
                String creationDate = rs.getString("CREATION_DATE");
                LocalDateTime localDateTime = LocalDateTime.parse(creationDate,dateTimeFormatter);
                int donorId = rs.getInt("DONOR_ID");
                int fundedProjectId = rs.getInt("FUNDED_PROJECT_ID");

                donationList.add(new Donation(id,donationAmount,localDateTime,donorId,fundedProjectId));

            }
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }

        return donationList;
    }

    public static List<Project> getAllProjects(){
        List<Project> projectList = new ArrayList<>();

        try(Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM PROJECT";
            Statement stmt = connection.createStatement();
            stmt.execute((sqlQuery));
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                int id = rs.getInt("ID");
                String projectName = rs.getString("PROJECT_NAME");
                String description = rs.getString("DESCRIPTION");
                double targetAmount = rs.getInt("TARGET_AMOUNT");
                double currentAmount = rs.getInt("CURRENT_AMOUNT");

                List<Donation> relatedDonations = getDonationsByFundingProjectId(id);

                projectList.add(new Project(id,projectName,description,targetAmount,currentAmount,relatedDonations));
            }
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }

        return projectList;
    }

    public static void saveNewProject(Project project){
        try(Connection connection = connectToDatabase()){
            String insertItem = "INSERT INTO PROJECT(PROJECT_NAME,DESCRIPTION,TARGET_AMOUNT,CURRENT_AMOUNT) VALUES(?,?,?,?);";
            PreparedStatement pstmt = connection.prepareStatement(insertItem);
            pstmt.setString(1,project.getProjectName());
            pstmt.setString(2,project.getDescription());
            pstmt.setDouble(3,project.getTargetAmount());
            pstmt.setDouble(4,project.getCurrentAmount());
            pstmt.execute();
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }

    public static void deleteDonation(int donationId){
        String sqlQuery = "DELETE FROM DONATION WHERE ID = ?";
        try (Connection connection = connectToDatabase()){
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
             pstmt.setInt(1, donationId);
             pstmt.executeUpdate();
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            ex.printStackTrace();
            logger.info(message);
        }

    }
    public static void deleteProject(int projectId){
        String sqlQuery = "DELETE FROM PROJECT WHERE ID = ?";
        try (Connection connection = connectToDatabase()){
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1, projectId);
            pstmt.executeUpdate();
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            ex.printStackTrace();
            logger.info(message);
        }

    }

    public static double getCurrentAmount(int projectId){
        double currentAmount = 0;
        try(Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM PROJECT";
            Statement stmt = connection.createStatement();
            stmt.execute((sqlQuery));
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                int id = rs.getInt("ID");
                if(id == projectId){
                    currentAmount = rs.getInt("CURRENT_AMOUNT");
                }
            }
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
        return currentAmount;
    }

    public static Project getProjectById(int checkId) {
        Project project = null;
        String sqlQuery = "SELECT * FROM PROJECT WHERE ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, checkId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("ID");
                    String projectName = rs.getString("PROJECT_NAME");
                    String description = rs.getString("DESCRIPTION");
                    double targetAmount = rs.getDouble("TARGET_AMOUNT");
                    double currentAmount = rs.getDouble("CURRENT_AMOUNT");
                    List<Donation> relatedDonations = getDonationsByFundingProjectId(id);
                    project = new Project(id, projectName, description, targetAmount, currentAmount, relatedDonations);
                }
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
        return project;
    }
    public static Donation getDonationById(int checkId) {
        Donation donation = null;
        String sqlQuery = "SELECT * FROM DONATION WHERE ID = ?";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection connection = connectToDatabase();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, checkId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()){
                    int id = rs.getInt("ID");
                    double donationAmount = rs.getDouble("AMOUNT");
                    String creationDate = rs.getString("CREATION_DATE");
                    LocalDateTime localDateTime = LocalDateTime.parse(creationDate,dateTimeFormatter);
                    int donorId = rs.getInt("DONOR_ID");
                    int fundedProjectId = rs.getInt("FUNDED_PROJECT_ID");
                    donation = new Donation(id,donationAmount,localDateTime,donorId,fundedProjectId);
                }
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
        return donation;
    }
    public static Optional<Project> getProjectByName(String name) {
        Optional<Project> optionalProject = Optional.empty();

        String sqlQuery = "SELECT * FROM PROJECT WHERE PROJECT_NAME = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("ID");
                    String projectName = rs.getString("PROJECT_NAME");
                    String description = rs.getString("DESCRIPTION");
                    double targetAmount = rs.getDouble("TARGET_AMOUNT");
                    double currentAmount = rs.getDouble("CURRENT_AMOUNT");
                    List<Donation> relatedDonations = getDonationsByFundingProjectId(id);
                    optionalProject = Optional.of(new Project(id,projectName,description,targetAmount,currentAmount,relatedDonations));
                }
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
        return optionalProject;
    }

    public static void updateDonationFundingProject(int donationId, int newProjectId){

        try (Connection connection = connectToDatabase()) {
            String updateDonation = "UPDATE DONATION SET FUNDED_PROJECT_ID = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateDonation)) {
                pstmt.setInt(1,newProjectId);
                pstmt.setInt(2, donationId);

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }
    public static void updateDonationAmount(int donationId, double donationAmount){

        try (Connection connection = connectToDatabase()) {
            String updateDonation = "UPDATE DONATION SET AMOUNT = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateDonation)) {
                pstmt.setDouble(1,donationAmount);
                pstmt.setInt(2, donationId);

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }
    public static void updateProjectName(int projectId, String newName){

        try (Connection connection = connectToDatabase()) {
            String updateProject = "UPDATE PROJECT SET PROJECT_NAME = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateProject)) {
                pstmt.setString(1,newName);
                pstmt.setInt(2, projectId);

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }
    public static void updateProjectDescription(int projectId, String newDescription){

        try (Connection connection = connectToDatabase()) {
            String updateProject = "UPDATE PROJECT SET DESCRIPTION = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateProject)) {
                pstmt.setString(1,newDescription);
                pstmt.setInt(2, projectId);

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }
    public static void updateProjectTargetAmount(int projectId, double newTargetAmount){

        try (Connection connection = connectToDatabase()) {
            String updateProject = "UPDATE PROJECT SET TARGET_AMOUNT = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateProject)) {
                pstmt.setDouble(1,newTargetAmount);
                pstmt.setInt(2, projectId);

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }

    public static void updateProjectCurrentAmountAfterDeletingDonation(Donation donation){
        Project project = Database.getProjectById(donation.whatIsFundedProjectId());
        double newAmount = project.getCurrentAmount() - donation.getDonationAmount();
        try (Connection connection = connectToDatabase()) {
            String updateProject = "UPDATE PROJECT SET CURRENT_AMOUNT = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateProject)) {
                pstmt.setDouble(1,newAmount);
                pstmt.setInt(2, donation.whatIsFundedProjectId());

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }
    public static void updateProjectCurrentAmountAfterEditingDonation(Donation donation, double newAmount){
        Project project = Database.getProjectById(donation.whatIsFundedProjectId());

        double result = project.getCurrentAmount() - (donation.getDonationAmount() - newAmount);

        try (Connection connection = connectToDatabase()) {
            String updateProject = "UPDATE PROJECT SET CURRENT_AMOUNT = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateProject)) {
                pstmt.setDouble(1,result);
                pstmt.setInt(2, donation.whatIsFundedProjectId());

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }

    public static void addDonationAmountToProject(Donation donation, int projectId){

        Project project = Database.getProjectById(projectId);

        double newAmount = project.getCurrentAmount() + donation.getDonationAmount();
        try (Connection connection = connectToDatabase()) {
            String updateProject = "UPDATE PROJECT SET CURRENT_AMOUNT = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateProject)) {
                pstmt.setDouble(1,newAmount);
                pstmt.setInt(2, projectId);

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }

    public static void addDonationToProject(Donation donation,int projectId){
        double currentAmount = getCurrentAmount(projectId);
        try (Connection connection = connectToDatabase()) {
            String updateCategory = "UPDATE PROJECT SET CURRENT_AMOUNT = ? WHERE ID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(updateCategory)) {
                pstmt.setDouble(1,currentAmount+donation.getDonationAmount());
                pstmt.setInt(2, projectId);

                int rowsUpdated = pstmt.executeUpdate();
            }
        } catch (SQLException | IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }

    }

    public static void saveSuspendedUserInfo(Donor donor, Organization organization, String reason){
        try(Connection connection = connectToDatabase()){
            String insertItem = "INSERT INTO SUSPENDEDUSERS(SUSPENDED_BY,SUSPENDED_USER_ID,SUSPENSION_REASON) VALUES(?,?,?);";
            PreparedStatement pstmt = connection.prepareStatement(insertItem);
            pstmt.setInt(1,donor.getUserId());
            pstmt.setInt(2,donor.getUserId());
            pstmt.setString(3,reason);
            pstmt.execute();

        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }

    public static void removeUserFromSuspensionList(Donor donor){
        try(Connection connection = connectToDatabase()){
            String insertItem = "DELETE FROM SUSPENDEDUSERS WHERE SUSPENDED_USER_ID = ?";
            PreparedStatement pstmt = connection.prepareStatement(insertItem);
            pstmt.setInt(1,donor.getUserId());
            pstmt.execute();
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
    }

    public static SuspendedUsers getAllSuspendedUsers(){
        SuspendedUsers suspendedUsers = new SuspendedUsers();

        try(Connection connection = connectToDatabase()){
            String sqlQuery = "SELECT * FROM SUSPENDEDUSERS";
            Statement stmt = connection.createStatement();
            stmt.execute((sqlQuery));
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                int suspension_id = rs.getInt("SUSPENSION_ID");
                int organization_id = rs.getInt("SUSPENDED_BY");
                int donor_id = rs.getInt("SUSPENDED_BY");
                String reason = rs.getString("SUSPENSION_REASON");

                suspendedUsers.suspendedUsersMap.put(donor_id,organization_id);
                suspendedUsers.reasonsMap.put(donor_id,reason);
            }
        }catch (SQLException | IOException ex){
            String message = ex.getMessage();
            System.out.println(message);
            logger.info(message);
        }
        return suspendedUsers;
    }





}
