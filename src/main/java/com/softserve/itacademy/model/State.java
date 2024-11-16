package com.softserve.itacademy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "states")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Name can only contain Latin letters, numbers, dashes, spaces, and underscores")
    private String name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public State() {
    }

    public State(long id, String name, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return id == state.id && Objects.equals(name, state.name) && Objects.equals(tasks, state.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tasks);
    }
}
