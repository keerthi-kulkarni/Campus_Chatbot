package com.campuschatbot.service;

import com.campuschatbot.model.UserSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatbotService {
    private final Map<String, UserSession> userSessions = new HashMap<>(); // Store user sessions by user ID

    public String getResponse(String message, String userId) {
        String lowerCaseMessage = message.toLowerCase().trim();
        UserSession session = userSessions.computeIfAbsent(userId, k -> new UserSession());

        // Handle greetings
        if (lowerCaseMessage.equals("hi") || lowerCaseMessage.equals("hello") || lowerCaseMessage.equals("hey")) {
            session.setState("MAIN");
            session.clearSubjects();
            return "Hi there! Welcome to the Campus Chatbot. Type 'menu' to see what I can assist you with.";
        } else if (lowerCaseMessage.contains("how are you")) {
            session.setState("MAIN");
            session.clearSubjects();
            return "I'm doing awesome, thanks for asking! How can I help you today? Type 'menu' for options.";
        }

        // Handle menu command
        if (lowerCaseMessage.equals("menu") || lowerCaseMessage.equals("options")) {
            session.setState("MAIN");
            session.clearSubjects();
            return "I can assist with the following topics:\n" +
                    "\n1. Library information (type 'library')\n" +
                    "\n2. Cafeteria details (type 'cafeteria')\n" +
                    "\n3. Campus events (type 'events')\n" +
                    "\n4. Available courses (type 'courses')\n" +
                    "\nType a topic to learn more!";
        }

        // Handle state-based responses
        switch (session.getState()) {
            case "MAIN":
                if (lowerCaseMessage.equals("courses")) {
                    session.setState("COURSES");
                    return "Available courses this semester:\n" +
                            "\n1. Computer Science 101\n" +
                            "\n2. Mathematics 201\n" +
                            "\n3. Literature 301\n" +
                            "\nType the course number (e.g., '1') to get details or 'menu' to go back.";
                } else if (lowerCaseMessage.equals("events")) {
                    session.setState("EVENTS");
                    return "Upcoming campus events:\n" +
                            "\n1. Tech Fest\n" +
                            "\n2. Career Fair\n" +
                            "\n3. Guest Lecture Series\n" +
                            "\nType the event number (e.g., '1') to get details or 'menu' to go back.";
                } else if (lowerCaseMessage.equals("library")) {
                    session.setState("LIBRARY_BOOKS");
                    return "The campus library is open from 8 AM to 8 PM, located in Building A.\n" +
                            "Would you like to know about books? Type 'yes' or 'no'.";
                } else if (lowerCaseMessage.equals("cafeteria")) {
                    session.setState("MAIN");
                    return "The cafeteria serves meals from 7 AM to 7 PM. Check the menu on the campus website!";
                }
                break;

            case "COURSES":
                if (lowerCaseMessage.equals("1")) {
                    session.setState("MAIN");
                    return "Computer Science 101: Intro to Programming\n" +
                            "\nInstructor: Dr. Smith\n" +
                            "\nSchedule: Mon/Wed 10 AM - 11:30 AM\n" +
                            "\nLocation: Building B, Room 204\n" +
                            "\nType 'menu' to explore other topics.";
                } else if (lowerCaseMessage.equals("2")) {
                    session.setState("MAIN");
                    return "\nMathematics 201: Calculus II\n" +
                            "\nInstructor: Prof. Jones\n" +
                            "\nSchedule: Tue/Thu 1 PM - 2:30 PM\n" +
                            "\nLocation: Building C, Room 101\n" +
                            "\nType 'menu' to explore other topics.";
                } else if (lowerCaseMessage.equals("3")) {
                    session.setState("MAIN");
                    return "\nLiterature 301: Modern Fiction\n" +
                            "\nInstructor: Dr. Brown\n" +
                            "\nSchedule: Fri 9 AM - 12 PM\n" +
                            "\nLocation: Building A, Room 305\n" +
                            "\nType 'menu' to explore other topics.";
                } else if (lowerCaseMessage.equals("menu")) {
                    session.setState("MAIN");
                    return "\nI can assist with the following topics:\n" +
                            "\n1. Library information (type 'library')\n" +
                            "\n2. Cafeteria details (type 'cafeteria')\n" +
                            "\n3. Campus events (type 'events')\n" +
                            "\n4. Available courses (type 'courses')\n" +
                            "\nType a topic to learn more!";
                }
                break;

            case "EVENTS":
                if (lowerCaseMessage.equals("1")) {
                    session.setState("MAIN");
                    return "\nTech Fest: November 10, 2025\n" +
                            "\nLocation: Main Auditorium\n" +
                            "\nDetails: Workshops, tech talks, and project showcases.\n" +
                            "\nType 'menu' to explore other topics.";
                } else if (lowerCaseMessage.equals("2")) {
                    session.setState("MAIN");
                    return "\nCareer Fair: November 15, 2025\n" +
                            "\nLocation: Building C\n" +
                            "\nDetails: Meet top employers and explore job opportunities.\n" +
                            "\nType 'menu' to explore other topics.";
                } else if (lowerCaseMessage.equals("3")) {
                    session.setState("MAIN");
                    return "\nGuest Lecture Series: Every Friday in November\n" +
                            "\nLocation: Check campus portal for specific rooms\n" +
                            "\nDetails: Industry experts share insights.\n" +
                            "\nType 'menu' to explore other topics.";
                } else if (lowerCaseMessage.equals("menu")) {
                    session.setState("MAIN");
                    return "\nI can assist with the following topics:\n" +
                            "\n1. Library information (type 'library')\n" +
                            "\n2. Cafeteria details (type 'cafeteria')\n" +
                            "\n3. Campus events (type 'events')\n" +
                            "\n4. Available courses (type 'courses')\n" +
                            "\nType a topic to learn more!";
                }
                break;

            case "LIBRARY_BOOKS":
                if (lowerCaseMessage.equals("yes")) {
                    session.setState("SELECT_SUBJECTS");
                    session.clearSubjects();
                    return "Available subjects for books:\n" +
                            "\n1. Computer Science\n" +
                            "\n2. Mathematics\n" +
                            "\n3. Literature\n" +
                            "\nType the subject number(s) (e.g., '1', '1,2') to see books or 'menu' to go back.";
                } else if (lowerCaseMessage.equals("no") || lowerCaseMessage.equals("menu")) {
                    session.setState("MAIN");
                    return "\nI can assist with the following topics:\n" +
                            "\n1. Library information (type 'library')\n" +
                            "\n2. Cafeteria details (type 'cafeteria')\n" +
                            "\n3. Campus events (type 'events')\n" +
                            "\n4. Available courses (type 'courses')\n" +
                            "\nType a topic to learn more!";
                }
                break;

            case "SELECT_SUBJECTS":
                if (lowerCaseMessage.equals("menu")) {
                    session.setState("MAIN");
                    session.clearSubjects();
                    return "\nI can assist with the following topics:\n" +
                            "\n1. Library information (type 'library')\n" +
                            "\n2. Cafeteria details (type 'cafeteria')\n" +
                            "\n3. Campus events (type 'events')\n" +
                            "\n4. Available courses (type 'courses')\n" +
                            "\nType a topic to learn more!";
                } else {
                    String[] subjects = lowerCaseMessage.split(",");
                    session.clearSubjects();
                    StringBuilder response = new StringBuilder("Books for selected subjects:\n");
                    boolean validSelection = false;

                    for (String subject : subjects) {
                        subject = subject.trim();
                        if (subject.equals("1")) {
                            session.addSubject("Computer Science");
                            response.append("- Computer Science: 'Introduction to Algorithms' by Cormen, 'Clean Code' by Martin\n");
                            validSelection = true;
                        } else if (subject.equals("2")) {
                            session.addSubject("Mathematics");
                            response.append("- Mathematics: 'Calculus' by Stewart, 'Linear Algebra' by Strang\n");
                            validSelection = true;
                        } else if (subject.equals("3")) {
                            session.addSubject("Literature");
                            response.append("- Literature: '1984' by Orwell, 'To Kill a Mockingbird' by Lee\n");
                            validSelection = true;
                        }
                    }

                    if (validSelection) {
                        session.setState("MAIN");
                        response.append("Type 'menu' to explore other topics.");
                        return response.toString();
                    } else {
                        return "Invalid subject selection. Available subjects:\n" +
                                "1. Computer Science\n" +
                                "2. Mathematics\n" +
                                "3. Literature\n" +
                                "Type the subject number(s) (e.g., '1', '1,2') or 'menu' to go back.";
                    }
                }
        }

        // Default response
        session.setState("MAIN");
        session.clearSubjects();
        return "Sorry, I didn’t understand that. Type 'menu' to see what I can assist you with!";
    }
}