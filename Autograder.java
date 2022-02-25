/**
 * Simulation of Autograder Ticket System. 
 * Used for PA7 Part 2 in CSE 12
 */
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;  
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.border.BevelBorder;

/**
 * Represents the Autograder Ticket System with a Ticket Priority Queue,
 * Resolved Tickets stack and other Queue Statistics
 */
public class Autograder extends javax.swing.JFrame {

    public static final int BUSY = 1;
    public static final int AVAILABLE = 0;
    public static final int ADD_TICKET_DELAY = 4000;
    public static final int PROCESS_TICKET_DELAY = 7000;
    public static final int REMOVE_TICKET_DELAY = 13000;
    public static final int UPDATE_STATISTICS_DELAY = 3000;
    public static final int UPDATE_CLOCK_DELAY = 1000;
    public static final String DATE_FORMAT = "HH:mm:ss";
    public static final String TICKETS_RESOLVED_MESSAGE 
            = "Tickets Resolved: %o";
    public static final String STUDENT_NAME_LABEL = "Student Name: %s";
    public static final String STATUS_LABEL = "Status: %s";
    public static final String TYPE_LABEL = "Type: %s";
    public static final String TICKET_NUMBER_LABEL = "Ticket Number: %o";
    public static final String TICKET_CREATED_AT_LABEL = 
            "Created at: %o s";
    public static final String TICKET_RESOLVED_AT_LABEL = 
            "Resolved at: %o s";
    public static final long SECONDS_CONVERSION = 1000;
    public static final String SECONDS_UNIT = "%o s";
    
    public static final int FIRST_PRIORITY = 1;
    public static final int SECOND_PRIORITY = 2;
    public static final int THIRD_PRIORITY = 3;
    public static final int FOURTH_PRIORITY = 4;
    


    MyPriorityQueue<Ticket> ticketQueue;    
    
    
    ArrayList<Ticket> tickets;
    Stack<Ticket> historyStack;
    Ticket currTicket;
    public int status;
    public int ticketNumber = 0;
    public int ticketsResolved = 0;
    public Long seconds;
    Timer clockTime;
    
 
    public Autograder(){
        initComponents();
        ticketQueue = new MyPriorityQueue<>();
        tickets = new ArrayList<>();
        historyStack = new Stack<>();
        seconds = Long.valueOf("0");
        createTicketsList();
        startClock();
        makeQueuePanels();
        setTicketTypePriority();
               
    }
    
    
    //TODO: Play around with different priorities for different ticket types
    public void setTicketTypePriority(){
        HashMap<String, Integer> orderMap = new HashMap<>();
        orderMap.put(Ticket.ENVIRONMENT_SETUP, Autograder.FIRST_PRIORITY);
        orderMap.put(Ticket.DEBUGGING, Autograder.SECOND_PRIORITY);
        orderMap.put(Ticket.CONCEPT_DOUBTS, Autograder.THIRD_PRIORITY);
        orderMap.put(Ticket.OTHERS, Autograder.FOURTH_PRIORITY);
        Ticket.setOrderMap(orderMap);
    }
    
    
    private void startClock(){
        clockTime = new Timer(Autograder.UPDATE_CLOCK_DELAY, updateClockAction);
        clockTime.start(); 
    }
    protected void makeQueuePanels() {
        ticketPanel.setMaximumSize(new java.awt.Dimension(340,417));
        ticketPanel.setPreferredSize(new java.awt.Dimension(340,417));
        ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));
        resolvedTicketsPanel.setMaximumSize(new java.awt.Dimension(340,417));
        resolvedTicketsPanel.setPreferredSize(new java.awt.Dimension(340,417));
        resolvedTicketsPanel.setLayout(new BoxLayout(resolvedTicketsPanel,
                BoxLayout.Y_AXIS));
    }
    
    
    ActionListener updateClockAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            clockTimeLabel.setText(String
                    .format(Autograder.SECONDS_UNIT, ++seconds));             
        }
    };
    
    ActionListener processTicketsAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {            
            if(status == Autograder.AVAILABLE && ticketQueue.getLength()>0){
                currTicket = ticketQueue.pop();
                currTicket.setTicketStatus(Ticket.PROCESSING);
                status = Autograder.BUSY;
                printQueue();
            }
            
        }
    };
    
    ActionListener removeTicketsAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
            if(status == Autograder.BUSY){
                Ticket resolvedTicket = currTicket;
                currTicket = null;
                resolvedTicket.setTicketStatus(Ticket.RESOLVED);
                resolvedTicket.setResolvedAt(seconds);
                historyStack.push(resolvedTicket);
                
                ticketsResolvedLabel.setText(String
                        .format(Autograder.TICKETS_RESOLVED_MESSAGE, 
                                ++ticketsResolved));
            }
            status = Autograder.AVAILABLE;
            printQueue();
            printResolvedTickets();
            
        }
    };
    
    ActionListener addTicketsAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                if(ticketNumber<tickets.size()){
                    Ticket currTicket = tickets.get(ticketNumber);
                    currTicket.setCreatedAt(seconds);
                    currTicket.setTicketNumber(ticketNumber+1);
                    ticketQueue.push(currTicket);
                    ticketNumber++;
                    printQueue();
                    
                }
            
            
        }
    };
    
    //Do not change these
    private void createTicketsList(){
        this.tickets.add(new Ticket("Eman", Ticket.ENVIRONMENT_SETUP));
        this.tickets.add(new Ticket("Bill", Ticket.DEBUGGING));
        this.tickets.add(new Ticket("Sanjana", Ticket.OTHERS));
        this.tickets.add(new Ticket("Jeff", Ticket.CONCEPT_DOUBTS));
        this.tickets.add(new Ticket("Andrew", Ticket.ENVIRONMENT_SETUP));

    }
    
    private void printQueue(){
        ticketsInQueueLabel.setText("Tickets in Queue: " 
                + ticketQueue.getLength());
        ticketPanel.removeAll();
        if(status == Autograder.BUSY){
            ticketPanel.add(createTicketPanel(currTicket));
            ticketsInQueueLabel.setText("Tickets in Queue: " 
                    + (ticketQueue.getLength()+1));
        }
        for(int i=0; i<ticketQueue.heap.size(); i++){
            Ticket ticket = ticketQueue.heap.data.get(i);
            if(!ticket.getTicketStatus().equals(Ticket.PROCESSING)){
                
            }
            ticketPanel.add(createTicketPanel(ticket));
            
            setVisible(true);
        }
        System.out.println();
        ticketPanel.repaint();
    }
    
    
      private void printResolvedTickets(){
        resolvedTicketsPanel.removeAll();
        for(int i=historyStack.size()-1; i>=0; i--){
            Ticket ticket = historyStack.get(i);
            resolvedTicketsPanel.add(createResolvedTicketPanel(ticket));
            setVisible(true);
        }
        resolvedTicketsPanel.repaint();
    }
    
    
    
    private JPanel createTicketPanel(Ticket ticket){
        JPanel currTicketPanel = new JPanel();
        currTicketPanel.setLayout(new GridLayout(2,2));
        JLabel studentName = new JLabel(String.format(Autograder
                .STUDENT_NAME_LABEL,ticket.getStudentName()));
        JLabel ticketStatus = new JLabel(String.format(Autograder
                .STATUS_LABEL, ticket.getTicketStatus())); 
        JLabel ticketType = new JLabel(String.format(Autograder
                .TYPE_LABEL, ticket.getTicketType())); 
        JLabel ticketCreatedNumber = new JLabel(String.format(Autograder
                .TICKET_NUMBER_LABEL, ticket.getTicketNumber()));
        
        
        currTicketPanel.add(ticketCreatedNumber);
        currTicketPanel.add(ticketStatus);
        currTicketPanel.add(ticketType);
        currTicketPanel.add(studentName);
        currTicketPanel.setBorder(javax.swing.BorderFactory
                .createSoftBevelBorder(BevelBorder.RAISED, 
                        Color.lightGray, Color.darkGray));
        
        currTicketPanel.setPreferredSize(new java.awt.Dimension(340,80));
        currTicketPanel.setMaximumSize(new java.awt.Dimension(340,80));
        currTicketPanel.setMinimumSize(new java.awt.Dimension(340,80));
        
        setPanelColor(currTicketPanel, ticket);
        return currTicketPanel;
    }
    
    private JPanel createResolvedTicketPanel(Ticket ticket){
        if(ticket == null){
            return null;
        }
        JPanel currTicketPanel = new JPanel();
        currTicketPanel.setLayout(new GridLayout(3,2));
        JLabel studentName = new JLabel(String.format(Autograder
                .STUDENT_NAME_LABEL,ticket.getStudentName()));
        JLabel ticketStatus = new JLabel(String.format(Autograder
                .STATUS_LABEL, ticket.getTicketStatus())); 
        JLabel ticketType = new JLabel(String.format(Autograder
                .TYPE_LABEL, ticket.getTicketType())); 
        
        
        JLabel ticketCreated = new JLabel(String
                .format(Autograder.TICKET_CREATED_AT_LABEL, 
                    ticket.getCreatedAt()));
        
        JLabel ticketCreatedNumber = new JLabel(String.format(Autograder
                .TICKET_NUMBER_LABEL, ticket.getTicketNumber()));
        
        JLabel ticketResolved = new JLabel(String
                .format(Autograder.TICKET_RESOLVED_AT_LABEL, 
                ticket.getResolvedAt()));
        
        
        currTicketPanel.add(ticketCreatedNumber);
        currTicketPanel.add(ticketStatus);
        currTicketPanel.add(ticketType);
        currTicketPanel.add(studentName);
        currTicketPanel.add(ticketCreated);
        currTicketPanel.add(ticketResolved);
        currTicketPanel.setBorder(javax.swing.BorderFactory
                .createSoftBevelBorder(BevelBorder.RAISED, 
                        Color.lightGray, Color.GRAY));
        
        currTicketPanel.setPreferredSize(new java.awt.Dimension(340,80));
        currTicketPanel.setMaximumSize(new java.awt.Dimension(340,80));
        currTicketPanel.setMinimumSize(new java.awt.Dimension(340,80));
        
        setPanelColor(currTicketPanel, ticket);
        return currTicketPanel;
    }
    
    
    
    private void setPanelColor(JPanel ticketPanel, Ticket ticket){
        String ticket_type = ticket.getTicketType();
        if(ticket_type.equals(Ticket.ENVIRONMENT_SETUP)){
            ticketPanel.setBackground(new Color(235, 64, 52, 60));
        }
        else if(ticket_type.equals(Ticket.DEBUGGING)){
            ticketPanel.setBackground(new Color(235, 153, 52,60));
        }
        else if(ticket_type.equals(Ticket.CONCEPT_DOUBTS)){
            ticketPanel.setBackground(new Color(235, 226, 52,60));
        }
        else{
            ticketPanel.setBackground(new Color(201, 235, 52,60));
        }
    }
    
     ActionListener updateStatistics = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
            
        
            if(ticketQueue.getLength()<=0){
                return;
            }    
            long sumEnvironmentWaitTime = 0;
            long sumDebuggingWaitTime = 0;
            long sumConceptWaitTime = 0;
            long sumOthersWaitTime = 0;
            int numESTickets = 0;
            int numDebugTickets = 0;
            int numConceptTickets = 0;
            int numOthersTickets = 0;
            for(int i=0; i<ticketQueue.heap.size(); i++){
                Ticket ticket = ticketQueue.heap.data.get(i);
                if(ticket.ticketStatus.equals(Ticket.WAITING)){
                    long diff = Math.abs(seconds - 
                            ticket.getCreatedAt());
                    if(ticket.ticketType.equals(Ticket.ENVIRONMENT_SETUP)){
                        sumEnvironmentWaitTime += diff;
                        numESTickets++;
                    }
                    if(ticket.ticketType.equals(Ticket.DEBUGGING)){
                        sumDebuggingWaitTime += diff;
                        numDebugTickets++;
                    }
                    if(ticket.ticketType.equals(Ticket.CONCEPT_DOUBTS)){
                        sumConceptWaitTime += diff;
                        numConceptTickets++;
                    }
                    if(ticket.ticketType.equals(Ticket.OTHERS)){
                        sumOthersWaitTime += diff;
                        numOthersTickets++;
                    }

                }
            }

            if(numESTickets>0){
                ESTimeLabel.setText(String
                        .format(Autograder.SECONDS_UNIT,
                                sumEnvironmentWaitTime/numESTickets));
            }
            if(numDebugTickets>0){
                DebugTimeLabel.setText(String.format(Autograder.SECONDS_UNIT,
                        sumDebuggingWaitTime/numDebugTickets));
            }
            if(numConceptTickets>0){
                ConceptTimeLabel.setText(String.format(Autograder.SECONDS_UNIT,
                        sumConceptWaitTime/numConceptTickets));
            }
            if(numOthersTickets>0){
                OthersTimeLabel.setText(String.format(Autograder.SECONDS_UNIT,
                        sumOthersWaitTime/numOthersTickets)); 
            }     

            repaint();

        }
        
    };


    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        startButton.setEnabled(false);
        Timer addTicketTimer = 
                new Timer(Autograder.ADD_TICKET_DELAY, addTicketsAction);
        Timer processTicketTimer = 
                new Timer(Autograder.PROCESS_TICKET_DELAY,
                        processTicketsAction); 
        Timer removeTicketTimer = 
                new Timer(Autograder.REMOVE_TICKET_DELAY, removeTicketsAction);
        Timer updateStats = 
                new Timer(Autograder.UPDATE_STATISTICS_DELAY, updateStatistics);
        updateStats.start();
        addTicketTimer.start();
        processTicketTimer.start();
        removeTicketTimer.start();

    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {         
                Autograder ag = new Autograder();
                ag.setVisible(true);               
                                
            }
        });
    } 
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        analyticsPanel = new javax.swing.JPanel();
        ESLabel = new javax.swing.JLabel();
        debuggingLabel = new javax.swing.JLabel();
        conceptLabel = new javax.swing.JLabel();
        othersLabel = new javax.swing.JLabel();
        AvgWaitTime = new javax.swing.JLabel();
        ESWaitTime = new javax.swing.JLabel();
        DebugWaitTime = new javax.swing.JLabel();
        ConceptWaitTime = new javax.swing.JLabel();
        OthersWaitTime = new javax.swing.JLabel();
        resolvedTicketsPanel = new javax.swing.JPanel();
        resolvedTicketsTitle = new javax.swing.JLabel();
        ticketsResolvedLabel = new javax.swing.JLabel();
        ticketsInQueueLabel = new javax.swing.JLabel();
        estimatedWaitTimeTitle = new javax.swing.JLabel();
        ESTimeLabel = new javax.swing.JLabel();
        DebugTimeLabel = new javax.swing.JLabel();
        ConceptTimeLabel = new javax.swing.JLabel();
        OthersTimeLabel = new javax.swing.JLabel();
        autograderSimulationLabel = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        timeLabel = new javax.swing.JLabel();
        ticketPanel = new javax.swing.JPanel();
        ticketQueueTitle = new javax.swing.JLabel();
        clockTimeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Autograder Simulation");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setResizable(false);

        ESLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 1, 11)); // NOI18N
        ESLabel.setForeground(new java.awt.Color(51, 51, 51));
        ESLabel.setText("Environment setup: ");

        debuggingLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 1, 11)); // NOI18N
        debuggingLabel.setForeground(new java.awt.Color(51, 51, 51));
        debuggingLabel.setText("Debugging:");

        conceptLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 1, 11)); // NOI18N
        conceptLabel.setForeground(new java.awt.Color(51, 51, 51));
        conceptLabel.setText("Conceptual:");

        othersLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 1, 11)); // NOI18N
        othersLabel.setForeground(new java.awt.Color(51, 51, 51));
        othersLabel.setText("Others:");

        resolvedTicketsPanel.setBackground(new java.awt.Color(255, 255, 255));
        resolvedTicketsPanel.setBorder(
            javax.swing.BorderFactory
            .createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout resolvedTicketsPanelLayout 
        = new javax.swing.GroupLayout(resolvedTicketsPanel);
        resolvedTicketsPanel.setLayout(resolvedTicketsPanelLayout);
        resolvedTicketsPanelLayout.setHorizontalGroup(
            resolvedTicketsPanelLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        resolvedTicketsPanelLayout.setVerticalGroup(
            resolvedTicketsPanelLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );

        resolvedTicketsTitle.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 1, 14)); // NOI18N
        resolvedTicketsTitle.setForeground(new java.awt.Color(51, 51, 51));
        resolvedTicketsTitle.setHorizontalAlignment(
            javax.swing.SwingConstants.CENTER);
        resolvedTicketsTitle.setText("Resolved Tickets");

        ticketsResolvedLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 1, 12)); // NOI18N
        ticketsResolvedLabel.setForeground(new java.awt.Color(51, 51, 51));
        ticketsResolvedLabel.setText("Tickets Resolved:");

        ticketsInQueueLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 1, 12)); // NOI18N
        ticketsInQueueLabel.setForeground(new java.awt.Color(51, 51, 51));
        ticketsInQueueLabel.setText("Tickets in Queue:");

        estimatedWaitTimeTitle.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 1, 12)); // NOI18N
        estimatedWaitTimeTitle.setForeground(new java.awt.Color(51, 51, 51));
        estimatedWaitTimeTitle.setText("Estimated Wait Times (in seconds)");

        ESTimeLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 2, 11)); // NOI18N
        ESTimeLabel.setForeground(new java.awt.Color(51, 51, 51));
        ESTimeLabel.setText("0 s");

        DebugTimeLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 2, 11)); // NOI18N
        DebugTimeLabel.setForeground(new java.awt.Color(51, 51, 51));
        DebugTimeLabel.setText("0 s");

        ConceptTimeLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 2, 11)); // NOI18N
        ConceptTimeLabel.setForeground(new java.awt.Color(51, 51, 51));
        ConceptTimeLabel.setText("0 s");

        OthersTimeLabel.setFont(
            new java.awt.Font("Lucida Sans Typewriter", 2, 11)); // NOI18N
        OthersTimeLabel.setForeground(new java.awt.Color(51, 51, 51));
        OthersTimeLabel.setText("0 s");

        javax.swing.GroupLayout analyticsPanelLayout = 
        new javax.swing.GroupLayout(analyticsPanel);
        analyticsPanel.setLayout(analyticsPanelLayout);
        analyticsPanelLayout.setHorizontalGroup(
            analyticsPanelLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analyticsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resolvedTicketsPanel, 
                javax.swing.GroupLayout.PREFERRED_SIZE,
                 javax.swing.GroupLayout.DEFAULT_SIZE, 
                 javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(analyticsPanelLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(analyticsPanelLayout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(OthersWaitTime))
                    .addGroup(analyticsPanelLayout.createSequentialGroup()
                        .addGap(415, 415, 415)
                        .addComponent(ConceptWaitTime))
                    .addGroup(analyticsPanelLayout.createSequentialGroup()
                        .addGap(361, 361, 361)
                        .addComponent(DebugWaitTime))
                    .addGroup(analyticsPanelLayout.createSequentialGroup()
                        .addGap(403, 403, 403)
                        .addComponent(ESWaitTime))
                    .addGroup(analyticsPanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(analyticsPanelLayout
                        .createParallelGroup(javax
                        .swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ticketsResolvedLabel)
                            .addComponent(ticketsInQueueLabel)
                            .addGroup(analyticsPanelLayout
                            .createSequentialGroup()
                                .addGroup(analyticsPanelLayout
                                .createParallelGroup(javax.swing
                                .GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment
                                    .TRAILING, analyticsPanelLayout
                                    .createSequentialGroup()
                                        .addComponent(ESLabel)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout
                                    .Alignment.TRAILING, analyticsPanelLayout
                                    .createSequentialGroup()
                                        .addGroup(analyticsPanelLayout
                                        .createParallelGroup(javax.swing
                                        .GroupLayout.Alignment.TRAILING)
                                            .addComponent(debuggingLabel)
                                            .addComponent(conceptLabel)
                                            .addComponent(othersLabel))
                                        .addGap(26, 26, 26)))
                                .addGroup(analyticsPanelLayout
                                .createParallelGroup(javax.swing.GroupLayout
                                .Alignment.LEADING)
                                    .addComponent(OthersTimeLabel)
                                    .addComponent(DebugTimeLabel)
                                    .addComponent(ConceptTimeLabel)
                                    .addComponent(ESTimeLabel)))))
                    .addGroup(analyticsPanelLayout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(AvgWaitTime))
                    .addGroup(analyticsPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(estimatedWaitTimeTitle))))
            .addGroup(analyticsPanelLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(resolvedTicketsTitle))
        );
        analyticsPanelLayout.setVerticalGroup(
            analyticsPanelLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analyticsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                 Short.MAX_VALUE)
                .addComponent(ticketsInQueueLabel)
                .addGap(18, 18, 18)
                .addComponent(ticketsResolvedLabel)
                .addGap(12, 12, 12)
                .addComponent(AvgWaitTime)
                .addGap(47, 47, 47)
                .addComponent(ESWaitTime)
                .addGap(42, 42, 42)
                .addComponent(estimatedWaitTimeTitle)
                .addGap(18, 18, 18)
                .addComponent(DebugWaitTime)
                .addGap(14, 14, 14)
                .addGroup(analyticsPanelLayout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ESLabel)
                    .addComponent(ESTimeLabel))
                .addGap(16, 16, 16)
                .addComponent(ConceptWaitTime)
                .addGap(14, 14, 14)
                .addGroup(analyticsPanelLayout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(debuggingLabel)
                    .addComponent(DebugTimeLabel))
                .addGap(15, 15, 15)
                .addComponent(OthersWaitTime)
                .addGap(15, 15, 15)
                .addGroup(analyticsPanelLayout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conceptLabel)
                    .addComponent(ConceptTimeLabel))
                .addGap(31, 31, 31)
                .addGroup(analyticsPanelLayout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(othersLabel)
                    .addComponent(OthersTimeLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, 
                Short.MAX_VALUE))
            .addGroup(analyticsPanelLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(resolvedTicketsTitle, 
                javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                 javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle
                .ComponentPlacement.RELATED)
                .addComponent(resolvedTicketsPanel,
                 javax.swing.GroupLayout.PREFERRED_SIZE, 
                 javax.swing.GroupLayout.DEFAULT_SIZE, 
                 javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        autograderSimulationLabel.setFont(new java.awt.Font(
            "Lucida Sans Typewriter", 1, 18)); // NOI18N
        autograderSimulationLabel.setForeground(new java.awt.Color(
            51, 51, 51));
        autograderSimulationLabel.setHorizontalAlignment(javax.swing
        .SwingConstants.CENTER);
        autograderSimulationLabel.setText("Autograder Simulation");

        startButton.setFont(new java.awt.Font("Lucida Sans Typewriter"
        , 1, 14)); // NOI18N
        startButton.setForeground(new java.awt.Color(51, 51, 51));
        startButton.setText("Start");
        startButton.setBorder(javax.swing.BorderFactory.createBevelBorder(
            javax.swing.border.BevelBorder.RAISED));
        startButton.setFocusPainted(false);
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        timeLabel.setFont(new java.awt.Font("Lucida Sans Typewriter", 
        1, 11)); // NOI18N
        timeLabel.setForeground(new java.awt.Color(102, 102, 102));
        timeLabel.setText("Time:");

        ticketPanel.setBackground(new java.awt.Color(255, 255, 255));
        ticketPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(
            javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout ticketPanelLayout = new javax
        .swing.GroupLayout(ticketPanel);
        ticketPanel.setLayout(ticketPanelLayout);
        ticketPanelLayout.setHorizontalGroup(
            ticketPanelLayout.createParallelGroup(javax
            .swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        ticketPanelLayout.setVerticalGroup(
            ticketPanelLayout.createParallelGroup(javax
            .swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );

        ticketQueueTitle.setFont(new java.awt.Font("Lucida Sans Typewriter",
         1, 14)); // NOI18N
        ticketQueueTitle.setForeground(new java.awt.Color(51, 51, 51));
        ticketQueueTitle.setText("Ticket Queue");

        javax.swing.GroupLayout layout = new javax.swing
        .GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing
            .GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment
            .TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax
                .swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(autograderSimulationLabel)
                        .addGap(18, 18, 18)
                        .addComponent(startButton, 
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                         58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing
                        .LayoutStyle.ComponentPlacement.RELATED, 
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(timeLabel)
                        .addPreferredGap(javax.swing
                        .LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clockTimeLabel, 
                        javax.swing.GroupLayout.PREFERRED_SIZE, 
                        78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax
                        .swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(ticketPanel,
                                 javax.swing.GroupLayout.PREFERRED_SIZE,
                                  javax.swing.GroupLayout.DEFAULT_SIZE, 
                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(146, 146, 146)
                                .addComponent(ticketQueueTitle)))
                        .addPreferredGap(javax.swing.LayoutStyle
                        .ComponentPlacement.RELATED, 
                        14, Short.MAX_VALUE)
                        .addComponent(analyticsPanel,
                         javax.swing.GroupLayout.PREFERRED_SIZE,
                          659, 
                          javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing
            .GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout
            .Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax
                .swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(autograderSimulationLabel, 
                    javax.swing.GroupLayout.DEFAULT_SIZE, 
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(startButton, 
                    javax.swing.GroupLayout.PREFERRED_SIZE, 
                    29, 
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeLabel)
                    .addComponent(clockTimeLabel))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax
                .swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ticketQueueTitle)
                        .addGap(11, 11, 11)
                        .addComponent(ticketPanel, 
                        javax.swing.GroupLayout.PREFERRED_SIZE, 
                        javax.swing.GroupLayout.DEFAULT_SIZE, 
                        javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(analyticsPanel, 
                    javax.swing.GroupLayout.PREFERRED_SIZE, 
                    javax.swing.GroupLayout.DEFAULT_SIZE, 
                    javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>                        

    
    

    // Variables declaration - do not modify                     
    private javax.swing.JLabel AvgWaitTime;
    private javax.swing.JLabel ConceptTimeLabel;
    private javax.swing.JLabel ConceptWaitTime;
    private javax.swing.JLabel DebugTimeLabel;
    private javax.swing.JLabel DebugWaitTime;
    private javax.swing.JLabel ESLabel;
    private javax.swing.JLabel ESTimeLabel;
    private javax.swing.JLabel ESWaitTime;
    private javax.swing.JLabel OthersTimeLabel;
    private javax.swing.JLabel OthersWaitTime;
    private javax.swing.JPanel analyticsPanel;
    private javax.swing.JLabel autograderSimulationLabel;
    private javax.swing.JLabel clockTimeLabel;
    private javax.swing.JLabel conceptLabel;
    private javax.swing.JLabel debuggingLabel;
    private javax.swing.JLabel estimatedWaitTimeTitle;
    private javax.swing.JLabel othersLabel;
    private javax.swing.JPanel resolvedTicketsPanel;
    private javax.swing.JLabel resolvedTicketsTitle;
    private javax.swing.JButton startButton;
    private javax.swing.JPanel ticketPanel;
    private javax.swing.JLabel ticketQueueTitle;
    private javax.swing.JLabel ticketsInQueueLabel;
    private javax.swing.JLabel ticketsResolvedLabel;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration                   
}