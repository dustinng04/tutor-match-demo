1. Run docker-compose.yml script
2. Import data to elastic search from json: curl -X POST "http://localhost:9200/tutors/_bulk" -H "Content-Type: application/json" --data-binary @tutors_bulk.json
3. Run application and test by postman
4. Sample body for /api/search:
{ 
    "availabilities": [
        {
            "dayOfWeek": "Monday", // "Monday" -> "Friday"
            "startTime": "08:00", // 07:00 -> 23:59
            "endTime": "10:00"    // 07:00 -> 23:59
        } // Add more if you want
    ],
    "syllabusId": 10,  // 10,20,30,40,50
    "level": "Intermediate" // Beginner, Intermediate, Advanced
}
