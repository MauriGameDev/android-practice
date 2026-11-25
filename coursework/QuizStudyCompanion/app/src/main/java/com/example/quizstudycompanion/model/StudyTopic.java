
package com.example.quizstudycompanion.model;

public class StudyTopic {

    // Store the study topic name and file name
    private final String topicName;
    private final String fileName;

    // Constructor
    public StudyTopic(String topicName, String fileName) {
        this.topicName = topicName;
        this.fileName = fileName;
    }

    // Return the topic name
    public String getTopicName() {
        return topicName;
    }

    // Return the file name
    public String getFileName() {
        return fileName;
    }
}
