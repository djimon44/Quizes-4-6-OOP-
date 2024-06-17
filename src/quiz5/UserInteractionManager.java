package quiz5;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserInteractionManager {
    private CommunicationManager communicationManager;
    private List<String> chatHistory;

    public UserInteractionManager(CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        this.chatHistory = new ArrayList<>();
    }

    public void addMessageToHistory(String sender, String message) {
        chatHistory.add(sender + ": " + message);
    }

    public JSONObject getChatHistoryAsJson() {
        JSONArray jsonArray = new JSONArray(chatHistory);
        return new JSONObject().put("chatHistory", jsonArray);
    }

    public void sendUserMessage(String message) {
        addMessageToHistory("User", message);
        JSONObject requestData = getChatHistoryAsJson();
        JSONObject response = communicationManager.sendData(requestData);
        if (response != null) {
            String chatbotResponse = response.getString("message");
            addMessageToHistory("Chatbot", chatbotResponse);
            System.out.println("Chatbot: " + chatbotResponse);
        } else {
            System.out.println("Chatbot: Error in communication with the server.");
        }
    }
}
