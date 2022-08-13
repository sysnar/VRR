package com.vrr.domain.tour.domain;

import com.vrr.common.code.tour.TourType;
import com.vrr.domain.converter.tour.TourTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Table(name = "TOUR")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Tour {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TourGroup tourGroup = new TourGroup();

    @Column(name = "TITLE")
    @NotNull
    private String title;

    @Column(name = "SUMMARY")
    @NotNull
    @Size(max = 100)
    private String summary;

    @Column(name = "TYPE")
    @NotNull
    @Convert(converter = TourTypeConverter.class)
    private TourType type;

    @Column(name = "OPEN")
    private boolean open;

    @Column(name = "DEPARTURE")
    @NotNull
    @Size(max = 50)
    private String departure;

    @Column(name = "ARRIVAL")
    @NotNull
    @Size(max = 50)
    private String arrival;

    @Column(name = "START_AT")
    @NotNull
    private LocalDateTime startAt;

    @Column(name = "CRAETED_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void addMember(TourMember member) {
        tourGroup.addMember(member);
    }

    public TourMember getLeader() {
        return tourGroup.getLeader();
    }
}
