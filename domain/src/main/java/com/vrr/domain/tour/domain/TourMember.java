package com.vrr.domain.tour.domain;

import com.vrr.common.code.tour.MemberType;
import com.vrr.domain.converter.tour.MemberTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "TOUR_GROUP")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourMember {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID")
    private Tour tour;

    @Column(name = "USER_ID")
    @NotNull
    private Long userId;

    @Column(name = "TYPE")
    @NotNull
    @Convert(converter = MemberTypeConverter.class)
    private MemberType type;

    @Column(name = "CREATED_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    public boolean isLeader() {
        return type == MemberType.LEADER;
    }
}
