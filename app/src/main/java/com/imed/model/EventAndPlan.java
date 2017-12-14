package com.imed.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by vinhnguyen.it.vn on 2017, December 14
 */

public class EventAndPlan {
    @Embedded
    public Event event;
    @Relation(parentColumn = "event_id", entityColumn = "event_id", entity = Plan.class)
    public List<Plan> plans;
}
