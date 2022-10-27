package com.vrr.domain.tour.domain;

import com.vrr.common.code.tour.MemberType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class TourGroup {

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<TourMember> members = new ArrayList<>();

    public TourGroup(List<TourMember> members) {
        this.members = members;
    }

    public void addMember(TourMember member) {
        if (this.members.isEmpty() && member.isLeader()) {
            members.add(member);
            return;
        }

        checkAssignedLeader(member);
        checkDuplicateLeader(member);
        checkDuplicateMember(member);

        members.add(member);
    }

    public TourMember getLeader() {
        return members.stream()
                .filter(tourMember -> tourMember.getType() == MemberType.LEADER)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Can't find leader"));
    }

    public boolean hasLeader() {
        for (TourMember tourMember : members) {
            if (tourMember.isLeader()) {
                return true;
            }
        }
        return false;
    }

    private void checkDuplicateMember(TourMember member) {
        members.stream()
                .filter(it -> it.isSameUser(member))
                .findFirst()
                .ifPresent(it -> {
                    throw new IllegalArgumentException("User already joined Tour");
                });
    }

    private void checkAssignedLeader(TourMember member) {
        if (!member.isLeader() && members.isEmpty()) {
            throw new IllegalStateException("Can't add member, tour leader is not registered");
        }
    }

    private void checkDuplicateLeader(TourMember member) {
        if (member.isLeader() && this.hasLeader()) {
            throw new IllegalStateException("Can't add leader, tour already have leader");
        }
    }
}
