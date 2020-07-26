package com.denisalupu.freecycle.domain;

public enum Status {

    AVAILABLE, //to see by all users
    FULLY_REQUESTED, //visible to the donor only in "active donations" category
//    AWAITING_CONFIRMATION, //to see by donating user and receiver while the donation awaits confirmation on both sides
//    IN_PROGRESS, //to see by donating and receiving user while the donation is being delivered/completed
    DONATED //to see by donating user after completion
}
