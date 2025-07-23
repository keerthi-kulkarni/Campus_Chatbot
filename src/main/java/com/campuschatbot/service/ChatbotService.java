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
            return "\nHi there! Welcome to the Campus Chatbot. Type 'menu' to see what I can assist you with.\n";
        } else if (lowerCaseMessage.contains("how are you")) {
            session.setState("MAIN");
            session.clearSubjects();
            return "\nI'm doing awesome, thanks for asking! How can I help you today? Type 'menu' for options.\n";
        } else if (lowerCaseMessage.equals("bye") || lowerCaseMessage.equals("Bye") || lowerCaseMessage.equals("ok bye")){
            session.setState("MAIN");
            session.clearSubjects();
            return "\nOk Bye. Looking forward to see you again.\n";

        }

            // Handle menu command
        if (lowerCaseMessage.equals("menu") || lowerCaseMessage.equals("options")) {
            session.setState("MAIN");
            session.clearSubjects();
            return "\nI can assist with the following topics:\n" +
                    "\n1. Library information (type 'library')\n" +
                    "\n2. Cafeteria details (type 'cafeteria')\n" +
                    "\n3. Campus events (type 'events')\n" +
                    "\n4. Available courses (type 'courses')\n" +
                    "\n5. Frequently Asked Questions (type 'faqs')\n" +
                    "\nType a topic to learn more!";
        }

        // Allow direct topic navigation from any state
        if (lowerCaseMessage.equals("courses")) {
            session.setState("COURSES");
            session.clearSubjects();
            return "\nCourse-related queries:\n" +
                    "\n1. List of courses\n" +
                    "2. Exam information\n" +
                    "3. Timetable schedules\n" +
                    "Type a number (e.g., '1') to proceed.";
        } else if (lowerCaseMessage.equals("events")) {
            session.setState("EVENTS");
            session.clearSubjects();
            return "Upcoming campus events:\n" +
                    "1. Tech Fest\n" +
                    "2. Career Fair\n" +
                    "3. Guest Lecture Series\n" +
                    "Type the event number (e.g., '1') to get details.";
        } else if (lowerCaseMessage.equals("library")) {
            session.setState("LIBRARY_BOOKS");
            session.clearSubjects();
            return "The campus library is open from 8 AM to 8 PM, located in Building A.\n" +
                    "Would you like to know about books? Type 'yes' or 'no'.";
        } else if (lowerCaseMessage.equals("cafeteria")) {
            session.setState("MAIN");
            session.clearSubjects();
            return "The cafeteria serves meals from 7 AM to 7 PM. Check the menu on the campus website!";
        } else if (lowerCaseMessage.equals("faqs")) {
            session.setState("MAIN");
            session.clearSubjects();
            return "Frequently Asked Questions:\n" +
                    "- How do I register for courses? Visit the campus portal and log in with your student ID.\n" +
                    "- Where can I find the academic calendar? It’s available on the university website under 'Academics'.\n" +
                    "- How do I contact the registrar? Email registrar@campus.edu or visit Building D, Room 101.\n" +
                    "Type 'menu' to explore other topics or a topic name (e.g., 'courses').";
        }

        // Handle state-based responses
        switch (session.getState()) {
            case "COURSES":
                if (lowerCaseMessage.equals("1")) {
                    session.setState("COURSE_LIST");
                    return "Available courses this semester:\n" +
                            "1. Computer Science 101\n" +
                            "2. Mathematics 201\n" +
                            "3. Literature 301\n" +
                            "Type the course number (e.g., '1') to get details or another course option (e.g., '2' for exams).";
                } else if (lowerCaseMessage.equals("2")) {
                    session.setState("COURSES"); // Stay in COURSES state
                    return "Exam Information:\n" +
                            "- Midterms: December 1-5, 2025, check your course portal for specific dates.\n" +
                            "- Finals: December 15-20, 2025, schedules posted in Building A.\n" +
                            "- Exam registration: Complete by November 15, 2025, via the campus portal.\n" +
                            "Type another course option (e.g., '1' for courses, '3' for timetable) or a topic (e.g., 'library').";
                } else if (lowerCaseMessage.equals("3")) {
                    session.setState("COURSES"); // Stay in COURSES state
                    return "Timetable Schedules:\n" +
                            "- Computer Science 101: Mon/Wed 10 AM - 11:30 AM, Building B\n" +
                            "- Mathematics 201: Tue/Thu 1 PM - 2:30 PM, Room 101\n" +
                            "- Literature 301: Monday 9 AM - 12 PM, Classroom 101\n" +
                            "Check the campus portal for your personalized timetable.\n" +
                            "Type another course option (e.g., '1' for courses, '2' for exams) or a topic (e.g., 'library').";
                }
                break;

            case "COURSE_LIST":
                if (lowerCaseMessage.equals("1")) {
                    session.setState("COURSES"); // Return to COURSES state
                    return "Computer Science 101: Intro to Programming\n" +
                            "Instructor: Dr. Smith\n" +
                            "Schedule: Mon/Wed 10 AM - 11:30 AM\n" +
                            "Location: Building B, Room 204\n" +
                            "Type another course option (e.g., '2' for exams, '3' for timetable) or a topic (e.g., 'library').";
                } else if (lowerCaseMessage.equals("2")) {
                    session.setState("COURSES"); // Return to COURSES state
                    return "Mathematics 201: Calculus II\n" +
                            "Instructor: Prof. Jones\n" +
                            "Schedule: Tue/Thu 1 PM - 2:30 PM\n" +
                            "Location: Building C, Room 101\n" +
                            "Type another course option (e.g., '2' for exams, '3' for timetable) or a topic (e.g., 'library').";
                } else if (lowerCaseMessage.equals("3")) {
                    session.setState("COURSES"); // Return to COURSES state
                    return "Literature 301: Modern Fiction\n" +
                            "Instructor: Dr. Brown\n" +
                            "Schedule: Fri 9 AM - 12 PM\n" +
                            "Location: Building A, Room 305\n" +
                            "Type another course option (e.g., '2' for exams, '3' for timetable) or a topic (e.g., 'library').";
                } else if (lowerCaseMessage.equals("1") || lowerCaseMessage.equals("2") || lowerCaseMessage.equals("3")) {
                    // Allow switching to other course options
                    session.setState("COURSES");
                    return handleCoursesOption(lowerCaseMessage);
                }
                break;

            case "EVENTS":
                if (lowerCaseMessage.equals("1")) {
                    session.setState("EVENTS"); // Stay in EVENTS state
                    return "Tech Fest: November 10, 2025\n" +
                            "Location: Main Auditorium\n" +
                            "Details: Workshops, tech talks, and project showcases.\n" +
                            "Type another event number (e.g., '2') or a topic (e.g., 'courses').";
                } else if (lowerCaseMessage.equals("2")) {
                    session.setState("EVENTS"); // Stay in EVENTS state
                    return "Career Fair: November 15, 2025\n" +
                            "Location: Building C\n" +
                            "Details: Meet top employers and explore job opportunities.\n" +
                            "Type another event number (e.g., '1') or a topic (e.g., 'courses').";
                } else if (lowerCaseMessage.equals("3")) {
                    session.setState("EVENTS"); // Stay in EVENTS state
                    return "Guest Lecture Series: Every Friday in November\n" +
                            "Location: Check campus portal for specific rooms\n" +
                            "Details: Industry experts share insights.\n" +
                            "Type another event number (e.g., '1') or a topic (e.g., 'courses').";
                }
                break;

            case "LIBRARY_BOOKS":
                if (lowerCaseMessage.equals("yes")) {
                    session.setState("SELECT_SUBJECTS");
                    session.clearSubjects();
                    return "Available subjects for books:\n" +
                            "1. Computer Science\n" +
                            "2. Mathematics\n" +
                            "3. Literature\n" +
                            "Type the subject number(s) (e.g., '1', '1,2') to see books.";
                } else if (lowerCaseMessage.equals("no")) {
                    session.setState("MAIN");
                    session.clearSubjects();
                    return "Alright, I'm here to help! Type a topic (e.g., 'courses', 'library') or 'menu' to see all options.";
                }
                break;

            case "SELECT_SUBJECTS":
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
                    session.setState("SELECT_SUBJECTS"); // Stay in SELECT_SUBJECTS state
                    response.append("Type another subject number(s) (e.g., '1,2') or a topic (e.g., 'courses').");
                    return response.toString();
                } else {
                    return "Invalid subject selection. Available subjects:\n" +
                            "1. Computer Science\n" +
                            "2. Mathematics\n" +
                            "3. Literature\n" +
                            "Type the subject number(s) (e.g., '1', '1,2') or a topic (e.g., 'courses').";
                }
        }

        // Default response
        session.setState("MAIN");
        session.clearSubjects();
        return "Sorry, I didn’t understand that. Type a topic (e.g., 'courses', 'library') or 'menu' to see all options.";
    }

    private String handleCoursesOption(String option) {
        switch (option) {
            case "1":
                return "Available courses this semester:\n" +
                        "1. Computer Science 101\n" +
                        "2. Mathematics 201\n" +
                        "3. Literature 301\n" +
                        "Type the course number (e.g., '1') to get details or another course option (e.g., '2' for exams).";
            case "2":
                return "Exam Information:\n" +
                        "- Midterms: December 1-5, 2025, check your course portal for specific dates.\n" +
                        "- Finals: December 15-20, 2025, schedules posted in Building A.\n" +
                        "- Exam registration: Complete by November 15, 2025, via the campus portal.\n" +
                        "Type another course option (e.g., '1' for courses, '3' for timetable) or a topic (e.g., 'library').";
            case "3":
                return "Timetable Schedules:\n" +
                        "- Computer Science 101: Mon/Wed 10 AM - 11:30 AM, Building B\n" +
                        "- Mathematics 201: Tue/Thu 1 PM - 2:30 PM, Room 101\n" +
                        "- Literature 301: Monday 9 AM - 12 PM, Classroom 101\n" +
                        "Check the campus portal for your personalized timetable.\n" +
                        "Type another course option (e.g., '1' for courses, '2' for exams) or a topic (e.g., 'library').";
            default:
                return "Invalid option. Course-related queries:\n" +
                        "1. List of courses\n" +
                        "2. Exam information\n" +
                        "3. Timetable schedules\n" +
                        "Type a number (e.g., '1') or a topic (e.g., 'library').";
        }
    }
}