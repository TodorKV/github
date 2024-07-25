# github

A demo project for fetching repositories and their branches using the https://developer.github.com/v3

## Project Structure

The project is structured as follows:

- `src/main/java/com/atipera/github` contains the main application code
- `src/main/java/com/atipera/github/api` contains the REST API controllers
- `src/main/java/com/atipera/github/api/model` contains the model classes for the REST API
- `src/main/java/com/atipera/github/api/mapper` contains the classes for mapping between external and internal models
- `src/main/java/com/atipera/github/external` contains the classes for interacting with the GitHub API
- `src/main/java/com/atipera/github/external/model` contains the model classes for the GitHub API
- `src/main/java/com/atipera/github/exception` contains the exception classes for the application

## Properties

The application properties are stored in `src/main/resources/application.properties`. The following properties are used:

- `demo.apitera.github.classic-key` - the GitHub API key
- `github.api.url` - the base URL for the GitHub API
