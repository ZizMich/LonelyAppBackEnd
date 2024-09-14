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
    private Long groupid;

    @Column(name = "memberid")
    private Long memberid;

    @Column(name = "role")
    private Long role;

    @Column(name = "membersince")
    private Long membersince;

    public ChatMemberEntity() {
    }


    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
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
