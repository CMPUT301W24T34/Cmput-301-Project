package com.example.swiftcheckin.organizer;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.swiftcheckin.R;

import java.util.ArrayList;

public class EventInfoPage extends AppCompatActivity {


    ListView checkedInList;
    ArrayList<Pair<String, String>> checkedInDataList;
    CheckInArrayAdapter checkInArrayAdapter;
    ListView signedUpList;
    ArrayList<Pair<String, String>> signedUpDataList;
    CheckInArrayAdapter signUpArrayAdapter;

    TextView liveCount;

    TextView checkedInButton;
    TextView signedUpButton;

    String eventId;

    Event finalEvent = new Event();

    FirebaseOrganizer dbOrganizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_event_information);   // This the xml the activity is connected to.
        View view = getWindow().getDecorView();     // OpenAI, April 4, 2024. ChatGPT. Prompt: how to get the current acitivity view in the activity

        dbOrganizer = new FirebaseOrganizer(getApplicationContext());
        eventId = getIntent().getStringExtra("eventId");

        checkedInButton= findViewById(R.id.organizerEventInfo_CheckedInTitle);
        signedUpButton = findViewById(R.id.organizerEventInfo_SignedUpTitle);

        checkedInList = findViewById(R.id.organizerEventInfo_CheckedInList);
        signedUpList = findViewById(R.id.organizerEventInfo_SignedUpList);

        liveCount = findViewById(R.id.organizerEventInfo_LiveAttendance);

        getEventInformation(view);      // event specific data is fetched

        // check-in and signup details initialization
        checkedInDataList = new ArrayList<>();
        checkInArrayAdapter = new CheckInArrayAdapter(this, checkedInDataList);
        checkedInList.setAdapter(checkInArrayAdapter);

        signedUpDataList = new ArrayList<>();
        signUpArrayAdapter = new CheckInArrayAdapter(this, signedUpDataList);
        signedUpList.setAdapter(signUpArrayAdapter);

        // button initializations
        Button showQrButton = findViewById(R.id.organizerEventInfo_qrButton);
        Button showPushNotif = findViewById(R.id.organizerEventInfo_pushButton);

        // milestone image
        ImageView milestone = findViewById(R.id.organizerEventInfo_milestoneImage);
        
        fetchCheckedInDetails();
        fetchSignUpDetails();

        initializeListButton(checkedInButton);
        initializeSignedListButton(signedUpButton);
        initializeShowQrButton(showQrButton);
        initializeAnnouncementButton(showPushNotif);
        initializeMileStoneButton(milestone);

    }

    private void initializeListButton(TextView view1)
    {
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });

    }

    private void initializeSignedListButton(TextView view1)
    {
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignedUpList();
            }
        });

    }

    private void showSignedUpList()
    {
        if(signedUpList.getVisibility() == View.INVISIBLE)
        {
            checkedInList.setVisibility(View.INVISIBLE);
            signedUpList.setVisibility(View.VISIBLE);
            checkedInButton.setBackground(null);
            signedUpButton.setBackgroundResource(R.drawable.grey_circle_background);
        }
    }

    private void initializeMileStoneButton(ImageView milestoneImage)
    {
        milestoneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMilestone();
            }
        });
    }
    private void initializeAnnouncementButton(Button button)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAnnouncementPage();
            }
        });
    }

    private void showList()
    {
        if(checkedInList.getVisibility() == View.INVISIBLE)
        {
            signedUpList.setVisibility(View.INVISIBLE);
            checkedInList.setVisibility(View.VISIBLE);
            signedUpButton.setBackground(null);
            checkedInButton.setBackgroundResource(R.drawable.grey_circle_background);
        }
    }

    private void getEventInformation(View view)
    {
        dbOrganizer.getEvent(eventId, new FirebaseOrganizer.EventCallback() {
            @Override
            public void onCompleteFetch(Event event) {
                finalEvent = event;
                TextView title = view.findViewById(R.id.organizerEventInfo_eventTitle);
                TextView date = view.findViewById(R.id.organizerEventInfo_eventStartDate);
                TextView time = view.findViewById(R.id.organizerEventInfo_eventStartTime);
                TextView description = view.findViewById(R.id.organizerEventInfo_eventDescription);
                ImageView poster = view.findViewById(R.id.organizerEventInfo_eventPoster);

                title.setText(event.getEventTitle());
                date.setText(event.getStartDate());
                time.setText(event.getStartTime());
                description.setText(event.getDescription());
                if(event.getEventImageUrl() == null)
                {
                    poster.setImageResource(R.drawable.test_rect);
                }
                else
                {
                    Glide.with(getApplicationContext()).load(event.getEventImageUrl()).into(poster);
                }

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("event fetch", errorMessage);
            }
        });
    }

    private void fetchCheckedInDetails()
    {
        dbOrganizer.getCheckedInDetails(eventId, "checkedIn", checkedInDataList, new FirebaseOrganizer.getCheckInCallback() {
            @Override
            public void onDataFetched(ArrayList<Pair<String, String>> dataList) {
                for(int i = 0; i < dataList.size(); i++)
                {
                    Pair<String, String> pair = dataList.get(i);
                    String deviceId = pair.first;
                    int index = i;
                    dbOrganizer.getUserName(deviceId, new FirebaseOrganizer.getNameCallBack() {
                        @Override
                        public void onNameFetched(String name) {
                            Pair<String, String> updatedPair = new Pair<>(name, pair.second);
                            dataList.set(index, updatedPair);
                            if (index == dataList.size() - 1) {
                                checkInArrayAdapter.notifyDataSetChanged();
                                liveCount.setText(String.valueOf(checkedInDataList.size()));
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("Firebase - checkin", errorMessage);
            }
        });
    }

    private void fetchSignUpDetails()
    {
        dbOrganizer.getCheckedInDetails(eventId, "eventsWithAttendees", signedUpDataList, new FirebaseOrganizer.getCheckInCallback() {
            @Override
            public void onDataFetched(ArrayList<Pair<String, String>> dataList) {
                for(int i = 0; i < dataList.size(); i++)
                {
                    Pair<String, String> pair = dataList.get(i);
                    String deviceId = pair.first;
                    int index = i;
                    dbOrganizer.getUserName(deviceId, new FirebaseOrganizer.getNameCallBack() {
                        @Override
                        public void onNameFetched(String name) {
                            Pair<String, String> updatedPair = new Pair<>(name, pair.second);
                            dataList.set(index, updatedPair);
                            if (index == dataList.size() - 1) {
                                signUpArrayAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("Error", errorMessage);
            }
        });
    }

    private void initializeShowQrButton(Button button)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQrFragment();
            }
        });
    }

    private void showQrFragment()
    {
        Bitmap checkInImage = QrCodeManager.generateQRCode(finalEvent.getQrID());
        Bitmap promoImage = QrCodeManager.generateQRCode(finalEvent.getQrPromoID());

        EventFragmentQrs fragmentQrs = new EventFragmentQrs(eventId, checkInImage, promoImage);
        fragmentQrs.show(getSupportFragmentManager(), "Event Information Qr options");
    }

    private void launchAnnouncementPage()
    {
        Intent intent = new Intent(getApplicationContext(), AddAnnouncementActivity.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
    }

    private void launchMilestone()
    {
        FragmentMilestone fragmentMilestone = new FragmentMilestone(this.checkedInDataList, this.signedUpDataList);
        fragmentMilestone.show(getSupportFragmentManager(), "Milestone");
    }
}

