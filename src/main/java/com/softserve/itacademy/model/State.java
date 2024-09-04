package com.softserve.itacademy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;


public class State {

    private long id;

    private String name;

    private List<Task> tasks;

}