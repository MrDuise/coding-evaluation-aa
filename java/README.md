# Coding Evaluation - American Airlines

## Overview

This project is a simple Java-based system that implements the hiring function for an organization. To make the project easier to run, share, and extend, I updated it to use **Gradle (version 8.10)** for dependency management and build automation, while keeping the functionality the same as required in the assessment. For simplicity the other languages are removed from this branch, and the 'java' folder is the project root directory.

The project is now compiled using **Java 17**.

## Key Changes

- **Gradle Integration**:
  Instead of adding JAR files directly into the project structure, I integrated Gradle to manage dependencies and build the project. This makes it easier to run and share.

- **Unit Tests**:
  I added unit tests to improve code quality. The unit tests focus on the core `MyOrganization` class and test its functionality, including the `hire` function. While this wasn't part of the assessment requirements, I had some fun extending the project by adding tests to ensure the code works as expected.


### Key Files
- `src/main/java/aa.evaluation/...` - Contains the main logic for the organization and its structure.
- `src/test/java/aa.evaluation/...` - Contains the unit tests for `MyOrganization` and related classes.

## How to Run the Project

1. Build the project:

   ```bash
   gradle build
   ```
2. Run the project:

    ```bash
    gradle run
     ```
3. Run the unit tests:
    ```
   gradle test
   ```

To view the detailed results of the tests, open the [index.html](./app/build/reports/tests/test/index.html) file located in the `build/reports/tests/test` directory.


