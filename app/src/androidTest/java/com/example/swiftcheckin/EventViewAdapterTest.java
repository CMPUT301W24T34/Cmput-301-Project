package com.example.swiftcheckin;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class EventViewAdapterTest {

    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testGetView() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", "Description 1", "Location 1", "DeviceId 1", "ImageUrl 1", "2024-03-01", "2024-03-01", "10:00", "12:00"));
        events.add(new Event("Event 2", "Description 2", "Location 2", "DeviceId 2", "ImageUrl 2", "2024-03-02", "2024-03-02", "11:00", "13:00"));

        EventViewAdapter adapter = new EventViewAdapter(context, events);

        View view = LayoutInflater.from(context).inflate(R.layout.item_event, null);
        ViewGroup parent = new ViewGroup(context) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
            }
        };

        View eventView = adapter.getView(0, view, parent);
        TextView eventName = eventView.findViewById(R.id.event_Title);
        TextView eventDate = eventView.findViewById(R.id.edit_event_date);
        TextView eventStartTime = eventView.findViewById(R.id.edit_event_start_time);
        TextView eventEndTime = eventView.findViewById(R.id.edit_event_end_Time);

        assertEquals("Event 1", eventName.getText().toString());
        assertEquals("2024-03-01", eventDate.getText().toString());
        assertEquals("10:00", eventStartTime.getText().toString());
        assertEquals("12:00", eventEndTime.getText().toString());
    }

    @Test
    public void testGetCount() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", "Description 1", "Location 1", "DeviceId 1", "ImageUrl 1", "2024-03-01", "2024-03-01", "10:00", "12:00"));
        events.add(new Event("Event 2", "Description 2", "Location 2", "DeviceId 2", "ImageUrl 2", "2024-03-02", "2024-03-02", "11:00", "13:00"));

        EventViewAdapter adapter = new EventViewAdapter(context, events);

        assertEquals(2, adapter.getCount());
    }

    @Test
    public void testGetItem() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", "Description 1", "Location 1", "DeviceId 1", "ImageUrl 1", "2024-03-01", "2024-03-01", "10:00", "12:00"));
        events.add(new Event("Event 2", "Description 2", "Location 2", "DeviceId 2", "ImageUrl 2", "2024-03-02", "2024-03-02", "11:00", "13:00"));

        EventViewAdapter adapter = new EventViewAdapter(context, events);

        assertEquals(events.get(0), adapter.getItem(0));
        assertEquals(events.get(1), adapter.getItem(1));
    }


    @Test
    public void testGetItemId() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", "Description 1", "Location 1", "DeviceId 1", "ImageUrl 1", "2024-03-01", "2024-03-01", "10:00", "12:00"));
        events.add(new Event("Event 2", "Description 2", "Location 2", "DeviceId 2", "ImageUrl 2", "2024-03-02", "2024-03-02", "11:00", "13:00"));

        EventViewAdapter adapter = new EventViewAdapter(context, events);

        assertEquals(0, adapter.getItemId(0));
        assertEquals(1, adapter.getItemId(1));
    }

}
