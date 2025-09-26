# Cinema Tickets Spring-Project

This is the backend of my final project for Java Web Spring MVC - August 2024 from SoftUni!

For testing the app should to use this Rest-api:
[Cinema Tickets Movies API - GitHub Repo](https://github.com/BlagoyVelinov/Spring-Project-REST-API)

and the front-end of the project is: [Front-End GitHub Repo](https://github.com/BlagoyVelinov/CinemaTickets-React)

## 📧 User Registration & Email Verification
When a user registers, the system automatically sends a verification email using https://app.mailjet.com as the SMTP provider.
To ensure email sending works correctly, a few environment variables need to be configured.

✅ Required Environment Variables
Variable Name	Description
 - MY_PUBLIC_KEY -> Your Mailjet public API key
 - MY_SECRET_KEY -> Your Mailjet private (secret) API key
 - MAIL_CINEMA_TICKETS -> The sender email address (must be verified in Mailjet)

📨 How to Verify a Sender Email in Mailjet
  - Log in to your Mailjet account
  - Navigate to Sender Addresses
  - Click Add sender address
  - Enter your desired sender email (e.g., cinema@yourdomain.com)
  - Confirm the verification email you’ll receive

🔁 What Happens During User Registration?
 - The user completes the registration form
 - The backend generates an HTML email with an activation link
 - The email is sent to the provided address using Mailjet
 - When the user clicks the link, their account is activated and redirected to the login page

## 🚀 Run with Docker

If you want to run the Cinema Tickets backend using **Docker**, you can pull the prebuilt image from Docker Hub:

```bash
docker pull blagoyvelinov/cinema-tickets
```
 - More information about docker image you can see here: [Cinema Tickets - Docker](https://hub.docker.com/repository/docker/blagoyvelinov/cinema-tickets/general)