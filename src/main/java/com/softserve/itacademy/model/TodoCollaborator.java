package com.softserve.itacademy.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "todo_collaborator")
public class TodoCollaborator implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private ToDo todo;

    @Id
    @ManyToOne
    @JoinColumn(name = "collaborator_id", nullable = false)
    private User collaborator;

    public TodoCollaborator() {
    }

    public TodoCollaborator(ToDo todo, User collaborator) {
        this.todo = todo;
        this.collaborator = collaborator;
    }

    public ToDo getTodo() {
        return todo;
    }

    public void setTodo(ToDo todo) {
        this.todo = todo;
    }

    public User getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(User collaborator) {
        this.collaborator = collaborator;
    }

    @Override
    public String toString() {
        return "TodoCollaborator{" +
                "todo=" + todo +
                ", collaborator=" + collaborator +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoCollaborator that = (TodoCollaborator) o;
        return Objects.equals(todo, that.todo) && Objects.equals(collaborator, that.collaborator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todo, collaborator);
    }
}
