package com.iStudent.model.entity;

import com.iStudent.model.DTOs.SubjectDTO;
import com.iStudent.model.entity.base.BasePersonEntity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "students")
public class Student extends BasePersonEntity {

    @Column(name = "enroll_date", nullable = false)
    private LocalDate enrollDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Club club;

    @ManyToOne(cascade = CascadeType.ALL)
    private Parent parent;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Subject> subjects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "students_clubs",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "club_id", referencedColumnName = "id")
    )
    private Set<Club> clubs;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "students_parents",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id")
    )
    private Set<Parent> parents;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "students_subjects",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id")
    )
    private Set<Subject> subjects2;

    public Student() {
        this.clubs = new HashSet<>();
        this.parents = new HashSet<>();
        this.subjects2 = new HashSet<>();
    }

    public Set<Club> getClubs() {
        return Collections.unmodifiableSet(clubs);
    }

    public Set<Subject> getSubjects2() {
        return Collections.unmodifiableSet(subjects2);
    }

    public Set<Parent> getParents() {
        return Collections.unmodifiableSet(parents);
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    public Club getClub() {
        return club;
    }
 
    public void setClub(Club club) {
        this.club = club;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void addClub(Club club) {
        clubs.add(club);
    }

    public void addParent(Parent parent) {
        parents.add(parent);
    }

    public void addSubject2(Subject subject) {
        subjects2.add(subject);
    }

    public void removeClub(Club club) {
        clubs.remove(club);
    }

    public void removeParent(Parent parent) {
        parents.remove(parent);
    }

    public void removeSubject(Subject subject) {
        subjects2.remove(subject);
    }

}
