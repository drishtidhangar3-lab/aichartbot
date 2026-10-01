import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class AIChatbot extends JFrame {

    // ---------------------------------------------------------
    // FAQ / Training Data
    // ---------------------------------------------------------
    private final Map<String, List<String>> trainingData = new LinkedHashMap<>();

    // GUI components
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton clearButton;

    public AIChatbot() {
        initializeTrainingData();
        createGUI();
    }

    // ---------------------------------------------------------
    // MAIN METHOD
    // ---------------------------------------------------------
    public static void main(String[] args) {

        // Use Swing's Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            AIChatbot chatbot = new AIChatbot();
            chatbot.setVisible(true);
        });
    }

    // ---------------------------------------------------------
    // TRAINING DATA
    // ---------------------------------------------------------
    private void initializeTrainingData() {

        trainingData.put("greeting", Arrays.asList(
                "hello",
                "hi",
                "hey",
                "good morning",
                "good afternoon",
                "good evening",
                "namaste"
        ));

        trainingData.put("name", Arrays.asList(
                "what is your name",
                "your name",
                "who are you",
                "tell me your name"
        ));

        trainingData.put("how_are_you", Arrays.asList(
                "how are you",
                "how are you doing",
                "are you fine",
                "are you okay"
        ));

        trainingData.put("java", Arrays.asList(
                "what is java",
                "tell me about java",
                "java programming",
                "java language"
        ));

        trainingData.put("ai", Arrays.asList(
                "what is artificial intelligence",
                "what is ai",
                "tell me about ai",
                "define artificial intelligence"
        ));

        trainingData.put("nlp", Arrays.asList(
                "what is nlp",
                "what is natural language processing",
                "tell me about nlp",
                "natural language processing"
        ));

        trainingData.put("machine_learning", Arrays.asList(
                "what is machine learning",
                "define machine learning",
                "tell me about machine learning",
                "machine learning"
        ));

        trainingData.put("chatbot", Arrays.asList(
                "what is a chatbot",
                "define chatbot",
                "what is chatbot",
                "tell me about chatbots"
        ));

        trainingData.put("college", Arrays.asList(
                "what is college",
                "tell me about college",
                "college information"
        ));

        trainingData.put("help", Arrays.asList(
                "help",
                "what can you do",
                "commands",
                "features",
                "how can you help me"
        ));

        trainingData.put("time", Arrays.asList(
                "what time is it",
                "tell me the time",
                "current time",
                "time"
        ));

        trainingData.put("thanks", Arrays.asList(
                "thank you",
                "thanks",
                "thank",
                "thanks a lot"
        ));

        trainingData.put("bye", Arrays.asList(
                "bye",
                "goodbye",
                "see you",
                "exit",
                "quit"
        ));
    }

    // ---------------------------------------------------------
    // GUI
    // ---------------------------------------------------------
    private void createGUI() {

        setTitle("AI Chatbot - Java NLP");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // -----------------------------------------------------
        // Heading
        // -----------------------------------------------------
        JLabel title = new JLabel("Artificial Intelligence Chatbot", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitle = new JLabel(
                "Java + NLP + Rule-Based AI",
                SwingConstants.CENTER
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(title);
        headerPanel.add(subtitle);

        // -----------------------------------------------------
        // Chat Area
        // -----------------------------------------------------
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 15));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatArea);

        // -----------------------------------------------------
        // Input Area
        // -----------------------------------------------------
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 15));

        sendButton = new JButton("Send");
        clearButton = new JButton("Clear");

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(sendButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // -----------------------------------------------------
        // Add components
        // -----------------------------------------------------
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // -----------------------------------------------------
        // Button Actions
        // -----------------------------------------------------
        sendButton.addActionListener(e -> processUserInput());

        inputField.addActionListener(e -> processUserInput());

        clearButton.addActionListener(e -> {
            chatArea.setText("");
            showWelcomeMessage();
        });

        showWelcomeMessage();
    }

    // ---------------------------------------------------------
    // WELCOME MESSAGE
    // ---------------------------------------------------------
    private void showWelcomeMessage() {

        chatArea.append("AI Bot: Hello! Welcome to the Java AI Chatbot.\n");
        chatArea.append("AI Bot: You can ask me about Java, AI, NLP, "
                + "Machine Learning, Chatbots and more.\n");
        chatArea.append("AI Bot: Type 'help' to see what I can do.\n\n");
    }

    // ---------------------------------------------------------
    // PROCESS USER INPUT
    // ---------------------------------------------------------
    private void processUserInput() {

        String userInput = inputField.getText().trim();

        if (userInput.isEmpty()) {
            return;
        }

        chatArea.append("You: " + userInput + "\n");

        String response = generateResponse(userInput);

        chatArea.append("AI Bot: " + response + "\n\n");

        inputField.setText("");

        // Automatically scroll to bottom
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    // ---------------------------------------------------------
    // NLP + RESPONSE GENERATION
    // ---------------------------------------------------------
    private String generateResponse(String input) {

        // NLP preprocessing
        String cleanedInput = preprocessText(input);

        // Detect intent
        String intent = detectIntent(cleanedInput);

        // Generate response
        return getResponse(intent);
    }

    // ---------------------------------------------------------
    // NLP PREPROCESSING
    // ---------------------------------------------------------
    private String preprocessText(String text) {

        // Convert to lowercase
        text = text.toLowerCase();

        // Remove punctuation
        text = text.replaceAll("[^a-zA-Z0-9\\s]", "");

        // Remove extra spaces
        text = text.replaceAll("\\s+", " ").trim();

        return text;
    }

    // ---------------------------------------------------------
    // TOKENIZATION
    // ---------------------------------------------------------
    private List<String> tokenize(String text) {

        if (text.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.asList(text.split("\\s+"));
    }

    // ---------------------------------------------------------
    // INTENT DETECTION
    // ---------------------------------------------------------
    private String detectIntent(String input) {

        List<String> inputTokens = tokenize(input);

        String bestIntent = "unknown";
        int highestScore = 0;

        for (Map.Entry<String, List<String>> entry : trainingData.entrySet()) {

            String intent = entry.getKey();

            for (String example : entry.getValue()) {

                String processedExample = preprocessText(example);

                int score = calculateSimilarity(
                        inputTokens,
                        tokenize(processedExample)
                );

                if (score > highestScore) {
                    highestScore = score;
                    bestIntent = intent;
                }
            }
        }

        /*
         * Minimum matching threshold.
         * If there is not enough similarity, return unknown.
         */
        if (highestScore < 1) {
            return "unknown";
        }

        return bestIntent;
    }

    // ---------------------------------------------------------
    // SIMPLE NLP SIMILARITY
    // ---------------------------------------------------------
    private int calculateSimilarity(
            List<String> inputTokens,
            List<String> exampleTokens) {

        int score = 0;

        for (String inputWord : inputTokens) {

            for (String exampleWord : exampleTokens) {

                // Exact word match
                if (inputWord.equals(exampleWord)) {
                    score += 2;
                }

                // Partial word match
                else if (inputWord.length() > 3 &&
                        exampleWord.length() > 3 &&
                        (inputWord.contains(exampleWord)
                                || exampleWord.contains(inputWord))) {

                    score++;
                }
            }
        }

        return score;
    }

    // ---------------------------------------------------------
    // RESPONSE DATABASE
    // ---------------------------------------------------------
    private String getResponse(String intent) {

        switch (intent) {

            case "greeting":
                return getRandomResponse(
                        "Hello! How can I help you?",
                        "Hi! Nice to meet you.",
                        "Hey! What would you like to know?"
                );

            case "name":
                return "My name is Java AI Bot. I am a rule-based chatbot created using Java and NLP techniques.";

            case "how_are_you":
                return getRandomResponse(
                        "I'm doing great! Thanks for asking.",
                        "I'm fine and ready to chat!",
                        "I'm functioning perfectly."
                );

            case "java":
                return "Java is a high-level, object-oriented programming language developed by Sun Microsystems. It is widely used for desktop, web, mobile and enterprise applications.";

            case "ai":
                return "Artificial Intelligence (AI) is a field of computer science that enables machines to perform tasks that normally require human intelligence, such as understanding language, recognizing patterns and making decisions.";

            case "nlp":
                return "Natural Language Processing (NLP) is a branch of AI that enables computers to understand, process and generate human language.";

            case "machine_learning":
                return "Machine Learning is a branch of AI in which computers learn patterns from data and use those patterns to make predictions or decisions.";

            case "chatbot":
                return "A chatbot is a software application designed to communicate with users through text or speech. This chatbot uses Java, NLP preprocessing and rule-based intent detection.";

            case "college":
                return "A college is an educational institution where students study academic or professional subjects and develop knowledge and skills.";

            case "help":
                return "I can answer questions about Java, Artificial Intelligence, NLP, Machine Learning and Chatbots. You can also ask me for the current time.";

            case "time":
                return "The current time is " + LocalTime.now().withNano(0) + ".";

            case "thanks":
                return getRandomResponse(
                        "You're welcome!",
                        "My pleasure!",
                        "Happy to help!"
                );

            case "bye":
                return "Goodbye! Have a great day.";

            default:
                return "Sorry, I don't understand that yet. Try asking about Java, AI, NLP, Machine Learning or Chatbots.";
        }
    }

    // ---------------------------------------------------------
    // RANDOM RESPONSE
    // ---------------------------------------------------------
    private String getRandomResponse(String... responses) {

        Random random = new Random();

        return responses[random.nextInt(responses.length)];
    }
}