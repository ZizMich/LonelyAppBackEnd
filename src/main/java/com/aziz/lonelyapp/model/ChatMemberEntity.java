package com.aziz.lonelyapp.model;

import jakarta.persistence.*;

/**
 * This class represents a member of a group in the LonelyApp application.
 * It is mapped to the "group_members" table in the database using JPA annotations.
 */
@Entity
@Table(name = "group_members")
public class ChatMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The unique identifier of the group this member belongs to.
     */
    @Column(name = "groupid")
    private String groupid;

    /**
     * The unique identifier of the member.
     */
    @Column(name = "memberid")
    private String memberid;

    /**
     * The role of the member in the group.
     */
    @Column(name = "role")
    private Long role;

    /**
     * The timestamp indicating when the member joined the group.
     */
    @Column(name = "membersince")
    private Long membersince;

    public ChatMemberEntity() {
    }

    /**
     * Returns the unique identifier of the member.
     *
     * @return the memberid
     */
    public String getMemberid() {
        return memberid;
    }

    /**
     * Sets the unique identifier of the member.
     *
     * @param memberid the memberid to set
     */
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    /**
     * Returns the unique identifier of the group this member belongs to.
     *
     * @return the groupid
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * Sets the unique identifier of the group this member belongs to.
     *
     * @param groupid the groupid to set
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    /**
     * Returns the role of the member in the group.
     *
     * @return the role
     */
    public Long getRole() {
        return role;
    }

    /**
     * Sets the role of the member in the group.
     *
     * @param role the role to set
     */
    public void setRole(Long role) {
        this.role = role;
    }

    /**
     * Returns the timestamp indicating when the member joined the group.
     *
     * @return the membersince
     */
    public Long getMembersince() {
        return membersince;
    }

    /**
     * Sets the timestamp indicating when the member joined the group.
     *
     * @param membersince the membersince to set
     */
    public void setMembersince(Long membersince) {
        this.membersince = membersince;
    }

    /**
     * Returns the unique identifier of the chat member entity.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the chat member entity.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
