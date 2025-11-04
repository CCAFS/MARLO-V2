package com.example.demo.modules.innovationreports.adapters.web.mapper;

import com.example.demo.modules.innovationreports.adapters.web.dto.CreateInnovationReportRequestDto;
import com.example.demo.modules.innovationreports.adapters.web.dto.InnovationReportResponseDto;
import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InnovationReportMapper {

    public InnovationCatalogReport toEntity(CreateInnovationReportRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        InnovationCatalogReport report = new InnovationCatalogReport(
                requestDto.getInnovationId(),
                requestDto.getUserName(),
                requestDto.getUserLastname(),
                requestDto.getUserEmail(),
                requestDto.getInterestNarrative()
        );
        report.setModificationJustification(requestDto.getModificationJustification());
        return report;
    }

    public InnovationReportResponseDto toResponseDto(InnovationCatalogReport report) {
        if (report == null) {
            return null;
        }

        return new InnovationReportResponseDto(
                report.getId(),
                report.getInnovationId(),
                report.getUserName(),
                report.getUserLastname(),
                report.getUserEmail(),
                report.getInterestNarrative(),
                report.getIsActive(),
                report.getActiveSince(),
                report.getModificationJustification()
        );
    }

    public List<InnovationReportResponseDto> toResponseDtoList(List<InnovationCatalogReport> reports) {
        return reports == null ? List.of() : reports.stream().map(this::toResponseDto).toList();
    }
}
