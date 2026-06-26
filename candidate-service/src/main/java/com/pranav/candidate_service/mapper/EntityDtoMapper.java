
package com.pranav.candidate_service.mapper;

import com.pranav.candidate_service.dto.CandidateDetails;
import com.pranav.candidate_service.entity.Candidate;
import com.pranav.candidate_service.entity.WorkExperience;

public class EntityDtoMapper {

    public static CandidateDetails toCandidateDetails(Candidate candidate){
        return new CandidateDetails(
                candidate.getId(),
                candidate.getName(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getLocation(),
                candidate.getExperienceInYears(),
                candidate.getSkills(),
                candidate.getWorkExperiences()
                         .stream()
                         .map(EntityDtoMapper::toWorkExperienceDto)
                         .toList()
        );
    }

    private static CandidateDetails.WorkExperience toWorkExperienceDto(WorkExperience workExperience){
        return new CandidateDetails.WorkExperience(
                workExperience.getCompanyName(),
                workExperience.getJobTitle(),
                workExperience.getStartDate(),
                workExperience.getEndDate(),
                workExperience.getDescription(),
                workExperience.getTechnologies()
        );
    }

}