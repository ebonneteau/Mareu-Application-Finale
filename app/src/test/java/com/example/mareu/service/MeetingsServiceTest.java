package com.example.mareu.service;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.model.Attendees;
import com.example.mareu.model.Meetings;
import com.example.mareu.model.Places;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MeetingsServiceTest {
    private ReuApiService service;
    private Meetings meetings1;
    private Meetings meetings2;
    private Meetings meetings3;
    @BeforeClass public static void Method_Name() {
        // class setup code here
    }

    @Before
    public void setUp() throws Exception {
        service = DI.getReuApiService();

        //if (service.getMeetings().size()>0){
        //    service.deleteMeeting(meetings1 );
        //    service.deleteMeeting(meetings2 );
        //    service.deleteMeeting(meetings3 );

        //}
        //Generation of 3 fake meetings

        List<Attendees> mAttendees = new ArrayList<>();
        String emailAddress = ("rico@gmail.com");
        Attendees attendees = new Attendees(0, emailAddress);
        mAttendees.add( attendees );
        meetings1 =  new Meetings(0,
                "Sujet A", "23h00", "23h45", "Room1",mAttendees );
        meetings2 =  new Meetings(1,
                "Sujet B", "05h00", "05:45", "Place2",mAttendees );
        meetings3 = new Meetings( 2,
                "Sujet C", "6h00", "7h45", "Athena", mAttendees );
        service.addMeeting( meetings1 );
        service.addMeeting( meetings2 );
        service.addMeeting( meetings3 );

    }
    @After
    public void deleteData(){
        service.deleteMeeting(meetings1 );
        service.deleteMeeting(meetings2 );
        service.deleteMeeting(meetings3 );
    }





    @Test
    public void getMeetingsWithSuccess() {

        //Given the meeting list generated in set-up
        //When using api service to get meetings
        List<Meetings> expectedMeetingSize = service.getMeetings();
        //Then meeting size should be 3
        assertEquals( expectedMeetingSize.size(), 3 );


    }

    @Test
    public void checkMeetingListOrderByTime() {


        //When the meeting list generated in set-up
        List<Meetings> expectedMeetingOrderByTime = service.getMeetingsByTime();
        //then
        assertEquals( meetings2,expectedMeetingOrderByTime.get( 0 ) );

    }
    @Test
    public void checkMeetingListOrderByPlace() {


        //When the meeting list generated in set-up
        List<Meetings> expectedMeetingOrderByTime = service.getMeetingsByPlace();
        //then
        assertEquals( meetings3,expectedMeetingOrderByTime.get( 0 ) );

    }

    @Test
    public void deleteMeetingWithSuccess() {
        //Given meeting at position 1
        Meetings meetingToDelete = service.getMeetings().get(0);

        //When deleting this meeting
        service.deleteMeeting(meetingToDelete);
        //Then assert list does not contain this object anymore
        assertFalse( service.getMeetings().contains(meetingToDelete));

    }
}