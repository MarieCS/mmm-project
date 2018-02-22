package com.example.mcs.mmm_project.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;

import com.example.mcs.mmm_project.pojo.Event;

public class IntentHelper {
    public static void dialPhoneNumber(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    public static void openWebPage(Activity activity, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    public static void addCalendarEvent(Activity activity, Event event) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, event.titre_fr)
                .putExtra(CalendarContract.Events.DESCRIPTION, event.description_longue_fr)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, event.getGeolocalisation().getPosition().toString().substring(10).replace(")", ""))
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.getStartDatetime().getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.getEndDatetime().getTimeInMillis());
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    public static void share(Activity activity, Event event) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        share.putExtra(Intent.EXTRA_SUBJECT,  event.titre_fr);
        share.putExtra(Intent.EXTRA_TEXT, event.description_fr);
        activity.startActivity(Intent.createChooser(share, "Share"));
    }
}
