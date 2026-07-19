# GymHub Membership Management System

GymHub is a Java console application that simulates the core operations of a gym membership and service-management platform. It was developed as a five-member university software-engineering project for the Department of Computer Engineering and Informatics at the University of Patras.

The repository contains the working console prototype, technical diagrams, the project report, and a manual test plan.

## Features

### Personal accounts

Personal users can:

- Create an account and sign in.
- View and update profile information.
- Search for gyms, services, and service categories.
- Add services to favourites.
- Add and remove services from the shopping cart.
- Complete checkout and create order records.
- View subscription and order history.
- View gyms associated with completed purchases.
- View digital membership cards.
- View announcements from relevant gyms.

### Company accounts

Company users can:

- Create a company account and sign in.
- View and update gym information.
- Add, edit, and delete services.
- Search for gyms and services.
- View orders associated with their gym.
- Create, edit, and delete announcements.

### Validation and consistency

The application includes checks for:

- Duplicate usernames, company names, and email addresses.
- Name collisions between personal and company accounts.
- Duplicate services, favourites, and cart entries.
- Invalid numeric input and menu selections.
- Negative service prices.
- Consistent updates of related services, favourites, cart entries, and announcements.

## Implementation Scope

The report and interface mock-ups describe the broader design of the proposed GymHub platform. The implementation in this repository is an educational Java console prototype focused on the system's principal workflows.

The application stores its data in memory and does not include a database, real payment processing, email delivery, geolocation, ratings, or a graphical/mobile interface.

## Technologies

- Java
- JDK 21
- Visual Studio Code
- Windows PowerShell
- Draw.io
- Balsamiq Wireframes

## Project Structure

```text
gymhub-membership-management-system/
├── docs/
│   ├── diagrams/
│   │   ├── class-diagram.png
│   │   ├── domain-model.png
│   │   └── use-case-diagram.png
│   └── report/
│       └── gymhub-project-report.pdf
├── src/
│   ├── Announcements.java
│   ├── Cart.java
│   ├── Company_User.java
│   ├── Favourites.java
│   ├── Find_Gyms.java
│   ├── Main.java
│   ├── Menu.java
│   ├── My_Codes.java
│   ├── My_Gyms.java
│   ├── Order.java
│   ├── Services.java
│   └── User.java
├── tests/
│   └── manual-test-plan.md
├── .gitignore
└── README.md
```

## Build and Run

### Requirements

Install JDK 21 and confirm that the following commands are available:

```powershell
java -version
javac -version
```

### Windows PowerShell

Run the following commands from the repository root:

```powershell
Remove-Item .\out -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force out | Out-Null
$javaFiles = Get-ChildItem .\src\*.java | Select-Object -ExpandProperty FullName
javac -Xlint:all -d .\out $javaFiles
java -cp .\out Main
```

### macOS or Linux

```bash
rm -rf out
mkdir -p out
javac -Xlint:all -d out src/*.java
java -cp out Main
```

The initial menu is:

```text
1. Sign in
2. Sign up
3. Exit
```

## Testing

The project was compiled with `javac -Xlint:all` and tested manually across account management, profile editing, service management, favourites, cart operations, checkout, subscription history, digital cards, announcements, and invalid-input handling.

See the complete [manual test plan](tests/manual-test-plan.md).

## Documentation

- [Project report](docs/report/gymhub-project-report.pdf)
- [Use-case diagram](docs/diagrams/use-case-diagram.png)
- [Domain model](docs/diagrams/domain-model.png)
- [Implementation class diagram](docs/diagrams/class-diagram.png)
- [Manual test plan](tests/manual-test-plan.md)

## Limitations

- Data is lost when the application closes.
- Passwords are stored as plain text in memory.
- Checkout is a simulation and does not process real payments.
- No email, database, geolocation, rating, or notification service is connected.
- Digital cards are represented as console data rather than scannable production cards.
- Testing is manual rather than based on an automated unit-test suite.

## Contributors

This was a group project developed collaboratively by:

- Andreas Kerkidis
- Pantelis Petrou
- Rafael Kyriakou
- Rafael Kitromilidis
- Spyros Asonitis

## License

No license has been granted for this repository. All rights are reserved by the project contributors.
