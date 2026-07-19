# Manual Test Plan

This document records the principal workflows and regression tests completed for the GymHub console prototype.

## Test Environment

- Java Development Kit: JDK 21
- Operating system: Windows
- Terminal: PowerShell
- Compilation command:

```powershell
Remove-Item .\out -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force out | Out-Null
$javaFiles = Get-ChildItem .\src\*.java | Select-Object -ExpandProperty FullName
javac -Xlint:all -d .\out $javaFiles
```

- Execution command:

```powershell
java -cp .\out Main
```

The project compiled with no errors or warnings.

## Account Management

| Test | Expected result | Status |
|---|---|---|
| Create a personal account | Account is created successfully | Passed |
| Create a company account | Company account is created successfully | Passed |
| Sign in with valid personal credentials | Personal home screen appears | Passed |
| Sign in with valid company credentials | Company home screen appears | Passed |
| Sign in with invalid credentials | One clear error message appears | Passed |
| Register a duplicate personal username | Registration is rejected | Passed |
| Register a duplicate personal email | Registration is rejected | Passed |
| Register a duplicate company name | Registration is rejected | Passed |
| Register a duplicate company email | Registration is rejected | Passed |
| Register a personal username matching a company name | Registration is rejected | Passed |
| Register a company name matching a personal username | Registration is rejected | Passed |
| Change a personal username to an existing company name | Profile change is rejected | Passed |
| Change a company name to an existing personal username | Profile change is rejected | Passed |
| Sign out from a personal account | Application returns to the main menu | Passed |
| Sign out from a company account | Application returns to the main menu | Passed |

## Profile Management

| Test | Expected result | Status |
|---|---|---|
| Edit personal name | Updated name appears in the profile and welcome message | Passed |
| Edit personal address | Updated address appears in the profile | Passed |
| Edit personal phone number | Updated phone number appears in the profile | Passed |
| Edit personal email | Updated email appears in the profile | Passed |
| Edit personal username | Updated username is used for future sign-in | Passed |
| Edit company name | Updated company name appears throughout related records | Passed |
| Rename a company with existing announcements | Existing announcements display the new company name | Passed |
| Edit company address | Updated address appears in the company profile | Passed |
| Edit company phone number | Updated phone number appears in the company profile | Passed |
| Edit company email | Updated email appears in the company profile | Passed |

## Service Management

| Test | Expected result | Status |
|---|---|---|
| Add a service | Service is added to the company | Passed |
| Add multiple services | All valid services are added | Passed |
| Reject duplicate service name within one gym | Duplicate is not added | Passed |
| Edit service name | Updated name is displayed | Passed |
| Edit service price | Updated price is displayed | Passed |
| Edit service category | Updated category is displayed | Passed |
| Reject a negative service price | Input is rejected | Passed |
| Attempt an edit with an invalid price | No service fields are changed | Passed |
| Keep cart entries synchronized after a valid edit | Cart reflects the edited service | Passed |
| Keep favourite entries synchronized after a valid edit | Favourites reflect the edited service | Passed |
| Delete a service | Service is removed from the gym | Passed |
| Attempt to edit another gym's service | Operation is rejected | Passed |

## Favourites and Cart

| Test | Expected result | Status |
|---|---|---|
| Add an existing service to favourites | Service is added | Passed |
| Reject duplicate favourite | Duplicate is not added | Passed |
| Remove one favourite | Selected favourite is removed | Passed |
| Remove all favourites | User's favourite list is cleared | Passed |
| Add an existing service to the cart | Service is added | Passed |
| Reject duplicate cart entry | Duplicate is not added | Passed |
| Remove one cart item | Selected item is removed | Passed |
| Remove all cart items | User's cart is cleared | Passed |
| Attempt to add a nonexistent service | Clear error message appears | Passed |

## Checkout and Memberships

| Test | Expected result | Status |
|---|---|---|
| Checkout with items in cart | Order is completed | Passed |
| Checkout with an empty cart | Operation is rejected clearly | Passed |
| Complete an order | Cart items are converted into order records | Passed |
| Create a digital card | Membership card is created for the gym | Passed |
| Add gym to My Gyms | Purchased gym appears in My Gyms | Passed |
| View personal subscription history | Completed purchases are displayed | Passed |
| View company subscription history | Purchases associated with the company are displayed | Passed |

## Announcements

| Test | Expected result | Status |
|---|---|---|
| Open announcement management as a company | Management menu is displayed | Passed |
| Add an announcement | Announcement is stored and displayed | Passed |
| Edit an announcement | Updated title and description are displayed | Passed |
| Delete an announcement | Announcement is removed | Passed |
| View company announcements | Only that company's announcements appear | Passed |
| View relevant personal announcements | Announcements from subscribed or favourited gyms appear | Passed |
| View announcements when none exist | Clear message is displayed | Passed |
| Rename a company after creating an announcement | Announcement displays the updated company name | Passed |

## Input Validation

| Test | Expected result | Status |
|---|---|---|
| Enter text where a number is required | Application does not crash | Passed |
| Enter an invalid menu option | User is prompted again | Passed |
| Enter mismatched passwords | User is prompted to re-enter them | Passed |
| Enter a negative service price | Input is rejected | Passed |
| Enter an invalid announcement ID | Clear error message appears | Passed |

## Regression Tests Added During Repository Cleanup

| Regression | Result | Status |
|---|---|---|
| Cross-account name collision | Personal usernames and company names cannot duplicate one another | Passed |
| Partial service edit after invalid price | Invalid edits leave the original service unchanged | Passed |
| Company rename and announcements | Existing announcements receive the updated company name | Passed |

## Notes

The application is an in-memory educational console prototype.

Data is not persisted after the program closes. Passwords are stored as plain text in memory, and no real payment, email, location, or database integration is implemented.

The generated `out/` directory contains compiled files and must not be committed to the repository.
