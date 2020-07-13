# Duplicate Checker

Take Home for Co-op

## Solution:
- Added a POST request on the server that will parse and try to find the duplicates on the
 uploaded CSV file.
- The CSV rows are parsed using the OpenCSV library into an array of ContactModel
- ContactModel is a simple Java class to model a Contact's information
- To find duplciates all 2-combinations are computed from the submitted contancs, then for each 
pair I compute a "distance" score value using a simple heuristic. If the distance is below a 
threshold they are marked as duplicated and added to a result set. Java Parallel Streams are 
used here.
- The heuristic assigns (arbitrarily) a weight to each field and then computes the Levenshtein 
Distance between the strings of each field on the pair, then this are added to get a distance 
score between the 2 contacts. If a field is empty ("") on either of the contacts it is ignored 
(distance 0 for that field).
- The frontend is a simple form to submit the CSV file (result is printed to the console), but 
testing is easier using Postman.

## NOTE:
I thought of a better solution, but it would take longer to implement. Using strategies to encode
 rules to better detect duplicates (and maybe why the duplicate happened), this idea is explained
  slightly on a comment on the DuplicateContactService.java file.

## Tasks:
1. Write an app that parses the attached normal.csv file using the starter app we have created to
 start you off.
2. Identify possible duplicate records in the test-files (which be found in the zip file).
3. Leverage existing algorithms (no need to reinvent the wheel)
4. Example algorithms include Metaphone and Levenshtein distance
5. We’re looking to find not just exact duplicates but also records that are likely to be a
 duplicate entry with different spelling, missing data, small differences, etc.
6. Ignore the ID column for deduplication as they are here to help you visually identify duplicates
7. Here is an example of a duplicate record: bill,smith,bsmith@gmail.com,190 main st boston mass
 and bill,smith,bsmith@gmail.com,400 west street boston ma
8. Build a lightweight web application to display the processed data. Print each set of duplicates
 separately as well as a set of non-duplicate entries.
9. Create a JSON object output
10. Display duplicates separately as well as a set of non-duplicate entries.
11. Some duplicates are harder to identify than others and some false negatives harder to eliminate. Do what you can in the few hours you can dedicate to this.
12. Submit your code through a Github repository for us to review, with a Pull Request showing the
 commits you made that allows us to comment on it.
13. Try to time-box your effort to around 2 hours if you can.

---
---
---

# MonolithStarter

This project is intended to help developers quickly stand up a monolithic React/Spring Boot application. For the most part you can simply replace all instances of `monolithstarter` with appropriately-cased `yourprojectname` but make sure to do a Ctrl + F for `FIXME` to find places where more action is required.

This readme assumes that you have completed the [newhire checklist](https://github.com/validityhq/we_the_engineers/tree/master/newhire).

## Development Setup

During development, you may simply work on front-end and backend with independent tools.

For PC users: For all the `./mvnw` commands mentioned below use `mvnw` instead.

### To run the back-end:

```bash
# Move to the backend directory
cd monolithstarter-svc

# Start the application in dev mode
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

You will now have a host running at [localhost:8080](http://localhost:8080/)

### To run the front-end:

```bash
# Move to the right directory
cd monolithstarter-static

# Install dependencies
yarn

# Start the application in dev mode
yarn run start
```
You will now have your frontend running at [localhost:9000](http://localhost:9000/)

To build frontend for production with minification:

```bash
yarn run build
```

The build artifacts will be stored in the `build/` directory


## Build / Deployment

### Using the right version of node.js

The repo is setup to run a specific version of node.js.  This version is managed in the `.nvmrc` file in the root directory.

In order to avoid errors related to node engine mismatches, make sure to run `nvm use` upon entering the root directory in each new terminal session. It will automatically download and set the active version to the correct one.

Pro-tip: If you're using Zsh as your bash client, there is a plugin for automatically switching node versions when entering a new directory (automatically triggers `nvm use` and  loads the version based on the directory’s `.nvmrc` config) [nvm-auto](https://github.com/dijitalmunky/nvm-auto)

Note: WINDOWS only - `.nvmrc` does not work on Windows, so `nvm use` in the root directory will not work as above. You must manually install the node.js version that needs to be used using `nvm install [version]` and then using `nvm use [version]` For more information, see this issue, which indicates there are no plans to implement this feature unfortunately (https://github.com/coreybutler/nvm-windows/issues/128)

### Local

For local development you want both the backend and frontend running for maximum efficiency :)

### To build and run the backend:

```bash
# Move to the backend directory
cd monolithstarter-svc

# Start the application in dev mode
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

You will now have a host running at [localhost:8080](http://localhost:8080/)

### To build and run the front-end:

```bash
# Move to the right directory
cd monolithstarter-static

# Install dependencies
yarn install

# Start the application in dev mode
yarn run start
```
You will now have your frontend running at [localhost:9000](http://localhost:9000/). Note that this won't work without the backend running!

To build frontend for production with minification:

```bash
yarn run build
```

### Production Build

```bash
# Install dependencies
yarn install

# run build script
yarn run build
```


### Docker
```bash
# Move to the monolithstarter-svc directory
cd monolithstarter-svc

# Generate a clean package
./mvnw clean package

# Construct the docker image
docker build -t monolithstarter-svc .

# Run the image container (substitute 5000 for desired port)
docker run -p 5000:8080 monolithstarter-svc
```
The full application is now accessible at [localhost:5000](http://localhost:5000/) (or whatever port you chose to use)
