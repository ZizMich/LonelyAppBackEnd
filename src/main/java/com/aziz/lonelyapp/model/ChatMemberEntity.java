package com.aziz.lonelyapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "group_members")
public class ChatMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "groupid")
    private String groupid;

    @Column(name = "memberid")
    private String memberid;

    @Column(name = "role")
    private Long role;

    @Column(name = "membersince")
    private Long membersince;

    public ChatMemberEntity() {
    }


    public String getMemberid() {

        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getMembersince() {
        return membersince;
    }

    public void setMembersince(Long membersince) {
        this.membersince = membersince;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
