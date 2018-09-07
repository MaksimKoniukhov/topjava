GetAll -  curl http://localhost:8080/topjava/rest/meals
Get -  curl http://localhost:8080/topjava/rest/meals/100002
Delete -  curl -X DELETE "http://localhost:8080/topjava/rest/meals/100002"
Create -  curl -d "id=&dateTime=2018-05-05T13:00&description=CreatedMeal&calories=700" http://localhost:8080/topjava/rest/meals/created
Update -  curl -T d:/update.txt "http://localhost:8080/topjava/rest/meals/100004"
Filter -  curl -d "startDate=2015-05-30&endDate=2015-05-30&startTime=09:00&endTime=23:00" http://localhost:8080/topjava/rest/meals/filter


