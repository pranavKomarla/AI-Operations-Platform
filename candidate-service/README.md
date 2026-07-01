# Candidate Service

## What This Service Does

Candidate Service is the source of truth for candidate information.
It provides profile data that other services use to make job recommendations, comparisons, and hiring decisions.

At a high level, it manages:

- Candidate identity and contact details
- Experience and skill information
- Work history

## Main Endpoint

- GET /api/candidates/{candidateId}
  - Purpose: fetch one candidate profile by id
  - Used by: career-advisor when evaluating and comparing jobs

## Run

- Start with: ./mvnw spring-boot:run
