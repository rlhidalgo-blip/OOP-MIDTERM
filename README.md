# Yab's Salon Mini Operations System

A Java console-based Object-Oriented Programming (OOP) application developed as a midterm laboratory project for Yab's Hair and Beauty Studio.

The application is a simplified version of a larger Salon Management System concept. It focuses on recording salon transactions, automatically calculating staff commissions and Business Share, handling Cash and GCash payments, and displaying transaction and session summaries.

## Features

- Record multiple transactions during a program session.
- Add multiple services and products to a transaction.
- Support fixed-price and variable-price services.
- Assign staff members to services and optionally credit staff for product sales.
- Automatically calculate Staff Commission and Business Share.
- Accept Cash and GCash payments.
- Validate GCash reference numbers (digits only).
- View completed transactions and their details.
- Display a Session Summary with sales totals, Cash/GCash breakdowns, and per-staff commissions.
- Handle invalid menu selections and transaction inputs.

## Technologies Used

- **Programming Language:** Java
- **Application Type:** Console-based application
- **Development Environment:** Visual Studio Code
- **Concepts:** Encapsulation, Inheritance, Polymorphism, Abstraction, and Interfaces

## Requirements

Before running the application, make sure you have:

- Java Development Kit (JDK) installed.
- A terminal or command prompt.
- Visual Studio Code (optional).

You can verify your Java installation by running:

```bash
java -version
javac -version
```

## How to Compile and Run

**Step 1 — Open the project folder**

Download or clone the repository and open the folder containing the Java source files.

**Step 2 — Open a terminal**

Open the terminal in Visual Studio Code or use your computer's command prompt.

Make sure you are in the directory containing `SalonApp.java`.

**Step 3 — Compile the Java files**

```bash
javac *.java
```

This compiles the Java source files into `.class` files.

**Step 4 — Run the application**

```bash
java SalonApp
```

The application will display the main menu:

```text
===== YAB'S SALON MINI OPERATIONS SYSTEM =====

1. Record Transaction
2. View Transactions
3. View Session Summary
4. Exit
```

Choose an option by entering the corresponding number.

## Sample Business Rules

| Item | Staff Commission | Business Share |
|---|---|---|
| Haircut | 50% | 50% |
| Hair Coloring, Treatment, Rebonding | 40% | 60% |
| Hair Wax (credited to staff) | PHP 50.00 | Selling price minus PHP 50.00 |
| Hair Wax (no staff credited) | PHP 0.00 | Full selling price |

The application uses the term **Business Share**, not profit, because operating expenses are not included in the calculations.

## OOP Design

The application demonstrates object-oriented programming through:

- **Encapsulation:** Private attributes with controlled access through methods.
- **Inheritance:** `ServiceSale` and `ProductSale` extend the abstract `SaleItem` class.
- **Polymorphism:** Different sale items implement their own commission calculations through overridden methods.
- **Abstraction:** `SaleItem` defines common behaviors for different types of sale items.
- **Interface:** `CashPayment` and `GCashPayment` implement `PaymentMethod`.

## Limitations

This application was developed for educational purposes and is not a complete production Salon Management System.

- Transaction data is stored only during the current program session.
- No database or file persistence.
- No authentication or user accounts.
- No inventory or appointment management.
- No customer profiles.
- No transaction editing, deletion, or voiding.
- No actual GCash payment integration.
- No graphical user interface.

## Academic Information

**Project:** Yab's Salon Mini Operations System  
**Course:** Object-Oriented Programming  
**Institution:** FEU Institute of Technology  
**Program:** BS Computer Science — Artificial Intelligence Specialization  
**Project Type:** Midterm Laboratory Activity

## Acknowledgment

ChatGPT was used as a brainstorming, learning, design-review, debugging, and testing assistant during development. The project scope and design decisions were evaluated and finalized as part of the student's learning process.