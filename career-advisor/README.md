# Career Advisor Service

## What This Service Does

Career Advisor is the intelligence layer of the platform.
It combines candidate data and job data, then uses an LLM to generate recommendations and guidance.

At a high level, it handles:

- Matching candidates to relevant jobs
- Comparing shortlisted job options
- Generating a tailored resume for a selected job

It also serves the user-facing advisor pages.

## Main Endpoints

- GET /api/career-advisor/find-jobs?candidateId=...
  - Purpose: return ranked job recommendations for a candidate

- GET /api/career-advisor/compare-jobs?candidateId=...&jobIds=...
  - Purpose: compare selected jobs and provide a clear recommendation

- GET /api/career-advisor/generate-resume?candidateId=...&jobId=...
  - Purpose: generate a job-targeted resume draft

## Frontend Pages Served

- /
  - Main advisor UI entry
- /career-advisor-candidate.html
  - Candidate-focused interface
- /career-advisor-recruiter.html
  - Recruiter-focused interface

## Run

- Start with: ./mvnw spring-boot:run
