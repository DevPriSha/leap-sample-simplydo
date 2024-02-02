# Simply Do
# =========

## Technologies Used
### Backend
- Spring Boot 3.3.0 (M1)
- Java 21.0.2 openjdk 21.0.2 2024-01-16
- Gradle - Groovy

### Database
- H2 Database (In-memory)

### Frontend
- React 17.0.2
- MUI Components
- Axios

### Note: Has been developed and tested on M1 Macbook Air 2020.

## Frontend Project
- Link: [Simply Do Frontend](https://github.com/DevPriSha/simply-do-frontend)

## Instructions to run the project
- Clone the backend as well as frontend repository
- Open the project in your favorite IDE (IntelliJ IDEA for backend and VSCode for frontend are recommended)

### Backend
- Run the project using the IDE or using the command `./gradlew bootRun`
- Make sure you have Java 21.

### Frontend
- Install the dependencies using the command `npm install`
- Run the project using the command `npm start` or you can create a build using the command `npm run build` and then serve the build using a server like `serve -s build` (npm run build makes the project considerably faster)

## Important Ports (defaults)
- Backend: 8080
- Frontend: 3000

## TODO
- [ ] Add user authentication to allow each user to have their set of tasks.
    - Need to figure out how guest users can use the application without authentication.
- [ ] Implement task sorting and filtering options (e.g., by due date, completion status).
    - Implemented to some extent in backend. Need to implement in frontend.
- [ ] Incorporate visual cues for task priorities (e.g., colour-coded based on due date).
    - Need to add a priority field in the task model and then implement the visual cues in the frontend.
- [ ] Host the application using free online hosting services like Netlify (frontend) and Heroku (backend).
    - Heroku is no longer free. Need to find a free alternative. Feels useless to host the frontend without the backend.
