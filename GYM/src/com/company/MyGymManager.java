package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/** MyGymManager extends the JavaFX Application and implements GymManager **/
public class MyGymManager extends Application
        implements GymManager, AutoCloseable {

    public static DefaultMember[] members = new DefaultMember[100];
    public static int noOfMembers = 0;
    private static TableView tableView;
    private static Stage window = null;

    /** method for add a new member **/
    @Override
    public String addNewMember() {
        MEMBER_TYPE type;
        DefaultMember member = null;
        int option;
        boolean flag = false;
        Scanner scanner = new Scanner(System.in);
        if(noOfMembers < 100) {
            System.out.println("> Add new member : ");
            System.out.println("> Select Membership Type : ");
            System.out.println("> 1. DefaultMember");
            System.out.println("> 2. StudentMember ");
            System.out.println("> 3. Over60Member");
            System.out.print("> Select membership type : ");
            option = scanner.nextInt();
            switch (option) {
                case 1 : {
                    flag = true;
                    type = MEMBER_TYPE.DEFAULT_MEMBER;
                    member = new DefaultMember();
                    break;
                }
                case 2 : {
                    flag = true;
                    type = MEMBER_TYPE.STUDENT_MEMBER;
                    member = new StudentMember();
                    break;
                }
                case 3 : {
                    flag = true;
                    type = MEMBER_TYPE.OVER_60_MEMBER;
                    member = new Over60Member();
                    break;
                }
                default:
                    return  "> Invalid membership type!";
            }

            if (flag) {
                try{
                    System.out.print("> Enter membership number : ");
                    member.setMembershipNumber(scanner.nextInt());
                    System.out.print("> Enter member name : ");
                    scanner.nextLine();
                    member.setName(scanner.nextLine());
                    try {
                        Date date = new Date();
                        System.out.println("> Enter start membership date");
                        System.out.print("> Year : ");
                        date.setYear(scanner.nextInt());
                        System.out.print("> Month : ");
                        date.setMonth(scanner.nextInt());
                        System.out.print("> Date : ");
                        date.setDate(scanner.nextInt());
                        member.setStartMembershipDate(date);
                        switch (type) {
                            case STUDENT_MEMBER: {
                                System.out.print("> Enter school name : ");
                                scanner.nextLine();
                                String schoolName = scanner.nextLine();
                                ((StudentMember) member).setSchoolName(schoolName);
                                break;
                            }
                            case OVER_60_MEMBER: {
                                try {
                                    System.out.print("> Enter age : ");
                                    scanner.nextLine();
                                    int age = scanner.nextInt();
                                    ((Over60Member) member).setAge(age);
                                } catch (InputMismatchException e) {
                                    return "> Invalid input type for age!";
                                }
                                break;
                            }
                        }
                        for(int i=0;i<noOfMembers;i++) {
                            if(member.getMembershipNumber() == members[i].getMembershipNumber()){
                                return "> The given membership number already exists!";
                            }
                        }
                        members[noOfMembers] = member;
                        noOfMembers++;
                        return "> The member is registered successfully!";
                    } catch (InputMismatchException e) {
                        return "> Invalid date!";
                    }
                }catch (InputMismatchException e){
                    return "> Membership should contains only digits!";
                }
            }
        }
        return "> There is no space available!";
    }

    /** method for delete a member **/
    @Override
    public String deleteMember() {
        System.out.println("> Delete member : ");
        int membershipNo;
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.print("> Enter membership number : ");
            membershipNo = scanner.nextInt();
            for(int i=0;i<noOfMembers;i++) {
                if(members[i].getMembershipNumber() == membershipNo){
                    Date date = members[i].getStartMembershipDate();
                    System.out.println("> Name : " + members[i].getName());
                    System.out.println("> Start Membership Date : " + date.toString());
                    if(members[i] instanceof StudentMember){
                        System.out.println("> Membership Type - StudentMember");
                    }
                    else if(members[i] instanceof Over60Member){
                        System.out.println("> Membership Type - Over60Member");
                    }else {
                        System.out.println("> Membership Type - DefaultMember");
                    }
                    shiftMembersArrayToLeftByOneMember(i);
                    return membershipNo + " has been deleted successfully!\nAvailable spaces : "  + (100 -noOfMembers);
                }
            }
            return "> The given membership number doesn't exits!";
        }catch(InputMismatchException e){
            return "> Invalid membership number!";
        }
    }

    /** method for print all the members in the system **/
    @Override
    public void getAllMembers() {
        System.out.println("> Registered Members");
        if(noOfMembers != 0) {
            for(int i=0;i<noOfMembers; i++){
                System.out.println("> Member - " + (i+1));
                System.out.println("> Membership Number - " + members[i].getMembershipNumber());
                System.out.println("> Member Name - " + members[i].getName());
                Date date = members[i].getStartMembershipDate();
                System.out.println("> Start Membership Date - " + date.toString());
                if(members[i] instanceof StudentMember){
                    System.out.println("> Membership Type - StudentMember");
                    System.out.println("> School Name - " + ((StudentMember) members[i]).getSchoolName());
                }
                else if(members[i] instanceof Over60Member){
                    System.out.println("> Membership Type - Over60Member");
                    System.out.println("> Age - "  + ((Over60Member) members[i]).getAge());
                }else {
                    System.out.println("> Membership Type - DefaultMember");
                }
            }
        } else {
            System.out.println("> Empty members in the system!");
        }
    }

    /** method for sort members by name in ascending order **/
    @Override
    public void sortMembers() {
        System.out.println("> Sorting members by name : ");
        // Bubble Sort Algorithm
        for(int i=0;i<noOfMembers-1;i++){
            for(int j=0;j<noOfMembers-i-1;j++){
                swap(j);
            }
        }
    }

    /** method for save data in a text file **/
    @Override
    public void saveData() {
        FileWriter fileWriter;
        DefaultMember member = null;
        File file = new File("output.txt");
        try{
            if(file.exists()){
                file.delete();
            }
            file.createNewFile(); // Create new text file
            fileWriter = new FileWriter("output.txt");
            for(int i=0;i<noOfMembers;i++){
                member = members[i];
                MEMBER_TYPE membershipType;
                if(member instanceof StudentMember){
                    membershipType = MEMBER_TYPE.STUDENT_MEMBER;
                    fileWriter.write(membershipType.name() +"|"+member.getMembershipNumber()+"|"+member.getName()+"|"+member.getStartMembershipDate()+"|"+((StudentMember) member).getSchoolName()+"\n");
                }else if(member instanceof Over60Member){
                    membershipType = MEMBER_TYPE.OVER_60_MEMBER;
                    fileWriter.write(membershipType.name() +"|"+member.getMembershipNumber()+"|"+member.getName()+"|"+member.getStartMembershipDate()+"|"+((Over60Member) member).getAge()+"\n");
                }else{
                    membershipType = MEMBER_TYPE.DEFAULT_MEMBER;
                    fileWriter.write(membershipType.name() +"|"+member.getMembershipNumber()+"|"+member.getName()+"|"+member.getStartMembershipDate()+"\n");
                }
            }
            fileWriter.close();
            System.out.print("Saved!\n");
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /** method for open UI **/
    @Override
    public void openGUI() {
        if (window != null) {
            window.showAndWait();
        } else  {
            buildUI();
            openGUI();
        }
    }

    /** main method **/
    public static void main(String[] args) {
        launch(args);
    }

    /** application start method **/
    public void start(Stage primaryStage) {
        retrieveSavedMembers();
        tableView = new TableView();
        Scanner scanner = new Scanner(System.in);
        System.out.println("> ---- Welcome to Gym Management System ----\n\n");
        while (true) {
            System.out.println("> 1. Add new member");
            System.out.println("> 2. Delete a member");
            System.out.println("> 3. Print all members");
            System.out.println("> 4. Sort all members");
            System.out.println("> 5. Save data");
            System.out.println("> 6. GUI");
            System.out.println("> 7. Exit\n");
            System.out.print("> Select your option : ");
            int selectedOption;
            try {
                selectedOption = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                continue;
            }

            switch (selectedOption) {
                case 1:
                    System.out.println(addNewMember()); break ;
                case 2:
                    System.out.println(deleteMember()); break ;
                case 3:
                    getAllMembers(); break ;
                case 4:
                    sortMembers(); break;
                case 5:
                    saveData(); break ;
                case 6:
                    openGUI(); break ;
                case 7: {
                    System.out.println("> Thank You!");
                    System.exit(0);
                    break;
                }
                default:
                    System.out.println("> There is no such option, Try again!");

            }
        }
    }

    /** Building UI for search functionality **/
    private static void buildUI() {
        window = new Stage();
        Label label1 = new Label("Search ");
        TextField txt_search = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, txt_search);
        hb.setSpacing(10);
        TableColumn columnMembershipNumber, columnName, columnStartMembershipDate, columnMembershipType, columnAge, columnSchoolName;

        columnMembershipType = new TableColumn("Membership Type");
        columnMembershipType.setCellValueFactory(new PropertyValueFactory<>("membershipType"));

        columnMembershipNumber = new TableColumn("Membership Number");
        columnMembershipNumber.setCellValueFactory(new PropertyValueFactory<>("membershipNumber"));

        columnName = new TableColumn("Name");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        columnStartMembershipDate = new TableColumn("Start Membership Date");
        columnStartMembershipDate.setCellValueFactory(new PropertyValueFactory<>("startMembershipDate"));

        columnSchoolName = new TableColumn("School Name");
        columnSchoolName.setCellValueFactory(new PropertyValueFactory<>("schoolName"));

        columnAge = new TableColumn("Age");
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        tableView.getColumns().addAll(columnMembershipType,columnMembershipNumber,columnName,columnStartMembershipDate,columnSchoolName,columnAge);
        tableView.setMinWidth(700);

        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter(newValue);
        });
        filter("");
        VBox vbox0 = new VBox(hb, tableView);
        vbox0.setSpacing(25);
        vbox0.setAlignment(Pos.CENTER);
        vbox0.setPadding(new Insets(10));
        FlowPane flowPane0 = new FlowPane();
        flowPane0.getChildren().add(vbox0);
        Scene scene0 = new Scene(flowPane0, 800, 500);
        window.setScene(scene0);
    }

    private static void filter(String keyword){
        tableView.getItems().clear();
        for(int i=0;i<noOfMembers;i++){
            Row row = new Row();
            row.setMembershipNumber(String.valueOf(members[i].getMembershipNumber()));
            row.setName(members[i].getName());
            row.setStartMembershipDate(members[i].getStartMembershipDate());
            if(keyword.isEmpty()
                    || members[i].getName().toLowerCase().contains(keyword.toLowerCase())){
                if(members[i] instanceof StudentMember){
                    row.setMembershipType("StudentMember");
                    row.setSchoolName(((StudentMember) members[i]).getSchoolName());
                }else if(members[i] instanceof  Over60Member){
                    row.setMembershipType("Over60Member");
                    row.setAge(Integer.toString(((Over60Member) members[i]).getAge()));
                }else {
                    row.setMembershipType("DefaultMember");
                }
                tableView.getItems().add(row);
            }
        }
    }

    /** method for swap two members **/
    private void swap(int j) {
        DefaultMember tempMember;
        // Swap two members
        if(members[j].getName().compareTo(members[j+1].getName()) > 0){
            tempMember = members[j];
            members[j] = members[j+1];
            members[j+1] = tempMember;
        }
    }

    /** Method to shift whole members array to left by one index **/
    private void shiftMembersArrayToLeftByOneMember(int index) {
        for(int j=index;j<noOfMembers;j++){
            members[j] = members[j+1];
        }
        members[noOfMembers] = null;
        noOfMembers--;
    }

    /** Method to retrieve all members from the text file **/
    private void retrieveSavedMembers() {
        File file = new File("output.txt");
        members = new DefaultMember[100];
        noOfMembers = 0;
        boolean flag = true;
        try{
            if(file.exists()) {
                Scanner scanner = new Scanner(file);
                flag = true;
                while (scanner.hasNextLine()) {
                    String data[] = scanner.nextLine().split("\\|");
                    DefaultMember member = null;
                    if(data[0].equals("STUDENT_MEMBER")){
                        member = new StudentMember();
                        ((StudentMember) member).setSchoolName(data[4]);
                        flag = true;
                    }else if(data[0].equals("OVER_60_MEMBER")){
                        member = new Over60Member();
                        ((Over60Member) member).setAge(Integer.parseInt(data[4]));
                        flag = true;
                    }else if(data[0].equals("DEFAULT_MEMBER")){
                        member = new DefaultMember();
                        flag = true;
                    }else{
                        flag = false;
                    }
                    if(flag){
                        member.setMembershipNumber(Integer.parseInt(data[1]));
                        member.setName(data[2]);
                        String date[] = data[3].split("\\-");
                        member.setStartMembershipDate(new Date(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2])));
                        members[noOfMembers] = member;
                        noOfMembers++;
                    }
                }
            } else  {
                file.createNewFile();
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /** AutoClosable method for close the resources such as Scanners **/
    @Override
    public void close() throws Exception {

    }
}
