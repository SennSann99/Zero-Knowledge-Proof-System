# Zero-Knowledge-Proof System

A Java-based implementation of a **Zero-Knowledge Proof (ZKP)** protocol designed for secure data exchange without revealing sensitive information.  
This project demonstrates how privacy-preserving verification can be achieved using cryptographic principles.

---

## 🔍 Overview
The system consists of three main components:
- **ClientProverFX**: Sends cryptographic proofs to the verifier.
- **ClientVerifierFX**: Validates proofs without accessing underlying data.
- **Server**: Mediates secure communication between prover and verifier.

---

## 🧠 Features
- Implementation of ZKP using **BigInteger** and custom prime-based cryptography.
- Secure client-server communication using **Java NIO Socket**.
- Persistent data storage with **MongoDB**.
- Graphical user interface built with **JavaFX**.

---

## ⚙️ Tech Stack
**Languages:** Java  
**Frameworks / Libraries:** JavaFX, MongoDB Driver, Java NIO  
**Encryption:** Custom BigInteger-based modular arithmetic  

---

## 🚀 How to Run
1. Install [JavaFX SDK](https://openjfx.io/).  
2. Launch the `Server` module.  
3. Run `ClientProverFX` and `ClientVerifierFX` instances.  
4. Observe the proof exchange and verification process in real time.

---

## 📄 Academic Context
This project was developed as part of a bachelor’s thesis on  
**“Design of a Data Exchange Model Using Zero-Knowledge Proofs.”**

---

## 👤 Author
**Sen(Tom Ford)**  
Email: cheonjiwon@tutamail.com
