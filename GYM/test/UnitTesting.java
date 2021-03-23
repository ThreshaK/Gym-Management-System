import com.company.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/** JUnit Testing for insert and delete methods **/
public class UnitTesting {

    private GymManager gymManager;
    private DefaultMember member1,member2;

    @Before
    public void createDummyData(){
        gymManager = new MyGymManager();
        member1 = new DefaultMember();
        member1.setMembershipNumber(100);
        member1.setName("Amesh M Jayaweera");
        member1.setStartMembershipDate(new Date(2020,06,03));
        MyGymManager.members[MyGymManager.noOfMembers] = member1;
        MyGymManager.noOfMembers++;
        member2 = new StudentMember();
        member2.setMembershipNumber(101);
        member2.setName("Hiruni Maleesha");
        member2.setStartMembershipDate(new Date(2020,06,03));
        MyGymManager.members[MyGymManager.noOfMembers] = member1;
        MyGymManager.noOfMembers++;
    }

    @Test
    public void testAddNewMemberIsSuccess() {
        String input = "1\n78967\nEmma Watson\n2019\n12\n22\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("> The member is registered successfully!",gymManager.addNewMember());
    }

    @Test
    public void testAddNewMemberAlreadyExistsValidation() {
        String input = "1\n100\nEmma Watson\n2019\n12\n22\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("> The given membership number already exists!",gymManager.addNewMember());
    }

    @Test
    public void testInsufficientSpaceForAddNewMemberValidation(){
        String input = "1\n107\nEmma Watson\n2019\n12\n22\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MyGymManager.noOfMembers = 100;
        assertEquals("> There is no space available!",gymManager.addNewMember());
    }

    @Test
    public void testDeleteMemberIsSuccess() {
        String input = "100";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("100 has been deleted successfully!\nAvailable spaces : 99",gymManager.deleteMember());
    }

    @Test
    public void testMemberDoesNotExistsToDeleteValidation(){
        String input = "456";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("> The given membership number doesn't exits!",gymManager.deleteMember());
    }

}
