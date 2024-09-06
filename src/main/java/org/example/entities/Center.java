package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "center")
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long federalId;

    private String name;

    private String location;

    private String contact;

    private Double rank;

    private Date createDate;

    @OneToMany
    @JoinColumn(name = "center_id")
    private List<Document> documentList;

    @OneToMany
    @JoinColumn(name = "center_id")
    private List<Event> eventList;

    @ManyToOne
    @JoinColumn(name = "team_leader_id")
    private Volunteer teamLeader;
}