
/**
 * Name: Roya Savoj
 * ID: A16644834
 * Email: rsavoj@ucsd.edu
 * Sources used: Tutors, Zybooks, and Lecture Slides
 * 
 * This file outlines the behavior of the ticket objects. These objects are 
 * used in the autograder implementation. These tickets are compared using 
 * their instance variables 
 */

import java.util.HashMap;

/**
 * This class creates a Ticket object to be used as a
 * node for a priority queue. Contains a static HashMap to maintain
 * the priority ordering.
 */
public class Ticket implements Comparable<Ticket> {

    public static final String ENVIRONMENT_SETUP = "Environment Setup";
    public static final String DEBUGGING = "Debgugging";
    public static final String CONCEPT_DOUBTS = "Conceptual Doubt";
    public static final String OTHERS = "Others";

    public static final String WAITING = "Waiting";
    public static final String PROCESSING = "Accepted";
    public static final String RESOLVED = "Resolved";
    public static final String DATE_FORMAT = "HH:mm:ss";

    public static HashMap<String, Integer> orderMap;

    String studentName;
    String ticketType;

    String ticketStatus;

    Long createdAt;
    Long resolvedAt;
    int ticketNumber;

    /**
     * Constructor that initializes a Ticket with the name of the
     * student that created it and type of the ticket
     * 
     * @param studentName Name of student that created the ticket
     * @param ticketType  Type of the ticket
     */
    public Ticket(String studentName, String ticketType) {
        this.studentName = studentName;
        this.ticketType = ticketType;
        this.ticketStatus = WAITING;
    }

    /**
     * Sets the studentName
     * 
     * @param studentName Name of student that created the ticket
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Sets the ticket's status in the Queue
     * The status can be Waiting, Accepted, or Resolved.
     * 
     * @param ticketStatus Current status of the ticket in queue
     */
    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    /**
     * Sets the type of the ticket.
     * Type can be Environment setup,
     * Debugging, Conceptual Doubt, or Others
     * 
     * @param ticketType Type of the ticket
     */
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * Sets the ticket number
     * 
     * @param ticketNumber Unique ID in order of creation
     */
    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    /**
     * Sets the second at which the ticket was created.
     * 
     * @param createdAt Second at which ticket was created
     */
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Sets the second at which the ticket was resolved.
     * 
     * @param resolvedAt Second at which ticket was resolved
     */
    public void setResolvedAt(Long resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    /**
     * Returns name of student that created the ticket
     * 
     * @return Student name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Returns type of ticket
     * 
     * @return type of ticket
     */
    public String getTicketType() {
        return ticketType;
    }

    /**
     * Returns the time at which ticket was created
     * 
     * @return second at which ticket was created
     */
    public Long getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the status of the ticket in the queue
     * 
     * @return status of ticket
     */
    public String getTicketStatus() {
        return ticketStatus;
    }

    /**
     * Returns the ticket Number
     * 
     * @return Unique ID in the order it was created
     */
    public int getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Returns time at which ticket was resolved.
     * 
     * @return seconds at which ticket was resolved
     */
    public Long getResolvedAt() {
        return resolvedAt;
    }

    /**
     * Sets the priority order
     * 
     * @param orderMap mapping of TicketType to priority order
     */
    public static void setOrderMap(HashMap<String, Integer> orderMap) {
        Ticket.orderMap = orderMap;
    }

    /**
     * This helper method compares two tickets using their
     * ticketType and their CreatedAt value
     * 
     * @param other the other ticket being compared
     */
    @Override
    public int compareTo(Ticket other) {
        setOrderMap(orderMap);
        // calls helper method to compare ticketTypes
        int compareTicketType = compareTickets(this, other);
        int compareCreatedAt = this.createdAt.compareTo(other.createdAt);
        if (compareTicketType == 0) {
            // returns the compareTo of the createdAt variables if the Types are
            // equal
            return compareCreatedAt;
        }
        return compareTicketType;

    }

    /**
     * Helper method to compare two tickets
     * 
     * @param ticket1 the first ticket being compared
     * @param ticket2 the second ticket being compared
     * @return the integer compare to value returned -1 if ticket1s type is
     *         less than ticket 2. 0 if the two ticket types are equal or one
     *         otherwise
     */
    private int compareTickets(Ticket ticket1, Ticket ticket2) {

        int ticket1Value = orderMap.get(ticket1.ticketType);
        int ticket2Value = orderMap.get(ticket2.ticketType);
        // returns 0 if ticket types are equal
        if (ticket1Value == ticket2Value) {
            return 0;
        }
        // returns -1 if ticket 1 has higher prority
        else if (ticket1Value < ticket2Value) {
            return -1;
        }
        return 1;
    }

}